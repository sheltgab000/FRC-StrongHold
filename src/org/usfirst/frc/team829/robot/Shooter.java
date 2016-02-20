package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	
	// Fields
	private double shootSpeed = 0.90;	// Variables for when shooting
	private double slowSpeed = 0.18;	// slowing
	private final double stopSpeed = 0;		// or stopping
	
	private double TIME_FOR_SHOOT = 275;	//variables for shooter time kill
	private long startTime;					//			...
	
	private int dartStatus;					// status for the dart 
	private final int UP = 0;				// status for up
	private final int DOWN = 1;				// status for down
	private final int USER = 2;				// status for user ("TRAVEL") 
	
	private int shooterStatus;		// Variable that stores shooter's status
	private final int STOPPED = 0;	// stopped
	private final int SHOOTING = 1;	// shooting
	private final int SLOWING = 2;	// slowing
	private final int READY = 3;	// ready
	
	private int LOAD_POS = 20;
	private int SHOOT_POS = 50;
	private int TRAVEL_POS = 10;
	
	boolean hasRun = false;
	
	DigitalInput stopSwitch, homeSwitch, dartOut, dartIn;	// Limit switches
	
	AnalogInput dartPot;
	
	CANTalon shooter1, shooter2;
	Talon dartMotor;	// Motors for shooter and dart
	
	// Constructor
	public Shooter(){	// Set's everything to it's default value
		homeSwitch = new DigitalInput(Ports.STOP_SWITCH);
		stopSwitch = new DigitalInput(Ports.SLOW_SWITCH);
		dartIn = new DigitalInput(Ports.DART_IN_SWITCH);
		dartOut = new DigitalInput(Ports.DART_OUT_SWITCH);
		
		dartPot = new AnalogInput(Ports.DART_POT);
		
		shooter1 = new CANTalon(Ports.SHOOTER_1);
		shooter2 = new CANTalon(Ports.SHOOTER_2);
		dartMotor = new Talon(Ports.DART_MOTOR);
		shooter1.enableBrakeMode(true);
		shooter2.enableBrakeMode(true);
		
		dartStatus = USER;
		
		shooterStatus = STOPPED;
		
		startTime = System.currentTimeMillis();
		
		SmartDashboard.putNumber("shootSpeed", shootSpeed);
		SmartDashboard.putNumber("slowSpeed", slowSpeed);
		SmartDashboard.putNumber("Time for Shoot", TIME_FOR_SHOOT);
		
		// dartStatus = IN;
	}
	
	public void shootPressed(){	// Change status when button pressed
		if(hasRun){
			shooterStatus = SHOOTING;
			startTime = System.currentTimeMillis();
		}
	}
	
	public void dartUpPressed(){ // Set dartstatus to up
		dartStatus = UP;
	}
	
	public void dartDownPressed(){
		dartStatus = DOWN;			// set dartstatus to down
	}
	
	public void update(double speed){
		
		SmartDashboard.putNumber("Dart Pot:", dartPot.getValue());		//Add debugging values to the SmartDashboard
		SmartDashboard.putBoolean("Dart In", dartIn.get());				//				  \/
		SmartDashboard.putBoolean("Dart Out", dartOut.get());			//				  \/
		SmartDashboard.putBoolean("Stop Switch", stopSwitch.get());		//				  \/
		SmartDashboard.putBoolean("Shooter Home Switch", homeSwitch.get());		//				  \/		
		SmartDashboard.putNumber("shooter status", shooterStatus);		//				  \/	
		shootSpeed = SmartDashboard.getNumber("shootSpeed");			//				  \/
		slowSpeed = SmartDashboard.getNumber("slowSpeed");				//				  \/
		TIME_FOR_SHOOT = SmartDashboard.getNumber("Time for Shoot");	//				  \/
		
		switch(dartStatus){
		case UP:
			if(dartIn.get())	// if the dart isn't in go down
    			setDartSpeed(-.5);
    		else if(!dartIn.get())	// if the dart is in enable user control
    			dartStatus = USER;
			break;
		case DOWN:
			if(dartOut.get()) // if the dart isn't out go up
    			setDartSpeed(.5);
    		else if(!dartOut.get())	// if the dart is out enable user control
    			dartStatus = USER;
			break;
		case USER:
			setDartSpeed(speed);	// move according to the users speed
			break;
		}
		
		//Move through the shooter statuses and adjust accordingly
		switch(shooterStatus){	
		case SHOOTING:			//Go at shootign speed until either the slow switch is hit or the timeout expires
			System.out.println("Shooting");
			shoot(shootSpeed);
			if(System.currentTimeMillis() - startTime >= TIME_FOR_SHOOT)	//moves to a slowing status when the slow switch is hit
				shooterStatus = SLOWING;														//or the time out expires
			break;
		case SLOWING:		//Go at the slow down speed till the stop/home switch is hit 
			System.out.println("Slowing");
			shoot(slowSpeed);
			if(!stopSwitch.get())
				shooterStatus = STOPPED;	//move to STOPPED mode when the switch is seen
			break;
		case STOPPED:			//Don't move
			System.out.println("Stopped");
			shoot(stopSpeed);
			break;
		case READY:
			System.out.println("Ready");
			if(!hasRun){
				/*if(dartOut.get()){
					setDartSpeed(1);
				}
				else */if(homeSwitch.get()){
					shoot(.2);
				}
				else if(!homeSwitch.get()){
					System.out.println("Passed sensor");
					shoot(0);
					hasRun = true;
					shooterStatus = STOPPED;
				}
			}
		}
		
	}
	
	public int getShooterStatus(){
		return shooterStatus;
	}
	
	public void readyPressed(){
		shooterStatus = READY;
		hasRun = false;
	}
	
	public void shoot(double speed){
		shooter1.set(speed);
		shooter2.set(speed);
	}
	
	public void setDartSpeed(double speed){
		
		//Only allow the dart to move within the limit switches
		if(speed > 0 && dartOut.get() == true){		//If the dart is going up and the up switch is not active - go up
			dartMotor.set(speed);
		}
		else if(speed < 0 && dartIn.get() == true){	//If the dart is going down and the down switch is not active - go down
			dartMotor.set(speed);
		}
		else	//stop it if switches are active
			dartMotor.set(0);
	}
}
