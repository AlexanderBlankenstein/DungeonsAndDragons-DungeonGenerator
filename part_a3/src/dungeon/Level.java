package dungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class Level {

    private ArrayList<Door> doors;
    private ArrayList<Chamber> chambers;
    private HashMap <Door,Chamber> exits;
    private HashMap <Door,Door> matches;
    private HashMap <Door,ArrayList<Chamber>> connections;


    /**
     * level constructor method.
     */
    private Level(){
        chambers = new ArrayList<>();
        exits = new HashMap<>();
        matches = new HashMap<>();
        connections = new HashMap<>();
    }

    /**
     * This method should create however many chambers
     * is desired for the level simulation
     * @param count
     */
    private void createChambers(int count){
        for (int i = 0; i<count;i++){
            Chamber chamber = new Chamber();
            getDoors(chamber);

            /**
             * TODO: make a junit test that tests to make sure I can add a door
             * without adding a passage section first.
             */
            createPassage(chamber);
            chambers.add(chamber);
        }
    }

    /**
     * adds 2 passage ways to and a door to the current chamber.
     * @param chamber
     */
    private void createPassage(Chamber chamber){
        for (Door door : doors) {
            Passage passage = new Passage();
            PassageSection ps1 = new PassageSection();
            PassageSection ps2 = new PassageSection();

            /**
             * TODO: make a junit test that tests to make sure I can add a door
             * without adding a passage section first.
             */

            passage.addPassageSection(ps1);
            passage.addDoor(door);
            passage.addPassageSection(ps2);
            door.setSpaces(chamber, passage);
        }
    }

    /**
     * This method assigns a chamber to be the target
     * for each door.  At the end of this method the instance variable
     * exits will be filled
     */
    private void setConnectTargets(){
        for (Chamber c: chambers) {
            int count=0;
            ArrayList<Chamber> toBeConnected = getPossibleTargets(c);
            ArrayList<Door> curChamberDoors = c.getDoors();

            for (Door curChamberDoor: curChamberDoors) {
                exits.put(curChamberDoor, toBeConnected.get(count));
                count++;
            }
        }
    }

    /**
     * this method gets a list of possible chambers to be attached
     * that excludes the current chamber because we don't want to attach the same chamber
     * @param chamber
     * @return targets
     */
    private ArrayList<Chamber> getPossibleTargets(Chamber chamber){
        ArrayList<Chamber> targets = new ArrayList<>();

        for (Chamber value : chambers) {
            if (chamber != value) {
                targets.add(value);
            }
        }
        return targets;
    }

    /**
     *  this method finds a door to join.  For
     * 	every chamber in the chamber hashmap, a door is found from
     * 	the attacked chamber. The end result of this method is that the matches
     * 	instance variable is complete
     *  fills matches with door from each chamber and their matched door.
     */
    private void setTargets(){
        Chamber target;
        Door doorChoice;

        for (Door curDoor:exits.keySet()) {
            target = exits.get(curDoor);
            int doorNum = (int) (Math.random() * (target.getDoors().size()));
            doorChoice = target.getDoors().get(doorNum);
            matches.put(curDoor, doorChoice);
        }
    }

    /**
     * This method evaluates the pairs in matches and creates the final
     * 	data structure that represents each door and an ArrayList of chambers
     * 	that are being connected.  The ArrayList should contain chambers that are
     * 	being actively connected as well as those being connected to.  At the
     * 	end of this method the instance variable connections is populated
     */
    private void makeFinalLineup(){
        for (Door door : matches.keySet()){

            ArrayList<Chamber> chamberArrayList = new ArrayList<>();
            for (Chamber chamber:chambers) {
                for (Door chamberDoor:chamber.getDoors()){
                    if (door==chamberDoor){
                        chamberArrayList.add(chamber);
                    }
                }
            }
            connections.put(door,chamberArrayList);
        }
    }

    /**
     * this method prints out the attacking instance variable in
     * a human readable form
     */
    private void showPreliminaryLevel(){
        for (Door door: exits.keySet()){
            String key = door.toString();
            String value = exits.get(door).toString();
            display(key + " <-- Door | is connected to | Chamber-->  " + value);
        }
    }

    /**
     * this method should print the finalLineup instance variable
     * 	in human readable form
     */
    private void showFinalLevel(){
        String key = "";
        for (Door door: connections.keySet()){
            key = door.toString();
            String value = " is Connected to: ";
            ArrayList<Chamber> connectionsList = connections.get(door);
            for (Chamber c: connectionsList) {
                value += c.toString() + ", ";
            }
            display(key + value);
        }
    }

    /**
     * prints the level out in a pretty way
     */
    private void printLevel(){
        int chamberCount = 1;
        for (Chamber chamber:chambers) {
            display("Chamber " + chamberCount + ":\n" + chamber.getDescription());
            chamberCount++;
            printConnections(chamber);
        }
    }

    /**
     * uses chamber from printlevel() to print out what connects the chambers.
     * @param chamber
     */
    private  void printConnections(Chamber chamber){
        int exitNum = 1;
        for (Door door:chamber.getDoors()) {
            String description = "Passage connected to exit " + exitNum + ": " + door.getSpaces().get(1).getDescription();
            exitNum++;
            for (Door connectedDoor:exits.keySet()) {
                if (door == connectedDoor){
                    description += "\nLeads to chamber number: " + (chambers.indexOf(exits.get(connectedDoor))+1);
                    display(description);
                }
            }
        }
    }

    /**
     * sets a list of doors to the level's doors list from a chamber that is passed in.
     * @param chamber
     */
    private void getDoors(Chamber chamber){
        doors = chamber.getDoors();
    }

    /**
     * just to make the code pretty!
     * @param myString
     */
    private void display(String myString){
        System.out.println("===========================================================================");
        System.out.println(myString);
        System.out.println("===========================================================================");
    }

    /**
     * adds connections to chamber that is passed through
     * this is old code used in A2.
     * @param chamber
     * @param doorID
     * @return
     */
    private boolean addConnection(Chamber chamber, int doorID){
        boolean result = false;
        if(chamber != null){ //check for null objects being sent into program. they will do this
            connections.put(chamber.getDoors().get(doorID), chambers);
            result = connections.containsKey(chamber.getDoors().get(doorID));
        }
        return result;
    }

    /**
     * Program starts here!
     *
     * uncomment showPreliminaryLevel and showFinalLevel to print out:
     * door to door connections or
     * door to chambers connections
     * @param args
     */
    public static void main(String[] args){
        Level level = new Level();
        level.createChambers(5);
        level.setConnectTargets();
        //level.showPreliminaryLevel();
        level.setTargets();
        level.makeFinalLineup();
        //level.showFinalLevel();
        level.printLevel();
    }
}
