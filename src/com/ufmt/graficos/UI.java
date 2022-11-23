package com.ufmt.graficos;


import java.awt.Color;
import java.awt.Graphics;

import com.ufmt.entities.Player;
import com.ufmt.main.Game;
import com.ufmt.world.Camera;

public class UI {

	Player player;
	int SCALE;
	
	public UI() {
		this.player = Game.player;
		this.SCALE = Game.SCALE;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((player.getIntX() - Camera.x) * SCALE, (player.getIntY() - Camera.y + player.getIntHeight()) * SCALE, player.getIntWidth() * SCALE, 5);
		g.setColor(Color.GREEN);
		if(player.isRuk) g.fillRect((player.getIntX() - Camera.x) * SCALE, (player.getIntY() - Camera.y + player.getIntHeight()) * SCALE, (int)((player.life / player.rukLife) * player.getIntWidth() * SCALE), 5);
		else g.fillRect((player.getIntX() - Camera.x) * SCALE, (player.getIntY() - Camera.y + player.getIntHeight()) * SCALE, (int)((player.life / player.brunoLife) * player.getIntWidth() * SCALE), 5);
	}	
}
