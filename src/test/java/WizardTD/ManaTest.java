package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManaTest {

    private Mana mana;

    @BeforeEach
    public void setUp() {
        mana = new Mana(200, 1000, 2, 100, 150, 1.5, 1.1);
    }

    @Test
    // Test the mana is doing adding correctly
    public void testAddMana() {
        mana.addMana(10);
        assertEquals(210, mana.getMana(), "Mana should be increased by 10");
    }
    @Test
    public void testAddManaEdge() {
        mana.addMana(100000);
        assertEquals(100200, mana.getMana(), "Mana should be increased by 100000");
    }
    @Test
    public void testAddManaEdge2() {
        mana.addMana(-100);
        assertEquals(200, mana.getMana(), "Mana should be not changed");
    }



    // Test the mana is doing minus correctly
    @Test
    public void testRemoveMana() {
        mana.removeMana(10);
        assertEquals(190, mana.getMana(), "Mana should be decreased by 10");
    }
    @Test
    public void testRemoveManaEdge() {
        mana.removeMana(10000);
        assertEquals(0, mana.getMana(), "Mana should be 0 if over the number");
    }
    @Test
    public void testRemoveManaEdge2() {
        mana.removeMana(-1);
        assertEquals(200, mana.getMana(), "Mana should be not changed");
    }

    //Test the mana can be set correctly
    @Test
    public void testSetMana() {
        mana.setMana(1000);
        assertEquals(1000, mana.getMana(), "Mana should be set to 1000");
    }
    @Test
    public void testSetManaEdge() {
        mana.setMana(-1);
        assertEquals(200, mana.getMana(), "Mana should not changed");
    }
    @Test
    public void testSetManaEdge2() {
        mana.setMana(2000);
        assertEquals(200, mana.getMana(), "Mana should not changed");
    }


    //Test the mana can auto add mana per second
    @Test
    public void testupManaPersecond() {
        int fps=0;
        while (fps<=3000){
            fps++;
            mana.updateTime(60);
        }
        assertEquals(300, mana.getMana(), "Mana should be increased by 2 times 3000/60 = 50");

    }
    //Test the manapool can be upgraded
    @Test
    public void testUpgradeManapool() {
        mana.upgradepool();
        assertEquals(1500, mana.getmana_cap(), "Manapool should be increased by cap times multiplier");
        assertEquals(1,mana.getmanapoollevel(),"Manapool level should be increased by 1");
        assertEquals(250,mana.getmanapoolspellinitialcost(),"Manapool spell cost should be increased by 150");
    }
    @Test
    public void testUpgradeManapoolEdge() {
        int time=0;
        while(time<5){
            mana.upgradepool();
            time++;
        }
        assertEquals(7593, mana.getmana_cap(), "Manapool should be increased by cap times multiplier");
        assertEquals(5,mana.getmanapoollevel(),"Manapool level should be increased by 4");
        assertEquals(850,mana.getmanapoolspellinitialcost(),"Manapool spell cost should be increased by 750");

    }


}
