package dungeon;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DoorTest {


    public DoorTest() {}

    @Before
    public void setup(){
        //set up any instance variables here so that they have fresh values for every test
    }

    /**
     * Test of setTrapped method, of class Door.
     * Case: flag == true without roll
     * Expected result, Trap is set.
     */
    @Test
    public void testSetTrapped() {
        System.out.println("setTrapped");

        Door instance = new Door();
        instance.setArchway(false);
        instance.setTrapped(true);
        assertTrue(instance.isTrapped());
    }

    /**
     * Test of setTrapped method, of class Door.
     * Expected result, Trap is not set.
     */
    @Test
    public void testSetNotTrapped() {
        System.out.println("setNotTrapped");

        Door instance = new Door();
        instance.setArchway(true);
        instance.setTrapped(false);
        assertFalse(instance.isTrapped());
    }

    /**
     * Test of setTrapped method, of class Door.
     * Case: flag == true, with roll
     * Expected result, Trap is set 1/20.
     */
    @Test
    public void testSetSpecificTrapped() {
        System.out.println("setSpecificTrapped");

        Door instance = new Door();
        instance.setArchway(false);
        instance.setTrapped(true,6);
        String expect = " Pit, 10' deep, 3 in 6 to fall in.";
        assertTrue(instance.isTrapped());
        assertEquals(expect,instance.getTrapDescription());
    }
    /**
     * Test of setOpen method, of class Door.
     * Case: It is not an archway. Set door close is ok
     *
     */
    @Test
    public void testSetNotOpenArchway() {
        System.out.println("setNotOpenArchway");

        Door instance = new Door();
        instance.setArchway(false);
        instance.setOpen(false);
        assertFalse(instance.isOpen());
    }
    /**
     * Test of setOpen method, of class Door.
     * Case: It is not an archway. Set door close is ok
     *
     */
    @Test
    public void testSetNotOpenLocked() {
        System.out.println("setNotOpenLocked");

        Door instance = new Door();
        instance.setLocked(true);
        instance.setOpen(false);
        assertFalse(instance.isOpen());
    }
    /**
     * Test of setArchway method, of class Door.
     */
    @Test
    public void testSetArchway() {
        System.out.println("setArchway");
        Door instance = new Door();
        instance.setArchway(true);
        boolean result = instance.isArchway();
        assertTrue(result);
    }

    /**
     * Test of isTrapped method, of class Door.
     * SetTrap to true
     */
    @Test
    public void testIsTrapped() {
        System.out.println("isTrapped");
        Door instance = new Door();
        instance.setArchway(false);
        instance.setTrapped(true);
        boolean result = instance.isTrapped();
        assertTrue(result);
    }
    /**
     * Test of isTrapped method, of class Door.
     * SetTrap to false
     */
    @Test
    public void testIsNotTrapped() {
        System.out.println("isNotTrapped");
        Door instance = new Door();
        instance.setArchway(false);
        instance.setTrapped(false);
        boolean result = instance.isTrapped();
        assertFalse(result);
    }

    /**
     * Test of isOpen method, of class Door.
     * Case door is closed
     */
    @Test
    public void testIsNotOpen() {
        System.out.println("isNotOpen");
        Door instance = new Door();
        instance.setOpen(false);
        boolean expResult = false;
        if (instance.isLocked()){
            expResult = false;
        }
        if(instance.isArchway())
            expResult = true;

        boolean result = instance.isOpen();
        assertEquals(expResult, result);
    }

    /**
     * Test of isOpen method, of class Door.
     * Case door is open
     */
    @Test
    public void testIsOpen() {
        System.out.println("isOpen");
        Door instance = new Door();
        instance.setArchway(true);
        boolean result = instance.isOpen();
        assertTrue(result);
    }

    /**
     * Test of isArchway method, of class Door.
     * Check case archway is false;
     */
    @Test
    public void testIsNotArchway() {
        System.out.println("isNotArchway");
        Door instance = new Door();
        instance.setArchway(false);
        boolean result = instance.isArchway();
        assertFalse(result);
    }

    /**
     * Test of isArchway method, of class Door.
     * Check case archway is true;
     */
    @Test
    public void testIsArchway() {
        System.out.println("isArchway");
        Door instance = new Door();
        instance.setArchway(true);
        boolean result = instance.isArchway();
        assertTrue(result);
    }

    /**
     * Test of getTrapDescription method, of class Door.
     */
    @Test
    public void testGetTrapDescription() {
        System.out.println("getTrapDescription");
        Door instance = new Door();
        instance.setArchway(false);
        instance.setTrapped(true, 6);
        String expResult = " Pit, 10' deep, 3 in 6 to fall in.";

        String result = instance.getTrapDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTrapDescription method, of class Door.
     * make sure when trap is false that it returns that there is no trap.
     */
    @Test
    public void testGetNoTrapDescription() {
        System.out.println("getNoTrapDescription");
        Door instance = new Door();
        instance.setArchway(true);
        instance.setTrapped(false);
        String expResult = "No trap description";

        String result = instance.getTrapDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSpaces method, of class Door.
     * Case: Just kip the references to the spaces.
     * Don't consider the case of Door need to be existed in Chamber.
     */
    @Test
    public void testSetSpaces() {
        System.out.println("setSpaces");
        Chamber c = new Chamber();
        Chamber c1 = new Chamber();
        Door instance = new Door();
        instance.setSpaces(c, c1);

        ArrayList<Space> result = instance.getSpaces();
        int expResult = 2;
        assertEquals(expResult,result.size());
    }

    /**
     * Test of setSpaces method, of class Door.
     * make sure it will only set one space.
     */
    @Test
    public void testSetOneSpace() {
        System.out.println("setOneSpace");
        Chamber c = new Chamber();
        Door instance = new Door();
        instance.setSpaces(c);

        ArrayList<Space> result = instance.getSpaces();
        int expResult = 1;
        assertEquals(expResult,result.size());
    }

    /**
     * Test of getSpaces method, of class Door.
     */
    @Test
    public void testGetSpaces() {
        System.out.println("getSpaces");
        Chamber c = new Chamber();
        Chamber c1 = new Chamber();
        Door instance = new Door();
        instance.setSpaces(c, c1);

        ArrayList<Space> result = instance.getSpaces();
        int expResult = 2;
        assertEquals(expResult,result.size());
    }

    /**
     * Test of getSpaces method, of class Door.
     * sending one space only to see if it only gets one space.
     */
    @Test
    public void testGetOneSpace() {
        System.out.println("getOneSpaces");
        Chamber c = new Chamber();
        Door instance = new Door();
        instance.setSpaces(c);

        ArrayList<Space> result = instance.getSpaces();
        int expResult = 1;
        assertEquals(expResult,result.size());
    }


    /**
     * Test of getDescription method, of class Door.
     * sets the doorway to trapped door
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Door instance = new Door();
        instance.setArchway(false);
        instance.setTrapped(true, 15);

        String expResult = "trap";
        String result = instance.getDescription();
        assertTrue(result.contains(expResult));
    }

    /**
     * Test of getDescription method, of class Door.
     * sets the door to archway
     */
    @Test
    public void testGetArchwayDescription() {
        System.out.println("getArchwayDescription");
        Door instance = new Door();
        instance.setArchway(true);
        String expResult = "archway";
        String result = instance.getDescription();
        assertTrue(result.contains(expResult));
    }
}
