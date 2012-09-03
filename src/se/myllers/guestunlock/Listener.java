package se.myllers.guestunlock;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener implements org.bukkit.event.Listener {

	/**
	 *  The latest version of the plugin. Set on plugin start.
	 */
	public static String pluginVersion;
	
	/**
	 * Called when a player joins the game.
	 * <p />
	 * Checks if the player is a guest, and in that case sends him a
	 * welcome-message
	 * 
	 * @param e
	 *            - The PlayerJoinEvent
	 */
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		if (Permissions.isGuest(e.getPlayer())
				&& !Permissions.isModerator(e.getPlayer())) {
			e.getPlayer().sendMessage(
					ChatColor.GREEN
							+ formatJoinMessage(
									Main.config.getString("Guest.JoinMessage"),
									e.getPlayer().getName()));
		}
		if (Permissions.isModerator(e.getPlayer())) {
			if(!pluginVersion.equals(UpdateCheck.newestVersion)) {
				e.getPlayer().sendMessage(ChatColor.YELLOW
						+ "New version available for GuestUnlock: "
						+ UpdateCheck.newestVersion);
				e.getPlayer().sendMessage(ChatColor.YELLOW
						+ "Please check http://dev.bukkit.org/server-mods/GuestUnlock");
			}
		}
	}
	
	/**
	 * Formats a string
	 * 
	 * @param in - The String to be formatted
	 * @param playerName - The players name
	 * @return - The String in with the format applied on to it
	 */
	public static final String formatJoinMessage(final String in,
			final String playerName) {
		return in.replace("{NAME}", playerName);
	}
}
