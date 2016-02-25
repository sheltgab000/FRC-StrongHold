package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends SubSystem{

	int transmissionStatus;	//Tracks the current status of the transmission
	
	final int HIGH = 0;
	final int LOW = 1;
	final int DEFAULT_STATE = HIGH;
	
	private final int BUTTON_TIMEOUT = 250;	    //A time out within which the transmission switch will not work between presses
	private long startTime;						//allows comparison to see if the timeout time has expired
	
	DoubleSolenoid shifter;		//allow shifting
	
	CANTalon backLeft, backRight, frontLeft, frontRight;	//control the drive train
	
	public Drive(){
		transmissionStatus = DEFAULT_STATE;	// Sets default transmissionStatus
		shifter = new DoubleSolenoid(Ports.SHIFTER_HIGH, Ports.SHIFTER_LOW);	// Initializes the Double Solenoid for shifting
		
		backLeft = new CANTalon(Ports.DRIVE_BACK_LEFT);		//initialize the drive motors
		backRight = new CANTalon(Ports.DRIVE_BACK_RIGHT);	//			\/
		frontLeft = new CANTalon(Ports.DRIVE_FRONT_LEFT);	//			\/
		frontRight = new CANTalon(Ports.DRIVE_FRONT_RIGHT); //			\/
		
		startTime = System.currentTimeMillis();		//set the initial time for the shift button timeout
	}
	
	
	public void transmissionPressed(){	// Whenever the button is pressed swaps the current value of the transmission
		if(System.currentTimeMillis() - startTime >= BUTTON_TIMEOUT){	//if the timeout has not expired - shift
			if(transmissionStatus == HIGH)
				transmissionStatus = LOW;
			else
				transmissionStatus = HIGH;
			startTime = System.currentTimeMillis();	//reset the timer for the timeout
		}
	}
	
	public void update(double leftSpeed, double rightSpeed){	// Changes the speed of the drive accordingly as well as pushing out or pulling in the solenoid
		
		SmartDashboard.putNumber("CurrentTime", startTime);						//Debugging values to the SmartDashboard
		SmartDashboard.putNumber("state", transmissionStatus);					//					\/
		SmartDashboard.putNumber("Left Encoder:", backLeft.getEncPosition());	//					\/
		SmartDashboard.putNumber("Right Encoder", backRight.getEncPosition());	//					\/
		
		backLeft.set(-leftSpeed);	//set drive motors and reverse the left side
		backRight.set(rightSpeed);	//					\/
		frontLeft.set(-leftSpeed);	//					\/
		frontRight.set(rightSpeed);	//					\/
		
		if(transmissionStatus == HIGH)		//update the transmission based on the current state
			shifter.set(DoubleSolenoid.Value.kForward);
		else
			shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public long getLeftEncoder(){
		return backLeft.getEncPosition();
	}
	
	public long getRightEncoder(){
		return backRight.getEncPosition();
	}
	
}
