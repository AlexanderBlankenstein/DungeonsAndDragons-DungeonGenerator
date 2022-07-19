package com.ablanken.a2;
import dnd.die.D20;
import dnd.die.D6;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;

public class Door{

	/** Door ID used to number door */
	private String doorID;

	/** space list for storing spaces associated with door */
	private ArrayList<Space> mySpace;

	/** Exit used for associated with door */
	private Exit exit;

	/** true = door is open, false = door is closed */
	private boolean open;

	/** true = door is trapped, false = door is not trapped */
	private boolean trapped;

	/** true = is an archway, false meas it is not */
	private boolean archway;

	/** true = door is locked, false = door is unlocked */
	private boolean locked;

	/** trap object */
	private Trap trap;

	/**
	 * Creates a new door object
	 */
	public Door(){
		//needs to set defaults
		this(new Exit());
	}

	/**
	 * creates door object with pre assigned exit.
	 * @param theExit
	 */
	public Door(Exit theExit){
		//sets up the door based on the Exit from the tables
		trap = null;
		trapped=false;
		open=false;
		archway=false;
		locked=false;
		mySpace = new ArrayList<>();

		exit = theExit;
		/**
		 * 1/20 chance it will be a trapped door (use the dnd.models.Traps)
		 * 1/6 chance it will be locked
		 * A door can connect two areas
		 * a door can be open (only if unlocked), or closed
		 * 1/10 chance that the door is an archway (in which case it is not trapped and not locked but is open)
		 */
		//TODO: might have to do this... grab passage description from a2 in order to tell if archway.
		D20 d20 = new D20();
		D6 d6 = new D6();
		int rollD20 = d20.roll();
		int rollD6 = d6.roll();

		if (rollD20 <= 2){ //archway
			setArchway(true);
		}
		if (rollD20 <= 10){ //open door
			setOpen(true);
		} else if (rollD20 == 11){ //closed + trapped...locked?
			setTrapped(true);
			if (rollD6 == 1){
				setLocked(true);
			}
		} else { //closed...locked?
			if (rollD6 == 1){
				setLocked(true);
			}
		}
	}

	/******************************
	 Required Methods for that we will test during grading
	 *******************************/
	/* note:  Some of these methods would normally be protected or private, but because we 
	don't want to dictate how you set up your packages we need them to be public 
	for the purposes of running an automated test suite (junit) on your code.  */

	/**
	 * sets a trap on a door depending on given flag and if a door is available.
	 * otherwise create a roll
	 * @param flag
	 * @param roll
	 */
	public void setTrapped(boolean flag, int ... roll){
		// true == trapped.  Trap must be rolled if no integer is given
		if(flag){
			D20 d20 = new D20();
			//trap = new Trap();
			int newRoll;
			if (trap == null){
				trap = new Trap();
			}
			if (roll.length == 0){
				newRoll = d20.roll();
				trap.setDescription(newRoll);
			} else {
				trap.setDescription(roll[0]);
			}
		}
		trapped = flag;
	}

	/**
	 * sets the door to open using a flag provided.
	 * @param flag
	 */
	public void setOpen(boolean flag){
		//true == open
		open = flag;
		if (archway){
			open = true;
		}
	}

	/**
	 * sets the door to an archway using the flag provided.
	 * @param flag
	 */
	public void setArchway(boolean flag){
		//true == is archway
		archway = flag;
		if (archway){
			setTrapped(false);
			setLocked(false);
			setOpen(true);
		}
	}

	/**
	 * locks the door if the flag provided is true.
	 * @param flag
	 */
	public void setLocked(boolean flag){
		if (!archway && !open){
			locked = flag;
		} else {
			locked = false;
		}
	}

	/**
	 * returns true if door is trapped.
	 * @return
	 */
	public boolean isTrapped(){
		return trapped;
	}

	/**
	 * returns true if door is open
	 * @return
	 */
	public boolean isOpen(){
		return open;
	}

	/**
	 * returns true if the door is an archway.
	 * @return
	 */
	public boolean isArchway(){
		return archway;
	}

	/**
	 * returns true if the door is locked
	 * @return
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * sets the trap description for the door class.
	 * @return
	 */
	public String getTrapDescription(){
		if(trapped){
			return trap.getDescription();
		} else {
			return "No trap description";
		}
	}

	/**
	 * sets the spaces on either side of the door.
	 * @return
	 */
	public ArrayList<Space> getSpaces(){
		//returns the two spaces that are connected by the door
		return mySpace;
	}

	/**
	 * sets spaces on either side of the door using two space parameters.
	 * @param spaceOne
	 * @param spaceTwo
	 */
	public void setSpaces(Space spaceOne, Space spaceTwo){
		//identifies the two spaces with the door
		// this method should also call the addDoor method from Space
		//needs reference to passage 1 or chamber 1, (what it is connected to)
		//TODO: check to make sure there aren't already spaces
		setSpaces(spaceOne);
		setSpaces(spaceTwo);
	}

	/**
	 * sets one side of the door to a space.
	 * @param spaceOne
	 */
	public void setSpaces( Space spaceOne){
		//use this for starting door with only one
		mySpace.add(spaceOne);
		spaceOne.setDoor(this);
	}

	/**
	 * returns a description of door depending on earlier stats.
	 * @return
	 */
	public String getDescription(){
		StringBuilder description = new StringBuilder();

		if (isArchway()){
			description.append(" archway ");
		} else {
			if (isOpen()){
				description.append(" open door ");
			} else if (isLocked()){
					description.append(" locked door ");
			} else {
				description.append(" unlocked closed door ");
			}
		}

		if (isTrapped()){
			description.append("that is trapped with\n")
					.append(getTrapDescription());
		}
		return description.toString();
	}
	/***********
	 You can write your own methods too, you aren't limited to the required ones
	 *************/
}