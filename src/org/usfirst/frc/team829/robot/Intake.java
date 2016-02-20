package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	final int tolerance = 25;
	
	int pivotState;
	final int USER = 0;	
	final int DISPENSING = 1;
	final int LOADING = 2;
	final int TRAVELING = 3;
	final int EJECTING = 4;
	final int START_POSITION = USER;
	
	int USER_POS = 0;
	int DISPENSING_POS = 3;
	int LOADING_POS = 56; //LIMIT SWITCH;
	int TRAVELING_POS = 0;
	
	Talon pivot, roller;		//motors
	DigitalInput ballDetector;	//photo sensor for if the ball is in the intake
	DigitalInput homeSwitch;	//home position to calibrate the pivot
	DigitalInput ball;
	AnalogInput intakePot;
	Encoder encoder;			//Tracks the position of the pivot
	
	public Intake(){
		//Set up tracking variables
		pivotState = START_POSITION;
		
		//Set up hardware
		pivot = new Talon(Ports.INTAKE_PIVOT);
		roller = new Talon(Ports.INTAKE_ROLLER);
		ballDetector = new DigitalInput(Ports.INTAKE_BALL_DETECTOR);
		homeSwitch = new DigitalInput(Ports.INTAKE_HOME_SWITCH);
		intakePot = new AnalogInput(Ports.INTAKE_POT);
		ball = new DigitalInput(Ports.BALL_SWITCH);
		//TODO set up encoder
	}
	
	
	
	public void update(double speed){
		
		SmartDashboard.putBoolean("BALL SWITCH", ball.get());
		
		switch(pivotState){
		case LOADING:
			if(homeSwitch.get())				// If the home switch is pressed pivot stays at position
				setPivotSpeed(0);
			else								// If home switch isn't pressed pivot goes down
				setPivotSpeed(-1);
			
			if(ball.get())						// If the IR is triggered rollers stop spinning
				setRollerSpeed(0);
			else
				setPivotSpeed(-1);				// If the IR isn't triggered rollers keep spinning
			
			if(homeSwitch.get() & ball.get())	// When both the home switch and IR are triggered USER control is enabled
				pivotState = USER;
			
		case DISPENSING:
			if(intakePot.getValue() >= (LOADING_POS - tolerance) || intakePot.getValue() <= (LOADING_POS + tolerance))
				if(ball.get())					// If the ball is there dispense it
					setRollerSpeed(-.4);
				else{							// Once the ball is no longer there stop the rollers and enable USER
					setRollerSpeed(0);
					pivotState = 0;
				}
			else
				goToPos(LOADING_POS);			// Goes to the specified potentiometer position.
			break;
		case EJECTING:
			setRollerSpeed(1);
			pivotState = USER;
			break;
		case USER:
			setPivotSpeed(speed);				// Sets the pivot speed to that of the axis
			break;
		}
	}
	
	public void downIn(){
		pivotState = LOADING;					// Makes the pivot state change to loading
	}
	
	public void upOut(){
		pivotState = DISPENSING;				// Makes the pivot state change to 
	}
	
	public void ejecting(){
		pivotState = EJECTING;
	}
	
	public void setPivotSpeed(double speed){	// Set pivot speed
		// Disables the pivot if attempting to go down while the limit switch is triggered
		if(speed < 0 && homeSwitch.get())
			pivot.set(0);
		else
			pivot.set(speed);
	}
	
	public void setRollerSpeed(double speed){	// Set roller speed
		roller.set(speed);
	}
	
	public void goToPos(int target){
		
		// Stops pivot if it is within the tolerance of the target
		if(intakePot.getValue() >= (target - tolerance) || intakePot.getValue() <= (target + tolerance)){
			setPivotSpeed(0);
		}
		// If the position is above or below the target adjust accordingly
		else{
			if(intakePot.getValue() > target)
				setPivotSpeed(-1);
			else
				setPivotSpeed(1);
		}
	}
	
	
}
