package dungeon;

public abstract class Space{

	/**
	 * abstract class for description.
	 * @return back the description for any subclass under
	 */
	public abstract  String getDescription();

	/**
	 * abstract class for adding the door to any subclass under space.
	 * @param theDoor
	 */
	public abstract void addDoor(Door theDoor);
}