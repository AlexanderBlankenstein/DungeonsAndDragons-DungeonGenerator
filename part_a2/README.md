Dungeons and Dragons 1st Edition: Dungeon Generation.
Part a2.

Alexander Blankenstein
Date: 2019-10-07

Prerequisite Skills
How to import and use libraries
How to use java collections
How to create objects and call methods
How to create an algorithm to solve a problem

Learning Objectives
Create and use cohesive classes to solve a programming problem
Write informative documentation for methods and classes
Identify potential improvements in encapsulation and information hiding

Project Task
I will write a program that can generate a single level of dungeon description for a DM to use with her/his game.    
My final program should generate and print the text description for an entire level for the user when it is run. 
The text description should be printed in a way that makes it easy for the DM to understand which spaces are connected to each other.

To accomplish this I will create 4 new classes: Chamber, Door, Passage, and PassageSection.
Random Generation Expectations
The same tables will be used for this part as for my a1 with two exceptions. Replace Table 1 with the table shown below. I will use this modified table to generate the dungeon level.
1-2 passage goes straight for 10 ft
3-5 passage ends in Door to a Chamber
6-7 archway (door) to right (main passage continues straight for 10 ft)
8-9 archway (door) to left (main passage continues straight for 10 ft)
10-11 passage turns to left and continues for 10 ft
12-13 passage turns to right and continues for 10 ft
14-16 passage ends in archway (door) to chamber
17 Stairs, (passage continues straight for 10 ft)
18-19 Dead End
20 Wandering Monster (passage continues straight for 10 ft)
 The level generation is complete when 5 Chambers have been created.

About the Required Classes:
Door
Doors connect two Spaces. A Space is either a Passage or a Chamber. Doors may be open or closed. A door may be locked or unlocked. A door may be just a frame (an archway) in which case it is automatically unlocked and open.  A door can be trapped.
Use the following rules to identify characteristics of the door. I will ignore the tables in AppendixA that relate to doors.
1/20 chance it will be a trapped door (use the dnd.models.Traps)
1/6 chance it will be locked
A door can connect two areas
a door can be open (only if unlocked), or closed
1/10 chance that the door is an archway (in which case it is not trapped and not locked but is open)

Chamber
A Chamber can contain doors, monsters, treasure.  It is possible that the treasure in the chamber could be trapped.   If a Chamber contains both monster and treasure, the DM can still just roll once for treasure, ignoring any rules about increasing value by 10%.

Passage
A Passage is composed of PassageSections.  Each PassageSection represents 10 ft of passage.   A PassageSection may contain a Door (or Archway) or a Monster or it may be a dead end.

DnD Package
Decisions should be made by "rolls" on random tables. 