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
import java.util.logging.Level;
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
		public Logger logger;
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
    	
    	// VersionCheck
    	public UpdateCheck uc = new UpdateCheck(this);
    	
    	public boolean passwordCheck = false;
    	public boolean newVersion = false;
    	private boolean configDebug;
	
	/*
	 * 
	 * On plugin enable
	 * 
	 */
	@Override
	public void onEnable() {

		// Get the logger
		logger = Logger.getLogger("Minecraft.GuestUnlock");
		
		// Get the config
		config = getConfig();
		
		// Debug
		configDebug = config.getBoolean("Admin.Debug");
		
		//Check The Password
		if (configDebug) {
			checkPassword();
		}
		// Get the plugin.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		
		// Msg to the console
		log("==================================", false, Level.INFO);
		log("=====   Loading", true, Level.INFO);
		log("=====   Checking password", true, Level.INFO);
		if (passwordCheck == false) {
			log("=====   Password is NOT OK!", true, Level.WARNING);
			log("=====   Password is NOT OK!", true, Level.WARNING);
			log("=====   Password is NOT OK!", true, Level.WARNING);
			log("=====   Password is NOT OK!", true, Level.WARNING);
			log("=====   Password is NOT OK!", true, Level.WARNING);
			log("=====   Password is NOT OK!", true, Level.WARNING);
			log("-------------------------------------------------", true, Level.INFO);
			log("=====   Password MUST be a STRING!", true, Level.WARNING);
			log("=====   Password set to default value: 'GuestUnlock'", true, Level.WARNING);
			log("-------------------------------------------------", true, Level.INFO);
		} else {
			log("=====   Password OK", true, Level.INFO);
		}
		log("=====   Lets see if you want auto-group moving", true, Level.INFO);
		
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
			log("=====   You wanted auto-group moving support!", true, Level.INFO);
			
			// Implement PEX
			if (config.getBoolean("Permissions.PermissionsEx.Enable") == true ) {
				log("=====   Ill try to find PermissionsEx [PEX]!", true, Level.INFO);
				ppe = new PermPermissionsEx(this);
				ppe.getPex();
			}
			
			// Implement GM
			else if (config.getBoolean("Permissions.GroupManager.Enable") == true ) {
				log("=====   Ill try to find GroupManager [GM]!", true, Level.INFO);
				pgm = new PermGroupManager(this);
				pgm.getGM();
				}
			
			// Implement bP
			else if (config.getBoolean("Permissions.bPermissions.Enable") == true) {
				log("=====   Ill try to find bPermissions [bP]!", true, Level.INFO);
				pbp = new PermbPermissions(this);
				pbp.getBP();
				}
		} else {
			log("=====   You did not want auto-group moving enabled!", true, Level.INFO);
		}
		log("version " + pdfFile.getVersion() + " by Myller is now Enabled!", false, Level.INFO);
		log("==================================", false, Level.INFO);
		uc.run();
	}
	/*
	 * 
	 * On plugin disable
	 *
	 */
	@Override
	public void onDisable() {
		// Get the logger
		logger = Logger.getLogger("Minecraft");
		
		// Get the plugin.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		
		// Stop the running task
		Bukkit.getServer().getScheduler().cancelTask(tid);
		
		// Msg:s to the log
		log("[" + pdfFile.getName() + "] stopped running task!", true, Level.INFO);
		log("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " by Myller is now Disabled!", false, Level.INFO);
		
	}
	public void checkPassword() {
		config.getString("Admin.Password").toString();
		if (config.isString("Admin.Password")) {
			passwordCheck = true;
			return;
		} else {
			config.set("Admin.Password", "GuestUnlock");
			this.saveConfig();
			passwordCheck = false;
			return;
		}
	}
	public void log(String message, boolean debug, Level level) {
		String prefix = "[GuestUnlock] ";
		if (debug == false) {
			logger.log(level, prefix + message);
		} else if (debug && configDebug) {
			logger.log(level, prefix + message);
		} else if (configDebug == false && debug) {
			return;
		} else {
			logger.log(level, prefix + message);
		}
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
				log("=====   Configuration file and directory created!", false, Level.INFO);
			} else {
				log("=====   Configuration file loaded!", true, Level.INFO);
			}
		} catch(Exception e) {	
			log("=====   Configuration file failed to load!", false, Level.SEVERE);
			log("=====   Disabling Plugin.", false, Level.SEVERE);
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
						player.sendMessage(ChatColor.GREEN + config.getString("Guest.Join.Message"));
					} else {
						player.sendMessage(ChatColor.GREEN + config.getString("Guest.RepeatMessage.RepeatMessage"));
					}
			}
		}
	}
}



	
	


