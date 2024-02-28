package WizardTD;
import WizardTD.MonsterPathfinder.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.core.PImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static processing.core.PApplet.loadJSONObject;
public class AppTest {
    public boolean mousePressed;
    private Tower tower;
    public App app= new App();
    public Mana mana;
    private List<Point> path=new ArrayList<Point>();
    private List<Monster> monsters= new ArrayList<>();
    private JSONObject json;
    private String configpath;
    private List<Point> path2=new ArrayList<Point>();
    public processing.event.MouseEvent MouseEvent;

    public processing.event.KeyEvent KeyEvent;
    private KeyEvent keyEvent;

    @BeforeEach
    public void setUp() {
        tower=new Tower((45.25f*4)/32,1.5,10,new Point(0,0));
        mana = new Mana(1000, 1000, 2, 100, 150, 1.5, 1.1);
        app.setTesting(true);
        configpath="config.json";
        json=loadJSONObject(new File(configpath));
        app.jsonObject=json;
        path.add(new Point(0,1));
        path.add(new Point(0,2));
        path.add(new Point(0,3));
        path.add(new Point(1,3));
        path.add(new Point(2,3));
        path.add(new Point(3,3));
        path2.add(new Point(0,32));
        path2.add(new Point(0,64));
        path2.add(new Point(0,96));
        path2.add(new Point(32,96));
        path2.add(new Point(32,104));
        path2.add(new Point(64,96));
        path2.add(new Point(96,96));
        app.path=new ArrayList<>();
        app.path.add(path2);
    }

    //Test the tower can upgrade correctly
    @Test
    public void TestTowerUpgrade() {
        app.mana=mana;
        app.mousePressed=true;
        app.updamage=true;
        app.upgrade(tower,true);
        assertEquals(1, tower.getDamageLevel(), "line 29 Should be upgrade 1");
    }
    @Test
    public void TestTowerUpgrade2() {
        app.mana=mana;
        app.mousePressed=true;
        app.upspeed=true;
        app.uprange=true;
        app.upgrade(tower,true);
        assertEquals(1, tower.getSpeedLevel(), "line 39 should be upgrade 1");
        assertEquals(1, tower.getRangeLevel(), "line 40 Should be upgrade 1");
    }
    //Tower upgrade edge case
    @Test
    public void TestTowerUpgradeEdge() {
        app.mana=this.mana;
        app.mousePressed=true;
        app.upspeed=true;
        app.uprange=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.upspeed=true;
        app.uprange=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.upspeed=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.upspeed=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.upspeed=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.uprange=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.uprange=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.uprange=true;
        app.upgrade(tower,true);
        app.mousePressed=true;
        app.updamage=true;
        app.upgrade(tower,true);
        assertEquals(2, tower.getSpeedLevel(), "line 61 the max level should be 2 ");
        assertEquals(2, tower.getRangeLevel(), "line 62 the max level should be 2");
        assertEquals(1, tower.getLevel(), "line 63 Should be only level 1");
    }

