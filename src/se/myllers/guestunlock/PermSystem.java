/*
 * GuestUnlock - a bukkit plugin
 * Copyright (C) 2013 Mylleranton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package se.myllers.guestunlock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PermSystem {

	public static final String getPermissionName() {
		return Main.perms.getName();
	}

	public static final void setGroup(final Player player) {
		Main.DEBUG("Setting players group, Vault");

		if (Main.config.getBoolean("PermissionSystem.Vault.UseSpecificWorld")) {
			final String groupName = Main.perms.getPrimaryGroup(player);
			if (Main.perms.playerAddGroup(player.getWorld().getName(), player.getName(), Main.config.getString("PermissionSystem.Vault.BuildGroup"))) {
				
				if(Main.config.getBoolean("PermissionSystem.Vault.RemoveOldGroup")) {
					Main.perms.playerRemoveGroup(player.getWorld().getName(), player.getName(), groupName);
				}
				player.sendMessage(ChatColor.YELLOW + "You have been moved to the build group!");
				Main.INFO("Set " + player.getName() + "s group to the one specifyed in config.yml");
				onGroupChange(player);
			}
			else {
				Main.SEVERE("Failed to set " + player.getName() + "s group to the buildgroup");
			}
		}
		else {
			final String groupName = Main.perms.getPrimaryGroup(player);
			if (Main.perms.playerAddGroup((String) null, player.getName(), Main.config.getString("PermissionSystem.Vault.BuildGroup"))) {
				if(Main.config.getBoolean("PermissionSystem.Vault.RemoveOldGroup")) {
					Main.perms.playerRemoveGroup(player.getWorld().getName(), player.getName(), groupName);
				}
				player.sendMessage(ChatColor.YELLOW + "You have been moved to the build group!");
				Main.INFO("Set " + player.getName() + "s group to the one specifyed in config.yml [NON WORLD SPECIFIC]");
				onGroupChange(player);
			}
			else {
				Main.SEVERE("Failed to set " + player.getName() + "s group to the buildgroup");
			}
		}

	}

	/**
	 * Sends messages to moderators that the player p has been moved to another
	 * group
	 * 
	 * @param p
	 *            - The player that is being moved
	 */
	private static final void onGroupChange(final Player p) {
		for (final Player x : Bukkit.getServer().getOnlinePlayers()) {
			if (Permission.isModerator(x)) {
				x.sendMessage(ChatColor.YELLOW + x.getName() + " was moved to the build-group!");
			}
		}
	}
}
