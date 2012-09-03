package se.myllers.guestunlock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandEx implements org.bukkit.command.CommandExecutor {

	/**
	 * Called when a command is sent
	 * @param cs - The CommandSender Object
	 * @param cmd - The Command
	 * @param alias - The alias used
	 * @param args - The arguments
	 */
	@Override
	public boolean onCommand(final CommandSender cs, final Command cmd,
			final String alias, final String[] args) {

		final String cmdName = cmd.getName();
		Main.DEBUG("Parsing command: " + cmdName + ", Sender: " + cs.getName());
		if (cs instanceof Player) {
			if (cmdName.equalsIgnoreCase("guestunlock")
					&& Permissions.isGuest((Player) cs)) {
				if (args.length == 1) {
					onSendPassword(cs, args[0]);
					return true;
				} else {
					return false;
				}
			} else if (cmdName.equalsIgnoreCase("gupassword")
					&& Permissions.isAdmin((Player) cs)) {
				if (args.length == 1) {
					onChangePassword(cs, args[0]);
					return true;
				} else {
					return false;
				}
			}
		} else if (cs instanceof ConsoleCommandSender) {
			if (cmdName.equalsIgnoreCase("guestunlock")) {
				cs.sendMessage(ChatColor.RED
						+ "You must be a player to be able to send a password!");
				return true;
			} else if (cmdName.equalsIgnoreCase("gupassword")
					&& args.length == 1) {
				onChangePassword(cs, args[0]);
				return true;
			}
		}

		return false;
	}

	/**
	 * Called when a player is authorized and sends a password
	 * via the /guestunlock command
	 * 
	 * @param p - The CommandSender
	 * @param pass - The password
	 */
	private void onSendPassword(final CommandSender p, final String pass) {

		int modsOnline = 0;

		if (pass.equals(Main.config.getString("Admin.Password"))) {
			Main.INFO(p.getName() + " has sent the correct password!");

			p.sendMessage(ChatColor.GREEN
					+ "You have sent the correct password!");

			if (Main.config.getBoolean("PermissionSystem.PermissionsEx.Enable")) {
				PermSystem.setGroupPEX((Player) p);
			} else if (Main.config
					.getBoolean("PermissionSystem.GroupManager.Enable")) {
				PermSystem.setGroupGM((Player) p);
			} else if (Main.config
					.getBoolean("PermissionSystem.bPermissions.Enable")) {
				PermSystem.setGroupBP((Player) p);
			} else {
				for (final Player x : Bukkit.getServer().getOnlinePlayers()) {
					if (Permissions.isModerator(x)) {
						modsOnline++;
						x.sendMessage(ChatColor.BLUE + "The player: "
								+ p.getName()
								+ " has sent the correct password, "
								+ ChatColor.YELLOW + pass);
					}
				}
				if (modsOnline == 0) {
					p.sendMessage(ChatColor.RED
							+ "There is currently no moderator online! Send the password again later.");
				} else if (modsOnline == 1) {
					p.sendMessage(ChatColor.BLUE + "There is currently "
							+ modsOnline
							+ " moderator online, he will contact you shortly");
				} else {
					p.sendMessage(ChatColor.BLUE
							+ "There are currently "
							+ modsOnline
							+ " moderator online, they will contact you shortly");
				}
			}
		}
	}

	/**
	 * Called when a user is authorized and sends a new password
	 * via the /gupassword command
	 * 
	 * @param s - The CommandSender
	 * @param newPass - The new password
	 */
	private void onChangePassword(final CommandSender s, final String newPass) {
		Main.config.set("Admin.Password", newPass);
		
		for (final Player x : Bukkit.getServer().getOnlinePlayers()) {
			if (Permissions.isModerator(x)) {
				x.sendMessage(ChatColor.YELLOW + s.getName()
						+ " changed the password to: " + newPass);
			}
		}
		s.sendMessage(ChatColor.GREEN + "Password changed!");
	}

}
