package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;

public class Drive {

	// Fields:
	int transmissionStatus;
	final int HIGH = 0;
	final int LOW = 1;
	final int DEFAULT_STATE = HIGH;
	
	DoubleSolenoid shifter;
	
	RobotDrive drive;
	
	// Constructor:
	public Drive(){
		transmissionStatus = DEFAULT_STATE;
		shifter = new DoubleSolenoid(Ports.SHIFTER_HIGH, Ports.SHIFTER_LOW);
		drive = new RobotDrive(Ports.DRIVE_BACK_LEFT, Ports.DRIVE_BACK_RIGHT, Ports.DRIVE_FRONT_LEFT, Ports.DRIVE_FRONT_RIGHT);
	}
	
	
	// Methods:
	public void transmissionPressed(){
		if(transmissionStatus == HIGH)
			transmissionStatus = LOW;
		else
			transmissionStatus = HIGH;
	}
	
	public void update(double leftSpeed, double rightSpeed){
		drive.tankDrive(leftSpeed, rightSpeed);
		if(transmissionStatus == HIGH)
			shifter.set(DoubleSolenoid.Value.kForward);
		else
			shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
}
