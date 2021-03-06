package me.InfoPaste.MobLoot.objects;

import me.InfoPaste.MobLoot.core.Config;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static me.InfoPaste.MobLoot.Main.plugin;

public class Logger {

    public static Logger logger;

    private boolean enabled;

    private File file;

    public Logger(String file) {
        enabled = Config.config.getBoolean("Logger");
        if (enabled) {
            this.file = new File(plugin.getDataFolder() + "/logs/", file);
            createFile();
        }
    }

    public static void loadLogger() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        logger = new Logger(df.format(date) + ".txt");
    }

    public void drop(String player, String mob, List<ItemStack> itemList, double money) {
        if (enabled) {

            StringBuilder stringBuilder = new StringBuilder();
            for (ItemStack item : itemList) {

                String name = item.getType().name().toLowerCase();
                if (item.hasItemMeta()) {
                    name = item.getItemMeta().getDisplayName();
                }

                stringBuilder.append(name).append(", ");
            }

            String items = stringBuilder.toString().substring(0, stringBuilder.length() - 2);

            String log = "%player% killed a %mob% and earned %items% and earned %money%"
                    .replaceAll("%player%", player)
                    .replaceAll("%mob%", mob)
                    .replaceAll("%items%", items)
                    .replaceAll("%money%", String.valueOf(money));

            writeString(log);
        }
    }

    public void createFile() {
        if (!file.exists()) {
            try {
                plugin.getLogger().info("Creating new log file...");
                File dataFolder = new File(plugin.getDataFolder() + "/logs/");
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }

                file.createNewFile();

                writeString(" ***** Log file created ***** ");
                writeString("Format = [YEAR-MONTH-DAY HOURS/MINUTES/SECONDS]");

            } catch (IOException e) {
                plugin.getLogger().info("Could not create a log file");
                e.printStackTrace();
            }
        }
    }

    public void writeString(String string) {
        if (enabled) {
            try {
                FileWriter fw = new FileWriter(file, true);
                PrintWriter pw = new PrintWriter(fw);

                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String prefix = "[" + df.format(date) + "] ";
                pw.println(prefix + string);

                pw.flush();
                pw.close();

            } catch (IOException e) {
                plugin.getLogger().info("Could not write to transaction file");
            }
        }
    }
}
