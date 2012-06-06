package se.myller.GuestUnlock;

import java.io.File;
import java.util.logging.Level;

public class DataHandler {

	private Main plugin;
	public boolean directoryExixts = false;
	public boolean configFileExists = false;

	public DataHandler(Main instance) {
		plugin = instance;
	}

	/*
	 * 
	 * Make our config + directory
	 */
	public boolean createDataDirectory() {
		plugin.log("DEBUG: Trying to create directory", true, Level.INFO);
		File file = plugin.getDataFolder();
		if (!file.isDirectory()) {
			if (!file.mkdirs()) {
				plugin.log("Failed to create directory", false, Level.SEVERE);
				plugin.log("Disabling Plugin.", false, Level.SEVERE);
				plugin.pluginManager.disablePlugin(plugin);
				return false;
			} else {
				plugin.log("Created directory sucessfully!", false, Level.INFO);
				directoryExixts = true;
				return true;
			}
		} else {
			plugin.log("Directory exixts", true, Level.INFO);
			directoryExixts = true;
		}
		return true;
	}

	public boolean createConfigFile() {
		plugin.log("DEBUG: Trying to create config.yml", true, Level.INFO);
		File configFile = new File("plugins/GuestUnlock/config.yml");
		if (!configFile.exists()) {
			try {
				plugin.saveDefaultConfig();
				configFileExists = true;
				plugin.log("Created configuration file", false, Level.INFO);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				plugin.log(e.getMessage(), false, Level.SEVERE);
				return false;
			}
		} else {
			plugin.log("Trying to load config.yml", true, Level.INFO);
		}
		return true;
	}

}
