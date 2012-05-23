package se.myller.GuestUnlock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class UpdateCheck implements Runnable {

	private Main plugin;
	public String newestVersion;
	
	
	public UpdateCheck(Main main) {
		plugin = main;
	}
	
	@Override
	public void run() {
		try {
			plugin.log("Starting updatecheck", true, Level.INFO);
			final String address = "https://raw.github.com/Mylleranton/GuestUnlock/83eff113a48c76bdfea3b9128ae8b5d67c43f017/GuestUnlockVersion.txt";
			final URL url = new URL(address.replace(" ", "%20"));
			final URLConnection conn = url.openConnection();
			conn.setConnectTimeout(8000);
            conn.setReadTimeout(15000);

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            newestVersion = bufferedReader.readLine();
            if (newestVersion != null) {
            	if (!plugin.getDescription().getVersion().equals(newestVersion)) {
                    plugin.log("Found a different version available: " + newestVersion, false, Level.WARNING);
                    plugin.log("Check http://dev.bukkit.org/server-mods/GuestUnlock/", false, Level.WARNING);
                    plugin.newVersion = true;
            	} else {
            		plugin.log("Did not found a new version", true, Level.INFO);
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
		} finally {
			plugin.log("[GuestUnlock] Finished updatecheck", true, Level.INFO);
		}
		
	}

}
