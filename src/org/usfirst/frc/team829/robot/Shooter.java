package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
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
	
	private int shooterStatus;		// Variable that stores shooter's status
	private final int STOPPED = 0;	// stopped
	private final int SHOOTING = 1;	// shooting
	private final int SLOWING = 2;	// slowing
	
	private int LOAD_POS = 20;
	private int SHOOT_POS = 50;
	private int TRAVEL_POS = 10;
	
	DigitalInput stopSwitch, slowSwitch, dartOut, dartIn;	// Limit switches
	
	AnalogInput dartPot;
	
	CANTalon shooter1, shooter2;
	Talon dartMotor;	// Motors for shooter and dart
	
	// Constructor
	public Shooter(){	// Set's everything to it's default value
		stopSwitch = new DigitalInput(Ports.STOP_SWITCH);
		slowSwitch = new DigitalInput(Ports.SLOW_SWITCH);
		dartIn = new DigitalInput(Ports.DART_IN_SWITCH);
		dartOut = new DigitalInput(Ports.DART_OUT_SWITCH);
		
		dartPot = new AnalogInput(Ports.DART_POT);
		
		shooter1 = new CANTalon(Ports.SHOOTER_1);
		shooter2 = new CANTalon(Ports.SHOOTER_2);
		dartMotor = new Talon(Ports.DART_MOTOR);
		
		
		shooterStatus = STOPPED;
		
		startTime = System.currentTimeMillis();
		
		SmartDashboard.putNumber("shootSpeed", shootSpeed);
		SmartDashboard.putNumber("slowSpeed", slowSpeed);
		SmartDashboard.putNumber("Time for Shoot", TIME_FOR_SHOOT);
		
		// dartStatus = IN;
	}
	
	public void shootPressed(){	// Change status when button pressed
		if(shooterStatus == STOPPED){
			shooterStatus = SHOOTING;
			startTime = System.currentTimeMillis();
		}
	}
	
	public void dartUpPressed(){
	}
	
	public void update(){
		
		SmartDashboard.putNumber("Dart Pot:", dartPot.getValue());
		SmartDashboard.putBoolean("Dart In", dartIn.get());
		SmartDashboard.putBoolean("Dart Out", dartOut.get());
		SmartDashboard.putBoolean("Stop Switch", stopSwitch.get());
		SmartDashboard.putBoolean("slowSwitch", slowSwitch.get());
		SmartDashboard.putNumber("shooter status", shooterStatus);
		shootSpeed = SmartDashboard.getNumber("shootSpeed");
		slowSpeed = SmartDashboard.getNumber("slowSpeed");
		TIME_FOR_SHOOT = SmartDashboard.getNumber("Time for Shoot");
			
		
		switch(shooterStatus){
		case SHOOTING:
			shoot(shootSpeed);
			if(!slowSwitch.get()/* || !stopSwitch.get() */|| System.currentTimeMillis() - startTime >= TIME_FOR_SHOOT)
				shooterStatus = SLOWING;
			break;
		case SLOWING:
			shoot(slowSpeed);
			if(!stopSwitch.get())
				shooterStatus = STOPPED;
			break;
		case STOPPED:
			shoot(stopSpeed);
			break;
		}
		
	}
	
	public int getShooterStatus(){
		return shooterStatus;
	}
	
	public void shoot(double speed){
		shooter1.set(speed);
		shooter2.set(speed);
	}
	
	public void setDartSpeed(double speed){
		
		//System.out.println("Speed: " + speed + " Dart Out: " + dartOut.get() + " Dart In: " + dartIn.get());
		
		if(dartIn.get() == false)
			System.out.println("Dart In");
		if(dartOut.get() == false)
			System.out.println("Dart Out");
		if(speed > 0 && dartOut.get() == true){
			dartMotor.set(speed);
			System.out.println("Running");
		}
		else if(speed < 0 && dartIn.get() == true){
			dartMotor.set(speed);
			System.out.println("Running");
		}
		else if((speed >= 0 || speed <= 0) && (dartIn.get() == false || dartOut.get() == false))
			dartMotor.set(0);
	}
}
