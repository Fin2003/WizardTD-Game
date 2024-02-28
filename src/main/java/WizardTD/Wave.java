package WizardTD;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


import WizardTD.App;
import WizardTD.Monster;
import WizardTD.MonsterWaveManager;

public class Wave {
    private int duration; // 波次持续时间（秒）
    private double pre_wave_pause; // 暂停时间（秒）
    private List<Monster> monsterslist; // 怪物列表  
    private int wavecout;
    private boolean hasMonster;

    public Wave(int duration, double pre_wave_pause) {
        this.monsterslist = new ArrayList<>();
        this.duration = duration;
        this.pre_wave_pause = pre_wave_pause;
        this.hasMonster = false;
    }
    public void setMonster(Monster monster) {
        for (int i = 0; i < monster.getQuantity(); i++) {
            Monster newMonster = new Monster(
                monster.getHp(),
                monster.getSpeed(),
                monster.getArmour(),
                monster.getMana_gained_on_kill(),
                monster.getQuantity(),
                monster.getname()
            );
    
            monsterslist.add(newMonster);
            this.hasMonster = true;
        }
    }
    
    public void setWavecount(int wavecout) {
        this.wavecout = wavecout;
    }
    public int getWavecout() {
        return wavecout;
    }
    public String getmonsterList(){
        return monsterslist.toString();
    }
    public Monster getMonster(int index) {
        return monsterslist.get(index);
    }
    public int getMonsterSize() {
        return monsterslist.size();
    }

    public int getDuration() {
        return duration;
    }

    public double getPre_wave_pause() {
        return pre_wave_pause;
    }
    public Wave getCurrentWave() {
        return null;
    }
    public void addMonster(Monster monster) {
        Monster monster1 = new Monster(monster.getHp(),monster.getSpeed(),monster.getArmour(),monster.getMana_gained_on_kill(),monster.getQuantity(),monster.getname());
        monsterslist.add(monster1);
    }
    public boolean hasMonster() {
        return !monsterslist.isEmpty();
    }
    public void delcurrentmonster(Monster monster) {
        this.monsterslist.remove(monster);
    }
    public void removeMonster(Monster monster) {
        this.monsterslist.remove(monster);
    }

}
