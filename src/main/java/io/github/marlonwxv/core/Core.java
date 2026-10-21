package io.github.marlonwxv.core;

import io.github.marlonwxv.core.gui.GUIManager;
import io.github.marlonwxv.core.input.ChatInputManager;
import io.github.marlonwxv.core.scheduler.Scheduler;

public final class Core {

    private final Scheduler scheduler;
    private final ChatInputManager chatInputManager;

    private Core(MarlonPlugin plugin) {
        this.scheduler = new Scheduler(plugin);
        this.chatInputManager = new ChatInputManager(plugin);

        plugin.getServer().getPluginManager().registerEvents(new GUIManager(), plugin);
        plugin.getServer().getPluginManager().registerEvents(chatInputManager, plugin);
    }

    public static Core init(MarlonPlugin plugin) {
        return new Core(plugin);
    }

    public Scheduler scheduler() {
        return scheduler;
    }

    public ChatInputManager chatInputManager() {
        return chatInputManager;
    }

    public void shutdown() {
        chatInputManager.clear();
    }
}