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

import java.io.File;

public class FileHandler {

	private final Main	plugin;

	public FileHandler(final Main main) {
		plugin = main;
		directory();
		config();
	}

	/**
	 * Called on plugin enable to check if the config.yml exists or need to be
	 * created.
	 * <p />
	 * Handles the creation if nessecary
	 */
	private final void config() {
		final File configFile = new File("plugins" + File.separator + "GuestUnlock" + File.separator + "config.yml");
		if (!configFile.exists()) {
			plugin.saveDefaultConfig();
			Main.INFO("Created default configuration-file");
		}
	}

	/**
	 * Called on plugin enable to check if the data directory exists or need to
	 * be created.
	 * <p />
	 * Handles the creation if nessecary
	 */
	private final void directory() {
		if (!plugin.getDataFolder().isDirectory()) {
			if (!plugin.getDataFolder().mkdirs()) {
				Main.SEVERE("Failed to create directory");
			}
			else {
				Main.INFO("Created directory sucessfully!");
			}
		}
	}

}
