package se.myller.GuestUnlock.Listeners;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import se.myller.GuestUnlock.Main;

/**
 * GuestUnlock for Bukkit
 * 
 * @author Myller
 */
public class JoinListener implements Listener {

	private Main plugin;

	public JoinListener(Main instance) {
		plugin = instance;
	}

	/*
	 * 
	 * On player join event
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.log("DEBUG: Player " + player + " joined", true, Level.INFO);
		if (plugin.getConfig().getBoolean("Guest.Join.Enable")) {
			if (player.hasPermission("GuestUnlock.guest")
					&& !player.hasPermission("GuestUnlock.moderator")) {
				plugin.log("DEBUG: Sending GuestJoinMessage", true, Level.INFO);
				player.sendMessage(ChatColor.AQUA + "[GuestUnlock] "
						+ ChatColor.GREEN
						+ plugin.getConfig().getString("Guest.Join.Message"));
			}
		}
		if (player.hasPermission("GuestUnlock.moderator")
				&& (plugin.hasNewVersion)) {
			player.sendMessage(ChatColor.YELLOW
					+ "New version available for GuestUnlock: "
					+ plugin.updateCheck.newestVersion);
			player.sendMessage(ChatColor.YELLOW
					+ "Please check http://dev.bukkit.org/server-mods/GuestUnlock");
		}
		if (player.hasPermission("GuestUnlock.admin")
				&& (plugin.isNewConfigAvailable)) {
			player.sendMessage(ChatColor.YELLOW
					+ "New config-version is available for GuestUnlock!");
			player.sendMessage(ChatColor.YELLOW
					+ "Please regenerate your config.yml");
		}
	}
}
