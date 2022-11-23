package com.ufmt.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.ufmt.main.Game;
import com.ufmt.world.Camera;
import com.ufmt.world.World;

public class Player extends Entity{

	private static final long serialVersionUID = 1L;
	private static BufferedImage[] right_player, left_player, up_player, down_player;
	private static BufferedImage[] right_action, left_action, up_action, down_action;
	
	// Ruk
	private static BufferedImage[] ruk_right_player, ruk_left_player, ruk_up_player, ruk_down_player;
	private static BufferedImage[] ruk_right_punch, ruk_left_punch, ruk_up_punch, ruk_down_punch;
	
	// Davi Bruno Breno
	private static BufferedImage[] bruno_right_player, bruno_left_player, bruno_up_player, bruno_down_player;
	private static BufferedImage bruno_invisibility_player;
	
	// Animação do movimento
	private int frames = 0, maxFrames = 20;
	private int index = 0, maxIndex = 1;
	private boolean moved = false;
	
	// Transformacao
	public boolean isRuk = true;
	public static boolean canChange = true, change = false;
	private final int changeMax = 300;
	private int changeCount = 0;
	
	// Movimentacao
	public boolean right,up,left,down;
	
	// Rotacao
	private final int up_Dir = 0, down_Dir = 1, right_Dir = 2, left_Dir = 3;
	private int current_Dir = down_Dir;
	public boolean attack = false;
	public int mx, my;
	
	// Agarrar
	public boolean grab = false, canGrab = true;
	private Enemy enemyGrabbed = null;
	
	// Ataque
	public boolean mouseLeftClick = false, mouseRightClick = false;
	public int combo = 0;
	private int comboTimer = 0, comboMaxTimer = 25;
	private int attackTimer = 0;
	
	// Invisibilidade
	public boolean isInvisible = false;
	private int invisibleCount = 0;
	private final int invisibleCountMax = 60*5;
	
	// Vida
	public double brunoLife = 10;
	public double rukLife = 100;
	public double life = rukLife;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		right_player = new BufferedImage[2];
		left_player = new BufferedImage[2];
		up_player = new BufferedImage[2];
		down_player = new BufferedImage[2];

		right_action = new BufferedImage[2];
		left_action = new BufferedImage[2];
		up_action = new BufferedImage[2];
		down_action = new BufferedImage[2];
		
		ruk_right_player = new BufferedImage[2];
		ruk_left_player = new BufferedImage[2];
		ruk_up_player = new BufferedImage[2];
		ruk_down_player = new BufferedImage[2];

		ruk_right_punch = new BufferedImage[8];
		ruk_left_punch = new BufferedImage[8];
		ruk_up_punch = new BufferedImage[8];
		ruk_down_punch = new BufferedImage[8];
		
		bruno_right_player = new BufferedImage[2];
		bruno_left_player = new BufferedImage[2];
		bruno_up_player = new BufferedImage[2];
		bruno_down_player = new BufferedImage[2];
		
