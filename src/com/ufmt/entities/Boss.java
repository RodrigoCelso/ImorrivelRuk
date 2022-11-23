package com.ufmt.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.ufmt.main.Game;
import com.ufmt.world.AStar;
import com.ufmt.world.Vector2i;

public class Boss extends Entity{

	public Boss(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(path == null || path.size() == 0) {
			Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
			Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
			path = AStar.findPath(Game.world, start, end);
		}
		
		if(calculateDistance(getIntX(),getIntY(),Game.player.getIntX(),Game.player.getIntY()) < 50)
			followPath(path);
		
		if(x % 16 == 0 && y % 16 == 0) {
			if(new Random().nextInt(100) < 10) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}
		}
	}

}
