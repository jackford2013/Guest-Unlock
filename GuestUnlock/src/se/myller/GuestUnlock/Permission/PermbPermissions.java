package se.myller.GuestUnlock.Permission;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import se.myller.GuestUnlock.Main;

public class PermbPermissions {

	private Main plugin;
	public PermbPermissions(Main instance) {
		plugin = instance;
	}
	
	/*
	 * 
	 * bPermissions
	 * 
	 */
	public void getBP() {
			Plugin BP = plugin.pluginManager.getPlugin("bPermissions");
			if (BP != null) {
				if (BP.getClass().getName().equals("de.bananaco.bpermissions.imp.Permissions")) {
					plugin.log("=====   Found bPermissions, I will try to enable a hook!", false, Level.INFO);
				} else {
					plugin.log("-----------------------------------------", true, Level.INFO);
					plugin.log("=====   You wanted bPermissions support, could not find it.", false, Level.INFO);
					plugin.log("=====   I cant find it!", true, Level.INFO);
					plugin.log("=====   Could not find bPermissions.", true, Level.INFO);
					plugin.log("-----------------------------------------", true, Level.INFO);
					return;
					}
			} else {
				plugin.log(" -----------------------------------------", true, Level.INFO);
				plugin.log(" =====   You wanted bPermissions support, could not find it.", false, Level.INFO);
				plugin.log(" =====   I cant find it!", true, Level.INFO);
				plugin.log(" =====   Could not find bPermissions.", true, Level.INFO);
				plugin.log(" -----------------------------------------", true, Level.INFO);
				return;
			}
	}
	public void setGroupBP(Player p) {
		if (ApiLayer.hasGroup(p.getWorld().getName(), CalculableType.USER, p.getName(), plugin.config.getString("Permissions.bPermissions.Group.Default"))) {
			ApiLayer.setGroup(p.getWorld().getName(), CalculableType.USER, p.getName(), plugin.config.getString("Permissions.bPermissions.Group.Build"));
			plugin.log("Set " + p.getName() + ":s group to " + plugin.config.getString("Permissions.bPermissions.Group.Build"), true, Level.INFO);
			p.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + "Your group is now " + plugin.config.getString("Permissions.bPermissions.Group.Build"));
			if (plugin.config.getBoolean("Permissions.SendMessageOnGroupChange") == true ) {
				Player[] players = plugin.getServer().getOnlinePlayers();
				for (Player player : players) {
					if (player.hasPermission("GuestUnlock.moderator")) {
						player.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + "The player: " + ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " was moved to the build-group.");
					}
				}
			}
		} else {
			p.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.RED +  "Haha, tricky one, you doesnt belong to the default group!");
		}
	}
	
}
