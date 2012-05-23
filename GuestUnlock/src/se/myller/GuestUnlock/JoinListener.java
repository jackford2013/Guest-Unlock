package se.myller.GuestUnlock;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;



/**
 * GuestUnlock for Bukkit
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
	 * 
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (plugin.getConfig().getBoolean("Guest.Join.Enable")) {
			if (player.hasPermission("GuestUnlock.guest") && !player.hasPermission("GuestUnlock.moderator")) {
					player.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + plugin.getConfig().getString("Guest.Join.Message"));	
			} 
		}
		if (player.hasPermission("GuestUnlock.moderator") && (plugin.newVersion)) {
			player.sendMessage(ChatColor.YELLOW + "New version available for GuestUnlock: " + plugin.uc.newestVersion);
			player.sendMessage(ChatColor.YELLOW + "Please check http://dev.bukkit.org/server-mods/GuestUnlock");
		}
	}
}
