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

import org.bukkit.entity.Player;

public class Permission {

	/**
	 * Checks if a player is a Guest
	 * <p />
	 * Permission: guestunlock.guest
	 * 
	 * @param p
	 *            - The player to check
	 * @return true, if the player is a guest. Else false
	 */
	public static final boolean isGuest(final Player p) {
		return p.hasPermission("guestunlock.guest");
	}

	/**
	 * Checks if a player is a Moderator
	 * <p />
	 * Permission: guestunlock.moderator
	 * 
	 * @param p
	 *            - The player to check
	 * @return true, if the player is a moderator. Else false
	 */
	public static final boolean isModerator(final Player p) {
		return p.hasPermission("guestunlock.moderator");
	}

	/**
	 * Checks if a player is a Admin
	 * <p />
	 * Permission: guestunlock.admin
	 * 
	 * @param p
	 *            - The player to check
	 * @return true, if the player is a admin. Else false
	 */
	public static final boolean isAdmin(final Player p) {
		return p.hasPermission("guestunlock.admin");
	}
}
