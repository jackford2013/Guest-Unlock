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