		for(int i = 0; i < 2; i++){
			// Ruk
			ruk_right_player[i] = Game.spritesheet.getSprite(32, 0+(i*16),16,16);
			ruk_left_player[i] = Game.spritesheet.getSprite(48, 0+(i*16),16,16);
			ruk_up_player[i] = Game.spritesheet.getSprite(64, 0+(i*16),16,16);
			ruk_down_player[i] = Game.spritesheet.getSprite(80, 0+(i*16),16,16);
			
			// Soco 01
			ruk_right_punch[i] = Game.spritesheet.getSprite(96, 0+(i*16),16,16);
			ruk_left_punch[i] = Game.spritesheet.getSprite(112, 0+(i*16),16,16);
			ruk_up_punch[i] = Game.spritesheet.getSprite(128, 0+(i*16),16,16);
			ruk_down_punch[i] = Game.spritesheet.getSprite(144, 0+(i*16),16,16);
			
			// Soco 02
			ruk_right_punch[i+2] = Game.spritesheet.getSprite(96, 32+(i*16),16,16);
			ruk_left_punch[i+2] = Game.spritesheet.getSprite(112, 32+(i*16),16,16);
			ruk_up_punch[i+2] = Game.spritesheet.getSprite(128, 32+(i*16),16,16);
			ruk_down_punch[i+2] = Game.spritesheet.getSprite(144, 32+(i*16),16,16);
			
			// Soco 03
			ruk_right_punch[i+4] = Game.spritesheet.getSprite(96, 64+(i*16),16,16);
			ruk_left_punch[i+4] = Game.spritesheet.getSprite(112, 64+(i*16),16,16);
			ruk_up_punch[i+4] = Game.spritesheet.getSprite(128, 64+(i*16),16,16);
			ruk_down_punch[i+4] = Game.spritesheet.getSprite(144, 64+(i*16),16,16);
			
			// Soco pesado
			ruk_right_punch[i+6] = Game.spritesheet.getSprite(96, 96+(i*16),16,16);
			ruk_left_punch[i+6] = Game.spritesheet.getSprite(112, 96+(i*16),16,16);
			ruk_up_punch[i+6] = Game.spritesheet.getSprite(128, 96+(i*16),16,16);
			ruk_down_punch[i+6] = Game.spritesheet.getSprite(144, 96+(i*16),16,16);
			
			// Bruno
			bruno_right_player[i] = Game.spritesheet.getSprite(32, 32+(i*16),16,16);
			bruno_left_player[i] = Game.spritesheet.getSprite(48, 32+(i*16),16,16);
			bruno_up_player[i] = Game.spritesheet.getSprite(64, 32+(i*16),16,16);
			bruno_down_player[i] = Game.spritesheet.getSprite(80, 32+(i*16),16,16);
			
			// As variaveis acima sao usadas apenas como "estoque" de imagens
			// As variaveis abaixo sao as que sao efetivamente usadas na renderizacao
			right_player[i] = ruk_right_player[i];
			left_player[i] = ruk_left_player[i];
			up_player[i] = ruk_up_player[i];
			down_player[i] = ruk_down_player[i];
			
			right_action[i] = ruk_right_punch[i];
			left_action[i] = ruk_left_punch[i];
			up_action[i] = ruk_up_punch[i];
			down_action[i] = ruk_down_punch[i];
			
		}
	}
	
	public void tick(){
		depth = 1;
		moved = false;
		if(right && World.isFree((int)(x+speed),y)) {
			moved = true;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),y)) {
			moved = true;
			x-=speed;
		}
		if(up && World.isFree(x,(int)(y-speed))){
			moved = true;
			y-=speed;
		}
		else if(down && World.isFree(x,(int)(y+speed))){
			moved = true;
			y+=speed;
		}
		
		playerAnimation();
		atualizaCam();
		if(enemyGrabbed != null) enemyGrabbedDirControl();
		dirControl();
		if(life <= 0) gameOver();
		
		
		if(!canChange) {
			changeCount++;
			if(changeCount >= changeMax) {
				canChange = true;
				changeCount = 0;
				System.out.println("Transformacao pronta!");
			}
		}
		
		if(change) {
			changeForm();
			change = false;
			canChange = false;
		}
		
		if(isRuk) {
			// Se for o Ruk
			hulkAttack();
			if(grab) {
				grab = false;
				canGrab = false;
				grabEnemy();
			}
		}else {
			// Se for o doutor Davi Bruno Breno
			brunoUse();
			if(isInvisible) {
				speed = 2;
				invisibleCount++;
				if(invisibleCount >= invisibleCountMax) {
					invisibleCount = 0;
					isInvisible = false;
					speed = 1;
				}
			invisibilityControl();
			}
		}
	}
	
	private void playerAnimation() {
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
	}

	private void atualizaCam(){
		Camera.x = Camera.clamp(x - (Game.WIDTH/2), 0, World.WIDTH*World.TILE_SIZE - Game.WIDTH);
		Camera.y = Camera.clamp(y - (Game.HEIGHT/2), 0, World.HEIGHT*World.TILE_SIZE - Game.HEIGHT);
	}
	
	private void changeForm() {
		if(isRuk) {
			isRuk = false;
			life = brunoLife;
			for(int i = 0; i < 2; i++){
				right_player[i] = bruno_right_player[i];
				left_player[i] = bruno_left_player[i];
				up_player[i] = bruno_up_player[i];
				down_player[i] = bruno_down_player[i];
			}
		}else {
			isRuk = true;
			isInvisible = false;
			speed = 1;
			life = rukLife;
			for(int i = 0; i < 2; i++){
				right_player[i] = ruk_right_player[i];
				left_player[i] = ruk_left_player[i];
				up_player[i] = ruk_up_player[i];
				down_player[i] = ruk_down_player[i];
			}
		}
	}
	
	
	// [METODOS DO RUK]
	private void grabEnemy() {
		Rectangle r = null;
		Enemy e = null;
		if(current_Dir == down_Dir) {
			r = hitboxAttack(x,y+8,16,16);
		}else if(current_Dir == up_Dir) {
			r = hitboxAttack(x,y-8,16,16);
		}else if(current_Dir == left_Dir) {
			r = hitboxAttack(x-8,y,16,16);
		}else if(current_Dir == right_Dir) {
			r = hitboxAttack(x+8,y,16,16);
		}
		if(r instanceof Enemy) {
			e = (Enemy)r;
			e.isGrabbed = true;
			enemyGrabbed = e;
		}else {
			canGrab = true;
		}
	}
	
	private void hulkAttack() {
		if(mouseLeftClick) {
			mouseLeftClick = false;
			combo++;
			comboTimer = comboMaxTimer;
			attack = true;
			meleeRenderControl(false);
			lightAtack();
		}
		else if(mouseRightClick) {
			mouseRightClick = false;
			combo++;
			comboTimer = comboMaxTimer;
			attack = true;
			meleeRenderControl(true);
			heavyAtack();
		}
		if(attack){
			attackTimer++;
			
			// Tempo de duracao do ataque
			if(attackTimer > 5){
				attack = false;
				attackTimer = 0;
			}
		}
		
		// Temporizador do flow do combo
		if(comboTimer > 0){
			comboTimer--;
		}else
			combo = 0;
		
		// Combo maximo
		if(combo > 2){
			combo = 0;
		}
	}
	
	// Executa o soco leve com sua hitbox apropriada
	private void lightAtack(){
		Rectangle r = null;
		Enemy e = null;
		Terminal t = null;
		if(current_Dir == down_Dir) {
			r = hitboxAttack(x,y+8,16,16);
		}else if(current_Dir == up_Dir) {
			r = hitboxAttack(x,y-8,16,16);
		}else if(current_Dir == left_Dir) {
			r = hitboxAttack(x-8,y,16,16);
		}else if(current_Dir == right_Dir) {
			r = hitboxAttack(x+8,y,16,16);
		}
		if(r instanceof Enemy) {
			e = (Enemy)r;
			e.life-=1;
		}
		else if(r instanceof Terminal) {
			t = (Terminal)r;
			t.state = 2;
			t.emp();
		}
	}
	
	// Executa o soco pesado com sua hitbox apropriada
	private void heavyAtack(){
		Rectangle r = null;
		Enemy e = null;
		Terminal t = null;
		if(current_Dir == down_Dir) {
			r = hitboxAttack(x,y+8,16,24);
		}else if(current_Dir == up_Dir) {
			r = hitboxAttack(x,y-16,16,24);
		}else if(current_Dir == left_Dir) {
			r = hitboxAttack(x-16,y,24,16);
		}else if(current_Dir == right_Dir) {
			r = hitboxAttack(x+8,y,24,16);
		}
		if(r instanceof Enemy) {
			e = (Enemy)r;
			e.life-=2;
		}
		else if(r instanceof Terminal) {
			t = (Terminal)r;
			if(t.state == 0) t.emp();
			t.state = 2;
		}
		combo = 0;
	}
	
	
	// Funcao que cria a hitbox com o inimigo
	private Rectangle hitboxAttack(int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(x,y,width,height);
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(rect.intersects(e)) {
				if(e instanceof Enemy) return (Enemy)e;
			}
		}
		for(int i = 0; i < Game.terminals.size(); i++) {
			Terminal t = Game.terminals.get(i);
			if(rect.intersects(t)) {
				return t;
			}
		}
		return null;
	}
	
	// Altera a renderizacao para cada tipo de golpe
	public void meleeRenderControl(boolean isHeavy) {
		if(isHeavy) {
			for(int i = 0; i < 2; i++) {
				right_action[i] = ruk_right_punch[i+6];
				left_action[i] = ruk_left_punch[i+6];
				up_action[i] = ruk_up_punch[i+6];
				down_action[i] = ruk_down_punch[i+6];
			}
		}else if(combo == 1){
			for(int i = 0; i < 2; i++) {
				right_action[i] = ruk_right_punch[i];
				left_action[i] = ruk_left_punch[i];
				up_action[i] = ruk_up_punch[i];
				down_action[i] = ruk_down_punch[i];
			}
		}else if(combo == 2){
			for(int i = 0; i < 2; i++) {
				right_action[i] = ruk_right_punch[i+2];
				left_action[i] = ruk_left_punch[i+2];
				up_action[i] = ruk_up_punch[i+2];
				down_action[i] = ruk_down_punch[i+2];
			}
		}else if(combo == 3){
			for(int i = 0; i < 2; i++) {
				right_action[i] = ruk_right_punch[i+4];
				left_action[i] = ruk_left_punch[i+4];
				up_action[i] = ruk_up_punch[i+4];
				down_action[i] = ruk_down_punch[i+4];
			}
		}
	}
	
	// Renderiza os golpes
	public void meleeRender(Graphics g){
		if(current_Dir == up_Dir) g.drawImage(up_action[index],x - Camera.x,y - Camera.y,null);
		else if(current_Dir == down_Dir) g.drawImage(down_action[index],x - Camera.x,y - Camera.y,null);
		else if(current_Dir == right_Dir) g.drawImage(right_action[index],x - Camera.x,y - Camera.y,null);
		else if(current_Dir == left_Dir) g.drawImage(left_action[index],x - Camera.x,y - Camera.y,null);
	}
	
	private void enemyGrabbedDirControl() {
		double angle = Math.toDegrees(Math.atan2(my - (getY()+8 - Camera.y), mx - (getX()+8 - Camera.x)));
		if(angle >= 45 && angle < 145){
			// baixo
			enemyGrabbed.setX((int)this.getX());
			enemyGrabbed.setY((int)this.getY() + 8);
		}else if(angle <= -45 && angle > -145){
			// cima
			enemyGrabbed.setX((int)this.getX());
			enemyGrabbed.setY((int)this.getY() - 8);
		}else if(angle >= 145 || angle <= -145){
			// esquerda
			enemyGrabbed.setX((int)this.getX() - 8);
			enemyGrabbed.setY((int)this.getY());
		}else if(angle < 45 || angle > -45){
			// direita
			enemyGrabbed.setX((int)this.getX() + 8);
			enemyGrabbed.setY((int)this.getY());
		}
	}
	// [FIM DOS METODOS DO RUK]
	
	// [METODOS DO DOUTOR DAVI BRUNO BRENO]
	private void invisibilityControl() {
		if(System.currentTimeMillis() % 2 == 0 || !isInvisible) {
			for(int i = 0; i < 2; i++){
				right_player[i] = bruno_right_player[i];
				left_player[i] = bruno_left_player[i];
				up_player[i] = bruno_up_player[i];
				down_player[i] = bruno_down_player[i];
			}
		}else {
			for(int i = 0; i < 2; i++){
				right_player[i] = bruno_invisibility_player;
				left_player[i] = bruno_invisibility_player;
				up_player[i] = bruno_invisibility_player;
				down_player[i] = bruno_invisibility_player;
			}
		}
	}
	
	private void brunoUse() {
		if(mouseLeftClick) {
			mouseLeftClick = false;
			useButton();
		}
	}
	
	private void useButton() {
		Rectangle r = null;
		Terminal t = null;
		if(current_Dir == down_Dir) {
			r = hitboxButton(x,y+16,16,16);
		}else if(current_Dir == up_Dir) {
			r = hitboxButton(x,y-16,16,16);
		}else if(current_Dir == left_Dir) {
			r = hitboxButton(x-16,y,16,16);
		}else if(current_Dir == right_Dir) {
			r = hitboxButton(x+16,y,16,16);
		}
		if(r instanceof Terminal) {
			t = (Terminal)r;
			if(t.state == 0) {
				t.state = 1;
				t.emp();
			}
		}
	}
	
	private Rectangle hitboxButton(int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(x,y,width,height);
		for(int i = 0; i < Game.terminals.size(); i++) {
			Terminal e = Game.terminals.get(i);
			if(rect.intersects(e)) {
				return (Terminal)e;
			}
		}
		return null;
	}
	// [FIM DOS METODOS DO DOUTOR DAVI BRUNO BRENO]
	
	private void dirControl(){
		double angle = Math.toDegrees(Math.atan2(my - (getY()+8 - Camera.y), mx - (getX()+8 - Camera.x)));
		if(angle >= 45 && angle < 145){
			current_Dir = down_Dir;
		}else if(angle <= -45 && angle > -145){
			current_Dir = up_Dir;
		}else if(angle >= 145 || angle <= -145){
			current_Dir = left_Dir;
		}else if(angle < 45 || angle > -45){
			current_Dir = right_Dir;
		}
	}
	
	private void gameOver() {
		Game.entities.remove(this);
	}
	
	public void render(Graphics g){
		if(attack) meleeRender(g);
		else if(current_Dir == right_Dir) g.drawImage(right_player[index],x - Camera.x,y - Camera.y,null);
		else if(current_Dir == left_Dir) g.drawImage(left_player[index],x - Camera.x,y - Camera.y,null);
		else if(current_Dir == up_Dir) g.drawImage(up_player[index],x - Camera.x,y - Camera.y,null);
		else if(current_Dir == down_Dir) g.drawImage(down_player[index],x - Camera.x,y - Camera.y,null);
	}
}
