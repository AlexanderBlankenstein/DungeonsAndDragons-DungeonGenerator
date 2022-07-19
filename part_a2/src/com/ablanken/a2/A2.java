package com.ablanken.a2;

import dnd.die.D20;

import java.util.ArrayList;

public class A2 {

    /** counter for number of chambers made */
    private static int chamberCount;

    /** string sent to create passage section */
    private static String passageStr;

    /** passage object */
    private Passage passage;

    /** chamber object */
    private Chamber chamber;

    /**
     * Main method used to start dungeon and roll for passages until chamber count is 5
     * @param args
     */
    public static void main(String[] args){
        A2 a2 = new A2();
        passageStr = "";
        int roll = 0;
        do {
            D20 d20 = new D20();
            roll = d20.roll();
            a2.passage(roll);
        }while (chamberCount < 5);
    }

    /**
     * roll die
     * pick choice on table with roll
     * print out the choice
     *
     * 1-2 passage goes straight for 10 ft
     * 3-5 passage ends in Door to a Chamber
     * 6-7 archway (door) to right (main passage continues straight for 10 ft)
     * 8-9 archway (door) to left (main passage continues straight for 10 ft)
     * 10-11 passage turns to left and continues for 10 ft
     * 12-13 passage turns to right and continues for 10 ft
     * 14-16 passage ends in archway (door) to chamber
     * 17 Stairs, (passage continues straight for 10 ft)
     * 18-19 Dead End
     * 20 Wandering Monster (passage continues straight for 10 ft)
     */

    /**
     * creates passage and uses roll to decide which passage section is created.
     * @param roll
     */
    //TODO: make sure it returns a door.
    public void passage(int roll){
        if (roll <3){
            passageStr = "passage goes straight for 10 ft";

        } else if (roll <6){
            passageStr = "passage ends in Door to a Chamber -";
            //Passage.end()

        } else if (roll < 8){
            passageStr = "archway (door) to right (main passage continues straight for 10 ft) -";

        } else if (roll < 10){
            passageStr = "archway (door) to left (main passage continues straight for 10 ft) -";

        } else if (roll < 12){
            passageStr = "passage turns to left and continues for 10 ft";

        } else if (roll < 14){
            passageStr = "passage turns to right and continues for 10 ft";

        } else if (roll < 17){
            passageStr = "passage ends in archway (door) to chamber -";
            //create archway
            //new Chamber with archway (door)
            //passage.end()

        } else if (roll < 18){
            passageStr = "Stairs, (passage continues straight for 10 ft)";

        } else if (roll < 20){
            passageStr = "Dead End: Returning to previous Chamber";
            //passage.end()

        } else {
            passageStr = "Wandering Monster (passage continues straight for 10 ft)";
        }


        PassageSection passageSection = new PassageSection(passageStr);
        if (passage == null) {
            passage = new Passage();
        }
        passage.addPassageSection(passageSection);

        if (passageStr.contains("Chamber")){
            if (passageStr.contains("Dead End")){
                if (chamber==null){
                    passage = null;
                } else {
                    display(passage.getDescription());
                    display(chamber.getDescription());
                    passage = null;
                }
            } else {
                display(passage.getDescription());
                chamber = new Chamber();
                chamberCount++;
                display(chamber.getDescription());
            }
        }
        if (passageStr.contains("end") || passageStr.contains("End")){
            passage = null;
        }
        //return null;
    }

    /**
     * display output into a pretty string format.
     * @param myString
     */
    private void display(String myString){
        System.out.println("===========================================================================");
        System.out.println(myString);
        System.out.println("===========================================================================");
    }
}

