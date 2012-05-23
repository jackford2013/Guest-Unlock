package se.myller.GuestUnlock;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;



/**
 * GuestUnlock for Bukkit
 * @author Myller
 */
public class CommandExcecutor implements CommandExecutor {

	private Main plugin;
    public CommandExcecutor(Main instance) {
    	plugin = instance;
    }
    
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		// Get the name of the command
		String cmdname = cmd.getName();
		
		// If sender = player
		if (sender instanceof Player) {
			if (cmdname.equalsIgnoreCase("gutest")) {
				plugin.cgt.onCommand(sender);
			}
			else if (args.length == 0) {
				if (cmdname.equalsIgnoreCase("guestunlock")) {
					plugin.cgu.onCommandFail((Player) sender);
					return true;
				}
				else if (cmdname.equalsIgnoreCase("gupassword")) {
					plugin.cgp.onCommandFail((Player) sender);
					return true;
				}
				return false;
			}
			else if (args[0].equals("help")) {
				if (cmdname.equalsIgnoreCase("guestunlock")) {
					plugin.cgu.onCommandHelp((Player)sender);
					return true;
				}
				else if (cmdname.equalsIgnoreCase("gupassword")) {
					plugin.cgp.onCommandHelp((Player)sender);
					return true;
				}
				return false;
			}
			else if (sender.hasPermission("GuestUnlock.guest") && cmdname.equalsIgnoreCase("guestunlock")) {
				if  (args[0].equals(plugin.getConfig().getString("Admin.Password")) && args.length == 1) {
					plugin.cgu.cmdSend(sender, plugin.getConfig().getString("Admin.Password"));
					return true;
				} else if (sender.hasPermission("GuestUnlock.guest") && !args[0].equals(plugin.getConfig().getString("Admin.Password"))) {
					plugin.cgu.cmdFail(sender, args[0]);
					return true;
				}
			}
			else if (sender.hasPermission("GuestUnlock.admin") && cmdname.equalsIgnoreCase("gupassword")) {
				if (args.length == 1) {
					plugin.cgp.setPwd(sender, args[0]);
					return true;
				}
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (cmdname.equalsIgnoreCase("gupassword") && args.length == 1) {
				plugin.cgp.setPwd(sender, args[0]);
				return true;
			} else if (cmdname.equalsIgnoreCase("gutest")) {
				plugin.cgt.onCommand(sender);
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "[GuestUnlock] Must be a player to perform that command!");
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "[GuestUnlock] Error!");
			plugin.log("Error in commandhandling!", false, Level.SEVERE);
			return true;
		}
		return false;
	}
}
