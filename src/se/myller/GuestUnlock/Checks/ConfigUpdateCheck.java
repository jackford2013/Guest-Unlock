package se.myller.GuestUnlock.Checks;

import java.util.logging.Level;

import se.myller.GuestUnlock.Main;

public class ConfigUpdateCheck {

	private int newestConfigVersion = 4;
	private Main plugin;

	public ConfigUpdateCheck(Main instance) {
		plugin = instance;
	}

	public void checkConfigVersion() {
		plugin.log("DEBUG: Checking config-version", true, Level.INFO);
		int currentConfigVersion = plugin.config
				.getInt("Configuration-Version");
		if (currentConfigVersion < newestConfigVersion) {
			plugin.log(
					"There is a new config-version available, you are currently using v"
							+ currentConfigVersion + ", the latest is v"
							+ newestConfigVersion, false, Level.INFO);
			plugin.isNewConfigAvailable = true;
		}
	}

}
