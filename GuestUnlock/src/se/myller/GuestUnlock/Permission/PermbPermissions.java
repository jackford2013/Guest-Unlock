package se.myller.GuestUnlock.Permission;

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
			Plugin BP = plugin.pm.getPlugin("bPermissions");
			if (BP != null) {
				if (BP.getClass().getName().equals("de.bananaco.bpermissions.imp.Permissions")) {
					plugin.log.info("[GuestUnlock] =====   Found bPermissions!");
				} else {
					plugin.log.severe("[GuestUnlock] -----------------------------------------");
					plugin.log.severe("[GuestUnlock] =====   You wanted bPermissions support!");
					plugin.log.severe("[GuestUnlock] =====   I cant find it!");
					plugin.log.severe("[GuestUnlock] =====   Could not find bPermissions.");
					plugin.log.severe("[GuestUnlock] -----------------------------------------");
					return;
					}
			} else {
				plugin.log.severe("[GuestUnlock] -----------------------------------------");
				plugin.log.severe("[GuestUnlock] =====   You wanted bPermissions support!");
				plugin.log.severe("[GuestUnlock] =====   I cant find it!");
				plugin.log.severe("[GuestUnlock] =====   Could not find bPermissions.");
				plugin.log.severe("[GuestUnlock] -----------------------------------------");
				return;
			}
	}
	public void setGroupBP(Player p) {
		if (ApiLayer.hasGroup(p.getWorld().getName(), CalculableType.USER, p.getName(), plugin.config.getString("Permissions.bPermissions.Group.Default"))) {
			ApiLayer.setGroup(p.getWorld().getName(), CalculableType.USER, p.getName(), plugin.config.getString("Permissions.bPermissions.Group.Build"));
			plugin.log.info("[GuestUnlock] Set " + p.getName() + ":s group to " + plugin.config.getString("Permissions.bPermissions.Group.Build"));
			p.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + "Your group is now " + plugin.config.getString("Permissions.bPermissions.Group.Build"));
		} else {
			p.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.RED +  "Haha, tricky one, you doesnt belong to the default group!");
		}
	}
	
}
