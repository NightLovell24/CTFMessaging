package ctf.n0rth.config;


import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CTFConfig {
    private static final String folderName = "\\configs\\";
    private static final String fileGUIName = "ctf_gui.yml";
    private static final String fileServersName = "ctf_servers.yml";
    private static final File pluginFolder = Bukkit.getServer().getPluginManager()
            .getPlugin("CTFMessaging").getDataFolder();

    private static File fileGUI;
    private static File fileServers;
    private static FileConfiguration fileConfigurationGUI;
    private static FileConfiguration fileConfigurationServers;

    public static void setup() {
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }
        File subFolder = new File(pluginFolder + folderName);
        if (!subFolder.exists()) {
            subFolder.mkdir();
        }
        String fullPathGUI = pluginFolder + folderName + fileGUIName;
        fileGUI = new File(fullPathGUI);
        if (!fileGUI.exists()) {
            try {
                fileGUI.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fileConfigurationGUI = YamlConfiguration.loadConfiguration(fileGUI);
/////////////////////////////////////////////////////////////////////////////////
        String fullPathServers = pluginFolder + folderName + fileServersName;
        fileServers = new File(fullPathServers);
        if (!fileServers.exists()) {
            try {
                fileServers.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        fileConfigurationServers = YamlConfiguration.loadConfiguration(fileServers);


        CTFGUIConfigHelper.configure();

    }

    public static String getParameterGUI(String parameter) {
        return fileConfigurationGUI.getString(parameter);
    }

    public static ConfigurationSection configurationSection(String parameter) {
        return fileConfigurationServers.getConfigurationSection(parameter);
    }

    public static void setDefaultParameterGUI(String path, Object value) {
        fileConfigurationGUI.addDefault(path, value);

    }

    public static void setDefaultParameterServers(String path, Object value) {
        fileConfigurationServers.addDefault(path, value);

    }

    public static FileConfiguration getFileConfigurationGUI() {
        return fileConfigurationGUI;
    }

    public static FileConfiguration getFileConfigurationServers() {
        return fileConfigurationServers;
    }

    public static void save() {
        try {
            fileConfigurationGUI.save(fileGUI);
            fileConfigurationServers.save(fileServers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CTFConfig() {

    }
}
