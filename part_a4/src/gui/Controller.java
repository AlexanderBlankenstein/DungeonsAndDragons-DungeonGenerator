package gui;

import dnd.models.Monster;
import dnd.models.Treasure;
import dungeon.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private GuiView myGui;
    private Level level;
    private static int NUM_CHAMBERS = 5;
    private int ID;

    /**
     * controller used to communicate between model and view
     * @param theGui
     */
    public Controller(GuiView theGui){
        myGui = theGui;
        level = new Level();
        level.createChamberList(NUM_CHAMBERS);
        ID=0;
    }

    /**
     * gets the list of chamber and passage numbers
     * @param spaceID
     * @return
     */
    private String getNameList(int spaceID){
        String nameList;
        if (spaceID<0){
            spaceID=0;
        }
        if (spaceID <= 4){
            ArrayList<Chamber> chambers = Level.getChamberList();
            Chamber requestedChamber = chambers.get(spaceID);
            nameList = "Chamber " + (spaceID + 1) + ":\n" + requestedChamber.getDescription();
        } else {
            spaceID -= 5;
            ArrayList<Passage> passages = Level.getPassageList();
            Passage requestedPassages = passages.get(spaceID);
            nameList = "Passage " + (spaceID + 1) + ":\n" + requestedPassages.getDescription();
        }
        return nameList;
    }

    /**
     * gets the door description
     * @param spaceID
     * @param doorID
     * @return
     */
    private String getDoorDescription(int spaceID, int doorID) {

        Door currentDoor = null;
        ArrayList<String> doorDescription = new ArrayList<>();
        ArrayList<Chamber> chambers = getChamberList();
        ArrayList<Passage> passages = getPassageList();

        String doorStr = "";
        HashMap<Door, Chamber> exits = Level.getExitsList();
        if (spaceID < 0){
            spaceID = 0;
        }
        if (spaceID <= 4) {
            currentDoor = chambers.get(spaceID).getDoors().get(doorID);
            for (Door door : chambers.get(spaceID).getDoors()) {
                for (Door connectedDoor : exits.keySet()) {
                    if (door == connectedDoor) {
                        doorStr = "Description: " + currentDoor.getDescription() + "\nConnected to Passage "
                                + (getMatchedPassage(connectedDoor)+1) + "\nLeads to chamber: " + (chambers.indexOf(exits.get(connectedDoor)) + 1);
                        doorDescription.add(doorStr);
                        break;
                    }
                }
            }
        }
        if (spaceID > 4) {
            int tempID = spaceID - 5;
            currentDoor = passages.get(tempID).getDoor(doorID);
            ArrayList<Door> doorList = passages.get(tempID).getDoors();
            for (Door door : doorList) {
                for (Door connectedDoor : exits.keySet()) {
                    if (door == connectedDoor) {
                        doorStr = "Description: " + currentDoor.getDescription() + "\nConnected to Chamber "
                                + (getMatchedChamber(connectedDoor)+1) + " and Chamber " + (chambers.indexOf(exits.get(connectedDoor)) + 1);
                        doorDescription.add(doorStr);
                        break;
                    }
                }
            }
        }



        return doorDescription.get(doorID);
    }

    /**
     * get the matched chamber variable
     * @param connectedDoor
     * @return
     */
    private int getMatchedChamber(Door connectedDoor) {
        ArrayList<Chamber> chambers = getChamberList();
        int chamberID = 0;
        for (Chamber c:chambers) {
            for (Door d:c.getDoors()) {
                if (d==connectedDoor){
                    chamberID = chambers.indexOf(c);
                }
            }
        }
        return chamberID;
    }

    /**
     * get the matched passage variable
     * @param connectedDoor
     * @return
     */
    private int getMatchedPassage(Door connectedDoor) {
        ArrayList<Passage> passages = getPassageList();
        int passageID = 0;
        for (Passage p:passages) {
            if (p.getDoor(0)==connectedDoor){
                passageID = passages.indexOf(p);
            }
        }
        return passageID;
    }

    /**
     * get description of space
     * @param spaceID
     * @return
     */
    public String getNewDescription(int spaceID){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getNameList(spaceID);
    }

    /**
     * get door description
     * @param spaceID
     * @param doorID
     * @return
     */
    public String getNewDoorDescription(int spaceID, int doorID){
        return getDoorDescription(spaceID, doorID);
    }

    /**
     * exit program
     * @param stage
     */
    public void exit(Stage stage){
        stage.close();
    }

    /**
     * load the file
     * @param file
     */
    public void load(File file){
        Level.loadLevel(file);
    }

    /**
     * safe the file
     * @param file
     */
    public void save(File file){
        Level.saveLevel(file);
    }

    /**
     * using a value grab the door list from a selected space
     * @param value
     * @return
     */
    public ArrayList<String> getNewDoorList(int value) {
        ArrayList<String> toReturn = new ArrayList<>();
        if (value<0){
            value=0;
        }
        if (value<=4){
            for (Door d:Level.getChamberList().get(value).getDoors()) {
                toReturn.add("Door " + (Level.getChamberList().get(value).getDoors().indexOf(d)+1));
            }
        } else {
            value-=5;
            ArrayList<Door> doorsList = (Level.getPassageList().get(value).getDoors());
            for (Door d:doorsList) {
                if (d!=null){
                    toReturn.add("Door " + (Level.getPassageList().get(value).getDoors().indexOf(d)+1));
                }
            }
        }
        return toReturn;
    }

    /**
     * gets the space number
     * @return
     */
    public int getSpaceID() {
        return ID;
    }

    /**
     * sets space variable
     * @param newID
     */
    public void setSpaceID(int newID){
        ID = newID;
    }

    /**
     * get the full list of passages
     * @return
     */
    public ArrayList<Passage> getPassageList() {
        return Level.getPassageList();
    }

    /**
     * get the full list of chambers.
     * @return
     */
    public ArrayList<Chamber> getChamberList() {
        return Level.getChamberList();
    }

    /**
     * get the full list of monsters from current selected space.
     * @param spaceID
     * @return
     */
    public ArrayList<Monster> getMonsterList(int spaceID) {
        ArrayList<Monster> returnList;
        if (spaceID<0){
            spaceID=0;
        }
        if (spaceID<=4){
            returnList = getChamberList().get(spaceID).getMonsters();
        } else {
            spaceID-=4;
            returnList = getPassageList().get(spaceID).getMonsters();
        }
        return returnList;
    }

    /**
     * get the full list of treasure from current selected space.
     * @param spaceID
     * @return
     */
    public ArrayList<Treasure> getTreasureList(int spaceID) {
        ArrayList<Treasure> returnList;
        if (spaceID<0){
            spaceID=0;
        }
        if (spaceID<=4){
            returnList = getChamberList().get(spaceID).getTreasureList();
        } else {
            spaceID-=4;
            returnList = getPassageList().get(spaceID).getTreasures();
        }
        return returnList;
    }

    /**
     * Sends in the choice to add a monster object from chamber or passage
     * @param chosenMonster
     */
    public void addMonster(Monster chosenMonster) {
        if (getSpaceID()<=4){
            Level.getChamberList().get(getSpaceID()).addSetMonster(chosenMonster);
        } else {
            Level.getPassageList().get(getSpaceID()).addMonster(chosenMonster,0);
        }
    }

    /**
     * Sends in the choice to remove a monster object from chamber or passage
     * @param monsterChoice
     */
    public void removeMonster(int monsterChoice) {
        if (getSpaceID()<=4){
            Level.getChamberList().get(getSpaceID()).removeMonster(monsterChoice);
        } else {
            Level.getPassageList().get(getSpaceID()).removeMonster(0);
        }
    }

    /**
     * Sends in the choice to add a treasure object from chamber or passage
     * @param chosenTreasure
     */
    public void addTreasure(Treasure chosenTreasure) {
        if (getSpaceID()<=4){
            Level.getChamberList().get(getSpaceID()).addSetTreasure(chosenTreasure);
        } else {
            Level.getPassageList().get(getSpaceID()).addTreasure(chosenTreasure,0);
        }
    }

    /**
     * Sends in the choice to remove a treasure object from chamber or passage
     * @param treasureChoice
     */
    public void removeTreasure(int treasureChoice) {
        if (getSpaceID()<=4){
            Level.getChamberList().get(getSpaceID()).removeTreasure(treasureChoice);
        } else {
            Level.getPassageList().get(getSpaceID()).removeTreasure(0);
        }
    }
}
