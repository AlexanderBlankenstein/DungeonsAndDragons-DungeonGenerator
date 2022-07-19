package com.ablanken.a2;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Exit;

import java.util.ArrayList;

public class Chamber extends Space{

	/** Chamber contents object: can get and set description */
	private ChamberContents myContents;

	/** Chamber Shape object: can get and set area, exits, length, shape and width */
	private ChamberShape mySize;

	/** Door object: can create and get door */
	private Door myDoor;

	/** Monster object: created and placed within chamber. */
    private Monster monster;

    /** Treasure object: created for within the chamber */
    private Treasure treasure;

    /** Monster list used to store monsters from chambers. */
	private ArrayList<Monster> monsterList;

	/** Treasure list used to store treasure objects */
	private ArrayList<Treasure> treasureList;

	/** Door list used to store list of doors */
	private ArrayList<Door> doorList;

	/** Exit list used to store exits in chamber */
	private ArrayList<Exit> exitList;

	/******************************
	 Required Methods for that we will test during grading
	 *******************************/
	/* note:  Some of these methods would normally be protected or private, but because we 
	don't want to dictate how you set up your packages we need them to be public 
	for the purposes of running an automated test suite (junit) on your code.  */


	/**
	 * Chamber creates chamber with new shape and contents
	 */
	public Chamber(){
		this(new ChamberShape(),new ChamberContents());
	}

	/**
	 * Chamber creates chamber with provided shape and contents
	 * @param theShape
	 * @param theContents
	 */
	public Chamber(ChamberShape theShape, ChamberContents theContents){
		monsterList = new ArrayList<>();
		treasureList = new ArrayList<>();
		doorList = new ArrayList<>();

        monster = new Monster();
        treasure = new Treasure();

		setContents(theContents);
		setShape(theShape);
	}

	/**
	 * sets up contents for chamber
	 * @param theContents
	 */
	public void setContents(ChamberContents theContents){
		myContents = theContents;
		myContents.setDescription();
	}

	/**
	 * sets up shapes for chamber
	 * @param theShape
	 */
	public void setShape(ChamberShape theShape){
		mySize = theShape;
		mySize.setShape();
		mySize.setNumExits();

		if (mySize.getNumExits()==0){
			theShape.setNumExits(1);
		}

		exitList = mySize.getExits();

		for (Exit e: exitList){
			myDoor = new Door(e);
			setDoor(myDoor);
		}
	}

	/**
	 * gets a list of doors
	 * @return
	 */
	public ArrayList<Door> getDoors(){
		return doorList;
	}

	/**
	 * adds a monster to the monster list
	 * @param theMonster
	 */
	public void addMonster(Monster theMonster){
		monsterList.add(theMonster);
	}

	/**
	 * gets a list of monster
	 * @return
	 */
	public ArrayList<Monster> getMonsters(){
		return monsterList;
	}

	/**
	 * adds a treasure to the treasure list
	 * @param theTreasure
	 */
	public void addTreasure(Treasure theTreasure){
		treasureList.add(theTreasure);
	}

	/**
	 * gets a list of treasure as a list form
	 * @return
	 */
	public ArrayList<Treasure> getTreasureList(){
		return treasureList;
	}

	/**
	 * overrides the get description for another class to get information about chambers
	 * @return
	 */
	@Override
	public String getDescription(){
		StringBuilder description = new StringBuilder();
		addMonster(monster);
		addTreasure(treasure);

		String contents = myContents.getDescription();
		description.append("                               Chamber ")
				.append("\n" + chamberDescription() + "\nContents: " + contents);
		if(contents.contains("monster")){
			description.append("\nMonster: " + monsterDescription(getMonsters()));
		}
		if(contents.contains("treasure")){
			description.append("\nTreasure: " + treasureDescription(getTreasureList()));
		}
		return description.toString();
	}

	/**
	 * overrides set door method so that other classes can set a door to chamber
	 * @param newDoor
	 */
	@Override
	public void setDoor(Door newDoor){
		//should add a door connection to this room
		//myDoor = newDoor;
		doorList.add(newDoor);
	}


	/***********
	 You can write your own methods too, you aren't limited to the required ones
	 *************/

	/**
	 * returns a description of chamber in a string
	 * @return
	 */
	private String chamberDescription() {
		StringBuilder description = new StringBuilder();
		description.append("Shape is a ")
				.append(mySize.getShape());

		//Get the size of the chamber. Note! Had to get L&W first into variable or description would look strange.
		try {
			int length = mySize.getLength();
			int width = mySize.getWidth();
			description.append("\nwith a Length of ")
					.append(length)
					.append("ft and a Width of ")
					.append(width)
					.append("ft");
		} catch (UnusualShapeException e) {
			description.append("\nwith an Area of ")
					.append(mySize.getArea());
		}

		//get the number of exits, their location and which way they are going
		description.append(" and has ")
				.append(mySize.getNumExits())
				.append(" exit(s).");
		for(Exit exit:exitList){
			int exitNum = exitList.indexOf(exit) + 1;
			description.append("\n- Exit ")
					.append(exitNum)
					.append(": ")
					.append(exit.getLocation())
					.append(" going ")
					.append(exit.getDirection())
					.append(" is a/an")
					.append(doorList.get(exitNum-1).getDescription());
		}
		return description.toString();
	}

	/**
	 * creates monster description for chamber description
	 * @param list
	 * @return
	 */
	private String monsterDescription(ArrayList<Monster> list){
		String monsterStr = "";
		for(Monster m: list){
			monsterStr += monsterStr
					+ "\n- Between " + m.getMinNum()
					+ " to "+m.getMaxNum()
					+ " "+m.getDescription();
		}
		return monsterStr;
	}

	/**
	 * creates treasure description for chamber description
	 * @param list
	 * @return
	 */
	private String treasureDescription(ArrayList<Treasure> list){
		String treasureStr = "";
		for (Treasure t: list){
			treasureStr += treasureStr + "\n- " +
					t.getDescription() + " found in "
					+ t.getContainer();
			try {
				treasureStr += "\nProtected by " + t.getProtection();
			} catch (NotProtectedException e) {
				treasureStr += " left unprotected.";
			}
		}
		return treasureStr;
	}
}