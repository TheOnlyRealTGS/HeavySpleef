/**
 *   HeavySpleef - Advanced spleef plugin for bukkit
 *   
 *   Copyright (C) 2013 matzefratze123
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.matzefratze123.heavyspleef.command;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.matzefratze123.heavyspleef.command.UserType.Type;
import de.matzefratze123.heavyspleef.core.Game;
import de.matzefratze123.heavyspleef.core.GameManager;
import de.matzefratze123.heavyspleef.core.flag.Flag;
import de.matzefratze123.heavyspleef.core.region.IFloor;
import de.matzefratze123.heavyspleef.core.region.LoseZone;
import de.matzefratze123.heavyspleef.util.Permissions;

@UserType(Type.ADMIN)
public class CommandInfo extends HSCommand {

	public CommandInfo() {
		setMaxArgs(1);
		setMinArgs(1);
		setOnlyIngame(true);
		setPermission(Permissions.INFO);
		setUsage("/spleef info <name>");
		setHelp("Prints out information about this game");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player)sender;
		if (!GameManager.hasGame(args[0])) {
			sender.sendMessage(_("arenaDoesntExists"));
			return;
		}
		
		Game game = GameManager.getGame(args[0]);
		
		player.sendMessage(ChatColor.YELLOW + "Name: " + game.getName() + ChatColor.GRAY + ", type: " + game.getType().name());
		if (game.getFlags().size() > 0)
			player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Flags: " + ChatColor.BLUE + parseFlags(game));
		
		player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Floors:");
		for (IFloor floor : game.getComponents().getFloors()) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "# " + floor.asPlayerInfo());
		}
		player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Losezones:");
		for (LoseZone zone : game.getComponents().getLoseZones()) {
			player.sendMessage(ChatColor.YELLOW + "# " + zone.asInfo());
		}
	}
	
	private Set<String> parseFlags(Game game) {
		Map<Flag<?>, Object> flags = game.getFlags();
		Set<String> set = new HashSet<String>();
		
		for (Flag<?> flag : flags.keySet()) {
			set.add(flag.toInfo(flags.get(flag)));
		}
		
		return set;
	}

}
