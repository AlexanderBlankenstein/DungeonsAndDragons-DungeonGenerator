package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;

import java.io.Serializable;

/* Represents a 10 ft section of passageway */

public class PassageSection implements Serializable {

	private Monster monster;
	private Treasure treasure;
	private String sectionDescription;
	private Door door;


	/**
	 * Constructor for a Passage section with no input.
	 * sets up the 10 foot section with default settings
	 */
    PassageSection(){
		this("Passage goes straight for 10 ft");
	}

	/**
	 * Constructor for a Passage section with an input(description) given.
	 * @param description
	 */
    PassageSection(String description) {
		sectionDescription = description;
		checkMonster(description);
		checkDoor(description);
	}

	/**
	 * checks if monster was in passage section
	 * if monster found then create a monster.
	 * @param sectionDescription
	 */
	private void checkMonster(String sectionDescription){
		if (sectionDescription.contains("monster") || sectionDescription.contains("Monster")){
			setMonster(new Monster());
		}
	}

	private void checkTreasure(String sectionDescription){
		if (sectionDescription.contains("treasure") || sectionDescription.contains("treasure")){
			setTreasure(new Treasure());
		}
	}

	/**
	 * checks if Door was in passage section
	 * if Door found then create a Door.
	 * Checks if door is an archway.
	 * @param sectionDescription
	 */
	private void checkDoor(String sectionDescription){
		if (sectionDescription.contains("door") || sectionDescription.contains("Door")){
			setDoor(new Door());
			if (sectionDescription.contains("archway")){
				door.setArchway(true);
			} else {
				door.setArchway(false);
			}
		}
	}

	/**
	 * returns the door that is in the passage section, if there is one
	 * @return door
	 */
	public Door getDoor(){
		return door;
	}

	/**
	 * sets the door in the passage section
	 * @param newDoor
	 */
	public void setDoor(Door newDoor){
		door = newDoor;
	}

	/**
	 * returns the monster that is in the passage section, if there is one
	 * @return Monster
	 */
	public Monster getMonster(){
		return monster;
	}

	public Treasure getTreasure(){
		return treasure;
	}

	/**
	 * sets the monster in the massage section
	 * @param newMonster
	 */
	public void setMonster(Monster newMonster){
		monster = newMonster;
	}

	public void setTreasure(Treasure newTreasure) {
		treasure = newTreasure;
	}

	public void removeMonster(){
		monster = null;
	}

	public void removeTreasure(){
		treasure = null;
	}

	/**
	 * returns a description of this passage section.
	 * @return section Description
	 */
	public String getDescription(){
		if (monster!=null){
			sectionDescription += "\n- Between" + getMonster().getMinNum() + " to " + getMonster().getMaxNum() + " " + getMonster().getDescription();
		}
		if (treasure!=null){
			sectionDescription += "\n- With " + getTreasure().getDescription();
		}else if (door != null){
			sectionDescription += " " + getDoor().getDescription();
		}
		return sectionDescription;
	}
}