package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Shooter extends SubSystem{

	private final double SHOOTING_SPEED = .90;	//speed for shooting
	private final double SLOWWING_SPEED = .20;	//speed for slowing the shooter down
	private final double STOPPED_SPEED = 0;		//speed for the stopped 0 duh
	
	/*
	 * Shooter Mode Flow
	 * 
	 *		    Stopped
	 * 			  \/
	 * When shoot button is pressed 
	 * 			  \/
	 * 		   Shooting
	 * 			  \/
	 * when switch to slow down is hit
	 * 			  \/
	 * 		    SLOWING
	 * 			  \/
	 * when the switch to stop is hit
	 * 			  \/
	 * 			STOPPED
	 */
	
	private int shooterStatus;			//tracks the mode of the shooter
	private final int STOPPED = 0;		//when the shooter is stopped and waiting to shoot
	private final int SHOOTING = 1;		//when the shooter is shooting and waiting to slow
	private final int SLOWING = 2;		//when the shooter is slowing and waiting to stop
	
	
	private DigitalInput stopped, slowing;		//switches to stop and slow down
	private Talon shooter1, shooter2;			//shooter motors
	
	public Shooter(){
		shooter1 = new Talon(Ports.SHOOTER_1);	//motors are set to ports from the Ports.java file
		shooter2 = new Talon(Ports.SHOOTER_2);	//...
		
		stopped = new DigitalInput(Ports.STOP_SWITCH);	//switches to slow and stop are set to ports from Ports.java file
		slowing = new DigitalInput(Ports.SLOW_SWITCH);	//...
		
		shooterStatus = STOPPED;	//initializes the status to stopped
	}
	
	/*
	 * This is called to enter shooting mode from the main Robot class
	 * It should be called when the shoot button on the controller is pressed
	 */
	public void shootPressed(){
		if(shooterStatus == STOPPED)
			shooterStatus = SHOOTING;
	}
	
	
	public void update(){
		
		/*
		 * Updates the mode of the shooter and sets the speeds based on the mode
		 */
		switch(shooterStatus){
		case SHOOTING:
			//set motors at speed to shoot
			shoot(SHOOTING_SPEED);
			
			//exit shooting mode if shooter has hit stop position
			if(slowing.get())				
				shooterStatus = SLOWING;
			break;
			
		case SLOWING:
			//set the motors to speed for slowing down
			shoot(SLOWWING_SPEED);
			
			//exit slowing mode and stop if shooter has hit the home stop switch
			if(stopped.get())
				shooterStatus = STOPPED;
			break;
			
		case STOPPED:
			//set motor speed to 0
			shoot(STOPPED_SPEED);
		}
			
	}
	
	/*
	 * Used to set the motors to a passed speed
	 */
	private void shoot(double speed){
		shooter1.set(speed);
		shooter2.set(speed);
	}
}
