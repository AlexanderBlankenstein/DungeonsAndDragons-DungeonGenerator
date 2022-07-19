package com.ablanken.a2;

import dnd.models.Monster;
import java.util.ArrayList;
import java.util.HashMap;
/*
A passage begins at a door and ends at a door.  It may have many other doors along 
the way

You will need to keep track of which door is the "beginning" of the passage 
so that you know how to 
*/

public class Passage extends Space{
	//these instance variables are suggestions only
	//you can change them if you wish.

	/** creates a passage section for passage object. */
	private PassageSection passageSection;

	/** creates a list of Passage sections */
	private ArrayList<PassageSection> thePassage;

	/**creates a list of doors */
	private ArrayList<Door> doorList;

	/** creates a hash map of doors for the passage section */
	private HashMap<Door,PassageSection> doorMap;

	/******************************
	 Required Methods for that we will test during grading
	 *******************************/
	/* note:  Some of these methods would normally be protected or private, but because we 
	don't want to dictate how you set up your packages we need them to be public 
	for the purposes of running an automated test suite (junit) on your code.  */

	/**
	 * Creates a passage object with the passage, door list, and passage section then sets it to the passage
	 */
	public Passage(){
		thePassage = new ArrayList<>();
		doorList = new ArrayList<>();
		//passageSection = new PassageSection("10 ft section with door");
		//addPassageSection();
	}

	/**
	 * gets a list of the doors for passage objects
	 * @return
	 */
	public ArrayList getDoors(){
		//gets all of the doors in the entire passage
        try {
            for (int d=0;d<thePassage.size();d++){
                doorList.add(thePassage.get(d).getDoor());
            }
        } catch (IndexOutOfBoundsException e){
            return null;
        }

		return doorList;
	}

	/**
	 * using the passed integer, get the door at position given.
	 * @param i
	 * @return
	 */
	public Door getDoor(int i){
		//returns the door in section 'i'. If there is no door, returns null

        try {
            if (thePassage.get(i).getDoor() == null){
                return null;
            }
            return thePassage.get(i).getDoor();
		} catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	/**
	 * adds a monster within section at position given
	 * @param theMonster
	 * @param i
	 */
	public void addMonster(Monster theMonster, int i){
		// adds a monster to section 'i' of the passage
		passageSection = thePassage.get(i);
		passageSection.setMonster(theMonster);
        thePassage.add(i,passageSection);
	}

	/**
	 * gets a monster from position given.
	 * @param i
	 * @return
	 */
	public Monster getMonster(int i){
		//returns Monster door in section 'i'. If there is no Monster, returns null
		if (thePassage.get(i).getMonster() == null){
			return null;
		}
		return thePassage.get(i).getMonster();
	}

	/**
	 * adds a passage section to the list of passages.
	 * @param toAdd
	 */
	public void addPassageSection(PassageSection toAdd){
		//adds the passage section to the passageway
		thePassage.add(toAdd);
	}

	/**
	 * overrides method to add a door to the passage.
	 * @param newDoor
	 */
	@Override
	public void setDoor(Door newDoor){
		//should add a door connection to the current Passage Section
		passageSection = new PassageSection();
		//passageSection = thePassage.get(thePassage.size());
		passageSection.setDoor(newDoor);
	}

	/**
	 * overrides the method to get a string description of the passage way.
	 * @return
	 */
	@Override
	public String getDescription(){
		StringBuilder description = new StringBuilder();

		description.append("                               Passage");
		for (int i=0; i<thePassage.size(); i++){
			description.append("\nSection ")
					.append(i+1)
					.append(": ")
					.append(thePassage.get(i).getDescription());
		}
        return description.toString();
	}
	/***********
	 You can write your own methods too, you aren't limited to the required ones
	 *************/
}