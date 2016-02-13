package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	
	// Fields
	private final double shootSpeed = 0.90;	// Variables for when shooting
	private final double slowSpeed = 0.20;	// slowing
	private final double stopSpeed = 0;		// or stopping
	
	// private final double OUT_COUNT = 57;	// Variables for encoder counts
	// private final double IN_COUNT = 0;		// ...
	
	private boolean initialized;	// Boolean that keeps track of whether it has been initialized or not
	
	private int shooterStatus;		// Variable that stores shooter's status
	private final int STOPPED = 0;	// stopped
	private final int SHOOTING = 1;	// shooting
	private final int SLOWING = 2;	// slowing
	
	private int dartStatus;		// Variable that stores dart's status
	// private final int OUT = 1;	// out
	// private final int IN = 0;	// in
	
	DigitalInput stopSwitch, slowSwitch, dartOut, dartIn;	// Limit switches
	
	AnalogInput dartPot;
	
	Talon shooter1, shooter2, dartMotor;	// Motors for shooter and dart
	
	// Constructor
	public Shooter(){	// Set's everything to it's default value
		stopSwitch = new DigitalInput(Ports.STOP_SWITCH);
		slowSwitch = new DigitalInput(Ports.SLOW_SWITCH);
		dartIn = new DigitalInput(Ports.DART_IN_SWITCH);
		dartOut = new DigitalInput(Ports.DART_OUT_SWITCH);
		
		dartPot = new AnalogInput(Ports.DART_POT);
		
		shooter1 = new Talon(Ports.SHOOTER_1);
		shooter2 = new Talon(Ports.SHOOTER_2);
		dartMotor = new Talon(Ports.DART_MOTOR);
		
		initialized = false;
		
		shooterStatus = STOPPED;
		// dartStatus = IN;
	}
	
	public void shootPressed(){	// Change status when button pressed
		if(shooterStatus == STOPPED)
			shooterStatus = SHOOTING;
	}
	
	public void dartUpPressed(){
	}
	
	public void update(){
		
		SmartDashboard.putNumber("Dart Pot:", dartPot.getValue());
		SmartDashboard.putBoolean("Dart In", dartIn.get());
		SmartDashboard.putBoolean("Dart Out", dartOut.get());
		
		if(initialized){	// Init if it hasn't been initialised
			if(shooterStatus == STOPPED){	// Update shooter according to status
				shoot(stopSpeed);
			}
			else if(shooterStatus == SHOOTING){
				if(slowSwitch.get()){
					shooterStatus = SLOWING;
				}
				else{
					shoot(shootSpeed);
				}
			}
			else{
				if(stopSwitch.get()){
					shooterStatus = STOPPED;
				}
				else{
					shoot(slowSpeed);
				}
			}
			
		}
		else{
			/* if(dartHome.get() == true){
				dartEncoder.reset();
				initialized = true;
			}
			else{
				dartMotor.set(-1);	//TODO a bit fast? Maybe
			} */
		}
		
	}
	
	public int getShooterStatus(){
		return shooterStatus;
	}
	
	public int getDartStatus(){
		return dartStatus;
	}
	
	public void shoot(double speed){
		shooter1.set(speed);
		shooter2.set(speed);
	}
	
	public void setDartSpeed(double speed){
		if(speed > 0 && dartOut.get() != true)
			dartMotor.set(speed);
		else if(dartIn.get() != true)
			dartMotor.set(speed);
	}
}
