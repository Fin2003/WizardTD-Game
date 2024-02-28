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
public class Tower {
    private float damagerange;
    private int damage;
    private double speed;
    private int rangelevel;
    private int damagelevel;
    private int speedlevel;
    private int towerx;
    private int towery;
    private List<Monster> monsters;
    private int initial_tower_damage;
    private int level;
    private int cooldowncount;
    private List<Fireball> fireballs;

    public Tower(float damagerange,double speed,int damage, Point position) {
        this.cooldowncount=0;
        this.level=0;
        this.rangelevel=0;
        this.damagelevel=0;
        this.speedlevel=0;
        this.initial_tower_damage=damage;
        this.speed=speed;
        this.damage=damage;
        this.damagerange=damagerange;
        Point towerposition = new Point(position.x,position.y);
        this.towerx=towerposition.x;
        this.towery=towerposition.y;
        this.monsters = new ArrayList<Monster>();
        this.fireballs = new ArrayList<Fireball>();
    }
    public void cooldowncount(){
        this.cooldowncount+=1;
    }
    public double getspeed(){
        return this.speed;
    }
    public void resetcooldowncount(){
        this.cooldowncount=0;
    }
    public void addFireball(Fireball fireball){
        this.fireballs.add(fireball);
    }
    public List<Fireball> getFireballs(){
        return this.fireballs;
    }
    public void removeFireball(Fireball fireball){
        this.fireballs.remove(fireball);
    }
    public int getcooldown(){
        return cooldowncount;
    }
    public int getDamageLevel() {
        return this.damagelevel;
    }
    public int getRangeLevel() {
        return this.rangelevel;
    }
    public int getSpeedLevel() {
        return this.speedlevel;
    }
    
    public void updamage() {
        this.damagelevel++;
        this.damage+=(this.initial_tower_damage/2);
    }
    public void upspeed() {
        this.speedlevel++;
        this.speed+=0.5;
    }
    public void uprange() {
        this.rangelevel++;
        this.damagerange+=32;
    }

    public int getLevel() {
        if(this.level>=2){
            this.level=2;
            return this.level;
        }
        while(this.damagelevel>=1&&this.rangelevel>=1&&this.speedlevel>=1)
        {
            this.level++;
            this.rangelevel-=1;
            this.damagelevel-=1;
            this.speedlevel-=1;
        }
        return this.level;
    }

    public int getDamage() {
        return this.damage;
    }
    public float getDamagerange() {
        return this.damagerange/32;
    }
    public int getTowerX() {
        return this.towerx;
    }
    public int getTowerY() {
        return this.towery;
    }
    public void addMonsterInRange(Monster monsterinput) {
        if(!monsters.contains(monsterinput)) {
            monsters.add(monsterinput);
        }
    }
    public void removeMonsterInRange(Monster monsterinput) {
        if(monsters.contains(monsterinput)) {
            monsters.remove(monsterinput);
        }
    }
    public List<Monster> getMonsterInrange(){
        return this.monsters;
    }

    public Monster getrandMonster() {
        int rand = (int)(Math.random()*monsters.size());
        return monsters.get(rand);
    }
}