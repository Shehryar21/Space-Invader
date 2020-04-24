//Zehan Liu Shehryar suleman
//enemies.java
//enemies class that has functions in controlling enemies movement, speed, and bullets
package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.*;

public class enemies extends MyGdxGame{
    int x,y,i,j;
    int ebx;
    int eby;
    int oldeby;
    int oldebx;
    int end=1;
    boolean shoot;
    Rectangle erect;
    Rectangle ebrect;
    boolean down=false;
    boolean bulletshoot=true;
    int level;
    public enemies(int x, int y,int i,int j,boolean shoot,int level){//constructing enemy with parameter passed in
        this.x=x;
        this.y=y;
        this.i=i;
        this.j=j;
        this.shoot=shoot;
        this.level=level;
        ebx=-10000;
        eby=-10000;
        oldebx=x;
    }
    public Rectangle getEbrect(){//gets enemy bullet hitbox
        ebrect=new Rectangle(ebx,eby,5,5);
        return ebrect;
    }
    public void moveBullet(){

        eby-=5;//bullet movement
        if(eby<-1000 && shoot==true ){//when bullet gets off screen and soot is true
            oldebx=x;
            eby=y;//sets y to enemy position
            Shoot();//chance of shooting
        }
        if(shoot==false && eby<-1000 ){
            Shoot();//gives a not shooting enemy a cnace of shooting
        }
    }
    public int getEbX(){
        ebx=oldebx;
        return ebx;

    }
    public void changeLevel(){
        level+=1;

    }
    public int getEbY(){
        moveBullet();
        return eby;
    }
    public void move(){
        if(end==1){//moving right
            x+=(1+level);//level determines speed
            if(x+100>1024-((10-i)*50)){//reached right side
                down=true;
            }
        }
        if(end==2){//moving left
            x-=(1+level);
            if(x<(i*50)){//reached left side
                down=true;
            }
        }

        if (down && end==1) {//goes down then right if was going left
            y-=20;
            end=2;
            down=false;
        }
        if (down && end==2) {//goes down then left if was going right
            y-=20;
            end=1;
            down=false;
        }
    }
    public int getJ(){
        return j;
    }
    public int getX(){
        move();
        return x;

    }
    public int getY(){
        return y;
    }
    public int getI(){
        return i;
    }
    public void setEb(int xx,int yy){
        ebx=xx;
        eby=yy  ;
    }
    public boolean getShoot(){
        return shoot;
    }
    public void Shoot(){
        if ((int)(Math.random()*100000000)>90000000) {
            if ((int)(Math.random()*100000000)>90000000) {
                if ((int)(Math.random()*100000000)>90000000) {
                    shoot = true;//chance of shooting a bullet
                }
            }
        }
        else{
            shoot=false;
        }
    }
    public Rectangle getRect(){
        erect=new Rectangle(x,y,50,50);//enemy hitbox
        return erect;
    }

}
