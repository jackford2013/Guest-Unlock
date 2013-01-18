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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateCheck {

	public static String	newestVersion;
	public static String	version;

	public UpdateCheck(String version) {
		UpdateCheck.version = version;
		check();
	}

	/**
	 * Checks if there is a new version available
	 */
	private static final void check() {
		try {
			final URLConnection conn = new URL("https://raw.github.com/Mylleranton/GuestUnlock/master/src/version.txt").openConnection();
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(15000);
			conn.setRequestProperty("User-agent", "GuestUnlock");
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			if ((newestVersion = bufferedReader.readLine()) != null) {
				if (!version.equalsIgnoreCase(newestVersion)) {
					Listener.newestVersion = newestVersion;
					Main.INFO("Found a different version available: " + newestVersion);
					Main.INFO("Check http://dev.bukkit.org/server-mods/GuestUnlock/");
				}
				else {
					Main.DEBUG("Did not find a new version");
				}
				bufferedReader.close();
				conn.getInputStream().close();
				return;
			}
			else {
				bufferedReader.close();
				conn.getInputStream().close();
				Main.DEBUG("'newestVersion == null'");
			}
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
