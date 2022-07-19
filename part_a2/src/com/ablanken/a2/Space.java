package com.ablanken.a2;

public abstract class Space{

	/**
	 * creates a base method to get a description.
	 * later used in all sub space classes.
	 * @return
	 */
	public abstract  String getDescription();

	/**
	 * creates a base set door method used in all sub space classes.
	 * @param theDoor
	 */
	public abstract void setDoor(Door theDoor);

}