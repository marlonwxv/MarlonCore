package io.github.marlonwxv.core.input;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ChatInputManager implements Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, PendingInput> inputs = new ConcurrentHashMap<>();

    public ChatInputManager(JavaPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
    }

    public void request(Player player, Consumer<String> callback) {
        request(player, null, callback, null);
    }

    public void request(Player player, Duration timeout, Consumer<String> callback, Runnable timeoutAction) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(callback, "callback");

        UUID playerId = player.getUniqueId();
        PendingInput pending = new PendingInput(callback, timeoutAction);
        PendingInput previous = inputs.put(playerId, pending);
        if (previous != null) {
            previous.cancelTimeout();
        }

        if (timeout == null || timeout.isZero() || timeout.isNegative()) {
            return;
        }

        long ticks = Math.max(1L, (timeout.toMillis() + 49L) / 50L);
        ScheduledTask task = player.getScheduler().runDelayed(
                plugin,
                scheduledTask -> {
                    if (inputs.remove(playerId, pending)) {
                        pending.runTimeout();
                    }
                },
                () -> inputs.remove(playerId, pending),
                ticks
        );
        pending.setTimeoutTask(task);
        if (task != null && inputs.get(playerId) != pending) {
            task.cancel();
        }
    }

    public boolean cancel(Player player) {
        PendingInput pending = inputs.remove(player.getUniqueId());
        if (pending == null) {
            return false;
        }
        pending.cancelTimeout();
        return true;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        PendingInput pending = inputs.remove(player.getUniqueId());
        if (pending == null) {
            return;
        }

        event.setCancelled(true);
        pending.cancelTimeout();

        String message = PlainTextComponentSerializer.plainText()
                .serialize(event.message())
                .trim();

        player.getScheduler().run(
                plugin,
                task -> pending.accept(message),
                null
        );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        cancel(event.getPlayer());
    }

    public void clear() {
        inputs.values().forEach(PendingInput::cancelTimeout);
        inputs.clear();
    }

    private static final class PendingInput {
        private final Consumer<String> callback;
        private final Runnable timeoutAction;
        private volatile ScheduledTask timeoutTask;

        private PendingInput(Consumer<String> callback, Runnable timeoutAction) {
            this.callback = callback;
            this.timeoutAction = timeoutAction;
        }

        private void accept(String input) {
            callback.accept(input);
        }

        private void runTimeout() {
            if (timeoutAction != null) {
                timeoutAction.run();
            }
        }

        private void setTimeoutTask(ScheduledTask timeoutTask) {
            this.timeoutTask = timeoutTask;
        }

        private void cancelTimeout() {
            ScheduledTask current = timeoutTask;
            if (current != null) {
                current.cancel();
            }
        }
    }
}