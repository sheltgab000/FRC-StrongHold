package org.usfirst.frc.team829.robot;

public class Ports {

	//Talon SRX													
	public static final int DRIVE_FRONT_RIGHT = 10;		//CAN Ids for Talon SRXs
	public static final int DRIVE_BACK_RIGHT = 11;		//		   \/
	public static final int DRIVE_FRONT_LEFT = 12;		//		   \/
	public static final int DRIVE_BACK_LEFT = 13;		//		   \/
	public static final int SHOOTER_1 = 14;				//		   \/
	public static final int SHOOTER_2 = 15;				//		   \/
	
	//Talon
	public static final int INTAKE_ROLLER = 0;	//Ports for Talon controllers
	public static final int INTAKE_PIVOT = 1;	//			 \/
	public static final int DART_MOTOR = 2;		//			 \/
	
	//Digital Inputs
	public static final int STOP_SWITCH = 0;		// Switch for the shooter to stop at
	public static final int SLOW_SWITCH = 1;		// Switch for the shooter to slow down at
	public static final int DART_IN_SWITCH = 2;		// Mag switch for the dart being all the way in
	public static final int DART_OUT_SWITCH = 3;	// Mag switch fot the dart being all the way out
	public static final int INTAKE_HOME_SWITCH = 4;	// Limit switch for the intake pivoted all the way down
	public static final int BALL_SWITCH = 6;		// Optical switch 
	
	public static final int DART_POT = 0;
	public static final int INTAKE_BALL_DETECTOR = 5;
	
	public static final int INTAKE_POT = 1;
	
	public static final int SHIFTER_HIGH = 0;
	public static final int SHIFTER_LOW = 1;
	
}
