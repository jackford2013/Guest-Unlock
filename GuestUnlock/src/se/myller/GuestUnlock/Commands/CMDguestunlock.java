package se.myller.GuestUnlock.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import se.myller.GuestUnlock.Main;


public class CMDguestunlock implements Command{

	
	private Main plugin;
	
	public CMDguestunlock(Main instance) {
    	plugin = instance;
    }
	
	/*
	 * 
	 * Handle our commands
	 * 
	 */
	public boolean cmdSend(CommandSender s, String pwd) {
		s.sendMessage(ChatColor.GREEN + plugin.config.getString("Guest.Responds.Correct"));
		plugin.log.info("[GuestUnlock] " + s.getName() + " has sent the correct password!");
		Player[] players = plugin.getServer().getOnlinePlayers();
		for (Player p: players) {
			if (p.hasPermission("GuestUnlock.moderator")) {
				p.sendMessage(ChatColor.BLUE + "The player " + ChatColor.YELLOW + s.getName() + ChatColor.BLUE + " sent the correct password, " + ChatColor.GREEN + pwd);			
			}
		}
		if (plugin.config.getBoolean("Permissions.PermissionsEx.Enable") == true ) {
			Player player = (Player)s; 
			plugin.ppe.setGroupPEX(player);
		}
		if (plugin.config.getBoolean("Permissions.GroupManager.Enable") == true) {
			Player player = (Player)s; 
			plugin.pgm.setGroupGM(player);
		}
		if (plugin.config.getBoolean("Permissions.bPermissions.Enable") == true) {
			Player player = (Player)s; 
			plugin.pbp.setGroupBP(player);
		}
		return true;
	}
	public boolean cmdFail(CommandSender s, String pwd) {
		s.sendMessage(ChatColor.RED + plugin.config.getString("Guest.Responds.Incorrect"));
		if(plugin.config.getBoolean("Admin.SendIncorrectMessageToMods") == true) {
			plugin.log.warning("[GuestUnlock] The player " + s.getName() + " tried to send this password: " + pwd);
			Player[] players = plugin.getServer().getOnlinePlayers();
			for (Player player: players) {
				if(player.hasPermission("GuestUnlock.moderator")) {
					player.sendMessage(ChatColor.RED + "The player " + ChatColor.YELLOW + s.getName() + ChatColor.RED + " tried to send this password: " + ChatColor.DARK_RED + pwd);	
				}
			}
			return true;		
		}
		return true;
	}

	@Override
	public void onCommandFail(Player p) {
		p.sendMessage(ChatColor.RED + "Invalid arguments/syntax, try '/<command> help'.");
		return;
	}
	@Override
	public void onCommandHelp(Player p) {
		p.sendMessage(ChatColor.BLUE + "Usage: /<command> <password>");
		return;
	}
	
}
