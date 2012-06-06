package se.myller.GuestUnlock.Commands;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import se.myller.GuestUnlock.Main;

public class GuPassword implements Command {

	private Main plugin;

	public GuPassword(Main instance) {
		plugin = instance;
	}

	public boolean setPwd(CommandSender s, String newPwd) {
		plugin.log("DEBUG: " + s.getName() + " changed the password", true, Level.INFO);
		plugin.config.set("Admin.Password", newPwd);
		plugin.saveConfig();
		s.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.YELLOW
				+ "Password changed to: " + ChatColor.DARK_AQUA + newPwd);
		Player[] players = plugin.getServer().getOnlinePlayers();
		for (Player p : players) {
			if (p.hasPermission("GuestUnlock.admin")) {
				p.sendMessage(ChatColor.AQUA + "[GuestUnlock] "
						+ ChatColor.YELLOW + p.getName() + ChatColor.GREEN
						+ " has changed the password to: "
						+ ChatColor.DARK_AQUA + newPwd);
			}
		}
		return true;
	}

	@Override
	public void onCommandFail(Player p) {
		p.sendMessage(ChatColor.RED
				+ "Invalid arguments/syntax, try '/gupassword help'.");
		return;
	}

	@Override
	public void onCommandHelp(Player p) {
		p.sendMessage(ChatColor.BLUE + "Usage: /gupassword <new password>");
		p.sendMessage(ChatColor.BLUE
				+ "Example: '/gupassword hello' this would set the password to: hello");
		p.sendMessage(ChatColor.RED + "Aliases: /gadmin");
		return;
	}

}
