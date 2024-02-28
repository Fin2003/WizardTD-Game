package WizardTD;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class MonsterWaveManager {
    private Queue<Wave> waveQueue; // 波次队列
    private List<Monster> monsters; // 存活的怪物列表
    private int waveTimer; // 波次计时器
    private double reactionTime; // 反应时间计时器
    private boolean reactionTimeActive; // 是否处于反应时间状态
    private Wave currentWave; // 当前波次

    private boolean end;
    public MonsterWaveManager() {
        waveQueue = new LinkedList<>();
        monsters = new ArrayList<>();
        waveTimer = 0;
        reactionTime = 0;
        reactionTimeActive = false;
        end=false;
    }
    public void setend(){
        this.end=true;
    }
    public boolean getend(){
        return end;
    }
    public Queue<Wave> getWaveQueue() {
        return waveQueue;
    }
    public void addWave(Wave wave) {
        waveQueue.offer(wave);
    }

    public List<Monster> getMonsters() {
        // 清空之前的存活怪物列表
        monsters.clear();
        
        // 遍历波次队列，获取每个波次中的怪物
        for (Wave wave : waveQueue) {
            for (int i = 0; i < wave.getMonsterSize(); i++) {
                monsters.add(wave.getMonster(i));
            }
        }

        return monsters;
    }

    public void switchToNextWave() {
        if (!waveQueue.isEmpty()) {
            currentWave = waveQueue.poll();
            // 计时器重置为零
            waveTimer = 0;
            reactionTime = currentWave.getPre_wave_pause()*60; // 设置反应时间为波次的暂停时长
            reactionTimeActive = true;
        } else {
            // 所有波次已经结束
            currentWave = null;
        }
    }

    public boolean isWaveActive() {
        return !waveQueue.isEmpty();
    }

    public boolean isReactionTimeActive() {
        return reactionTimeActive;
    }

    public Wave getCurrentWave() {
        if (!waveQueue.isEmpty()) {
            return waveQueue.peek(); // 返回当前波次
        }
        return null;
    }
    public double getreactiontime(){
        return reactionTime;
    }
    public boolean hasWavesRemaining() {
        return !waveQueue.isEmpty();
    }
    public Wave getWaveinquWave(int i){
        return waveQueue.toArray(new Wave[waveQueue.size()])[i];
    }
    public boolean hasnexwave(){
        return waveQueue.size()>1;
    }
    public boolean thelastwave(){
        return waveQueue.size()<=1;
    }

}
