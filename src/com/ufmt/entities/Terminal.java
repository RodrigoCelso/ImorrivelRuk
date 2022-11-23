package com.ufmt.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.ufmt.main.Game;
import com.ufmt.world.Camera;
import com.ufmt.world.World;

public class Terminal extends Rectangle{
	private static BufferedImage[] terminalImages;
	
	// 0 = ligado, 1 = hackeado, 2 = quebrado
	public byte state = 0;
	private int range = 50;
	
	public Terminal(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		terminalImages = new BufferedImage[3];
		for(int i = 0; i < 3; i ++) terminalImages[i] = Game.spritesheet.getSprite(0, 16 * i + 112, World.TILE_SIZE, World.TILE_SIZE);
	}
	
	public int getIntX() {
		return (int)x;
	}
	
	public int getIntY() {
		return (int)y;
	}
	
	public int getIntWidth() {
		return (int)width;
	}
	
	public int getIntHeight() {
		return (int)height;
	}
	
	public void emp() {
		if(state != 0) {
			for(int i = 0; i < Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy && e.calculateDistance(e.getIntX(), e.getIntY(), getIntX(), getIntY()) < range) ((Enemy) e).isOff = true;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(terminalImages[state], getIntX() - Camera.x, getIntY() - Camera.y, getIntWidth(), getIntHeight(), null);
		
		// Desenha a área de efeito
		/*
		g.setColor(Color.CYAN);
		g.drawOval(getIntX() + 8 - range - Camera.x, getIntY() + 8 - range - Camera.y, range*2, range*2);
		*/
	}
}
