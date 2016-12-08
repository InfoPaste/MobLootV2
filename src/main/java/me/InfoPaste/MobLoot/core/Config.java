package me.InfoPaste.MobLoot.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static me.InfoPaste.MobLoot.Main.plugin;

public class Config {

    public static FileConfiguration config;
    static File cfile;

    public static FileConfiguration data;
    static File dfile;

    public static void setup() {
        cfile = new File(plugin.getDataFolder(), "config.yml");
        dfile = new File(plugin.getDataFolder(), "data.yml");

        if (!cfile.exists()) {
            cfile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        if (!dfile.exists()) {
            dfile.getParentFile().mkdirs();
            plugin.saveResource("data.yml", false);
        }

        config = new YamlConfiguration();
        data = new YamlConfiguration();

        try {
            config.load(cfile);
            data.load(dfile);

            config = YamlConfiguration.loadConfiguration(cfile);
            data = YamlConfiguration.loadConfiguration(dfile);

            config.options().copyDefaults(true);
            data.options().copyDefaults(true);

            plugin.saveDefaultConfig();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        try {
            data.save(dfile);
        } catch (IOException e) {
            System.out.print("Error");
        }
    }

    public static void saveConfig() {
        try {
            config.save(cfile);
        } catch (IOException ignored) {

        }
    }

    public static void reloadConfigFile() {

        if (config == null) {
            cfile = new File(plugin.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(cfile);

        try {
            // Look for defaults inside the jar
            InputStream defConfigStream = plugin.getResource("config.yml");

            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                config.setDefaults(defConfig);
            }
        } catch (NullPointerException ignored) {

        }
    }

    public static void reloadDataFile() {

        if (data == null) {
            dfile = new File(plugin.getDataFolder(), "config.yml");
        }
        data = YamlConfiguration.loadConfiguration(dfile);

        try {
            // Look for defaults inside the jar
            InputStream defConfigStream = plugin.getResource("data.yml");

            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                data.setDefaults(defConfig);
            }
        } catch (NullPointerException ignored) {

        }
    }

    public static void reloadAll() {
        reloadConfigFile();
        reloadDataFile();
    }
}
