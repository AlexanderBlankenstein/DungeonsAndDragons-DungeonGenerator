Dungeons and Dragons 1st Edition: Dungeon Generation.
Part a3.

Alexander Blankenstein
Date: 2019-10-25

level class starts the program with main. it is here that the 5 chamber objects are made with door and passage objects. 

they are then sent through several methods to create a hashmap of doors that are connected to different types of chambers. 
note there are also maps for door to door connections and door to single chamber connections. 

Note to user: if you wish to see which doors are connected to which chambers/doors just uncomment the //level.showPreliminaryLevel();
and //level.showFinalLevel();

Simular classes to A2
Chamber class creates a chamber object
door class creates a door object
passage creates a passage object of several passage sections
passage section creates a 10ft section. 
space class is parent class to passage and chamber classes. 

Project Tasks:
- refactor part a2 so that all classes and methods adhere to the Single Responsibility Principle
- conduct code review
- create unit tests using junit to test outerbounds. 
