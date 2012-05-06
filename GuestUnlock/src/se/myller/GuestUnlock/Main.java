/*
* GuestUnlock - a bukkit plugin
* Copyright (C) 2012 Mylleranton
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
package se.myller.GuestUnlock;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import se.myller.GuestUnlock.Commands.CMDguestunlock;
import se.myller.GuestUnlock.Commands.CMDgupassword;
import se.myller.GuestUnlock.Commands.CMDgutest;
import se.myller.GuestUnlock.Permission.PermGroupManager;
import se.myller.GuestUnlock.Permission.PermPermissionsEx;
import se.myller.GuestUnlock.Permission.PermbPermissions;



/**
 * GuestUnlock for Bukkit
 * @author Myller
 */

public class Main extends JavaPlugin {
	
	
		// Our variables:
		public static Main plugin;
		// Log
		public Logger log;
		// Config
		public FileConfiguration config;
		// Repeater
		public static int tid = 0;
		public static long interval;
		// PluginManager
		public PluginManager pm;
		// Different permissionplugins
		public GroupManager groupManager;
		public PermGroupManager pgm;
		public PermbPermissions pbp;
		public PermPermissionsEx ppe;
    
		// Commands
    	public CMDguestunlock cgu = new CMDguestunlock(this);
    	public CMDgupassword cgp = new CMDgupassword(this);
    	public CMDgutest cgt = new CMDgutest(this);

	
	/*
	 * 
	 * On plugin enable
	 * 
	 */
	@Override
	public void onEnable() {

		// Get the logger
		log = Logger.getLogger("Minecraft");
		
		// Get the config
		config = getConfig();
		
		// Get the plugin.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		
		// Msg to the console
		log.info("[GuestUnlock] ==================================");
		log.info("[GuestUnlock] =====   Loading");
		log.info("[GuestUnlock] =====   Checking password");
		log.info("[GuestUnlock] =====   PWD: " + config.getString("Admin.Password"));
		log.info("[GuestUnlock] =====   Password OK");
		log.info("[GuestUnlock] =====   Lets see if you want auto-group moving!");
		
		// Register our events
		pm = getServer().getPluginManager();
		pm.registerEvents(new JoinListener(this), this);
		
		// Make our config + directory
		MakeDefaultConfig();
		
		// Start the timer
		RepeatMessages();

		// Register our commands
		CommandExcecutor commandEx = new CommandExcecutor(this);
		getCommand("guestunlock").setExecutor(commandEx);
		getCommand("gupassword").setExecutor(commandEx);
		getCommand("gutest").setExecutor(commandEx);
		
		// Check config for permissions support
		if (config.getBoolean("Permissions.PermissionsEx.Enable") == true || config.getBoolean("Permissions.GroupManager.Enable") == true || config.getBoolean("Permissions.bPermissions.Enable") == true) {
			log.info("[GuestUnlock] =====   You wanted auto-group moving support!");
			
			// Implement PEX
			if (config.getBoolean("Permissions.PermissionsEx.Enable") == true ) {
				log.info("[GuestUnlock] =====   Ill try to find PermissionsEx [PEX]!");
				ppe = new PermPermissionsEx(this);
				ppe.getPex();
			}
			
			// Implement GM
			else if (config.getBoolean("Permissions.GroupManager.Enable") == true ) {
				log.info("[GuestUnlock] =====   Ill try to find GroupManager [GM]!");
				pgm = new PermGroupManager(this);
				pgm.getGM();
				}
			
			// Implement bP
			else if (config.getBoolean("Permissions.bPermissions.Enable") == true) {
				log.info("[GuestUnlock] =====   Ill try to find bPermissions [bP]!");
				pbp = new PermbPermissions(this);
				pbp.getBP();
				}
		} else {
			log.info("[GuestUnlock] =====   You didn´t want auto-group moving enabled!");
		}
		
		log.info("[GuestUnlock] ==================================");
		log.info("[GuestUnlock] version " + pdfFile.getVersion() + " by Myller is now Enabled!");
	}
	/*
	 * 
	 * On plugin disable
	 *
	 */
	@Override
	public void onDisable() {
		// Get the logger
		log = Logger.getLogger("Minecraft");
		
		// Get the plugin.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		
		// Stop the running task
		Bukkit.getServer().getScheduler().cancelTask(tid);
		
		// Msg:s to the log
		log.info("[" + pdfFile.getName() + "] stopped running task!");
		log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " by Myller is now Disabled!");
		
	}
	/*
	 * 
	 * Make our config + directory
	 * 
	 */
	public void MakeDefaultConfig() {
		try {			
			File configFile = new File("plugins/GuestUnlock/config.yml");
			if(!configFile.exists()) {
				new File("plugins/GuestUnlock").mkdir();
				this.saveDefaultConfig();
				log = Logger.getLogger("Minecraft");
				log.info("[GuestUnlock] =====   Configuration file and directory created!");
			} else {
				log.info("[GuestUnlock] =====   Configuration file loaded!");
			}
		} catch(Exception e) {	
			log.severe("[GuestUnlock] =====   Configuration file failed to load!");
			log.severe("[GuestUnlock] =====   Disabling Plugin.");
			getServer().getPluginManager().disablePlugin(this);
		}
	}
	/*
	 * 
	 * Start the task
	 * 
	 */
	public void RepeatMessages() {
		interval = config.getInt("Guest.RepeatingMessage.Interval");
		tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				try {
					SendGuestMessage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 0, interval * 20);
	}
	/*
	 * 
	 * The running task
	 * 
	 */
	public void SendGuestMessage() throws IOException {
		Player[] players = getServer().getOnlinePlayers();
		for (Player player: players) {
			if (player.hasPermission("GuestUnlock.guest") && !player.hasPermission("GuestUnlock.moderator")) {
					if (config.getBoolean("Guest.RepeatingMessage.UseJoinMessage") == true) { 
					player.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + config.getString("Guest.Join.Message"));
					} else {
						player.sendMessage(ChatColor.AQUA + "[GuestUnlock] " + ChatColor.GREEN + config.getString("Guest.RepeatMessage.RepeatMessage"));
					}
			}
		}
	}
}



	
	


