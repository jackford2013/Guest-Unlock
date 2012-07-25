package se.myller.GuestUnlock.Commands;

import org.bukkit.entity.Player;

public interface Command {

	public void onCommandFail(Player p);

	public void onCommandHelp(Player p);

}
