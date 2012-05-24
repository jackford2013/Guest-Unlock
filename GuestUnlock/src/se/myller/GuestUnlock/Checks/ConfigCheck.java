package se.myller.GuestUnlock.Checks;

import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import se.myller.GuestUnlock.Main;

public class ConfigCheck {
	
	public int numberBool = 0;
	public int numberInt = 0;
	public int numberString = 0;
	private FileConfiguration conf;
	private Main plugin;
	public ConfigCheck(Main instance) {
		plugin = instance;
	}
	public boolean checkConfig() {
		conf = plugin.getConfig();
		Set<String> allConfNodes = conf.getKeys(true);
		for (String s : allConfNodes) {
			if (conf.isString(s)) {
				numberString++;
			} else if (conf.isBoolean(s)) {
				numberBool++;
			} else if (conf.isInt(s)) {
				numberInt++;
			}
		}
		if (numberString != 10) {
			plugin.log("ERROR IN CONFIG, there is an error with a String, please check and reload.", false, Level.SEVERE);
			return false;
		} else {
			plugin.log("Your config is fine!", true, Level.INFO);
			numberString = 0;
		}
		if (numberBool != 8) {
			plugin.log("ERROR IN CONFIG, there is an error with a Boolean, please check and reload.", false, Level.SEVERE);
			return false;
		} else {
			plugin.log("Your config is fine!", true, Level.INFO);
			numberBool = 0;
		}
		if (numberInt != 2) {
			plugin.log("ERROR IN CONFIG, there is an error with an Integer, please check and reload.", false, Level.SEVERE);
			return false;
		} else {
			plugin.log("Your config is fine!", true, Level.INFO);
			numberInt = 0;
		}
		return false;
	}
	
}
