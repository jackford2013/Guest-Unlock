package se.myller.GuestUnlock.Permission;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import se.myller.GuestUnlock.Main;

public class PermPermissionsEx {

	private Main plugin;
	public PermPermissionsEx(Main instance) {
		plugin = instance;
	}
	
	/*
	 * 
	 * PermissionsEx
	 * 
	 */
	public void getPex() {
		Plugin pex = plugin.pluginManager.getPlugin("PermissionsEx");
		if (pex != null) {
			if (pex.getClass().getName().equals("ru.tehkode.permissions")) {
				plugin.log(" =====   Found PermissionEx!", true, Level.INFO);
			} else {
				plugin.log(" -----------------------------------------", true, Level.INFO);
				plugin.log(" =====   You wanted PermissionsEx support, could not find it.", false, Level.INFO);
				plugin.log(" =====   I cant find it!", true, Level.INFO);
				plugin.log(" =====   Could not find PermissionsEx.", true, Level.INFO);
				plugin.log(" -----------------------------------------", true, Level.INFO);
				return;
				}
		} else {
			plugin.log(" -----------------------------------------", true, Level.INFO);
			plugin.log(" =====   You wanted PermissionsEx support, could not find it.", false, Level.INFO);
			plugin.log(" =====   I cant find it!", true, Level.INFO);
			plugin.log(" =====   Could not find PermissionsEx.", true, Level.INFO);
			plugin.log(" -----------------------------------------", true, Level.INFO);
			return;
		}
	}
	public void setGroupPEX(Player player) {
		PermissionUser user  = PermissionsEx.getUser(player);
		String name = user.getName();
		PermissionGroup[] userGroups = user.getGroups();
		for (PermissionGroup pg: userGroups) {
			if (pg.toString().equals((plugin.config.getString("Permissions.PermissionsEx.Group.Default")))) {
				user.setGroups(new String[] { plugin.config.getString("Permissions.PermissionsEx.Group") });
				plugin.log("Set " + name + ":s group to " + plugin.config.getString("Permissions.PermissionsEx.Group"), true, Level.INFO);
				player.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + "Your group is now " + plugin.config.getString("Permissions.PermissionsEx.Group"));
				if (plugin.config.getBoolean("Permissions.SendMessageOnGroupChange") == true ) {
					Player[] players = plugin.getServer().getOnlinePlayers();
					for (Player p : players) {
						if (p.hasPermission("GuestUnlock.moderator")) {
							p.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + "The player: " + ChatColor.YELLOW + p.getName() + ChatColor.GREEN + " was moved to the build-group.");
						}
					}
				}
				return;
			} 
		}
			player.sendMessage(ChatColor.AQUA + "[GuestUnlock]" + ChatColor.RED + "Haha, tricky one, you doesnt belong to the default group!");
	}
	
}
