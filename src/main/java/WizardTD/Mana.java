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
public class Mana {
    private int mana;
    private int mana_cap;
    private int mana_gained_per_second;
    private int time;
    private int mana_pool_spell_initial_cost;
    private int mana_pool_spell_cost_increase_per_use;
    private double mana_pool_spell_cap_multiplier;
    private double mana_pool_spell_mana_gained_multiplier;
    private int manapoollevel;

    public Mana(int initial_mana,int initial_mana_cap,int initial_mana_gained_per_second,int mana_pool_spell_initial_cost,int mana_pool_spell_cost_increase_per_use,double mana_pool_spell_cap_multiplier,double mana_pool_spell_mana_gained_multiplier) {
        this.mana_cap=initial_mana_cap;
        this.mana_gained_per_second=initial_mana_gained_per_second;
        this.mana=initial_mana;
        this.time=0;
        this.mana_pool_spell_initial_cost=mana_pool_spell_initial_cost;
        this.mana_pool_spell_cost_increase_per_use=mana_pool_spell_cost_increase_per_use;
        this.mana_pool_spell_cap_multiplier=mana_pool_spell_cap_multiplier;
        this.mana_pool_spell_mana_gained_multiplier=1;
        this.manapoollevel=0;

    }
    public void addMana(int mana) {
        if(mana>0) {
            this.mana += mana;
        }
    }
    public void removeMana(int mana) {
        if(mana>0 && mana<=this.mana) {
            this.mana -= mana;
        }else
        if(mana>this.mana){
            this.mana=0;
        }
    }
    public int getMana() {
        return this.mana;
    }
    public void addmana_cap(int mana){
        this.mana_cap+=mana;
    }
    public int getmana_cap(){
        return this.mana_cap;
    }
    public void updateTime(int FPS){
        if(time==FPS){
            this.mana+=this.mana_gained_per_second;
            this.time=0;
        }
        this.time++;
    }
    public void setMana(int mana){
        if(mana>=0 && mana<=this.mana_cap) {
            this.mana = mana;
        }
    }
    public void upgradepool(){
        this.mana_cap*=this.mana_pool_spell_cap_multiplier;
        this.mana_pool_spell_initial_cost+=this.mana_pool_spell_cost_increase_per_use;
        this.manapoollevel++;
    }

    public int getmanapoolspellinitialcost(){
        return this.mana_pool_spell_initial_cost;
    }
    public int getmanapoolspellcostincreaseperuse(){
        return this.mana_pool_spell_cost_increase_per_use;
    }
    public double getmanapoolspellcapmultiplier(){
        return this.mana_pool_spell_cap_multiplier;
    }
    public double getmanapoolspellmanagainedmultiplier(){
        return this.mana_pool_spell_mana_gained_multiplier;
    }
    public int getmanapoollevel(){
        return this.manapoollevel;
    }
}