package com.ufmt.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.ufmt.main.Game;
import com.ufmt.world.Camera;
import com.ufmt.world.Node;
import com.ufmt.world.Vector2i;
import com.ufmt.world.World;

public class Entity extends Rectangle{
	
	private static final long serialVersionUID = 1L;

	protected double speed;
	
	public int depth;

	protected List<Node> path;
	
	public boolean debug = false;
	
	private BufferedImage sprite;
	
	public static Random rand = new Random();
	
	public Entity(int x,int y,int width,int height,double speed,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
	};
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public int getIntX() {
		return (int)x;
	}
	
	public void setY(int newY) {
		this.y = newY;
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
	public void tick(){
	}
	
	public double calculateDistance(int x1,int y1,int x2,int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x * 16) {
					x--;
				}
				
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
				
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,x - Camera.x,y - Camera.y,null);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() + maskx - Camera.x,this.getY() + masky - Camera.y,mwidth,mheight);
	}

}
