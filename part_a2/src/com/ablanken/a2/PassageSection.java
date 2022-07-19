package com.ablanken.a2;

import dnd.models.ChamberContents;
import dnd.models.Monster;
import java.util.ArrayList;

/* Represents a 10 ft section of passageway */

public class PassageSection{

	/** monster object for section of passage */
	private Monster monster;

	/** find out if passage has monster if true */
	private boolean hasMonster;

	/** door object for within passage section */
	private Door door;

	/** finds put if passage has a door */
	private boolean hasDoor;

	/** string description for passage section */
	private String sectionDescription;

	/******************************
	 Required Methods for that we will test during grading
	 *******************************/
	/* note:  Some of these methods would normally be protected or private, but because we 
	don't want to dictate how you set up your packages we need them to be public 
	for the purposes of running an automated test suite (junit) on your code.  */


	/**
	 * create passage section object using base case.
	 */
	public PassageSection(){
		//sets up the 10 foot section with default settings
		this("passage goes straight for 10 ft");
	}

	/**
	 * creates passage section using passed through description
	 * @param description
	 */
	public PassageSection(String description)
	{
		hasMonster = false;
		hasDoor = false;

		//sets up a specific passage based on the values sent in from
		//modified table 1
		sectionDescription = description;
		if (description.contains("monster") || description.contains("Monster")){

			setMonster(new Monster());
			hasMonster = true;
		}
		if (description.contains("door") || description.contains("Door")){
			setDoor(new Door());
			hasDoor = true;
			if (description.contains("archway")){
				door.setArchway(true);
			} else {
				door.setArchway(false);
			}
		}
	}

	/**
	 * returns a door object that is within passage section
	 * @return
	 */
	public Door getDoor(){
		//returns the door that is in the passage section, if there is one
		return door;
	}

	/**
	 * sets a door object to this passage section
	 * @param newDoor
	 */
	public void setDoor(Door newDoor){
		door = newDoor;
		hasDoor = true;
	}

	/**
	 * returns monster object found within passage section
	 * @return
	 */
	public Monster getMonster(){
		//returns the monster that is in the passage section, if there is one
		return monster;
	}

	/**
	 * sets a monster object within current passage section
	 * @param newMonster
	 */
	public void setMonster(Monster newMonster){
		monster = newMonster;
		hasMonster = true;
	}

	/**
	 * gets a description of the passage section for the dungeons
	 * @return
	 */
	public String getDescription(){
		StringBuilder description = new StringBuilder();
		description.append(sectionDescription);
		if (hasMonster){
			description.append("\n- Between ").append(getMonster().getMinNum())
					.append(" to ").append(getMonster().getMaxNum())
					.append(" ").append(getMonster().getDescription());
		} else if (hasDoor){
			description.append(getDoor().getDescription());
		}
		return description.toString();
	}
}