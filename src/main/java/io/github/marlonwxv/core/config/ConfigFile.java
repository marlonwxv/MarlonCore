package io.github.marlonwxv.core.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigFile {

    private final JavaPlugin plugin;
    private final File file;
    private final String resourceName;
    private FileConfiguration config;

    public ConfigFile(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.resourceName = name;
        this.file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Could not create configuration directory: " + parent);
            }
            plugin.saveResource(name, false);
        }
        reload();
    }

    public FileConfiguration get() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(java.util.logging.Level.SEVERE,
                    "Could not save configuration file '" + file.getName() + "'.", e);
        }
    }

    public void reload() {
        YamlConfiguration loaded = YamlConfiguration.loadConfiguration(file);
        try (InputStream resource = plugin.getResource(resourceName)) {
            if (resource != null) {
                YamlConfiguration defaults = YamlConfiguration.loadConfiguration(
                        new InputStreamReader(resource, StandardCharsets.UTF_8)
                );
                loaded.setDefaults(defaults);
            }
        } catch (IOException exception) {
            plugin.getLogger().log(java.util.logging.Level.WARNING,
                    "Could not load bundled defaults for '" + resourceName + "'.", exception);
        }
        this.config = loaded;
    }
}