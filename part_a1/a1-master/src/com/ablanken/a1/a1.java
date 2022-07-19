package com.ablanken.a1;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import java.util.ArrayList;

import java.util.Scanner;

public class a1 {
    
    /*
     *Note that this file has been edited past the due date only to fix the "cannot find symbol"
     * error that comes up from my use of an IDE
     * Judi has every right to say "I told you so!" and laugh at me. :P 
     */

    /*
     * the new way of implementing the scanner is from and credited to Judi in her lecture on 2019-09-20
     * I liked how it worked and looked better than mine. :)
     */
    private Scanner scanner;

    private a1(){
        scanner = new Scanner(System.in);
    }

    /*
     * gets an input from user and checks to make sure it is a integer.
     * if it isn't, loop back and ask user again, otherwise @return input.
     */
    private int getInput(){
        boolean check = true;
        do {
            if (scanner.hasNextInt()) {
                check = false;
            } else {
                System.out.print(" Invalid Response. Please enter a numerical digit: ");
                scanner.next();
            }
        } while (check);
        return scanner.nextInt();
    }

    /*
     * asks user if they wish to supply their own roll. if they do then asks whet their roll was using getInput method.
     * uses the @parameter description to inform user what their roll will be used for.
     * if user inputs any integer not found on a d20 die then ask again for a valid roll.
     */
    private int getRoll(String description) {
        boolean check = true;
        int roll = 0;
        do{
            rollMenu(description);
            int myInput = getInput();
            if (myInput == 1) {
                System.out.print(" What was your roll? :");
                roll = getInput();
                if (roll >= 1 && roll <= 20) {
                    check = false;
                }
            } else if (myInput == 2) {
                check = false;
                roll = 0;
            } else {
                System.out.println("Invalid Response.");
            }
        } while (check);
        return roll;
    }

    /*
     * Dungeon Generation Methods
     * ________________________________________________________________________________________________________________
     */

    /*
     * generate new chamber object and then uses object methods to build a string representing the description of said
     * object.
     * uses try catch in order to cover any unusual shaped dungeons.
     * @returns the string description to later be displayed.
     */
    private String chamber() {
        ChamberShape chamberShape = new ChamberShape();
        StringBuilder description = new StringBuilder();
        ArrayList<Exit> exitArrayList;

        //get the shape of the chamber
        int roll = getRoll("chamber shape");
        if (roll == 0){
            chamberShape.setShape();
        } else {
            chamberShape.setShape(roll);
        }
        description.append("Shape is a ")
                .append(chamberShape.getShape());

        //Get the size of the chamber. Note! Had to get L&W first into variable or description would look strange.
        try {
            int length = chamberShape.getLength();
            int width = chamberShape.getWidth();
            description.append("\nwith a Length of ")
                    .append(length)
                    .append("ft and a Width of ")
                    .append(width)
                    .append("ft");
        } catch (UnusualShapeException e) {
            description.append("\nwith an Area of ")
                    .append(chamberShape.getArea());
        }

        //get the number of exits, their location and which way they are going
        chamberShape.setNumExits();
        description.append(" and has ")
                .append(chamberShape.getNumExits())
                .append(" exits.");
        exitArrayList = chamberShape.getExits();
        for(Exit exit:exitArrayList){
            int exitNum = exitArrayList.indexOf(exit) + 1;
            description.append("\n- Exit ")
                    .append(exitNum)
                    .append(" is on the ")
                    .append(exit.getLocation())
                    .append(" going ")
                    .append(exit.getDirection());
        }
        return description.toString();
    }

    /*
     * Generate what is found within dungeon chamber.
     */
    private String chamberContents(){
        ChamberContents chamberContents = new ChamberContents();
        int roll = getRoll("contents description");
        if (roll != 0){
            chamberContents.setDescription(roll);
        }
        return chamberContents.getDescription();
    }

    /*
     * Generate the trap found.
     */
    private String trap(){
        Trap trap = new Trap();
        int roll = getRoll("trap description");
        if (roll != 0){
            trap.setDescription(roll);
        }
        return trap.getDescription();
    }

