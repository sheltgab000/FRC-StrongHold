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
	final int MANUAL = 5;
	final int START_POSITION = USER;
	
	int USER_POS = 0;
	int DISPENSING_POS = 3;
	int LOADING_POS = 56; //LIMIT SWITCH;
	int TRAVELING_POS = 400;
	
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
		
	}
	
	boolean ballSeen;
	
	public void update(double speed){
		ballSeen = ball.get();
		SmartDashboard.putBoolean("INTAKE HOME", homeSwitch.get());
		SmartDashboard.putNumber("Pivot State", pivotState);
		
		
		switch(pivotState){
		case LOADING:
			if(ballSeen){	// If you see the ball dont go
				setRollerSpeed(0);
			}
			else{			// If you don't do
				setRollerSpeed(-.8);
			}
			
			setPivotSpeed(-1);	// Pivot goes down
			
			if(ballSeen && homeSwitch.get()) // If it sees the ball and it is down go to USER mode
				pivotState = USER;
			break;
			
		case DISPENSING:
			System.out.println("Dispensing");
			if(intakePot.getValue() >= (DISPENSING_POS - tolerance) && intakePot.getValue() <= (DISPENSING_POS + tolerance)){
				setPivotSpeed(0);
				if(ball.get())					// If the ball is there dispense it
					setRollerSpeed(-.8);
				else{							// Once the ball is no longer there stop the rollers and enable USER
					setRollerSpeed(0);
					pivotState = 0;
				}
			}
			else{
				if(intakePot.getValue() <= DISPENSING_POS - tolerance) // If it is under the target go up
					setPivotSpeed(-1);
				else if(intakePot.getValue() >= DISPENSING_POS + tolerance) // If it is above the target go down
					setPivotSpeed(1);
			}
			break;
			
		case EJECTING:
			System.out.println("Ejecting");
			setRollerSpeed(1);	// Spit out ball
			pivotState = USER;
			break;
		case USER:
			System.out.println("Traveling");
			goToPos(TRAVELING_POS); // Instantly goes to the travel position
			break;
		case MANUAL:
			setPivotSpeed(speed);
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
		if(intakePot.getValue() >= (target - tolerance) && intakePot.getValue() <= (target + tolerance)){
			setPivotSpeed(0);
		}
		// If the position is above or below the target adjust accordingly
		else{
			if(intakePot.getValue() > target)
				setPivotSpeed(1);
			else
				setPivotSpeed(-1);
		}
	}
	
	public void setState(int state){
		pivotState = state;
	}
	
	public int getState(){
		return pivotState;
	}
	
}
