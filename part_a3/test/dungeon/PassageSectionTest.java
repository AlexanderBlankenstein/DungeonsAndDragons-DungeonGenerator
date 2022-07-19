package dungeon;

import dnd.models.Monster;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PassageSectionTest {


    public PassageSectionTest() {}

    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
    }

    /**
     * Test of getDoor method, of class PassageSection.
     */
    @Test
    public void testGetDoor() {
        System.out.println("testGetDoor");
        String s = "passage ends in Door to a Chamber";
        PassageSection instance = new PassageSection(s);
        Door d = instance.getDoor();
        boolean result = ( d == null);
        assertFalse(result);

        if(result){
            fail("There is no door attach to this passage section.");
        }
    }

    /**
     * Test of getDoor method, of class PassageSection.
     * make sure door is created even when a string with a lowercase d is passed through
     */
    @Test
    public void testGetLowerCaseDoor() {
        System.out.println("testGetLowerCaseDoor");
        String s = "passage ends in door to a Chamber";
        PassageSection instance = new PassageSection(s);
        Door d = instance.getDoor();
        boolean result = ( d == null);
        assertFalse(result);

        if(result){
            fail("There is no door attach to this passage section.");
        }
    }

    /**
     * Test of getDoor method, of class PassageSection.
     * make sure no door is found when description doesnt call for it
     */
    @Test
    public void testGetNoDoor() {
        System.out.println("testGetNoDoor");
        PassageSection instance = new PassageSection();

        Door d = instance.getDoor();
        boolean result = ( d != null);
        assertFalse(result);

        if(result){
            fail("There is no door attach to this passage section.");
        }
    }

    /**
     * Test of getMonster method, of class PassageSection.
     * Set a monster based on string description
     */
    @Test
    public void testGetMonster() {
        System.out.println("getMonster");
        String s = "Wandering Monster (passage continues straight for 10 ft)";
        PassageSection instance = new PassageSection(s);
        Monster m = instance.getMonster();
        boolean result = ( m != null);
        assertTrue(result);
    }

    /**
     * Test of getMonster method, of class PassageSection.
     * Set a monster based on string description with a lowercase m
     */
    @Test
    public void testGetLowerCaseMonster() {
        System.out.println("getLowerCaseMonster");
        String s = "Wandering monster (passage continues straight for 10 ft)";
        PassageSection instance = new PassageSection(s);
        Monster m = instance.getMonster();
        boolean result = ( m != null);
        assertTrue(result);
    }

    /**
     * Test of getMonster method, of class PassageSection.
     * There is no monster based on string description
     */
    @Test
    public void testGetNoMonster() {
        System.out.println("getNoMonster");
        PassageSection instance = new PassageSection();
        Monster m = instance.getMonster();
        boolean result = ( m != null);
        assertFalse(result);
    }

    /**
     * Test of getDescription method, of class PassageSection.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        String description = "passage ends in Door to a Chamber";
        PassageSection instance = new PassageSection(description);

        String expResult = "passage ends in Door to a Chamber";
        String result = instance.getDescription();
        assertTrue(result.contains(expResult));
    }

    /**
     * Test of getDescription method when no description is passed through, of class PassageSection.
     */
    @Test
    public void testGetRegularDescription() {
        System.out.println("getRegularDescription");
        PassageSection instance = new PassageSection();

        String expResult = "Passage goes straight for 10 ft";
        String result = instance.getDescription();
        assertTrue(result.contains(expResult));
    }
}
