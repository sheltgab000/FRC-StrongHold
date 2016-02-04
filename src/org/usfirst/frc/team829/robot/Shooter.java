package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Shooter {
	
	// Fields
	private final double shootSpeed = 0.90;	// Variables for when shooting
	private final double slowSpeed = 0.20;	// slowing
	private final double stopSpeed = 0;		// or stopping
	
	private final double OUT_COUNT = 57;	// Variables for encoder counts
	private final double IN_COUNT = 0;		// ...
	
	private boolean initialized;	// Boolean that keeps track of whether it has been initialized or not
	
	private int shooterStatus;		// Variable that stores shooter's status
	private final int STOPPED = 0;	// stopped
	private final int SHOOTING = 1;	// shooting
	private final int SLOWING = 2;	// slowing
	
	private int dartStatus;		// Variable that stores dart's status
	private final int OUT = 1;	// out
	private final int IN = 0;	// in
	
	DigitalInput stopSwitch, slowSwitch, travelSwitch, dartHome;	// Limit switches
	
	Encoder dartEncoder;	// Encoder
	
	Talon shooter1, shooter2, dartMotor;	// Motors for shooter and dart
	
	// Constructor
	public Shooter(){	// Set's everything to it's default value
		stopSwitch = new DigitalInput(Ports.STOP_SWITCH);
		slowSwitch = new DigitalInput(Ports.SLOW_SWITCH);
		travelSwitch = new DigitalInput(Ports.TRAVEL_SWITCH);
		dartHome = new DigitalInput(Ports.DART_HOME);
		
		dartEncoder = new Encoder(Ports.DART_ENCODER_1, Ports.DART_ENCODER_2);
		
		shooter1 = new Talon(Ports.SHOOTER_1);
		shooter2 = new Talon(Ports.SHOOTER_2);
		dartMotor = new Talon(Ports.DART_MOTOR);
		
		initialized = false;
		
		shooterStatus = STOPPED;
		dartStatus = IN;
	}
	
	public void shootPressed(){
		if(shooterStatus == STOPPED)
			shooterStatus = SHOOTING;
	}
	
	public void update(){
		
		if(initialized){
			if(shooterStatus == STOPPED){
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
			
			if(dartStatus == OUT){
				while(dartEncoder.get() != OUT_COUNT)
					dartMotor.set(-1);
			}
			else{
				while(dartEncoder.get() != IN_COUNT)
					dartMotor.set(1);
			}
		}
		else{
			if(dartHome.get() == true){
				dartEncoder.reset();
				initialized = true;
			}
			else{
				dartMotor.set(-1);
			}
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
	
}
