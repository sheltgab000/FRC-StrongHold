package org.usfirst.frc.team829.robot;

import java.util.ArrayList;

abstract public class Auto {

	private ArrayList<AutoCommand> commands;
	
	public Auto(){
		commands = new ArrayList<AutoCommand>();
	}
	
	//Adds a command to the queue
	public void addCommand(AutoCommand command){
		commands.add(command);
	}
	
	public void update(){
		AutoCommand com = commands.get(0);
		if(com.isComplete())	//checks to see if the oldest command added is complete
			commands.remove(0);				//if it is complete remove it from the queue
		com.update();
		
	}
}
