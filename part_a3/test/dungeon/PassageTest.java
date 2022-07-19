package dungeon;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;


public class PassageTest {
    //you don't have to use instance variables but it is easier
    // in many cases to have them and use them in each test

    public PassageTest() {
    }

    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
    }

    /**
     * Test of getDoors method, of class Passage.
     * Add 4 doors, associated with 4 section
     */
    @Test
    public void testGetDoors() {
        System.out.println("getDoors");
        Passage instance = new Passage();
        PassageSection ps = new PassageSection();
        instance.addPassageSection(ps);
        Door d = new Door();
        instance.addDoor(d);

        PassageSection ps1 = new PassageSection();
        Door d1 = new Door();
        instance.addPassageSection(ps1);
        instance.addDoor(d1);

        PassageSection ps2 = new PassageSection();
        Door d2 = new Door();
        instance.addPassageSection(ps2);
        instance.addDoor(d2);

        PassageSection ps3 = new PassageSection();
        Door d3 = new Door();
        instance.addPassageSection(ps3);
        instance.addDoor(d3);

        int result = instance.getDoors().size();
        int expResult = 4;
        assertEquals(expResult, result);
    }

    /**
     * Test of getDoors method, of class Passage.
     * Adds no doors apart from the entrance in passage and tests for that
     */
    @Test
    public void testGetNoDoors() {
        System.out.println("getNoDoors");
        Passage instance = new Passage();
        PassageSection ps = new PassageSection();
        instance.addPassageSection(ps);

        int result = instance.getDoors().size();
        int expResult = 1;
        assertEquals(expResult, result);
    }

    /**
     * Test of getDoor method, of class Passage.
     */
    @Test
    public void testGetDoor() {
        System.out.println("getDoor");
        Passage instance = new Passage();
        PassageSection ps = new PassageSection();
        Door d = new Door();
        instance.addPassageSection(ps);
        instance.addDoor(d);

        PassageSection ps1 = new PassageSection();
        Door d1 = new Door();
        instance.addPassageSection(ps1);
        instance.addDoor(d1);

        Door result =  instance.getDoor(1);
        assertEquals(d1, result);
    }

    /**
     * Test of getMonster and addMonster method, of class Passage.
     * since they both are needed to test properly.
     */
    @Test
    public void testGetAddMonster() {
        System.out.println("getMonster and addMonster");
        Monster theMonster = new Monster();
        PassageSection ps = new PassageSection();
        PassageSection ps1 = new PassageSection();

        Passage instance = new Passage();
        instance.addPassageSection(ps);
        instance.addPassageSection(ps1);

        instance.addMonster(theMonster, 1);
        boolean result = (theMonster.equals(ps1.getMonster()));
        assertTrue(result);
    }

    /**
     * Test of getNoMonster method, of class Passage.
     * should return null since no monster is set.
     *
     * note to self. later when I have time after during A4 make sure it returns no monster instead of null.
     */
    @Test
    public void testGetNoMonster() {
        System.out.println("getNoMonster");
        PassageSection ps = new PassageSection();
        PassageSection ps1 = new PassageSection();

        Passage instance = new Passage();
        instance.addPassageSection(ps);
        instance.addPassageSection(ps1);

        boolean result = (ps1.getMonster() == null);
        assertTrue(result);
    }


    /**
     * Test of addPassageSection method, of class Passage.
     * test to see if a default passage section will be added. (no description)
     */
    @Test
    public void testAddPassageSection() {
        System.out.println("addPassageSection");
        PassageSection toAdd = new PassageSection();
        Passage instance = new Passage();
        instance.addPassageSection(toAdd);
        assertTrue(instance.getDescription().contains("Passage goes straight for 10 ft"));
    }

    /**
     * Test of addTwoPassageSections method, of class Passage.
     * test to see if two sections will be passed through or if they will overwrite the other.
     */
    @Test
    public void testAddTwoPassageSections() {
        System.out.println("addPassageSection");
        PassageSection firstPS = new PassageSection("test section");
        PassageSection secondPS = new PassageSection();
        Passage instance = new Passage();
        instance.addPassageSection(firstPS);
        instance.addPassageSection(secondPS);
        assertTrue(instance.getDescription().contains("test section"));
    }

    /**
     * Test of setDoor method, of class Passage.
     */
    @Test
    public void testSetDoor() {
        System.out.println("setDoor");
        Door newDoor = new Door();
        Passage instance = new Passage();
        instance.addDoor(newDoor);
        Door door = instance.getDoor(0);
        assertEquals(newDoor, door);
    }

    /**
     * Test of setDoor method, of class Passage.
     * add a door to the second section within the passage.
     */
    @Test
    public void testSetSecondDoor() {
        System.out.println("setSecondDoor");
        Door newDoor = new Door();
        Passage instance = new Passage();
        PassageSection passageSection = new PassageSection();
        instance.addPassageSection(passageSection);
        instance.addPassageSection(passageSection);
        instance.addDoor(newDoor);
        Door door = instance.getDoor(1);
        assertEquals(newDoor, door);
    }

    /**
     * Test of getDescription method, of class Passage.
     * should make two sections with the first one having a monster
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Passage instance = new Passage();
        instance.addPassageSection(new PassageSection());
        instance.addPassageSection(new PassageSection("Passage goes straight for 20 ft"));
        Monster m = new Monster();
        instance.addMonster(m, 1);

        String result = instance.getDescription();
        assertTrue(result.contains(m.getDescription()) && result.contains("Passage goes straight for 20 ft"));
    }
}
