package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends SubSystem{

	// Fields: Integers and variables necessary for the methods inside of the class to work
	int transmissionStatus;
	final int HIGH = 0;
	final int LOW = 1;
	final int DEFAULT_STATE = HIGH;
	private final int BUTTON_TIMEOUT = 250;
	
	private long startTime;
	DoubleSolenoid shifter;
	
	//RobotDrive drive;
	CANTalon backLeft, backRight, frontLeft, frontRight;
	
	// Constructor: Initializes and defaults the variables to be used in the class
	public Drive(){
		transmissionStatus = DEFAULT_STATE;	// Sets default transmissionStatus
		shifter = new DoubleSolenoid(Ports.SHIFTER_HIGH, Ports.SHIFTER_LOW);	// Initializes the Double Solenoid for shifting
		//drive = new RobotDrive(Ports.DRIVE_BACK_LEFT, Ports.DRIVE_BACK_RIGHT, Ports.DRIVE_FRONT_LEFT, Ports.DRIVE_FRONT_RIGHT);
		backLeft = new CANTalon(Ports.DRIVE_BACK_LEFT);
		backRight = new CANTalon(Ports.DRIVE_BACK_RIGHT);
		frontLeft = new CANTalon(Ports.DRIVE_FRONT_LEFT);
		frontRight = new CANTalon(Ports.DRIVE_FRONT_RIGHT);
		
		startTime = System.currentTimeMillis();
	}
	
	
	// Methods:
	public void transmissionPressed(){	// Whenever the button is pressed swaps the current value of the transmission
		if(System.currentTimeMillis() - startTime >= BUTTON_TIMEOUT){
			if(transmissionStatus == HIGH)
				transmissionStatus = LOW;
			else
				transmissionStatus = HIGH;
			startTime = System.currentTimeMillis();
		}
	}
	
	public void update(double leftSpeed, double rightSpeed){	// Changes the speed of the drive accordingly as well as pushing out or pulling in the solenoid
		//drive.tankDrive(leftSpeed, rightSpeed);
		
		SmartDashboard.putNumber("CurrentTime", startTime);
		SmartDashboard.putNumber("state", transmissionStatus);
		
		backLeft.set(-leftSpeed);
		backRight.set(rightSpeed);
		frontLeft.set(-leftSpeed);
		frontRight.set(rightSpeed);
		
		if(transmissionStatus == HIGH)
			shifter.set(DoubleSolenoid.Value.kForward);
		else
			shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
}
