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

public class Monster {
    
    private float hp;
    private int speed;
    private float armour;
    private int mana_gained_on_kill;
    private int quantity;
    private String name;
    private List<Point> MonsterPath;
    private int MonsterX;
    private int MonsterY;
    private int pathIndex = 0;
    private PImage monsterpic;
    private int i;
    //Monster movement
    private boolean isAlive;
    private boolean isexist;
    private boolean deathImagestime;
    private List<PImage> deathImages;
    int deathImagesFrame;
    private float maxHP;
    public Monster(float hp,int speed,float armour,int mana_gained_on_kill,int quantity,String name) {
        this.hp=hp;
        this.maxHP=hp;
        this.speed=speed;
        this.armour=armour;
        this.mana_gained_on_kill=mana_gained_on_kill;
        this.quantity=quantity;
        this.name=name;
        this.isAlive=true;
        this.isexist=true;
        this.deathImagestime=false;
        this.deathImages=new ArrayList<PImage>();
        this.i=0;

    }
    public void reset(){
        this.pathIndex=0;
        this.isAlive=true;
        this.isexist=true;
        this.deathImagestime=false;

    }
    public int getMonsterX() {
        return MonsterX;
    }
    public int getindex(){
        return pathIndex;
    }
    public int getMonsterY() {
        return MonsterY;
    }
    public float getMaxHP() {
        return maxHP;
    }
    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }
    public boolean getisAlive() {
        return isAlive;
    }
    public PImage getMonsterpic() {
        return monsterpic;
    }
    public void setMonsterX(int MonsterX) {
        this.MonsterX = MonsterX;
    }
    public String getMonsterposition() {
        return MonsterX+" "+MonsterY;
    }
    public void setMonsterY(int MonsterY) {
        this.MonsterY = MonsterY;
    }
    public void setMonsterpic(PImage monsterpic) {
        this.monsterpic = monsterpic;
    }
    public void setMonsterPath(List<Point> MonsterPath) {
        this.MonsterPath = MonsterPath;
    }
    public List<Point> getMonsterPath() {
        return MonsterPath;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setArmour(float armour) {
        this.armour = armour;
    }
    public void setMana_gained_on_kill(int mana_gained_on_kill) {
        this.mana_gained_on_kill = mana_gained_on_kill;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getHp() {
        return hp;
    }
    public int getSpeed() {
        return speed;
    }
    public float getArmour() {
        return armour;
    }
    public int getMana_gained_on_kill() {
        return mana_gained_on_kill;
    }
    public int getQuantity() {
        return quantity;
    }
    public void checkAlive() {
        if (hp<=0) {
            this.isAlive=false;
        }
    }
    public String getname() {
        return name;
    }
    public void MoveToTarget() {
        if (this.pathIndex < this.MonsterPath.size() ) {
            // 获取 Monster 的当前目标点
            Point targetPoint = this.MonsterPath.get(this.pathIndex);
            // 计算 Monster 在 x 和 y 方向上的移动方向
            int dx = (int) Math.signum(targetPoint.x - MonsterX);
            int dy = (int) Math.signum(targetPoint.y - MonsterY);
            // 更新 Monster 的坐标，每帧移动 moveSpeed 像素
            this.MonsterX += dx * speed;
            this.MonsterY += dy * speed;
            // 如果 Monster 到达目标点，移动到下一个目标点
            if (this.MonsterX == targetPoint.x && this.MonsterY == targetPoint.y) {
                pathIndex++;
                
            if(this.MonsterX==this.MonsterPath.get(this.MonsterPath.size()-1).x&&this.MonsterY==this.MonsterPath.get(this.MonsterPath.size()-1).y){
                this.isexist=false;
                
            }


            }
        }
    }
    public boolean isexist() {
        return isexist;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void minusMonsterhealth(int hp) {
        this.hp -= hp;
    }
    public void setdeathImages(List<PImage> deathImages) {
        this.deathImages = deathImages;
    }
    public int getdeathImagessize() {
        return deathImages.size()-1;
    }
    public int getdeathImagesi() {
        return i;
    }
    public void setdeathImages(int i) {
        this.i = i;
    }
    public PImage getdeathImages() {
        if (i<deathImages.size()-1) {
            deathImagesFrame++;
        }
        if(deathImagesFrame==4){
            i++;
            deathImagesFrame=0;
        }
        return deathImages.get(i);
        
    }
    public boolean getdeathImagestime() {
        return deathImagestime;
    }
    public void setdeathImagestime(){
        this.deathImagestime=true;
    }
    public int getdeathImagesFrame() {
        return deathImagesFrame;
    }

}

