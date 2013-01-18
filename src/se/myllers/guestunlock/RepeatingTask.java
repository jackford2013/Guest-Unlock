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

public class RepeatingTask {

	public static int	threadId;
	private final Main	plugin;

	public RepeatingTask(final Main instance) {
		plugin = instance;
		startTask();
	}

	/**
	 * Start a SyncRepeatingTask that sends the repeating message to guests
	 */
	public void startTask() {
		threadId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				next();
			}
		}, 0, Main.config.getInt("Guest.MessageInterval") * 20);
	}

	/**
	 * Sends the message to guests
	 */
	public void next() {
		for (final Player x : Bukkit.getOnlinePlayers()) {
			if (Permission.isGuest(x) && !Permission.isModerator(x)) {
				x.sendMessage(ChatColor.GREEN + Main.config.getString("Guest.Message"));
			}
		}
	}
}
