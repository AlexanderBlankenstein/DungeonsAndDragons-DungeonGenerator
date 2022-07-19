package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;

import java.io.Serializable;
import java.util.ArrayList;

/*
A passage begins at a door and ends at a door.  It may have many other doors along 
the way

You will need to keep track of which door is the "beginning" of the passage 
so that you know how to 
*/

public class Passage extends Space implements Serializable {

	private ArrayList<Door> doors;
	private ArrayList<Monster> monsters;
	private ArrayList<Treasure> treasures;
	private ArrayList<PassageSection> sections;

	/**
	 * Constructor for Passage.
	 */
	Passage(){
		doors = new ArrayList<>();
		sections = new ArrayList<>();
		monsters = new ArrayList<>();
		treasures = new ArrayList<>();
	}

	/**
	 * gets all of the doors in the entire passage
	 * @return doors
	 */
	public ArrayList getDoors(){
		doors = new ArrayList<>();
		for (PassageSection section : sections) {
			doors.add(section.getDoor());
		}
		return doors;
	}

	/**
	 * returns the door in section 'i'. If there is no door, returns null
	 * @param i
	 * @return section door
	 */
	public Door getDoor(int i){
		try {
			return sections.get(i).getDoor();
		} catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	/**
	 * adds a monster to section 'i' of the passage
	 * @param theMonster
	 * @param i
	 */
	public void addMonster(Monster theMonster, int i){
		monsters.add(theMonster);
		PassageSection passageSection = sections.get(i);
		passageSection.setMonster(theMonster);
		sections.add(i,passageSection);
	}

	public void removeMonster(int i){
		PassageSection passageSection = sections.get(i);
		passageSection.removeMonster();
		monsters.remove(i);
	}

	public void addTreasure(Treasure theTreasure, int i){
		PassageSection passageSection = sections.get(i);
		passageSection.setTreasure(theTreasure);
		sections.add(i,passageSection);
		treasures.add(theTreasure);
	}

	public void removeTreasure(int i){
		PassageSection passageSection = sections.get(i);
		passageSection.removeTreasure();
		treasures.remove(i);
	}

	/**
	 * returns Monster door in section 'i'. If there is no Monster, returns null
	 * @param i
	 * @return section monster
	 */
	public Monster getMonster(int i){
		if (sections.get(i).getMonster() == null){
			return null;
		}
		return sections.get(i).getMonster();
	}

	public ArrayList<Monster> getMonsters(){
		return monsters;
	}

	public Treasure getTreasure(int i){
		if (sections.get(i).getTreasure() == null){
			return null;
		}
		return sections.get(i).getTreasure();
	}

	public ArrayList<Treasure> getTreasures(){
		return treasures;
	}

	/**
	 * adds the passage section to the passageway
	 * @param toAdd
	 */
	public void addPassageSection(PassageSection toAdd){
		sections.add(toAdd);
	}

	/**
	 * adds a door connection to the current Passage Section
	 * @param newDoor
	 */
	@Override
	public void addDoor(Door newDoor){
		try {
			int lastSection = (sections.size() - 1);
			PassageSection passageSection = sections.get(lastSection);
			passageSection.setDoor(newDoor);
			sections.set(lastSection,passageSection);
		} catch (IndexOutOfBoundsException e){
			PassageSection passageSection = new PassageSection();
			passageSection.setDoor(newDoor);
			sections.add(passageSection);
		}
	}

	/**
	 * creates a description of the passage with all the passage sections connected.
	 * @return description
	 */
	@Override
	public String getDescription(){
		StringBuilder description = new StringBuilder();
		for (int i=0; i<sections.size(); i++){
			description.append("\nSection ")
					.append(i + 1)
					.append(": ")
					.append(sections.get(i).getDescription());
		}
		return description.toString();
	}
}