    /*
     * Generate what monster is found any how many of that type.
     * NOTE!! this can easily be changed to have the system randomly select an amount between the Max and Min amounts.
     * Assignment didn't call for this function but I might integrate it later for my own use.
     */
    private String monster(){
        StringBuilder description = new StringBuilder();
        Monster monster = new Monster();
        int roll = getRoll("monster description");
        if (roll != 0){
            monster.setType(roll);
        }
        description.append("Between ").append(monster.getMinNum())
                .append(" to ").append(monster.getMaxNum())
                .append(" ").append(monster.getDescription());
        return description.toString();
    }

    /*
     * Generates a stair type that is found within dungeon.
     */
    private String Stairs(){
        Stairs stairs = new Stairs();
        int roll = getRoll("stair type");
        if (roll != 0){
            stairs.setType(roll);
        }
        return stairs.getDescription();
    }

    /*
     * Generates a treasure type and then uses the string builder once again to also describe the container and weather
     * it is protected by anything or left unprotected.
     */
    private String treasure(){
        StringBuilder description = new StringBuilder();
        Treasure treasure = new Treasure();
        int roll = getRoll("treasure Description");
        if (roll != 0){
            treasure.setDescription(roll);
        }
        int roll2 = getRoll("treasure Container");
        if (roll2 != 0){
            treasure.setContainer(roll2);
        }

        description.append(treasure.getDescription())
                .append(" found in ")
                .append(treasure.getContainer());

        try {
            description.append("\nProtected by ")
                    .append(treasure.getProtection());
        } catch (NotProtectedException e) {
            description.append(" left unprotected.");
        }
        return description.toString();
    }


    /*
    Menu Section
    ___________________________________________________________________________________________________________________
     */
    private void menu(){
        System.out.println("============================================");
        System.out.println("|            D&D Menu Selection            |");
        System.out.println("============================================");
        System.out.println("| Options:                                 |");
        System.out.println("|        1. Full Chamber Generation        |");
        System.out.println("|        2. Chamber                        |");
        System.out.println("|        3. Traps                          |");
        System.out.println("|        4. Monsters                       |");
        System.out.println("|        5. Stairs                         |");
        System.out.println("|        6. Treasure                       |");
        System.out.println("|        7. Contents                       |");
        System.out.println("|        0. Exit Program                   |");
        System.out.println("============================================");
        System.out.print(" Select option: ");
    }

    private void display(String myString){
        System.out.println("========================================================");
        System.out.println(myString);
        System.out.println("========================================================");
    }

    private void rollMenu(String description){
        System.out.println("========================================================");
        System.out.println("|       Would you like to provide your own roll        |");
        System.out.println("                  for "+description);
        System.out.println("========================================================");
        System.out.println("| Options:                                             |");
        System.out.println("|        1. Yes! (1d20 Required)                       |");
        System.out.println("|        2. No. (Generates a roll for you)             |");
        System.out.println("========================================================");
        System.out.print(" Select option: ");
    }


    /*
    Main Method
    ___________________________________________________________________________________________________________________
     */
    public static void main(String[] args){

        //create variables and object initializer.
        String myString = "";
        int menuSelect;
        boolean checker = true;
        a1 myObj = new a1();


        //Print 1st menu and ask user for selection. call object using selection and display.
        do{
            myObj.menu();
            menuSelect = myObj.getInput();

            switch(menuSelect){
                case 1:
                    String contents = myObj.chamberContents();
                    myString = "Chamber: " + myObj.chamber() + "\nContents: " + contents;
                    if(contents.contains("monster")){
                        myString += "\nMonster: " + myObj.monster();
                    }
                    if(contents.contains("treasure")){
                        myString += "\nTreasure: " + myObj.treasure();
                    }
                    if(contents.contains("trap")){
                        myString += "\nTrap: " + myObj.trap();
                    }
                    if(contents.contains("stairs")){
                        myString += "\nStairs: " + myObj.Stairs();
                    }
                    break;
                case 2:
                    myString = "Chamber: " + myObj.chamber();
                    break;
                case 3:
                    myString = "Trap: " + myObj.trap();
                    break;
                case 4:
                    myString = "Monster: " + myObj.monster();
                    break;
                case 5:
                    myString = "Stairs: " + myObj.Stairs();
                    break;
                case 6:
                    myString = "Treasure: " + myObj.treasure();
                    break;
                case 7:
                    myString = "Contents: " + myObj.chamberContents();
                    break;
                case 0:
                    myString = "Exit selected, Goodbye!";
                    checker = false;
                    break;
                default:
                    myString = "Invalid Response.";
            }
            myObj.display(myString);
        }while (checker);
    }
}
