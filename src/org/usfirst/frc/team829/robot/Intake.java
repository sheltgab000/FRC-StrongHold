package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Intake {

	final int tolerance = 25;
	
	//Constants for the pivot
	final int INITIALIZING = -1;
	final int INTAKE_POS = 0;
	final int TRAVEL_POS = 1;
	final int LOADING_POS = 2;
	
	//Constants for the loading state
	final int INTAKING = 0;
	final int STORED = 1;
	final int EMPTY = 2;
	final int LOADING = 3;
	final int EXPORTING = 4;
	
	//Encoder positions for the pivot
	//TODO enter positions
	
	Talon pivot, roller;		//motors
	DigitalInput ballDetector;	//photo sensor for if the ball is in the intake
	DigitalInput homeSwitch;	//home position to calibrate the pivot
	Encoder encoder;			//Tracks the position of the pivot
	
	int currentPos;
	int loadingState;
	
	public Intake(){
		//Set up tracking variables
		currentPos = INITIALIZING;
		loadingState = EMPTY;
		
		//Set up hardware
		pivot = new Talon(Ports.INTAKE_PIVOT);
		roller = new Talon(Ports.INTAKE_ROLLER);
		ballDetector = new DigitalInput(Ports.INTAKE_BALL_DETECTOR);
		homeSwitch = new DigitalInput(Ports.INTAKE_HOME_SWITCH);
		//TODO set up encoder
	}
	
	
	
	public void update(){
		if(currentPos == INITIALIZING){
			/*TODO initialize*/;
		}
		else{
			switch(loadingState){
			case INTAKING:
				
				break;
			case STORED:
				
				break;
			case EMPTY:
				
				break;
			case LOADING:
				
				break;
			case EXPORTING:
				
				break;
			}
		}
	}
	
	
	public void intakePressed(){
		if(loadingState == STORED)
			loadingState = EXPORTING;
		else if(loadingState == EMPTY)
			loadingState = INTAKING;
	}
	
	public void loadPressed(){
		if(loadingState == STORED)
			loadingState = LOADING;
	}
	
	public void setPivotSpeed(double speed){
		pivot.set(speed);
	}
	
	public void setRollerSpeed(double speed){
		roller.set(speed);
	}
	
	public boolean goToPos(int target){
		if(target == currentPos){
			return true;
		}
		else{
			//TODO Get the encoder code in here
		}
		return false;
	}
	
	
}
