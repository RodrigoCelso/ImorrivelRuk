package com.ufmt.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.ufmt.main.Game;
import com.ufmt.world.AStar;
import com.ufmt.world.Camera;
import com.ufmt.world.Vector2i;
import com.ufmt.world.World;

public class Enemy extends Entity{
	
	private static final long serialVersionUID = 1L;

	// Animacao
	private static BufferedImage[] enemyFrames;
	
	// Vida
	public double life;
	
	// Imovel
	public boolean isGrabbed = false;
	
	// Danificado
	public boolean isOff = false;
	
	// Movimentacao (render)
	private boolean moved = true;
	private final int maxIndex = 1, maxFrames = 4;
	private int index = 0, frames = 0;
	
	public Enemy(int x, int y, int width, int height, double life, int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		this.life = life;
		enemyFrames = new BufferedImage[2];
		
		for(int i = 0; i < 2; i++) {
			enemyFrames[i] = Game.spritesheet.getSprite(16, i * 16 + 96, width, height);
		}
	}

	public void tick(){
		if(life<=0) Game.entities.remove(this);
		if(!isGrabbed) {
			depth = 0;
			if(moved){
				frames++;
				if(frames > maxFrames){
					frames = 0;
					index++;
					if(index > maxIndex){
						index = 0;
					}
				}
			}else index = 0;
			
			// Atacar o player
			Player p = hitboxPlayer(getIntX(),getIntY(),getIntWidth(),getIntHeight());
			if(p != null && p.isInvisible == false) {
				p.life-=0.1;
			}
			
			// Deixa os robos parecendo danificados (usar com o hack do Bruno em algum terminal)
			
			if(isOff && new Random().nextInt(100) < 40)
				move();
			else if(!isOff && new Random().nextInt(100) < 80) move();
		}
	}
	
	private void move() {
		if(calculateDistance(Game.player.getIntX(), Game.player.getIntY(), getIntX(), getIntY()) < 40) {
			if(Game.player.getIntX() + 8 < getIntX() && World.isFree(getIntX() - 1, getIntY())) x--;
			else if(Game.player.getIntX() - 8 > getIntX() && World.isFree(getIntX() + 1, getIntY())) x++;
			
			if(Game.player.getIntY() + 8 < getIntY() && World.isFree(getIntX(), getIntY() - 1)) y--;
			else if(Game.player.getIntY() - 8 > getIntY() && World.isFree(getIntX(), getIntY() + 1)) y++;
		}
	}
	
	private Player hitboxPlayer(int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(x,y,width,height);
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Player) {
				if(rect.intersects(e)) 
					return (Player)e;	
			}
		}
		return null;
	}
	
	public void render(Graphics g) {
		g.drawImage(enemyFrames[index],x - Camera.x,y - Camera.y,null);
	}
}
