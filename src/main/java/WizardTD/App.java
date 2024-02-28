package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;
import processing.event.KeyEvent;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import WizardTD.MonsterPathfinder.Point;
public class App extends Monster {
    public int buildcost;
    public List<String> upgradename=new ArrayList<>();
    private boolean isTesting = false;
    public static final int CELLSIZE = 32;
    public static final int SIDEBAR = 120;
    public static final int TOPBAR = 40;
    public static final int BOARD_WIDTH = 20;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE+TOPBAR;

    public static final int FPS = 60;
    public static Mana mana;
    public String configPath;
    public Random random = new Random();
    public JSONObject jsonObject;
    public static float diagonal_length=45.25f;
    public static PImage grass;
    public static PImage tree;
    public static PImage road1;
    public static PImage road2;
    public static PImage road3;
    public static PImage road4;
    public static PImage house;
    public static PImage gremlin;
    public static PImage beetle;
    public static PImage worm;
    public static PImage fireball;
    public static Point position=new Point(0,0);
    public static String level;
    public static String struct;
    public List<Point> MonsterPath; // Monster 的路径
    public List<List<Point>> path;
    public List<Point> validblock=new ArrayList<>();
    private List<Fireball> Fireballslist=new ArrayList<>();
    private int Speed; // 设置移动速度，每帧移动的像素数
    public MonsterWaveManager waveManager;
    private Wave wave;
    private Monster monster;
    List<Monster> monstergenList = new ArrayList<>();
    public static KeyEvent e;
    public static char keyboardpressed;
    public static Point mouseposition=new Point(0,0);
    public static Point clickmouseposition=new Point(0,0);
    public static List<PImage> deathImages = new ArrayList<>();
    public static List<PImage> towerImages = new ArrayList<>();
    Point MonsterDeathposition=new Point(0,0);
    List<Point> Monsterdeathlist=new ArrayList<>();
    List<Tower> towerList = new ArrayList<>();
    public static Point towerposition=new Point(0,0);
    boolean finished=false;
    int numm=1;
    int fireballframe=0;
    int deathImagesIndex = 0;
    public String[] lines;
    int monstergenerateFrame=0;
    int currentWaveFrame = 0; // 当前波次内的帧计数
    int wavecount=0;
    int monstercount=0;
    int indexofmonster=0;
    boolean waveInProgress = false; // 是否处于波次生成中
    int num=0;
    int index=0;
    int destorynum=0;
    int length;
    Monster tempMonster;
    int destoryhouse=0;
    boolean buildkeyPressed=false;
    boolean pausekeyPressed=false;
    int PAUSEkeybpressed=0;
    int eachwave=0;
    boolean lose;
    boolean doublespeed=false;
    int DOUBLEspeed=0;
    boolean uprange=false;
    int Uprange=0;
    boolean updamage=false;
    int Updamage=0;
    boolean upspeed=false;
    int Upspeed=0;
    boolean upmana=false;
    int Upman=0;
    int manapoolcost=100;
    int towercost=100;
    int keybpressed=0;
    int mouspreeseed=0;
    int prewavepase;
    String[] labels = {"FF", "P", "T", "U1", "U2", "U3", "M"};
    String[] descriptions = {
            "2x speed", "PAUSE", "Build",
            "Upgrade", "Upgrade", "Upgrade",
            "Mana pool"
        };
    String[] secondline={"tower", "range" , "speed" ,"damage","cost: "};
    int levelnum=0;

	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
	@Override
    public void setup() {
        if(!isTesting){
            frameRate(FPS);
            this.jsonObject = loadJSONObject(this.configPath);
            deathImages.add(loadImage("src/main/resources/WizardTD/gremlin1.png"));
            deathImages.add(loadImage("src/main/resources/WizardTD/gremlin2.png"));
            deathImages.add(loadImage("src/main/resources/WizardTD/gremlin3.png"));
            deathImages.add(loadImage("src/main/resources/WizardTD/gremlin4.png"));
            towerImages.add(loadImage("src/main/resources/WizardTD/tower0.png"));
            towerImages.add(loadImage("src/main/resources/WizardTD/tower1.png"));
            towerImages.add(loadImage("src/main/resources/WizardTD/tower2.png"));
            grass=loadImage("src/main/resources/WizardTD/grass.png");
            tree=loadImage("src/main/resources/WizardTD/shrub.png");
            road1=loadImage("src/main/resources/WizardTD/path0.png");
            road2=loadImage("src/main/resources/WizardTD/path1.png");
            road3=loadImage("src/main/resources/WizardTD/path2.png");
            road4=loadImage("src/main/resources/WizardTD/path3.png");
            house=loadImage("src/main/resources/WizardTD/wizard_house.png");
            gremlin=loadImage("src/main/resources/WizardTD/gremlin.png");
            beetle=loadImage("src/main/resources/WizardTD/beetle.png");
            worm=loadImage("src/main/resources/WizardTD/worm.png");
            fireball=loadImage("src/main/resources/WizardTD/fireball.png");
        }
        // Load images during setup
		// Eg:
        // loadImage("src/main/resources/WizardTD/tower0.png");
        // loadImage("src/main/resources/WizardTD/tower1.png");
        // loadImage("src/main/resources/WizardTD/tower2.png");
        if (levelnum==0 && this.lose==false){
            System.out.println("erererererer");
            level=jsonObject.getString("layout");
            levelnum++;
        }
        else{
            level="level"+levelnum+".txt";
        }
        if(lose){
            this.lose=false;
            level=jsonObject.getString("layout");
        }
        mana=new Mana(jsonObject.getInt("initial_mana"),jsonObject.getInt("initial_mana_cap"),jsonObject.getInt("initial_mana_gained_per_second"),jsonObject.getInt("mana_pool_spell_initial_cost"),jsonObject.getInt("mana_pool_spell_cost_increase_per_use"),jsonObject.getDouble("mana_pool_spell_cap_multiplier"),jsonObject.getDouble("mana_pool_spell_mana_gained_multiplier"));
        String file = level;

        try {
            // Create a FileInputStream for the file
            FileInputStream fileInputStream = new FileInputStream(new File(file));

            // Create a Scanner to read the content of the file
            Scanner scanner = new Scanner(fileInputStream);

            // Use a StringBuilder to build the string from the file content
            StringBuilder stringBuilder = new StringBuilder();

            // Read the file line by line and append it to the StringBuilder
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(System.lineSeparator()); // Add newline character if needed
            }

            // Close the resources
            scanner.close();
            fileInputStream.close();

            // Convert the StringBuilder to a String
            struct = stringBuilder.toString();
            lines = struct.split("\n");  
            for(int i=0;i<lines.length;i++){
                if (lines[i].length() < 21) {
                    int numSpacesNeeded = 21 - lines[i].length(); 
                    for (int j = 1; j < numSpacesNeeded; j++) {
                        lines[i] += " "; 
                    }
                }
            }
            path=WizardTD.MonsterPathfinder.main(args);

            
        } catch (IOException e) {
            e.printStackTrace();
        }    
        waveManager = new MonsterWaveManager();
        
