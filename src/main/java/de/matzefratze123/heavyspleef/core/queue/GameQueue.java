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
package de.matzefratze123.heavyspleef.core.queue;

import de.matzefratze123.heavyspleef.core.Game;
import de.matzefratze123.heavyspleef.objects.SpleefPlayer;
import de.matzefratze123.heavyspleef.util.LanguageHandler;

public class GameQueue {
	
	private Queue<SpleefPlayer> queue;
	private Game game;
	
	public GameQueue(Game game) {
		this.game = game;
		this.queue = new ArrayQueue<SpleefPlayer>();
	}
	
	public int addPlayer(SpleefPlayer player) {
		if (queue.contains(player))
			return -1;
		
		int place = queue.add(player);
		player.sendMessage(LanguageHandler._("addedToQueue", game.getName(), String.valueOf(place + 1)));
		return place;
	}
	
	public void removePlayer(SpleefPlayer player) {
		if (!queue.contains(player))
			return;
		
		queue.remove(player);
	}
	
	public boolean contains(SpleefPlayer player) {
		return queue.contains(player);
	}
	
	public void processQueue() {
		SpleefPlayer currentItem = null;
		
		do {
			currentItem = queue.remove();
			
			if (currentItem != null && currentItem.isOnline()) {		
				game.join(currentItem);
			}
		} while (currentItem != null);
	}
	
	public int size() {
		return queue.size();
	}

	public void clear() {
		
	}
	
}
