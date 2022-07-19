package dungeon;
import dnd.die.D20;
import dnd.die.D6;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;

public class Door{

	private String description;
	private ArrayList<Space> connections;
	private Trap trap;
	private boolean open, trapped, archway, locked;


	/**
	 * constructor class with no exits passed.
	 */
	public Door(){
		this(new Exit());
	}

	/**
	 * constructor class sets up the door based on the Exit from the tables
	 * @param theExit
	 */
	public Door(Exit theExit){
		trap = null;
		trapped=false;
		open=false;
		archway=false;
		locked=false;
		connections = new ArrayList<>();

		createDoor();
	}

	/**
	 * Sets a trap for the door if true. Trap is rolled if no integer is given
	 * @param flag
	 * @param roll
	 */
	public void setTrapped(boolean flag, int ... roll){
		trapped = flag;
		if (flag){
			if (trap == null){
				trap = new Trap();
			}
			createTrap(roll);
		}
	}

	/**
	 * creates trap for set trapped method.
	 * @param roll
	 */
	private void createTrap(int ... roll){
		if (roll.length == 0){
			int newRoll = rollD20();
			trap.chooseTrap(newRoll);
		} else {
			trap.chooseTrap(roll[0]);
		}
	}

	/**
	 * sets the open flag to true if door is open or is an archway
	 * @param flag
	 */
	public void setOpen(boolean flag){
		open = false;
		if (!locked){
			open = flag;
		}
	}

	/**
	 * sets the archway flag to true if door is an archway
	 * @param flag
	 */
	public void setArchway(boolean flag){
		archway = false;
		if (!locked || !open){
			archway = flag;
		}
	}

	/**
	 * sets the locked flag to true if door is locked.
	 * cant be locked if open or archway.
	 * @param flag
	 */
	public void setLocked(boolean flag){
		locked = false;
		if (!archway || !open){
			locked = flag;
		}
	}

	/**
	 * sends back the trapped flag as true if door is trapped
	 * @return trapped
	 */
	public boolean isTrapped(){
		return trapped;
	}

	/**
	 * sends back the open flag as true if door is open
	 * @return open
	 */
	public boolean isOpen(){
		return open;
	}

	/**
	 * sends back the archway flag as true if door is archway
	 * @return archway
	 */
	public boolean isArchway(){
		return archway;
	}

	/**
	 * sends back the locked flag as true if door is locked
	 * @return locked
	 */
	public boolean isLocked(){
		return locked;
	}

	/**
	 * sends back the trapped description if door is trapped
	 * @return trap description
	 */
	public String getTrapDescription(){
		if (trapped){
			return trap.getDescription();
		}
		return "No trap description";
	}

	/**
	 * returns the two spaces that are connected by the door
	 * @return connections
	 */
	public ArrayList<Space> getSpaces(){
		return connections;
	}

	/**
	 * sets the two spaces with the door
	 * @param spaceOne
	 * @param spaceTwo
	 */
	public void setSpaces(Space spaceOne, Space spaceTwo){
		setSpaces(spaceOne);
		setSpaces(spaceTwo);
	}

	/**
	 * sets a space with the door
	 * @param spaceOne
	 */
	public void setSpaces(Space spaceOne){
		connections.add(spaceOne);
	}

	/**
	 * sends back the description of the door object.
	 * @return description
	 */
	public String getDescription(){
		if (isArchway()){
			description = "archway ";
		} else {
			description = checkDoor();
		}
		if (isTrapped()){
			description += "that is trapped with\n" + getTrapDescription();
		}
		return description;
	}

	/**
	 * checks door type for door description
	 * @return description
	 */
	private String checkDoor(){
		if (isOpen()){
			description = "open door ";
		} else if (isLocked()){
			description = "locked door ";
		} else {
			description = "unlocked closed door ";
		}
		return description;
	}

	/**
	 * creates a new d20 roll result.
	 * @return d20 roll
	 */
	private int rollD20(){
		D20 d20 = new D20();
		return d20.roll();
	}

	/**
	 * creates a new d6 roll result.
	 * @return
	 */
	private int rollD6(){
		D6 d6 = new D6();
		return d6.roll();
	}

	/**
	 * creates the door type for the door object
	 */
	private void createDoor(){
		int d20Result = rollD20();

		if (d20Result <= 2){ //archway
			setArchway(true);
		}
		if (d20Result <= 10){ //open door
			setOpen(true);
		} else if (d20Result == 11){ //closed + trapped...locked?
			setTrapped(true);
			lockDoor();
		} else { //closed...locked?
			lockDoor();
		}
	}

	/**
	 * makes the door locked
	 */
	private void lockDoor(){
		int d6Result = rollD6();
		if (d6Result == 1){
			setLocked(true);
		}
	}
}