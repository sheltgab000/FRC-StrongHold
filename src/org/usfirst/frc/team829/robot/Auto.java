package org.usfirst.frc.team829.robot;

public class Auto {

	/*
	 * FIELDS:
	 * 
	 * ArrayList for commands; 	A list that stores the current queue of commands to be executed
	 * int autoSelection; 	An integer that lets the program know which autonomous it will execute
	 * final int "Defences"; Let's the autoSelection change depending on selected defence
	 * 
	 * Some type of device for selecting the defence which in turn selects the autonomous
	 * 
	 */
	
	/*
	 * METHODS:
	 * 
	 * Constructor: Auto()
	 * init commands;	Sets the command list depending on the selected autonomous.
	 * default autoSelection; 	Sets the default autonomous
	 * 
	 * getAutoSelector()
	 * figure out which was selected
	 * and setup the command list accordingly
	 * 
	 * update()
	 * Execute the current command in the list.
	 * If the command has been completed then go to the next command in the list.
	 * To be used in the autonomous periodic in a loop
	 */
	
}
