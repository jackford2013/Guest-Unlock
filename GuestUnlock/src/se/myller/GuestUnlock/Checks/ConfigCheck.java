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
		plugin.log("DEBUG: Checking config", true, Level.INFO);
		conf = plugin.getConfig();
		Set<String> allConfNodes = conf.getKeys(true);
		for (String s : allConfNodes) {
		    if (s.contains(".")) {
			if (conf.isString(s)) {
				numberString++;
			} else if (conf.isBoolean(s)) {
				numberBool++;
			} else if (conf.isInt(s)) {
				numberInt++;
			}
		    }
		}
		plugin.logger.info("" + numberString + " " + numberBool + " " + numberInt);
		if (numberString != 11) {
			plugin.log("ERROR IN CONFIG, there is an error with a String, please check and reload.", false, Level.SEVERE);
			return false;
		} else {
			plugin.log("Your config-strings are fine!", true, Level.INFO);
			numberString = 0;
		}
		if (numberBool != 8) {
			plugin.log("ERROR IN CONFIG, there is an error with a Boolean, please check and reload.", false, Level.SEVERE);
			return false;
		} else {
			plugin.log("Your config-booleans are fine!", true, Level.INFO);
			numberBool = 0;
		}
		if (numberInt != 1) {
			plugin.log("ERROR IN CONFIG, there is an error with an Integer, please check and reload.", false, Level.SEVERE);
			return false;
		} else {
			plugin.log("Your config-integers are fine!", true, Level.INFO);
			numberInt = 0;
		}
		return false;
	}
	
}
