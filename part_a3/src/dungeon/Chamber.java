package dungeon;

import dnd.die.D20;
import dnd.die.Die;
import dnd.exceptions.NotProtectedException;
import dnd.models.*;
import java.util.ArrayList;

public class Chamber extends Space{

	private ChamberContents myContents;
	private ChamberShape mySize;
	private ArrayList<Door> doors;
    private ArrayList<Monster> monsters;
	private ArrayList<Treasure> treasures;


    /**
     * constructor for chamber class that calls for a new d20 roll to create its' shape and contents.
     */
	Chamber(){
		this(ChamberShape.selectChamberShape(Die.d20()), new ChamberContents());
	}

    /**
     * constructor for chamber class when a shape and content is passed through.
     * @param theShape
     * @param theContents
     */
	Chamber(ChamberShape theShape, ChamberContents theContents) {
		monsters = new ArrayList<Monster>();
		treasures = new ArrayList<Treasure>();
		doors = new ArrayList<Door>();

		setContents(theContents);
		setShape(theShape);
	}

    /**
     * sets the contents within chamber and adds a monster and treasure if found within contents description.
     * @param theContents
     */
	private void setContents(ChamberContents theContents){
		myContents = theContents;
		myContents.chooseContents(Die.d20());

		if (myContents.getDescription().contains("monster")){
			addMonster(new Monster());
		}

		if (myContents.getDescription().contains("treasure")){
			addTreasure(new Treasure());
		}
	}

    /**
     * sets the shape description for a chamber object.
     * do-while loop to avoid having more than 5 doors to a room. causes error when assigning chambers to doors.
     * @param theShape
     */
	private void setShape(ChamberShape theShape){
		mySize = theShape;

		do {
			mySize.setNumExits();
		} while (mySize.getNumExits() >= 5);

		for (int i=0; i<mySize.getNumExits(); i++){
			addDoor(new Door());
		}
	}

    /**
     * sends back a list of doors connected to chamber object
     * @return doors
     */
	public ArrayList<Door> getDoors(){
		return doors;
	}

    /**
     * adds a monster object into the chamber
     * @param theMonster
     */
	public void addMonster(Monster theMonster){
		theMonster.setType(Die.d20());
		monsters.add(theMonster);
	}

    /**
     * sends back a list of monsters connected to chamber object
     * used in A2 and used again in A4
     * @return monsters
     */
	public ArrayList<Monster> getMonsters(){
		return monsters;
	}

    /**
     * adds a treasure object into the chamber
     * @param theTreasure
     */
	public void addTreasure(Treasure theTreasure){
		theTreasure.chooseTreasure(Die.d20());
		theTreasure.setContainer(Die.d20());
		treasures.add(theTreasure);
	}

    /**
     * sends back a list of treasures connected to chamber object
     * used in A2 and will be used again in A4
     * @return
     */
	public ArrayList<Treasure> getTreasureList(){
		return treasures;
	}

    /**
     * sends back a description of the chamber including monsters and treasure if contents includes it.
     * @return description
     */
	@Override
	public String getDescription(){
        String description = "Shape: " + mySize.getDescription() + getExitInfo(doors);
		description += "\nContents: " + myContents.getDescription();

		if (monsters.size()>0){
			description += getMonsterInfo(monsters);
		}

		if (treasures.size()>0){
			description += getTreasureInfo(treasures);
		}
		return description;
	}

    /**
     * adds a door connection to this room
     * @param newDoor
     */
	@Override
	public void addDoor(Door newDoor){
		doors.add(newDoor);
	}

    /**
     * sends the chamber description the string for exit info.
     * @param list
     * @return exit description
     */
	private String getExitInfo(ArrayList<Door> list){
		StringBuilder returnStr = new StringBuilder();
		returnStr.append("\nHas ")
				.append(list.size())
				.append(" exit(s).");
		for (Door door: list){
			returnStr.append("\n- Exit ")
					.append(list.indexOf(door)+1)
					.append(": ")
					.append(door.getDescription());
		}
		return returnStr.toString();
	}

    /**
     * sends the chamber description the string for monster info.
     * @param list
     * @return monster description
     */
	private String getMonsterInfo(ArrayList<Monster> list){
		StringBuilder returnStr = new StringBuilder();
		for (Monster m: list){
			returnStr.append("\n- Between ")
					.append(m.getMinNum())
					.append(" to ")
					.append(m.getMaxNum())
					.append(" ")
					.append(m.getDescription());
		}
		return returnStr.toString();
	}

    /**
     * sends the chamber description the string for treasure info.
     * @param list
     * @return treasure description
     */
	private String getTreasureInfo(ArrayList<Treasure> list){
		StringBuilder returnStr = new StringBuilder();
		for (Treasure t: list){
			returnStr.append("\n- ")
					.append(t.getDescription())
					.append(" found in ")
					.append(t.getContainer())
                    .append(isProtected(t));
		}
		return returnStr.toString();
	}

    /**
     * sends the treasure description the string for if it is protected or not.
     * @param t
     * @return protected string
     */
	private String isProtected(Treasure t){
	    String protectedStr = "";
        try {
            protectedStr = "\nProtected by " + t.getProtection();
        } catch (NotProtectedException e) {
            protectedStr = " left unprotected.";
        }
        return  protectedStr;
    }
}