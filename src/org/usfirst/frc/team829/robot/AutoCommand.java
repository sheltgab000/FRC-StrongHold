package org.usfirst.frc.team829.robot;

abstract public class AutoCommand {

	/*
	 * This is called using the Auto class repeatedly
	 * It will update the motors and other moving parts
	 */
	abstract public void update(Subsytem system);
	
	/*
	 * This is called to see if the command is complete
	 * It is called after the update in order to see if the command sould be deleted
	 */
	abstract public boolean isComplete();
	
}
