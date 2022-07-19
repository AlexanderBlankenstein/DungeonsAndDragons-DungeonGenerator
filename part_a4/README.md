Dungeons and Dragons 1st Edition: Dungeon Generation.
Part a4.

Alexander Blankenstein
Date: 2019-11-14

To remove a monster from a chamber just select the choice in the listview and click remove. to add one select in dropdown menu and select remove. 

level class starts the program with main. it is here that the 5 chamber objects are made with door and passage objects. 
Launcher class starts the Gui part of the program with main. this launches a window for the user to interact with the program. 

they are then sent through several methods to create a hashmap of doors that are connected to different types of chambers. 
note there are also maps for door to door connections and door to single chamber connections. 

Note to user: if you wish to see which doors are connected to which chambers/doors just uncomment the //level.showPreliminaryLevel();
and //level.showFinalLevel();

Similar classes to part a3:
Chamber class creates a chamber object
door class creates a door object
passage creates a passage object of several passage sections
passage section creates a 10ft section. 
space class is parent class to passage and chamber classes. 

The Gui part of the program uses a model (similar to a3) a view (the visual component of the program), and a controller (the part that communicates information between the view and model.)

Optional Extras: (assuming I get to it)
- Chamber and Passage View
- Clickable Door List

Project Tesks:
- Create a graphical user interface for your DM's toolkit.

Required Features: 
UI: create a wireframe as a guide.

A window that contains a list of Chambers and Passages on the left hand side
User must be able to select an element of the list (clicking, checkbox, radio button, etc)
Centre text area that shows the description of the currently selected Chamber or Passage
On the right of the text area, a list of Doors belonging to the currently selected chamber or passage .
When a  doors is clicked a pop up shows door description and the name of the chamber or passage on the other side of that door.
Edit button near the bottom of the screen permits the user to edit the currently selected chamber or passage
When the edit button is clicked a pop up that allows the user to add or remove Monsters or Treasure from the currently selected Chamber or Passage
Any additions or removals must be reflected in the centre text area.
 File menu in the top left that allows the user to load a previously saved dungeon level or Save the current level. File saving should use serialization (will be discussed in class next week)