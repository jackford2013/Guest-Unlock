package se.myller.GuestUnlock.Commands;

import org.bukkit.command.CommandSender;
import se.myller.GuestUnlock.Main;

public class CMDgutest {

	private Main plugin;
	public CMDgutest(Main instance) {
		plugin = instance;
	}
	public boolean onCommand(CommandSender sender) {
		if (sender.hasPermission("GuestUnlock.admin")) {
			sender.sendMessage("Test!");
			sender.sendMessage("Password=");
			sender.sendMessage(plugin.config.getString("Admin.Password"));
			sender.sendMessage("The test was completed sucessfully!");
			sender.sendMessage("Good luck, /Myller!");
			return true;
		}
		return true;
	}
}
