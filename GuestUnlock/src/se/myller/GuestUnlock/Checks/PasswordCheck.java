package se.myller.GuestUnlock.Checks;

import java.util.logging.Level;

import se.myller.GuestUnlock.Main;

public class PasswordCheck {

	
	private Main plugin;
	public PasswordCheck(Main instance) {
		plugin = instance;
	}
	
	public void checkPassword() {
		plugin.config.getString("Admin.Password").toString();
		if (plugin.config.isString("Admin.Password")) {
			plugin.isPasswordOK = true;
			plugin.log("Password OK", true, Level.INFO);
			return;
		} else {
			plugin.config.set("Admin.Password", "GuestUnlock");
			plugin.saveConfig();
			plugin.isPasswordOK = false;
			plugin.log("Password is NOT OK!", true, Level.WARNING);
			plugin.log("-------------------------------------------------", true,Level.INFO);
			plugin.log("Password MUST be a STRING!", true, Level.WARNING);
			plugin.log("Password set to default value: 'GuestUnlock'", true,Level.WARNING);
			plugin.log("-------------------------------------------------", true,Level.INFO);
			return;
		}
	}
}
