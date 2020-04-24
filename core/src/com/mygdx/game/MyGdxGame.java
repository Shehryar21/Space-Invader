//Zehan Liu Shehryar Suleman
//MYGdxGame.java
//space invaders replica
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

	//////////////////// Initioalizing all the sprites, textures and other variable needed////////////////////////////////

	////Initializing ints//////////////
	int enspr=1;
	int espr=0;
	int liveleft=3;
	int bcount=0;
	int score = 0;
	int gamemenu=0;
	int level=1;

	//////////Initializing sprite/ textures/////////
	SpriteBatch batch;
	Texture img,img1,img2,img3,img4,img5,img6,live,end,bar1,bar2,ebonus,men,eb,bulltex,playertex,enemyA,playerA,playerA2;
	Sprite player,playerAnimation,playerAnimation2;
	Sprite livepic;
	Sprite bullet;
	Sprite ufo,menu ;
	Sprite enemyspr,enemyspr2,enemyspr3,enemyspr4,enemyspr5,enemyspr6,enemyAnimation;
	Sprite endscreen;
	Sprite enemybullet;
	Sprite barrier1,barrier2,barrier3;

	/////////ArrayList///////////////////
	ArrayList<enemies> enemieslist = new ArrayList<enemies>();
	ArrayList<barrier> barrierlist = new ArrayList<barrier>();

	float bulletposx=0;

	/////////////RECTS////////////////

	Rectangle brect;
	Rectangle uforect;
	Rectangle barrierRect1;
	Rectangle barrierRect2;
	Rectangle barrierRect3;
	Rectangle playerRect;

	int []shooter=new int[]{4,4,4,4,4,4,4,4,4,4,4,4};//array for the row that is shooting in eac

	/////BOOLEAN////////////////
	boolean shoot1;
	boolean shoot=false;
	////////FONTS///////////
	BitmapFont font;
	BitmapFont lives;
	BitmapFont levelf;


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void create () {
		//creates the textures, variables, and sprites
		Gdx.graphics.setWindowedMode(1024, 768);//screen
		batch = new SpriteBatch();
		playertex = new Texture("player.png");//player
		player = new Sprite(playertex);
		playerA = new Texture("playerAnim.png");//player animation pic for when its killed by enemy
		playerAnimation = new Sprite(playerA);
		playerA2 = new Texture("playerAnim2.png");//player animation pic when killed
		playerAnimation2 = new Sprite(playerA2);
		men = new Texture("menu.png");
		menu = new Sprite(men);
		end = new Texture("endgame.jpg");
		endscreen = new Sprite(end);
		bulltex=new Texture("bullet.png");
		bullet=new Sprite(bulltex);
		eb=new Texture("bullet.png");
		enemybullet=new Sprite(eb);
		live=new Texture("lives.png");
		livepic=new Sprite(live);
		enemyA=new Texture("enemyAnim.png");
		enemyAnimation=new Sprite(enemyA);
		img2=new Texture("enemy2.png");//All the different enemies texture
		img1=new Texture("enemy.png");
		img3=new Texture("enemy3.png");
		img4=new Texture("enemy4.png");
		img5=new Texture("enemy5.png");
		img6=new Texture("enemy6.png");
		bar1=new Texture("barrierBlock.png");
		bar2=new Texture("BarrierDamaged.png");
		//making different enemy sprites
		enemyspr=new Sprite(img1);
		enemyspr2=new Sprite(img2);
		enemyspr3=new Sprite(img3);
		enemyspr4=new Sprite(img4);
		enemyspr5=new Sprite(img5);
		enemyspr6=new Sprite(img6);
		//barrier sprite, one when its not damaged, 2 when it is
		barrier1 = new Sprite(bar1);//making barrier sprite
		barrier2 = new Sprite(bar2);
		ebonus=new Texture("ufo.png");
		ufo=new Sprite(ebonus);
		ufo.setX(-150);
		ufo.setY(690);
		Music music = Gdx.audio.newMusic(Gdx.files.internal("spaceinvaders1.mp3"));//background music
		music.play();

		createEnemy();// different method to create enemy objects
		createBarrier();// different method to create barrier objects

		player.setSize(100,100);//initializing widht height for player
		bullet.setY(900);// setting the starting position of bullet off screeen, so it doesnt show before

		/////Creating fonts///////////
		font = new BitmapFont();
		font.getData().setScale(2f);
		lives = new BitmapFont();
		lives.getData().setScale(2f);
		levelf = new BitmapFont();
		levelf.getData().setScale(2f);

	}

	public void createBarrier(){
		for(int d=0;d<3;d++) {//loop that makes 3 barriers with 8 barrier blocks each
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 4; j++) {
					if((i==0||i==4)||(j==2||j==3)) {//make a n shape barrier
						barrier barriers = new barrier(170 + 22 * i + 300 * d, 200 + (22 * j));//Calling barrier class to make barrier objects
						barrierlist.add(barriers);//added to list
					}

				}
			}
		}
	}


	public void createEnemy(){//creates 5 rows of 11 enemies
		for(int i=0;i<=11;i++){
			for(int j=0;j<5;j++){
				enemies enemy=new enemies(20+50*i,650-(50*j),i,j,shoot1,level);//Calling enemies class to make enemy objects
				enemieslist.add(enemy);//each added to list
			}
		}
	}

	@Override
	public void render () {
		//Music for sound effects
		Music smusic = Gdx.audio.newMusic(Gdx.files.internal("shoot.mp3"));//shooting music
		Music pmusic = Gdx.audio.newMusic(Gdx.files.internal("explosion.mp3"));//player when killed music
		Music enemyKillmusic = Gdx.audio.newMusic(Gdx.files.internal("invaderkilled.mp3"));//enemy killed music

		if(gamemenu==0){//Starting screen
			batch.begin();
			menu.draw(batch);//shows menu
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){//starts game when space is pressed
				gamemenu=1;
			}
			batch.end();
		}
		if(gamemenu==2){//Game over
			batch.begin();
			endscreen.draw(batch);//game over screen
			font.draw(batch, "Your Score: "+score, 400, 350);//score
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){//restart game when space is pressed
				gamemenu=1;
			}
			///////////Resetting all values, recreating enemy and barrier for new game////////////////
			for(int i=0;i<enemieslist.size();i++){
				enemieslist.remove(i);
			}
			for(int i=0;i<shooter.length;i++){//reset shooter array, array of row thats shooting for each column
				shooter[i]=4;
			}

			if(gamemenu==1){//checking if player pressed space

				liveleft=3;//reset lives
				for(int i=0;i<barrierlist.size();i++){
					barrierlist.remove(i);
				}//removing oldbarrier
				createBarrier();//creating new barrier
				createEnemy();
				level=1;
				score=0;
			}
			batch.end();
		}
		if(gamemenu==1){//game starts
			batch.begin();
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//player movement
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& (player.getX()+player.getWidth())<1024){
				player.translateX(5);//right
			}
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getX()>0){
				player.translateX(-5);//left
			}

			if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !shoot){//player shoot
				smusic.play();
				bullet.setY(player.getY());
				bulletposx=player.getX();
				shoot=true;
			}
			if (bullet.getY()<=768 && shoot){//bullet movement
				bullet.translateY(10);
				if(bullet.getY()>=768){
					shoot=false;
				}
			}


			brect=new Rectangle(Math.round(bulletposx)+47,Math.round(bullet.getY()+10),5,5);//Rect of bullet
			playerRect=new Rectangle(Math.round(player.getX()),Math.round(player.getY()),Math.round(player.getWidth()),Math.round(player.getHeight()));//Player Rect
			for(int i=0;i<enemieslist.size();i++){
				////////Code for changing enemy sprite//////////////
				if(enspr<25) {//draws first enemy sprite animation when counter is less than 25.
					if(enemieslist.get(i).getJ()==0) {//checking row, because each row has different sprites. if first row
						batch.draw(enemyspr3, enemieslist.get(i).getX(), enemieslist.get(i).getY());
					}
					if(enemieslist.get(i).getJ()==2||enemieslist.get(i).getJ()==1) {//if second or third row
						batch.draw(enemyspr5, enemieslist.get(i).getX(), enemieslist.get(i).getY());
					}
					if(enemieslist.get(i).getJ()==3||enemieslist.get(i).getJ()==4 ) {//if 4th or 5th row
						batch.draw(enemyspr, enemieslist.get(i).getX(), enemieslist.get(i).getY());
					}
				}
				else if(enspr<50) {//draws second enemy sprite animation when counter is less than 50 but more than 24
					if(enemieslist.get(i).getJ()==0) {
						batch.draw(enemyspr4, enemieslist.get(i).getX(), enemieslist.get(i).getY());
					}
					if(enemieslist.get(i).getJ()==2||enemieslist.get(i).getJ()==1) {
						batch.draw(enemyspr6, enemieslist.get(i).getX(), enemieslist.get(i).getY());
					}
					if(enemieslist.get(i).getJ()==3||enemieslist.get(i).getJ()==4 ) {
						batch.draw(enemyspr2, enemieslist.get(i).getX(), enemieslist.get(i).getY());
					}
				}

				if(enemieslist.get(i).getJ()==shooter[enemieslist.get(i).getI()]) {//enemy shoots bullet if its row matches the shooter array(row at most bottom in each column)
					batch.draw(enemybullet, enemieslist.get(i).getEbX(), enemieslist.get(i).getEbY());
				}
				if(enemieslist.get(i).getEbrect().intersects(playerRect)){//if player gets hit
					pmusic.play();//sound played
					enemieslist.get(i).setEb(-10000, -10000);//enemy bullet made off screen
					batch.draw(playerAnimation,player.getX(),player.getY());//player death animation
					batch.draw(playerAnimation2,player.getX(),player.getY());
					player.setX(20);//set orgiinal position
					if(liveleft>0){//subtract life
						liveleft-=1;
					}
				}
				if(enemieslist.get(i).getEbrect().intersects(brect)){//if enemy bullet hits player bullet
					//resetting bullet positions off screen for both player and enemy
					enemieslist.get(i).setEb(-10000, -10000);
					bullet.setY(1200);
					shoot=false;
				}

				if(brect.intersects(enemieslist.get(i).getRect())){//if player hits enemy
					enemyKillmusic.play();
					shooter[enemieslist.get(i).getI()]-=1;//when enemy from front row dies, change shooter array value so the one behind it start shooting
					//score for each enemy killed
					if(enemieslist.get(i).getJ()==4||enemieslist.get(i).getJ()==3){//row 4,5 enemy gets 20 points
						score+=20;
					}
					if(enemieslist.get(i).getJ()==2||enemieslist.get(i).getJ()==1){//row 2,3 enemy gets 40 points
						score+=40;
					}
					if(enemieslist.get(i).getJ()==0){//row 1 enemy gets 40 points
						score+=60;
					}

					batch.draw(enemyAnimation,enemieslist.get(i).getX(),enemieslist.get(i).getY());//animation when enemy killed
					enemieslist.remove(i);//removing enemy from list
					bullet.setY(1200);//making player bullet off screen
					shoot=false;
				}


			}
			enspr+=1;
			if(enspr==50){//count for enemy sprtes switching in order to slow it down
				enspr=0;//reset counter when it reaches 50
			}

			for(int i=0;i<barrierlist.size();i++){
				if(barrierlist.get(i).getHealth()==2){
					batch.draw(barrier1,barrierlist.get(i).getX(),barrierlist.get(i).getY());//draw normal barrier if health=2
				}
				if(barrierlist.get(i).getHealth()==1){
					batch.draw(barrier2,barrierlist.get(i).getX(),barrierlist.get(i).getY());//draw dmaged barrier if health=1
				}

				if(brect.intersects(barrierlist.get(i).getRect())){//if player bullet hits barrier
					barrierlist.get(i).setHealth();//decrease barrier health
					bullet.setY(1200);//move bullet offscreen
					shoot=false;
					if(barrierlist.get(i).getHealth()==0) {
						barrierlist.get(i).setPos();//if barrier health is 0 move offscreen
					}
				}
				for(int j=0;j<enemieslist.size();j++){
					if(enemieslist.get(j).getEbrect().intersects(barrierlist.get(i).getRect())){//when enemy bullet hits barriers
						barrierlist.get(i).setHealth();//remove barrier health
						enemieslist.get(j).setEb(-10000, -10000);//move bullet offscreen
						if(barrierlist.get(i).getHealth()==0) {
							barrierlist.get(i).setPos();//if barrier health is 0 move offscreen
						}
					}
				}

			}

			if(liveleft>0){//draw lives left
				for(int i=0;i<liveleft;i++){
					batch.draw(livepic,800+(50*i),720);
				}
			}

			for(int i=0;i<enemieslist.size();i++){
				if(enemieslist.get(i).getY()<=player.getY()+100){//game over if enemies reach player
					gamemenu=2;
				}
			}

			if(liveleft==0){
				gamemenu=2;//game over screen when all 3 lives are gone
			}
			uforect=new Rectangle(Math.round(ufo.getX()),Math.round(ufo.getY()),Math.round(ufo.getWidth()),Math.round(ufo.getHeight()));
			if(ufo.getX()>1100){//when ufo is off screen in te right
				ufo.setX(-1000);//set ufo position back to the left off screen and it moving towards screen
			}
			if(brect.intersects(uforect)){
				score+=100;//add score
				bullet.setY(1200);
				shoot=false;
				ufo.setX(-3000);//sets ufo position back when hit
			}

			if(enemieslist.size()==0){//if all enemies killed
				createEnemy();//recreate stage
				level+=1;
				for(int i=0;i<shooter.length;i++){//resets shooter array(the array that determines which row is shooting in eac column)
					shooter[i]=4;
				}
				for(int i=0;i<enemieslist.size();i++){
					enemieslist.get(i).changeLevel();//call function that increases level for each enemy which increases the movement speed
				}

			}
			ufo.translateX(6);
			ufo.draw(batch);
			player.draw(batch);
			batch.draw(bullet, bulletposx+47, bullet.getY()+55);
			///drawing font on screen///////
			font.draw(batch, "Score: "+score, 10, 750);
			lives.draw(batch, "Lives: ", 700, 750);
			levelf.draw(batch, "Level: "+level, 450, 750);
			batch.end();
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
