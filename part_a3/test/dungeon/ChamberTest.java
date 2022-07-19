package dungeon;

import dnd.die.Die;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChamberTest {
    ChamberShape theShape;
    ChamberContents theContents;

    //you don't have to use instance variables but it is easier
    // in many cases to have them and use them in each test

    public ChamberTest() {
    }

    @Before
    public void setup(){
        theShape = ChamberShape.selectChamberShape(Die.d20());
        theShape.setNumExits();
        theContents = new ChamberContents();
    }

    /**
     * Test of getDoors method, of class Chamber.
     */
    @Test
    public void testGetDoor() {
        System.out.println("GetDoor");
        Chamber instance = new Chamber(theShape, theContents);
        Door newDoor = new Door();
        instance.addDoor(newDoor);
        int doorSize = theShape.getNumExits() + 1;
        int result = instance.getDoors().size();
        assertEquals(doorSize, result);
    }

    /**
     * Test of setDoor method, of class Chamber.
     */
    @Test
    public void testAddDoor() {
        System.out.println("AddDoor");
        Door newDoor = new Door();
        Chamber instance = new Chamber(theShape, theContents);

        instance.addDoor(newDoor);
        int doorLocation = instance.getDoors().size() -1;
        Door result = instance.getDoors().get(doorLocation);
        assertEquals(newDoor, result);
    }

    /**
     * Test of getMonsters method, of class Chamber.
     */
    @Test
    public void testGetMonsters() {
        System.out.println("getMonsters");
        Chamber instance = new Chamber();
        int m = instance.getMonsters().size();

        instance.addMonster(new Monster());
        instance.addMonster(new Monster());
        instance.addMonster(new Monster());
        instance.addMonster(new Monster());
        Monster myMonster = new Monster();
        instance.addMonster(myMonster);

        int  expResult = 5 + m;
        ArrayList<Monster> result = instance.getMonsters();
        if(result.size() < expResult){
            assertEquals(myMonster,result.get(0));
        }else
        {
            assertEquals(expResult, result.size());
        }
    }

    /**
     * Test of getMonsters method, of class Chamber.
     */
    @Test
    public void testNoGetMonsters() {
        System.out.println("getNoMonsters");
        Chamber instance = new Chamber();
        int expResult = instance.getMonsters().size();
        ArrayList<Monster> result = instance.getMonsters();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getTreasureList method, of class Chamber.
     */
    @Test
    public void testGetTreasureList() {
        System.out.println("getTreasureList");
        Chamber instance = new Chamber();
        int t = instance.getTreasureList().size();
        instance.addTreasure(new Treasure());

        int expResult = 1 + t;
        ArrayList<Treasure> result = instance.getTreasureList();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of setTreasureList method, of class Chamber.
     */
    @Test
    public void testSetTreasureList() {
        System.out.println("setTreasureList");
        Boolean isEqual = false;
        Chamber instance = new Chamber();
        Treasure treasure = new Treasure();
        int t = instance.getTreasureList().size();
        instance.addTreasure(treasure);

        for (Treasure treasureObj:instance.getTreasureList()) {
            if (treasureObj == treasure) {
                isEqual = true;
                break;
            }
        }
        assertTrue(isEqual);
    }

    /**
     * Test of getDescription method, of class Chamber.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Chamber instance = new Chamber(theShape, theContents);
        assertTrue(instance.getDescription().contains(theContents.getDescription()));
    }
}