package se.myller.GuestUnlock;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RunningTask {

	public static int threadID = 0;
	public static long messageInterval;

	private Main plugin;

	public RunningTask(Main instance) {
		plugin = instance;
	}

	/*
	 * 
	 * Start the task
	 */
	public void StartTimer() {
		plugin.log("DEBUG: Starting running-task", true, Level.INFO);
		messageInterval = plugin.config.getInt("Guest.RepeatingMessage.Interval") * 20;
		threadID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
					@Override
					public void run() {
						SendGuestMessage();
						plugin.log("DEBUG: Calling method: SendGuestMessage();", true, Level.INFO);
					}
				}, 0, messageInterval);
	}

	/*
	 * 
	 * The running task
	 */
	public void SendGuestMessage() {
		try {
			Player[] players = plugin.getServer().getOnlinePlayers();
			for (Player player : players) {
				if (player.hasPermission("GuestUnlock.guest")
						&& !player.hasPermission("GuestUnlock.moderator")) {
					if (plugin.config
							.getBoolean("Guest.RepeatingMessage.UseJoinMessage")) {
						player.sendMessage(ChatColor.GREEN
								+ plugin.config.getString("Guest.Join.Message"));
						plugin.log("DEBUG: Sending guest-message to: " + player.getName(), true, Level.INFO);
					} else {
						player.sendMessage(ChatColor.GREEN
								+ plugin.config.getString("Guest.RepeatMessage.RepeatMessage"));
						plugin.log("DEBUG: Sending repeat-message to: " + player.getName(), true, Level.INFO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
