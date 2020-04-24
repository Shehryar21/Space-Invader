//Zehan Liu Shehryar Suleman
//barrier.java
//barrier class that has function that controlhealth and position
package com.mygdx.game;
import java.awt.*;
public class barrier extends MyGdxGame {
    int x,y,health;

    Rectangle brect;
    public barrier(int x,int y){
        this.x=x;
        this.y=y;
        health=2;
    }
    public int getX(){
        return x;
    }

    public void setPos(){//moves it off screen
        x=-1000;

    }
    public int getHealth(){
        return health;
    }
    public int setHealth(){
        health-=1;//reduce health
        return health;
    }
    public int getY(){
        return y;
    }
    public Rectangle getRect(){
        brect=new Rectangle(x,y,22,22);//barrier hitbox
        return brect;
    }
}
