
package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {		// Variable that controls the slowing speed
	
	static Drive drive;		// Variable that stores the shooter's status
	
	static Shooter shooter;
	
	Compressor compressor;		// Limit switches used for the shooter
	
	Joystick dual, leftStick, rightStick;	// Controls being used with the robot
	
	VisionHelper visionHelper;
	   public void robotInit() {	
		   
	       leftStick = new Joystick(Controller.LEFT_STICK);		// Initializes the Joysticks used
	       rightStick = new Joystick(Controller.RIGHT_STICK);	// ...
	       dual = new Joystick(Controller.DUAL);			
	       
	       Compressor compressor = new Compressor();	//set up the compressor
	       compressor.start();							// START COMPRESSOR
	       
	       drive = new Drive();
	       
	       shooter = new Shooter();
	       
	       visionHelper = new VisionHelper();
	       visionHelper.setHueRange(100, 155);
	       visionHelper.setSatRange(67, 255);
	       visionHelper.setValRange(200, 255);
	       visionHelper.setUploadingToServer(true);
        
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {	// Autonomous period
    	
    }

    
    public void teleopInit(){

        
        
    }
    
    public void teleopPeriodic() {			// Teleop Period
        					
    	SmartDashboard.putBoolean("Fire Button", dual.getRawButton(Controller.FIRE_BUTTON));	
    	
    	drive.update(-leftStick.getY(), -rightStick.getY());	// Drives using joysticks, and changes transmission if needed
    	
    	if(dual.getRawButton(Controller.TRANSMISSION_BUTTON)){		// Changes transmission if Button 3 is pressed
    		drive.transmissionPressed();
    	}
    	
    	shooter.update();
    	
    	if(dual.getRawButton(Controller.FIRE_BUTTON))
    		shooter.shootPressed();	
    	
    }
    
    
    public void testPeriodic() {
    
    }
    
    public void disabledInit(){
    	
    }
    
    public static Drive getDrive(){
    	return drive;
    }
    
    public static Shooter getShooter(){
    	return shooter;
    }
    
}