    //Test updateMonster
    @Test
    public void TestupdateMonster() {
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path);
        monster.MoveToTarget();
        assertEquals("0 1",monster.getMonsterposition(), "line 96 Should be 0,1");
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        assertEquals("2 3",monster.getMonsterposition(), "line 101 Should be 2,3");
        assertEquals(true,monster.isexist(), "line 102 The monster now arrive, should exist");
    }
    @Test
    public void TestupdateMonster2() {
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path);
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        assertEquals("2 3",monster.getMonsterposition(), "line 121 Should be 2,3");
    }
    @Test
    public void TestupdateMonster3() {
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path);
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        monster.MoveToTarget();
        assertEquals(true,monster.isexist(), "line 140 The monster not arrive, should exist");
    }
    @Test
    public void TestupdateMonster4() {
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path);
        while(monster.isexist()){
            monster.MoveToTarget();
        }
        assertEquals(false,monster.isexist(), "line 157 The monster now arrive, shouldn't exist");
        assertEquals("3 3",monster.getMonsterposition(), "line 158 The last position should be 3,3");
    }

    //Test bottom correctly
    @Test
    public void testbotton() {
        app.pausekeyPressed=true;
        app.PAUSEkeybpressed=1;
        app.botton();
        assertEquals(true,app.pausekeyPressed, "line 172 Should be true");
        app.PAUSEkeybpressed=2;
        app.botton();
        assertEquals(false,app.pausekeyPressed, "line 176 Should be false");

        app.uprange=true;
        app.Uprange=1;
        app.botton();
        assertEquals(true,app.uprange, "line 181 Should be true");
        app.Uprange=2;
        app.botton();
        assertEquals(false,app.uprange, "line 184 Should be false");



        app.upspeed=true;
        app.Upspeed=1;
        app.botton();
        assertEquals(true,app.upspeed, "line 191 Should be true");
        app.Upspeed=2;
        app.botton();
        assertEquals(false,app.upspeed, "line 194 Should be false");


        app.updamage=true;
        app.Updamage=1;
        app.botton();
        assertEquals(true,app.updamage, "line 200 Should be true");
        app.Updamage=2;
        app.botton();
        assertEquals(false,app.updamage, "line 203 Should be false");



        app.doublespeed=true;
        app.DOUBLEspeed=1;
        app.botton();
        assertEquals(true,app.doublespeed, "line 210 Should be true");
        app.DOUBLEspeed=2;
        app.botton();
        assertEquals(false,app.doublespeed, "line 213 Should be false");

        app.upmana=true;
        app.mana=mana;
        app.botton();
        assertEquals(1500,app.mana.getmana_cap(), "line 217 Should be 1500");

        app.jsonObject=json;
        app.mouseposition.x=660;
        app.mouseposition.y=170;
        app.buildkeyPressed=false;
        app.updamage=true;
        app.upspeed=true;
        app.botton();
        assertEquals(140,app.buildcost, "line 229 Should be 140");
        app.buildcost=0;
        app.buildkeyPressed=true;
        app.botton();
        assertEquals(140,app.buildcost, "line 229 Should be 140");
    }

    //Test createwaves
    @Test
    public void testCreatewaves() {
        JSONArray waves = json.getJSONArray("waves");
        MonsterWaveManager monsterWaveManager=new MonsterWaveManager();
        app.waveManager=monsterWaveManager;
        app.Createwaves(waves);
        assertEquals(3, app.waveManager.getWaveQueue().size(), "line 214 Should be 1");
        assertEquals(10,app.waveManager.getWaveinquWave(0).getMonsterSize(),"line 215 Should be 10 monster in waves");
        assertEquals(15,app.waveManager.getWaveinquWave(1).getMonsterSize(),"line 215 Should be 15 monster in waves");
        assertEquals(40,app.waveManager.getWaveinquWave(2).getMonsterSize(),"line 215 Should be 40 monster in waves");
    }

    //Test drawFireball
    @Test
    public void testdrawFireball() {
        Tower tower=new Tower(96f,1.5,10,new Point(0,0));
        Fireball fireball=new Fireball(tower);
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(50);
        monster.setMonsterY(50);
        fireball.setMonster(monster);
        tower.getFireballs().add(fireball);
        int x=0;
        while(tower.getFireballs().isEmpty()==false){
            x++;
            app.drawFireball(tower);
        }
        assertEquals(21,x,"line 234 should be fresh 21time to the monster");
        assertEquals(true,fireball.isHit(),"line 235 should be true, the fireball is hit");
    }
    @Test
    public void testdrawFireballEdge() {
        Tower tower=new Tower(96f,1.5,10,new Point(0,0));
        Fireball fireball=new Fireball(tower);
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(50);
        monster.setMonsterY(50);
        fireball.setMonster(monster);
        tower.getFireballs().add(fireball);

        Fireball fireball2=new Fireball(tower);
        Monster monster2=new Monster(100,1,1,2,1,"gremlin");
        monster2.setMonsterX(100);
        monster2.setMonsterY(100);
        fireball2.setMonster(monster2);
        tower.getFireballs().add(fireball2);
        int x=0;
        while(tower.getFireballs().isEmpty()==false){
            x++;
            app.drawFireball(tower);
        }
        assertEquals(35,x,"line 259 should be fresh 35time to the monster");
        assertEquals(true,fireball.isHit(),"line 260 should be true, the fireball is hit");
    }

    //test Reset the game
    @Test
    public void testreset() {
        app.reset();
        assertEquals(true,app.monstergenList.isEmpty(),"line 227 Should be Empty");
        assertEquals(true,app.towerList.isEmpty(),"line 228 Should be Empty");
    }

    //test the Wavegen
    @Test
    public void testwavegen() {
        JSONArray waves = json.getJSONArray("waves");
        MonsterWaveManager monsterWaveManager=new MonsterWaveManager();
        app.waveManager=monsterWaveManager;
        app.Createwaves(waves);
        app.pausekeyPressed=false;
        app.lose=false;
        app.finished=false;
        while (!app.waveManager.getend()){
            app.Wavegen();
        }
        assertEquals(3,app.eachwave,"line 285 there are 3 waves");
        assertEquals(65,app.monstergenList.size(),"line 286 there are 65 monster in the first wave");

    }


    //test add monter to tower
    @Test
    public void testMonstertotower(){
        List<Point> path2=new ArrayList<Point>();
        path2.add(new Point(0,32));
        path2.add(new Point(0,64));
        path2.add(new Point(0,96));
        path2.add(new Point(32,96));
        path2.add(new Point(64,96));
        path2.add(new Point(96,96));
        Monster monster=new Monster(100,1,1,2,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path);
        app.monstergenList.add(monster);
        Tower tower=new Tower(96f,1.5,10,new Point(0,96));
        int n=0;
        while(n<=360){
            app.AddMonterTower(tower);
            n++;
        }
        assertEquals(1,tower.getMonsterInrange().size(),"line 311 the size should be 1");
        assertEquals(9,tower.getFireballs().size(),"line 312 should generated 40 fireballs");
        monster.setMonsterX(100);
        monster.setMonsterY(100);
        app.AddMonterTower(tower);
        assertEquals(0,tower.getMonsterInrange().size(),"line 314 the Monster should removed");
    }

    //Test createtower
    @Test
    public void createtower(){
        List<Point> path2=new ArrayList<Point>();
        path2.add(new Point(0,32));
        path2.add(new Point(0,64));
        path2.add(new Point(0,96));
        path2.add(new Point(32,96));
        path2.add(new Point(32,104));
        path2.add(new Point(64,96));
        path2.add(new Point(96,96));

        app.mana=this.mana;
        Tower tower=new Tower(96f,1.5,10,new Point(0,104));
        app.towerList=new ArrayList<>();
        app.towerList.add(tower);
        app.clickmouseposition.x=0;
        app.clickmouseposition.y=96;
        app.mousePressed=true;
        app.upspeed=true;
        app.keybpressed=1;
        app.buildkeyPressed=true;
        app.lose=false;
        app.finished=false;
        app.Createtower();
        assertEquals(980,mana.getMana(),"line 335 the mana should be 990");
        app.towerList.remove(tower);
        app.validblock=path2;
        app.mousePressed=true;
        app.clickmouseposition.x=35;
        app.clickmouseposition.y=96;
        app.upspeed=false;
        app.Createtower();
        assertEquals(880,mana.getMana(),"line 340 the mana should be 990");

    }

    //Test update Monster
    @Test
    public void testupdateMonster(){
        app.mana=mana;
        List<Point> path2=new ArrayList<Point>();
        path2.add(new Point(0,32));
        path2.add(new Point(0,64));
        path2.add(new Point(0,96));
        path2.add(new Point(32,96));
        path2.add(new Point(64,96));
        path2.add(new Point(96,96));
        Monster monster=new Monster(100,1,1,100,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path2);
        app.monstergenList.add(monster);
        app.updateMonster(app.monstergenList);
        assertEquals("0 1",monster.getMonsterposition(),"line 384 the monster should be 0 1");
        //monster.minusMonsterhealth(100);;
        app.updateMonster(app.monstergenList);
        assertEquals("0 2",monster.getMonsterposition(),"line 387 the monster should be 0 2");
        while(monster.isexist()){
            app.updateMonster(app.monstergenList);
        }
        assertEquals(false,monster.isexist(),"line 391 the monster should be false");

        monster.minusMonsterhealth(100);
        app.updateMonster(app.monstergenList);
        assertEquals(0,monster.getHp(),"line 396 the monster should be dead");
        monster.checkAlive();
        assertEquals(false,monster.isAlive(),"line 397 the monster should be dead");

    }
    @Test
    public void testupdateMonster2(){
        app.mana=mana;
        app.mana.removeMana(200);
        List<Point> path2=new ArrayList<Point>();
        path2.add(new Point(0,32));
        path2.add(new Point(0,64));
        path2.add(new Point(0,96));
        path2.add(new Point(32,96));
        path2.add(new Point(64,96));
        path2.add(new Point(96,96));
        Monster monster=new Monster(0,1,1,100,1,"gremlin");
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.setMonsterPath(path2);
        app.monstergenList.add(monster);
        monster.checkAlive();
        monster.setdeathImagestime();
        monster.setdeathImages(-1);
        app.updateMonster(app.monstergenList);
        assertEquals(900,app.mana.getMana(),"line 421 the monster should be killed and add 100 mana");
    }

    //Test upgradeUI
    @Test
    public void testupgradeUI(){
        Tower tower=new Tower(96f,1.5,10,new Point(0,104));
        app.towerList=new ArrayList<>();
        app.towerList.add(tower);
        app.mouseposition.x=0;
        app.mouseposition.y=96;
        app.uprange=true;
        app.upspeed=true;
        app.upgradeUI();
        assertEquals("[range:     20, speed:     20]",app.upgradename.toString(),"line 434 should have range and speed up");
    }

    //Test draw tower insert correctly
    @Test
    public void testdrawtower(){
        Tower tower=new Tower(96f,1.5,10,new Point(0,104));
        app.towerList=new ArrayList<>();
        app.towerList.add(tower);
        tower.updamage();
        Tower tower2=new Tower(96f,1.5,10,new Point(0,104));
        app.towerList.add(tower2);
        tower2.uprange();
        Tower tower3=new Tower(96f,1.5,10,new Point(0,104));
        app.towerList.add(tower3);
        tower3.upspeed();
        app.drawtower();
        assertEquals(true,tower.getDamageLevel()!=0,"line 451 should be pass");
        assertEquals(true,tower2.getRangeLevel()!=0,"line 452 should be pass");
        assertEquals(true,tower3.getSpeedLevel()!=0,"line 453 should be pass");
    }

    //Test mousereleased
    @Test
    public void testmouseReleased(){
        app.updamage=true   ;
        app.upspeed=true;
        app.uprange=true;
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=65;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.doublespeed,"line 467 doublespeed should be true");
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=(41+60*2)+10;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.buildkeyPressed,"line 472 doublespeed should be true");
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=(41+60*1)+10;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.pausekeyPressed,"line 477 doublespeed should be true");
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=(41+60*3)+10;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.uprange,"line 482 doublespeed should be true");
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=(41+60*4)+10;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.upspeed,"line 486 doublespeed should be true");
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=(41+60*5)+10;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.updamage,"line 490 doublespeed should be true");
        app.clickmouseposition.x=670;
        app.clickmouseposition.y=(41+60*6)+10;
        app.mouseReleased(MouseEvent);
        assertEquals(true,app.upmana,"line 494 doublespeed should be true");
    }

    //testkeypressed
    @Test
    public void testkeypressed(){
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, 't', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.buildkeyPressed,"line 502 buildkeyPressed should be true");
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, 'P', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.pausekeyPressed,"line 505 pausekeyPressed should be true");
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, 'f', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.doublespeed,"line 508 doublespeed should be true");
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, '1', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.uprange,"line 511 uprange should be true");
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, '2', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.upspeed,"line 514 upspeed should be true");
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, '3', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.updamage,"line 517 updamage should be true");
        keyEvent = new processing.event.KeyEvent(KeyEvent, 0, 0, 0, 'm', 0);
        app.keyPressed(keyEvent);
        assertEquals(true,app.upmana,"line 520 upmana should be true");
    }

    //Test drawmap
    @Test
    public void testdrawmap(){
        String struct = "      S       S X SS\n" +
                "S  S   S    S   X   \n" +
                "     S  S XXXXXXXXXX\n" +
                "   S   S  X        S\n"+
                "           W  S S   \n"+
                "     S  S      S  S \n";
        String[] map = struct.split("\n");
        app.lines=map;
        app.drawMap();
        assertEquals(6,app.lines.length,"line 542 the map should be 6 lines");
    }

    //Test setup
    @Test
    public void testsetup(){
        app.jsonObject=json;
        app.setup();
        assertEquals("level2.txt",app.level,"line 550 the level6 should be level2");
    }
    //.\gradlew test
    //.\gradlew clean test
    //./gradlew test jacocoTestReport
}
