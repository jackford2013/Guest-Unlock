package se.myllers.guestunlock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.PluginDescriptionFile;

public class UpdateCheck {
	
	public static String newestVersion;
	public static PluginDescriptionFile pdf;
	public UpdateCheck(PluginDescriptionFile pdf){
		UpdateCheck.pdf = pdf;
		check();
	}
	
	/**
	 * Checks if there is a new version available
	 */
	private static final void check() {
		try { 
			final String address = "https://raw.github.com/Mylleranton/GuestUnlock/master/src/version.txt";
			final URL url = new URL(address.replace(" ", "%20"));
			final URLConnection conn = url.openConnection();
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(15000);

			final BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			newestVersion = bufferedReader.readLine();
			if (newestVersion != null) {
				if (!pdf.getVersion().equals(newestVersion)) {
					Main.INFO("Found a different version available: "
							+ newestVersion);
					Main.INFO(
							"Check http://dev.bukkit.org/server-mods/GuestUnlock/");
				} else {
					Main.DEBUG("Did not found a new version");
				}
				bufferedReader.close();
				conn.getInputStream().close();
				return;
			} else {
				bufferedReader.close();
				conn.getInputStream().close();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
