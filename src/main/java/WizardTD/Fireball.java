package WizardTD;
import processing.core.PApplet;
import WizardTD.MonsterWaveManager;
import WizardTD.Wave;
import java.util.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.checkerframework.checker.units.qual.s;

import java.awt.image.BufferedImage;
import WizardTD.App;
import WizardTD.MonsterPathfinder.Point;
import WizardTD.Wave;

import processing.core.PApplet;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;
public class Fireball {
    private int x;
    private int y;
    private boolean isHit;
    private Monster monster;
    private Tower tower;

    public Fireball(Tower tower) {
        this.tower=tower;
        this.x=tower.getTowerX()+15;
        this.y=tower.getTowerY()+15;
        this.isHit=false;
    }
    public Tower getTower(){
        return this.tower;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void updateX(int x){
        this.x=x;
    }
    public void updateY(int y){
        this.y=y;
    }
    public void setMonster(Monster monster){
        this.monster=monster;
    }
    public Monster getMonster(){
        return this.monster;
    }
    public int getTargetX(){
        return this.monster.getMonsterX();
    }
    public int getTargetY(){
        return this.monster.getMonsterY();
    }
    public void track(int targetx,int targety){
        float dx = targetx - x;
        float dy = targety - y;
        
        // 计算到目标的总距离
        float distance=PApplet.dist(x, y, targetx, targety);
        // 计算单位向量
        float unitX = dx / distance;
        float unitY = dy / distance;
        
        // 计算每次应该移动的距离（这里是5像素）
        float moveX = unitX * 5;
        float moveY = unitY * 5;
        
        // 如果距离大于5，正常移动；否则，直接到达目标
        if (distance > 5) {
            x += moveX;
            y += moveY;
        } else {
            x = targetx;
            y = targety;
            isHit = true;
        }
    }
    public boolean isHit(){
        return this.isHit;
    }

}