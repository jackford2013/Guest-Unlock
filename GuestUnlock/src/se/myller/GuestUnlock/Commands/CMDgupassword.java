package se.myller.GuestUnlock.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import se.myller.GuestUnlock.Main;


public class CMDgupassword implements Command {

	private Main plugin;
	
	public CMDgupassword(Main instance) {
    	plugin = instance;
    }
	public boolean setPwd(CommandSender s, String newPwd) {
		plugin.config.set("Admin.Password", newPwd);
		plugin.saveConfig();
		s.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + "Password changed to: " + ChatColor.RED + newPwd);
		return true;
	}
	@Override
	public void onCommandFail(Player p) {
		p.sendMessage(ChatColor.RED + "Invalid arguments/syntax, try '/<command> help'.");
		return;
	}
	@Override
	public void onCommandHelp(Player p) {
		p.sendMessage(ChatColor.BLUE + "Usage: /<command> <new password>");
		return;
	}
		
}
