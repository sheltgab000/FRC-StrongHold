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
			if(!homeSwitch.get() && !ball.get()){
				if(!homeSwitch.get())
					setPivotSpeed(-1);
				else
					setPivotSpeed(0);
				if(!ball.get())
					setRollerSpeed(-.4);
				else
					setRollerSpeed(0);
			}
			else{
				setRollerSpeed(0);
				setPivotSpeed(0);
				pivotState = USER;
			}
			break;
		case DISPENSING:
			goToPos(LOADING_POS);
			break;
		case USER:
			setPivotSpeed(speed);
			break;
		}
	}
	
	public void downIn(){
		pivotState = LOADING;
	}
	
	public void upOut(){
		pivotState = DISPENSING;
	}
	
	public void setPivotSpeed(double speed){
		if(speed < 0 && homeSwitch.get())
			pivot.set(0);
		else
			pivot.set(speed);
	}
	
	public void setRollerSpeed(double speed){
		roller.set(speed);
	}
	
	public void goToPos(int target){
		
		if(intakePot.getValue() >= (target - tolerance) || intakePot.getValue() <= (target + tolerance)){
			setPivotSpeed(0);
		}
		else{
			if(intakePot.getValue() > target)
				setPivotSpeed(-1);
			else
				setPivotSpeed(1);
		}
	}
	
	
}
