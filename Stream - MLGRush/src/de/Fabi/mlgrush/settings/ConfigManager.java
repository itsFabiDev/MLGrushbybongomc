package de.Fabi.mlgrush.settings;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private boolean useChatFormat;
    private int maxPoints;

    public void loadFile() {
        final File file = new File("plugins//MLGRush", "config.yml");
        if(!file.exists()) {
            final File folder = new File("plugins//MLGRush");
            if(!folder.exists()) folder.mkdirs();
            try {
                file.createNewFile();
                this.writeDefault();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeDefault() throws IOException {
        final File file = new File("plugins//MLGRush", "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        configuration.set("Settings.FormatChat", true);
        configuration.set("Settings.WinPoints", 3);
        configuration.save(file);
    }

    public void loadSettings() {
        final File file = new File("plugins//MLGRush", "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        setMaxPoints(configuration.getInt("Settings.WinPoints"));
        setUseChatFormat(configuration.getBoolean("Settings.FormatChat"));
    }


    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public boolean isUseChatFormat() {
        return useChatFormat;
    }

    public void setUseChatFormat(boolean useChatFormat) {
        this.useChatFormat = useChatFormat;
    }
}
