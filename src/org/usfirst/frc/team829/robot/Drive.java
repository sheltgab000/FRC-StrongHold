package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive extends SubSystem{

	// Fields: Integers and variables necessary for the methods inside of the class to work
	int transmissionStatus;
	final int HIGH = 0;
	final int LOW = 1;
	final int DEFAULT_STATE = HIGH;
	
	DoubleSolenoid shifter;
	
	RobotDrive drive;
	
	// Constructor: Initializes and defaults the variables to be used in the class
	public Drive(){
		transmissionStatus = DEFAULT_STATE;	// Sets default transmissionStatus
		shifter = new DoubleSolenoid(Ports.SHIFTER_HIGH, Ports.SHIFTER_LOW);	// Initializes the Double Solenoid for shifting
		drive = new RobotDrive(Ports.DRIVE_BACK_LEFT, Ports.DRIVE_BACK_RIGHT, Ports.DRIVE_FRONT_LEFT, Ports.DRIVE_FRONT_RIGHT);
	}
	
	
	// Methods:
	public void transmissionPressed(){	// Whenever the button is pressed swaps the current value of the transmission
		if(transmissionStatus == HIGH)
			transmissionStatus = LOW;
		else
			transmissionStatus = HIGH;
	}
	
	public void update(double leftSpeed, double rightSpeed){	// Changes the speed of the drive accordingly as well as pushing out or pulling in the solenoid
		drive.tankDrive(leftSpeed, rightSpeed);
		if(transmissionStatus == HIGH)
			shifter.set(DoubleSolenoid.Value.kForward);
		else
			shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
}