        JSONArray waves = jsonObject.getJSONArray("waves");
        Createwaves(waves);
    }
    /**
     * Receive key pressed signal from the keyboard.
     */
    
	@Override
    public void keyPressed(KeyEvent e){
        keyboardpressed=e.getKey();   
        if(keyboardpressed=='t'||keyboardpressed=='T'){
            keybpressed++;
            buildkeyPressed=true;
            }
        if(keyboardpressed=='p'||keyboardpressed=='P'){
            pausekeyPressed=true;
            PAUSEkeybpressed++;
            }
            if(keyboardpressed=='f'||keyboardpressed=='F'){
            doublespeed=true;
            DOUBLEspeed++;
            }
            //upgrade
            if(keyboardpressed=='1'||keyboardpressed=='1'){
            uprange=true;
            Uprange++;
            }
            if(keyboardpressed=='2'||keyboardpressed=='2'){
            upspeed=true;
            Upspeed++;
            }
            if(keyboardpressed=='3'||keyboardpressed=='3'){
            updamage=true;
            Updamage++;
            }
            if(keyboardpressed=='M'||keyboardpressed=='m'){
            upmana=true;
            Upman++;
            }
        }

    /**
     * Receive key released signal from the keyboard.
     * 
     */
	@Override
    public void keyReleased(KeyEvent e){
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickmouseposition.x=e.getX();
        clickmouseposition.y=e.getY();
        mousePressed=true;
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41 && clickmouseposition.y <= 82){
            doublespeed=true;
            DOUBLEspeed++;
            
        }
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41+60*2 && clickmouseposition.y <= 82+60*2){
            keybpressed++;
            buildkeyPressed=true;
        }
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41+60*1 && clickmouseposition.y <= 82+60*1){
            pausekeyPressed=true;
            PAUSEkeybpressed++;
        }
        //upgrade
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41+60*3 && clickmouseposition.y <= 82+60*3){
            uprange=true;
            Uprange++;
        }
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41+60*4 && clickmouseposition.y <= 82+60*4){
            upspeed=true;
            Upspeed++;
        }
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41+60*5 && clickmouseposition.y <= 82+60*5){
            updamage=true;
            Updamage++;
        }
        if(clickmouseposition.x >= 649 && clickmouseposition.x <= 690 && clickmouseposition.y >= 41+60*6 && clickmouseposition.y <= 82+60*6){
            upmana=true;
            Upman++;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseposition.x=e.getX();
        mouseposition.y=e.getY();
        
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseposition.x=e.getX();
        mouseposition.y=e.getY();
    }
    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {;
    for(int time=0;time<numm;time++){
        //add mana pre second
        if(mana.getMana()<mana.getmana_cap()&&(!pausekeyPressed)&&!lose&&!finished){
            mana.updateTime(FPS);
        }
        if(mana.getMana()>=mana.getmana_cap()&&(!pausekeyPressed)&&!lose&&!finished){
            mana.setMana(mana.getmana_cap());
        }

    //draw path & Map
        noStroke();
        fill(255);
        rect(0,0,WIDTH,HEIGHT);
        drawMap();        
    //draw the tower
    drawtower();
    //If game finished
        if(finished){
            fill(255,0,255);
            textSize(30);
            stroke(3);
            text("YOU WIN", 300, 300);
            textSize(15);
            text("Press 'E' to the next level", 300, 350);
            if(keyboardpressed=='E'||keyboardpressed=='e'){
                reset();
                setup();
            }
        }
        
        //System.out.println(monstergenList.size());
        updateMonster(monstergenList);
        if(towerList.size()>0){
            for(int x=0;x<towerList.size();x++){
                //put the monster in range into the tower
                AddMonterTower(towerList.get(x));
                //Move fireball and attack monster
                drawFireball(towerList.get(x));
                //see the attack range
                if(mouseposition.x/CELLSIZE * CELLSIZE==towerList.get(x).getTowerX()&&mouseposition.y/CELLSIZE * CELLSIZE+8==towerList.get(x).getTowerY()){
                    stroke(255,255,0);
                    strokeWeight(1);
                    noFill(); 
                    ellipse((towerList.get(x).getTowerX()+15), (towerList.get(x).getTowerY()+15), towerList.get(x).getDamagerange()*32*2, towerList.get(x).getDamagerange()*32*2);
                }
                    if (towerList.get(x).getTowerX() == mouseposition.x / CELLSIZE * CELLSIZE &&
                        towerList.get(x).getTowerY() == mouseposition.y / CELLSIZE * CELLSIZE + 8) {
                        upgrade(towerList.get(x), true);
            }
            }
        }
        /*for (Tower existingTower : towerList) {
                //put the monster in range into the tower
                AddMonterTower(existingTower);
                //Move fireball and attack monster
                drawFireball(existingTower);
                //see the attack range
                if(mouseposition.x/CELLSIZE * CELLSIZE==existingTower.getTowerX()&&mouseposition.y/CELLSIZE * CELLSIZE+8==existingTower.getTowerY()){
                    stroke(255,255,0);
                    strokeWeight(1);
                    noFill(); 
                    ellipse((existingTower.getTowerX()+15), (existingTower.getTowerY()+15), existingTower.getDamagerange()*32*2, existingTower.getDamagerange()*32*2);
                }
                    if (existingTower.getTowerX() == mouseposition.x / CELLSIZE * CELLSIZE &&
                        existingTower.getTowerY() == mouseposition.y / CELLSIZE * CELLSIZE + 8) {
                        upgrade(existingTower, true);
            }
        }*/
        //sidebar
        noStroke();
        fill(131,111,75); 
        rect(0, 0, 640, 40); 
        fill(131,111,75);
        rect(640, 0, 120, 680);
        
        Wavegen();
        //draw the house
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                char tile = line.charAt(x);
                if (tile == 'W') {
                    image(house, x * CELLSIZE - 8, y * CELLSIZE + TOPBAR - 8);
                }
            }
        }

        ui();
        //Create tower
        Createtower();
        //Show upgrade info
        upgradeUI();
        //Upgrade tower without "T"
        
        botton();
        //Draw mana bar
        stroke(0);
        strokeWeight(2);
        fill(255);
        rect(400,10,300,20);
        
        fill(4,208,214);
        double manabar=((double)mana.getMana()/mana.getmana_cap())*300; 
        int intmana=(int)manabar;
        if(intmana<300){
            rect(400,10,intmana,20);
        }else{
            rect(400,10,300,20);
        }
        //Draw mana bar text
        fill(0);
        textSize(20);
        stroke(3);
        String infoString=String.format("%s/%d",mana.getMana(),mana.getmana_cap());
        text(infoString,500,28);
        }
        
}
    public void bottondraw(int i,String text){
        fill(255, 255, 0);
        rect(649, 41 + 60 * i, 41, 41);
        fill(0);
        textSize(25);
        text(text, 655, 70 + 60 * i);
    }
    public void bottondraw2(int i,String text){
        fill(128, 128, 128);
        rect(649, 41 + 60 * i, 41, 41);
        fill(0);
        textSize(25);
        text(text, 655, 70 + 60 * i);
    }
    public void botton(){
        if((pausekeyPressed==true)){
            if((PAUSEkeybpressed<2)){
                if (!isTesting){
                    bottondraw(1,"P");
                }
            }else{
                pausekeyPressed=false;
                PAUSEkeybpressed=0;
                keyboardpressed='\0';
                clickmouseposition.x=0;
                clickmouseposition.y=0;
            }
        }
            if((uprange==true)){
            if((Uprange<2)){
                if (!isTesting){
                    bottondraw(3,"U1");
                }
            }else{
                uprange=false;
                Uprange=0;
                keyboardpressed='\0';
                clickmouseposition.x=0;
                clickmouseposition.y=0;
            }
        }
        if((upspeed==true)){
            if((Upspeed<2)){
                if (!isTesting){
                    bottondraw(4,"U2");
                }
            }else{
                upspeed=false;
                Upspeed=0;
                keyboardpressed='\0';
                clickmouseposition.x=0;
                clickmouseposition.y=0;
            }
        }
        if((updamage==true)){
            if((Updamage<2)){
                if (!isTesting){
                    bottondraw(5,"U3");
                }
            }else{
                updamage=false;
                Updamage=0;
                keyboardpressed='\0';
                clickmouseposition.x=0;
                clickmouseposition.y=0;
            }
        }

        if((doublespeed==true)){
            if((DOUBLEspeed<2)){
            numm=2;
                if (!isTesting){
                    bottondraw(0,"FF");
                }
            }else{
                numm=1;
                doublespeed=false;
                DOUBLEspeed=0;
                keyboardpressed='\0';
                clickmouseposition.x=0;
                clickmouseposition.y=0;
            }
        }
        if((upmana==true)){
            if (!isTesting){
                bottondraw(6,"M");
            }
            if(mana.getMana()>=mana.getmanapoolspellinitialcost()){
                mana.removeMana(mana.getmanapoolspellinitialcost());
                mana.upgradepool();
            }
            upmana=false;
            keyboardpressed='\0';
            clickmouseposition.x=0;
            clickmouseposition.y=0;
        }
        if(mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 && mouseposition.y <= 82 &&(!doublespeed)){
            if (!isTesting){
                fill(128, 128, 128);
                rect(649, 41, 41, 41);
                fill(0);
                textSize(25);
                text("FF", 655, 70);
            }
             //return 0;
            
        }
        if(mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41+60*2 && mouseposition.y <= 82+60*2 &&(!buildkeyPressed)){
            if (!isTesting){
                fill(128, 128, 128);
                rect(649, 41 + 60 * 2, 41, 41);
                fill(0);
                textSize(25);
                text("T", 655, 70 + 60 * 2);
                stroke(0);
                fill(255);
                rect(593, (41 + 60 * 2), 50, 20);
                fill(0);
                textSize(10);
            }
            int cost=jsonObject.getInt("tower_cost");
            if(updamage){
                cost+=20;
            }
            if(upspeed){
                cost+=20;
            }
            if(uprange){
                cost+=20;
            }
            if (!isTesting){
                text("Cost: " + (cost), 595, 55 + 60 * 2);
                noStroke();
            }

            if(isTesting){
                buildcost=cost;
            }
            
        }else if(mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41+60*2 && mouseposition.y <= 82+60*2){
            if (!isTesting){
                stroke(0);
                fill(255);
                rect(593, (41 + 60 * 2), 50, 20);
                fill(0);
                textSize(10);
            }
            int cost=jsonObject.getInt("tower_cost");
            if(updamage){
                cost+=20;
            }
            if(upspeed){
                cost+=20;
            }
            if(uprange){
                cost+=20;
            }
            if (!isTesting){
                text("Cost: " + (cost), 595, 55 + 60 * 2);
                noStroke();
            }

            if(isTesting){
                buildcost=cost;
            }
        }
        if (!isTesting){
            if (mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 + 60 * 1 && mouseposition.y <= 82 + 60 * 1 && (!pausekeyPressed)) {
                fill(128, 128, 128);
                rect(649, 41 + 60 * 1, 41, 41);
                fill(0);
                textSize(25);
                text("P", 655, 70 + 60 * 1);
                //return 1;
            }
            //upgrade
            if (mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 + 60 * 3 && mouseposition.y <= 82 + 60 * 3 && (!uprange)) {
                fill(128, 128, 128);
                rect(649, 41 + 60 * 3, 41, 41);
                fill(0);
                textSize(25);
                text("U1", 655, 70 + 60 * 3);
                //return 3;
            }
            if (mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 + 60 * 4 && mouseposition.y <= 82 + 60 * 4 && (!upspeed)) {
                fill(128, 128, 128);
                rect(649, 41 + 60 * 4, 41, 41);
                fill(0);
                textSize(25);
                text("U2", 655, 70 + 60 * 4);
                //return 4;
            }
            if (mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 + 60 * 5 && mouseposition.y <= 82 + 60 * 5 && (!updamage)) {
                fill(128, 128, 128);
                rect(649, 41 + 60 * 5, 41, 41);
                fill(0);
                textSize(25);
                text("U3", 655, 70 + 60 * 5);
                //return 5;
            }
            if (mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 + 60 * 6 && mouseposition.y <= 82 + 60 * 6 && (!upmana)) {
                fill(128, 128, 128);
                rect(649, 41 + 60 * 6, 41, 41);
                fill(0);
                textSize(25);
                text("M", 655, 70 + 60 * 6);
                stroke(0);
                fill(255);
                rect(593, (41 + 60 * 6), 50, 20);
                fill(0);
                textSize(10);
                text("Cost: " + mana.getmanapoolspellinitialcost(), 595, 55 + 60 * 6);
                noStroke();
            } else if (mouseposition.x >= 649 && mouseposition.x <= 690 && mouseposition.y >= 41 + 60 * 6 && mouseposition.y <= 82 + 60 * 6) {
                stroke(0);
                fill(255);
                rect(593, (41 + 60 * 6), 50, 20);
                fill(0);
                textSize(10);
                text("Cost: " + mana.getmanapoolspellinitialcost(), 595, 55 + 60 * 6);
                noStroke();
            }
        }
    }
    
    public void reset(){
        monstergenList.clear();
        Monsterdeathlist.clear();
        towerList.clear();
        Fireballslist.clear();
        position=new Point(0,0);
        //public static JSONObject configObject;
        validblock=new ArrayList<>();
        Fireballslist=new ArrayList<>();
        monstergenList = new ArrayList<>();
        mouseposition=new Point(0,0);
        clickmouseposition=new Point(0,0);
        deathImages = new ArrayList<>();
        towerImages = new ArrayList<>();
         MonsterDeathposition=new Point(0,0);
        Monsterdeathlist=new ArrayList<>();
        towerList = new ArrayList<>();
        towerposition=new Point(0,0);
         numm=1;
         fireballframe=0;
         deathImagesIndex = 0;
         monstergenerateFrame=0;
         currentWaveFrame = 0; // 当前波次内的帧计数
         wavecount=0;
         monstercount=0;
         indexofmonster=0;
         waveInProgress = false; // 是否处于波次生成中
         num=0;
         index=0;
         destorynum=0;
         destoryhouse=0;
         buildkeyPressed=false;
         pausekeyPressed=false;
         PAUSEkeybpressed=0;
         eachwave=0;
         doublespeed=false;
         DOUBLEspeed=0;
         uprange=false;
         Uprange=0;
         updamage=false;
         Updamage=0;
         upspeed=false;
         Upspeed=0;
         upmana=false;
         Upman=0;
         manapoolcost=100;
         towercost=100;
         keybpressed=0;
         mouspreeseed=0;
        finished=false;
    }

    public void drawMap(){
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                if(!isTesting){
                    image(grass, y * CELLSIZE, x * CELLSIZE + TOPBAR);
                }
            }      
        }
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                char tile = line.charAt(x);
                switch(tile){
                    case ' ':
                        validblock.add(new Point(x*CELLSIZE,y*CELLSIZE+TOPBAR));
                        break;
                    case 'S':
                    if(!isTesting){
                        image(tree, x * CELLSIZE, y * CELLSIZE + TOPBAR);
                    }
                        break;
                    case 'X':
                        try{
                            if ((line.charAt(x)==line.charAt(x+1))||(line.charAt(x)==line.charAt(x-1))){
                                if ((lines[y].charAt(x)!=lines[y+1].charAt(x))&&(lines[y].charAt(x)!=lines[y-1].charAt(x))){
                                    if(!isTesting){
                                        image(road1, x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                    }
                                }else{
                                    if((line.charAt(x-1)==line.charAt(x+1))&&(lines[y-1].charAt(x)==lines[y+1].charAt(x))){
                                        if(!isTesting){
                                            image(road4, x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }

                                    }
                                    if((lines[y-1].charAt(x)==line.charAt(x+1))&&(lines[y-1].charAt(x)!=lines[y+1].charAt(x))&&(line.charAt(x+1)==' ')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road2, 0), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((lines[y+1].charAt(x)==line.charAt(x+1))&&(lines[y-1].charAt(x)!=lines[y+1].charAt(x))&&(line.charAt(x+1)==' ')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road2, 90), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((lines[y+1].charAt(x)==line.charAt(x-1))&&(lines[y-1].charAt(x)!=lines[y+1].charAt(x))&&(line.charAt(x-1)==' ')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road2, 180), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((lines[y+1].charAt(x)==line.charAt(x+1))&&(lines[y-1].charAt(x)!=lines[y+1].charAt(x))&&(line.charAt(x-1)==' ')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road2, -90), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((line.charAt(x-1)==line.charAt(x+1))&&(lines[y-1].charAt(x)!=lines[y+1].charAt(x))&&(lines[y+1].charAt(x)=='X')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road3, 0), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((line.charAt(x-1)!=line.charAt(x+1))&&(lines[y-1].charAt(x)==lines[y+1].charAt(x))&&(line.charAt(x-1)=='X')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road3, 90), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((line.charAt(x-1)==line.charAt(x+1))&&(lines[y-1].charAt(x)!=lines[y+1].charAt(x))&&(lines[y-1].charAt(x)=='X')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road3, 180), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                    if((line.charAt(x-1)!=line.charAt(x+1))&&(lines[y-1].charAt(x)==lines[y+1].charAt(x))&&(line.charAt(x+1)=='X')){
                                        if(!isTesting){
                                            image(rotateImageByDegrees(road3, -90), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                        }
                                    }
                                }
                            }else{
                                if ((lines[y].charAt(x)==lines[y+1].charAt(x))||(lines[y].charAt(x)==lines[y-1].charAt(x))){
                                    if(!isTesting){
                                        image(rotateImageByDegrees(road1, 90), x * CELLSIZE, y * CELLSIZE + TOPBAR);
                                    }
                                }
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("here");
                            break;
                        }catch (StringIndexOutOfBoundsException e) {
                            System.out.println("here");
                            break;
                        }
                        break;
                }
                
                
            }
        }

    }

    public void Createwaves(JSONArray waves){
        for (int i = 0; i < waves.size(); i++) {
            JSONObject waveObject = waves.getJSONObject(i);
            int duration = waveObject.getInt("duration");
            double pre_wave_pause = waveObject.getDouble("pre_wave_pause");
            wave = new Wave(duration, pre_wave_pause);
            JSONArray monsterTypes = waveObject.getJSONArray("monsters");
            waveManager.addWave(wave);
            for(int j=0;j<monsterTypes.size();j++){
                JSONObject monsterObject = monsterTypes.getJSONObject(j);
                int quantity = monsterObject.getInt("quantity");
                int hp=monsterObject.getInt("hp");
                Speed=monsterObject.getInt("speed");
                String type=monsterObject.getString("type");
                int armour=monsterObject.getInt("armour");
                int mana_gained_on_kill=monsterObject.getInt("mana_gained_on_kill");
                monster=new Monster(hp,Speed,armour,mana_gained_on_kill,quantity,type);
                wave.setMonster(monster);
                wave.setWavecount(monsterTypes.size());
                for (int n=0;n<wave.getMonsterSize();n++){
                            MonsterPath = path.get(random.nextInt(path.size()));
                            wave.getMonster(n).setdeathImages(deathImages);
                            wave.getMonster(n).setMonsterPath(MonsterPath);
                        if (wave.getMonster(n).getMonsterPath().get(0).x == 0) {
                            wave.getMonster(n).setMonsterX(wave.getMonster(n).getMonsterPath().get(0).x - 32);
                            wave.getMonster(n).setMonsterY(wave.getMonster(n).getMonsterPath().get(0).y);

                        } else if (wave.getMonster(n).getMonsterPath().get(0).x == 608) {
                            wave.getMonster(n).setMonsterX(wave.getMonster(n).getMonsterPath().get(0).x + 32);
                            wave.getMonster(n).setMonsterY(wave.getMonster(n).getMonsterPath().get(0).y);

                        } else if (wave.getMonster(n).getMonsterPath().get(0).y == 0) {
                            wave.getMonster(n).setMonsterY(wave.getMonster(n).getMonsterPath().get(0).y - 32);
                            wave.getMonster(n).setMonsterX(wave.getMonster(n).getMonsterPath().get(0).x);

                        } else if (wave.getMonster(n).getMonsterPath().get(0).y == 608) {
                            wave.getMonster(n).setMonsterY(wave.getMonster(n).getMonsterPath().get(0).y);
                            wave.getMonster(n).setMonsterX(wave.getMonster(n).getMonsterPath().get(0).x);

                        }
                        if (wave.getMonster(n).getname().equals("gremlin")) {
                            wave.getMonster(n).setMonsterpic(gremlin);
                        }

                        if (wave.getMonster(n).getname().equals("beetle")) {
                            wave.getMonster(n).setMonsterpic(beetle);
                        }
                        if (wave.getMonster(n).getname().equals("worm")) {
                            wave.getMonster(n).setMonsterpic(worm);
                        }

                }



            }
        }
    }

    public void drawFireball(Tower existingTower){
    if(!existingTower.getFireballs().isEmpty()){
                for(int i=0;i<=existingTower.getFireballs().size()-1;i++){
                    existingTower.getFireballs().get(i).track((existingTower.getFireballs().get(i).getTargetX()+8),(existingTower.getFireballs().get(i).getTargetY()+TOPBAR+8));
                    if (!isTesting){
                        image(fireball, existingTower.getFireballs().get(i).getX(), existingTower.getFireballs().get(i).getY());
                    }
                    if(existingTower.getFireballs().get(i).isHit()){
                            existingTower.getFireballs().get(i).getMonster().minusMonsterhealth(existingTower.getFireballs().get(i).getTower().getDamage());
                            existingTower.removeFireball(existingTower.getFireballs().get(i));
                        }else{
                            if(existingTower.getFireballs().get(i).getMonster().getdeathImagestime()||existingTower.getFireballs().get(i).getMonster().getHp()<=0){
                                existingTower.removeMonsterInRange(existingTower.getFireballs().get(i).getMonster());
                            }
                    }
                }
            }
    }

    public void Wavegen(){
        if (waveManager.getCurrentWave()!=null&&(!pausekeyPressed)&&!lose&&!finished) {
            Wave currentWave = waveManager.getCurrentWave();
            length=currentWave.getMonsterSize();
            int waveduration=0;
            if(eachwave==0){
                waveduration=(int)(currentWave.getPre_wave_pause()*60);
            }
            prewavepase=0;
            if(waveManager.hasnexwave()){
                if (!isTesting){
                    fill(0);
                    textSize(20);
                    text("Wave: " + (eachwave + 2) + " starts " + (int) ((currentWave.getDuration() + (waveduration / 60) + waveManager.getWaveinquWave(wavecount + 1).getPre_wave_pause()) - currentWaveFrame / 60), 50, 30);
                }
            prewavepase=(int)waveManager.getWaveinquWave(wavecount+1).getPre_wave_pause()*60;
        }

            if (currentWaveFrame <= currentWave.getDuration()*60+waveduration) {
                        monstergenerateFrame++;
                        if (monstergenerateFrame>=(currentWave.getDuration()*60/length)){
                            monstergenerateFrame=0;
                            if(monstergenList.isEmpty()){
                                System.out.println("monstergenList.isEmpty");
                                index=0;
                            }
                            if(indexofmonster<length){
                                tempMonster=currentWave.getMonster(indexofmonster);
                                monstergenList.add(tempMonster);
                                indexofmonster++;
                            }
                        }          
                    }
                    if ((currentWaveFrame == currentWave.getDuration()*60+prewavepase+waveduration)&&waveManager.thelastwave()){
                        eachwave++;
                        waveManager.setend();
                    }
                    if (currentWaveFrame == currentWave.getDuration()*60+prewavepase+waveduration){
                        if(!waveManager.thelastwave()){
                            indexofmonster = 0;
                            waveManager.switchToNextWave();
                            length = 0;
                            eachwave++;
                            currentWaveFrame = 0;
                            waveInProgress = false;
                            num = 0;
                            index = 0;
                            destorynum = 0;
                            System.out.println("switch to next wave");
                        }
                        }
            currentWaveFrame++;
        }
    }
    
    public void AddMonterTower(Tower tower){
        if(!monstergenList.isEmpty()){
        for(int m=0;m<monstergenList.size();m++){
                    float distance=sqrt(pow((monstergenList.get(m).getMonsterX()+8)-(tower.getTowerX()+15),2)+pow((monstergenList.get(m).getMonsterY()+TOPBAR+8)-(tower.getTowerY()+15),2));
                    if ((distance <= tower.getDamagerange()*32)&&(!monstergenList.get(m).getdeathImagestime())&&(monstergenList.get(m).isAlive())) {
                        tower.addMonsterInRange(monstergenList.get(m));
                    }
                    if((distance > tower.getDamagerange()*32)||(monstergenList.get(m).getdeathImagestime())||(!monstergenList.get(m).isAlive())){
                        tower.removeMonsterInRange(monstergenList.get(m));
                        
                    }
                        
                    }
                
                if(!tower.getMonsterInrange().isEmpty()&&!lose&&!finished&&(!pausekeyPressed)){
                        tower.cooldowncount();
                        if(mouseposition.x/CELLSIZE * CELLSIZE==tower.getTowerX()&&mouseposition.y/CELLSIZE * CELLSIZE+8==tower.getTowerY()){
                                System.out.println("fireball add to this tower");
                                System.out.println(tower.getMonsterInrange().size());
                                System.out.println((int)(60.00/tower.getspeed()));
                                System.out.println(tower.getcooldown());
                            }
                        if(tower.getcooldown()>=(int)(60.00/tower.getspeed())){
                            tower.resetcooldowncount();
                            Fireball tempFireball=new Fireball(tower);
                            tempFireball.setMonster(tower.getrandMonster());
                            tower.addFireball(tempFireball);
                        }
                        
                        
                        
                }
                }
    }

    public void Createtower(){
        if ((buildkeyPressed == true)&&!lose&&!finished) {
            if ((keybpressed < 2)) {
                if (!isTesting) {
                    fill(255, 255, 0);
                    rect(649, 41 + 60 * 2, 41, 41);
                    fill(0);
                    textSize(25);
                    text("T", 655, 70 + 60 * 2);
                }
                boolean towerExistsAtPosition = false;

                for (Tower existingTower : towerList) {
                    if (existingTower.getTowerX() == clickmouseposition.x / CELLSIZE * CELLSIZE &&
                        existingTower.getTowerY() == clickmouseposition.y / CELLSIZE * CELLSIZE + 8) {
                        towerExistsAtPosition = true;
                        upgrade(existingTower,towerExistsAtPosition);
                        break;
                    }
                }
                if (!towerExistsAtPosition) {
                    for (Point point : validblock) {
                        if (clickmouseposition.x / CELLSIZE * CELLSIZE == point.x && 
                            clickmouseposition.y / CELLSIZE * CELLSIZE + 8 == point.y) {
                            towerposition.x = clickmouseposition.x / CELLSIZE * CELLSIZE;
                            towerposition.y = clickmouseposition.y / CELLSIZE * CELLSIZE + 8;
                            Tower towertemp=new Tower(jsonObject.getInt("initial_tower_range"),
                                                            jsonObject.getDouble("initial_tower_firing_speed"),
                                                            jsonObject.getInt("initial_tower_damage"),
                                                            towerposition);
                            upgrade(towertemp,towerExistsAtPosition);
                        }
                    }
                }
            } else {
                buildkeyPressed = false;
                keybpressed = 0;
                keyboardpressed = '\0';
                clickmouseposition.x = 0;
                clickmouseposition.y = 0;
            }
        }
    }

    public void updateMonster(List<Monster> monsters){
        if(!monstergenList.isEmpty()){
            for(int i=0;i<=monstergenList.size()-1;i++){
                if (!isTesting){
                    image(monstergenList.get(i).getMonsterpic(), monstergenList.get(i).getMonsterX() + 8, monstergenList.get(i).getMonsterY() + TOPBAR);
                }if(monstergenList.get(i).getHp()>=0){
                    if (!isTesting) {
                        stroke(0, 0, 0);
                        strokeWeight(1);
                        fill(255,0,0);
                        rect(monstergenList.get(i).getMonsterX(),monstergenList.get(i).getMonsterY()+TOPBAR-8,100/3,5);
                        fill(0,255,0);
                        rect(monstergenList.get(i).getMonsterX(),monstergenList.get(i).getMonsterY()+TOPBAR-8,(monstergenList.get(i).getHp()/monstergenList.get(i).getMaxHP())*100/3,5);
                        noStroke();
                    }}
                if((monstergenList.get(i).isexist())&&(monstergenList.get(i).isAlive())&&!monstergenList.get(i).getdeathImagestime()&&(!pausekeyPressed)&&!lose&&!finished){
                    monstergenList.get(i).MoveToTarget();
                    monstergenList.get(i).checkAlive(); 
                    if(!monstergenList.get(i).isexist()){
                        mana.removeMana(monstergenList.get(i).getMana_gained_on_kill());
                        /*if(isTesting){
                            break;
                        }*/
                        if(mana.getMana()<=0){
                            this.lose=true;
                            break;
                        }
                        monstergenList.get(i).reset();
                        if (monstergenList.get(i).getMonsterPath().get(0).x== 0) {
                                monstergenList.get(i).setMonsterX(monstergenList.get(i).getMonsterPath().get(0).x - 32);
                                monstergenList.get(i).setMonsterY(monstergenList.get(i).getMonsterPath().get(0).y);

                            } else if (monstergenList.get(i).getMonsterPath().get(0).x == 608) {
                                monstergenList.get(i).setMonsterX(monstergenList.get(i).getMonsterPath().get(0).x+32);
                                monstergenList.get(i).setMonsterY(monstergenList.get(i).getMonsterPath().get(0).y);

                            } else if (monstergenList.get(i).getMonsterPath().get(0).y == 0) {
                                monstergenList.get(i).setMonsterY(monstergenList.get(i).getMonsterPath().get(0).y-32);
                                monstergenList.get(i).setMonsterX(monstergenList.get(i).getMonsterPath().get(0).x);

                            } else if (monstergenList.get(i).getMonsterPath().get(0).y == 608) {
                                monstergenList.get(i).setMonsterY(monstergenList.get(i).getMonsterPath().get(0).y);
                                monstergenList.get(i).setMonsterX(monstergenList.get(i).getMonsterPath().get(0).x);

                            }
                    }
                    try{
                    if(!monstergenList.get(i).isAlive()){
                        monstergenList.get(i).setdeathImagestime();
                    }
                    }catch(IndexOutOfBoundsException e){
                    }
                    
            }       
                   if (!monstergenList.get(i).isAlive()&&monstergenList.get(i).getdeathImagestime()){
                       if (!isTesting){
                           image(monstergenList.get(i).getdeathImages(), monstergenList.get(i).getMonsterX() + 8, monstergenList.get(i).getMonsterY() + TOPBAR);
                       } if(monstergenList.get(i).getdeathImagesi()==monstergenList.get(i).getdeathImagessize()){
                           System.out.println("second level");
                           mana.addMana((int)(monstergenList.get(i).getMana_gained_on_kill()+mana.getmanapoollevel()*mana.getmanapoolspellmanagainedmultiplier()));
                            if(mana.getMana()>mana.getmana_cap()){
                                mana.setMana(mana.getmana_cap());
                            }
                            monstergenList.remove(i);
                            destorynum++;
                        }
                   }
//重置，下一波        

                if (!isTesting){
                    if (destorynum == length) {
                        destorynum = 0;
                        System.out.println("wave kills");
                        if (waveManager.thelastwave()) {
                            System.out.println("finished");
                            finished = true;
                        }

                    }
                }

        }
        if(lose && (mana.getMana()<=0)){
            if (!isTesting) {
            fill(0,255,0);
            textSize(30);
            stroke(3);
            text("YOU LOST", 300, 300);
            textSize(15);
            text("Press 'r' to restart", 300, 350);
            if(keyboardpressed=='r'||keyboardpressed=='R'){
                reset();
                setup();
                

            }
        }}

    }
    }

    public void upgradeUI(){
        for (Tower existingTower : towerList) {
            if (existingTower.getTowerX() == mouseposition.x / CELLSIZE * CELLSIZE &&
                existingTower.getTowerY() == mouseposition.y / CELLSIZE * CELLSIZE + 8) {
                int numbers=0;
                int total=0;
                if(uprange){
                    numbers++;
                    upgradename.add("range:     "+(10+(existingTower.getLevel()+existingTower.getRangeLevel()+1)*10));
                    total+=(10+(existingTower.getLevel()+existingTower.getRangeLevel()+1)*10);
                }
                if(upspeed){
                    numbers++;
                    upgradename.add("speed:     "+(10+(existingTower.getLevel()+existingTower.getSpeedLevel()+1)*10));
                    total+=(10+(existingTower.getLevel()+existingTower.getSpeedLevel()+1)*10);
                }
                if(updamage){
                    numbers++;
                    upgradename.add("damage:  "+(10+(existingTower.getLevel()+existingTower.getDamageLevel()+1)*10));
                    total+=(10+(existingTower.getLevel()+existingTower.getDamageLevel()+1)*10);
                }
                if(numbers!=0){
                    if (!isTesting) {
                        stroke(0);
                        fill(255);
                        rect(650, 535, 88, 20);
                        fill(0);
                        textSize(13);
                        text("Upgrade cost", 652, 550);
                    }
                    for(int i=0;i<=numbers-1;i++){
                        if (!isTesting) {
                            stroke(0);
                            strokeWeight(1);
                            fill(255);
                            rect(650, 555 + 20 * i, 88, 20);
                            fill(0);
                            textSize(13);
                            text(upgradename.get(i), 652, 570 + 20 * i);
                        }
                    }
                    if (!isTesting) {
                        stroke(0);
                        strokeWeight(1);
                        fill(255);
                        rect(650, 555 + 20 * numbers, 88, 20);
                        fill(0);
                        textSize(13);
                        text("Total:      " + total, 652, 570 + 20 * numbers);
                        noStroke();
                    }
                }
            }
        }
    }

    public void drawtower(){
        if(!towerList.isEmpty()) {
            for (int i = 0; i < towerList.size(); i++) {
                if (!isTesting){
                    image(towerImages.get(towerList.get(i).getLevel()), towerList.get(i).getTowerX(), towerList.get(i).getTowerY());
                }
                if (towerList.get(i).getRangeLevel() != 0) {
                    for (int j = 0; j <= towerList.get(i).getRangeLevel() - 1; j++) {
                        if (!isTesting) {
                            stroke(97, 71, 203);
                            strokeWeight(2);
                            noFill();
                            rect(towerList.get(i).getTowerX() + j * 5, towerList.get(i).getTowerY(), 5, 5);
                        }
                    }
                }
                if (towerList.get(i).getDamageLevel() != 0) {
                    for (int j = 0; j <= towerList.get(i).getDamageLevel() - 1; j++) {
                        if (!isTesting) {
                            fill(255, 0, 0);
                            textSize(8);
                            text("X", towerList.get(i).getTowerX() + j * 5, towerList.get(i).getTowerY() + 5 + 25);
                        }
                        }
                }
                if (towerList.get(i).getSpeedLevel() != 0) {
                    for (int j = 0; j <= towerList.get(i).getSpeedLevel() - 1; j++) {
                        if (!isTesting) {
                        stroke(135, 206, 235);
                        strokeWeight(j * 2 + 2);
                        noFill();
                        rect(towerList.get(i).getTowerX() + 6, towerList.get(i).getTowerY() + 6, 20, 20);
                    }}
                }

            }
        }
        }

    public void ui(){
        for (int i = 0; i < 7; i++) {
            fill(0);
            rect(647, 39+60*i, 45, 45);
            fill(131,111,75);
            rect(649, 41+60*i, 41, 41);
            // Draw label inside square
            fill(0);
            textSize(25);
            text(labels[i], 655,70+60*i);
            fill(0);
            textSize(12);
            text(descriptions[i], 695, 55+60*i);
            if(i>=2){
                text(secondline[i-2], 695, 70+60*i);
            }
            }
    }

    public void upgrade(Tower tower,boolean towerExistsAtPosition) {
        if (mousePressed == true) {
            int thisbuy = 0;
            if (updamage&& tower.getLevel() + tower.getDamageLevel() <= 2) {
                thisbuy += 10 + (tower.getLevel() + tower.getDamageLevel() + 1) * 10;
            }
            if (upspeed&& tower.getLevel() + tower.getSpeedLevel() <= 2) {
                thisbuy += 10 + (tower.getLevel() + tower.getSpeedLevel() + 1) * 10;
            }
            if (uprange&& tower.getLevel() + tower.getRangeLevel() <= 2) {
                thisbuy += 10 + (tower.getLevel() + tower.getRangeLevel() + 1) * 10;
            }
            if (mana.getMana() >= thisbuy) {
                if (updamage && tower.getLevel() + tower.getDamageLevel() <= 2) {

                    if (mana.getMana() >= thisbuy ) {
                        tower.updamage();
                        System.out.println("damage up");
                    } else {
                        System.out.println("not enough mana to upgrade damage");
                    }
                    if (upspeed == false && uprange == false) {
                        mousePressed = false;
                    }
                }
                if (upspeed && tower.getLevel() + tower.getSpeedLevel() <= 2) {

                    if (mana.getMana() >= thisbuy) {
                        tower.upspeed();
                        System.out.println("speed up");
                    } else {
                        System.out.println("not enough mana to upgrade speed");
                    }
                    if (updamage == false && uprange == false) {
                        mousePressed = false;
                    }
                }
                if (uprange && tower.getLevel() + tower.getRangeLevel() <= 2) {

                    if (mana.getMana() >= thisbuy) {
                        tower.uprange();
                        System.out.println("range up");
                    } else {
                        System.out.println("not enough mana to upgrade range");
                    }
                    if (upspeed == false && updamage == false) {
                        mousePressed = false;
                    }
                }
                if((!towerExistsAtPosition)){
                    if(mana.getMana()>=(towercost+thisbuy)){
                        towerList.add(tower);
                        mana.removeMana((towercost+thisbuy));
                        mousePressed = false;
                        System.out.println("this time buy" + (towercost+thisbuy));
                        System.out.println("leave: " + mana.getMana());
                        return;
                    }else{
                        mousePressed = false;
                        System.out.println("not enough mana");
                        return;
                    }
                    
                }
                System.out.println("this time buy" + thisbuy);
                mana.removeMana(thisbuy);
                System.out.println("leave: " + mana.getMana());
                mousePressed = false;
            } else {
                mousePressed = false;
                System.out.println("not enough mana");
            }
        }
    }


    public void setTesting(boolean isTesting) {
        this.isTesting = isTesting;
    }
    public static void main(String[] args) {
        PApplet.main("WizardTD.App");

    }

    /**
     * Source: https://stackoverflow.com/questions/37758061/rotate-a-buffered-image-in-java
     * @param pimg The image to be rotated
     * @param angle between 0 and 360 degrees
     * @return the new rotated image
     */
    public PImage rotateImageByDegrees(PImage pimg, double angle) {
        BufferedImage img = (BufferedImage) pimg.getNative();
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        PImage result = this.createImage(newWidth, newHeight, ARGB);
        //BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotated = (BufferedImage) result.getNative();
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                result.set(i, j, rotated.getRGB(i, j));
            }
        }

        return result;
    }
}
