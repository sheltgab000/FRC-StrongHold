
package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {		// Variable that controls the slowing speed
	
	
	Drive drive;		// Variable that stores the shooter's status
	
	Shooter shooter;	//controls the shooter
	
	//Ultrasonic rangeFinder;
	
	//double range; 
	
	Intake intake;	//interface to the intake
	
	Compressor compressor;		// Limit switches used for the shooter
	
	Joystick dual, leftStick, rightStick;	// Controls being used with the robot
	
	//VisionHelper visionHelper;
	
	
	
	   public void robotInit() {	
		   
	       leftStick = new Joystick(2);		// Initializes the Joysticks used
	       rightStick = new Joystick(0);	// ...
	       dual = new Joystick(3);			
	       
	       compressor = new Compressor();	//set up the compressor
	       compressor.start();							// START COMPRESSOR
	       
	       drive = new Drive();
	       
	       shooter = new Shooter();
	       
	      // rangeFinder = new Ultrasonic(Ports.RANGE_OUT, Ports.RANGE_IN);
	       //Once the range finder was coded the code would not connect to the driver station then "robo rio wsnt" connected 
	       
	     // rangeFinder.setAutomaticMode(true);
	       
	       
	       /*visionHelper = new VisionHelper();
	       visionHelper.setHueRange(100, 155);
	       visionHelper.setSatRange(67, 255);
	       visionHelper.setValRange(200, 255);
	       visionHelper.setUploadingToServer(true);*/
	       
	       intake = new Intake();
        
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {	// Autonomous period
    	
    }

    
    public void teleopInit(){

        
        
    }
    
    public void teleopPeriodic() {			// Teleop Period	
    	
    	//range = rangeFinder.getRangeInches(); 
    	
    	drive.update(-leftStick.getY(), -rightStick.getY());	// Drives using joysticks, and changes transmission if needed
    	
    	if(rightStick.getRawButton(2)){		// If the transmission button is pressed
    		drive.transmissionPressed();	//send a signal to change the transmission	
    	}
    	
    	if(dual.getRawButton(2))		//When the shoot button is pressed
    		shooter.shootPressed();		//tell the shooter that it needs to start shooting
    	shooter.update();				//update the shooter every iteration
    	
    	
    	intake.setPivotSpeed(dual.getY(Hand.kLeft));	//Test code for intake
														// 	  -----\/-----
    	if(dual.getRawButton(4))						// 	  -----\/-----
    		intake.setRollerSpeed(1);					// 	  -----\/-----
    	else if(dual.getRawButton(2))					// 	  -----\/-----
    		intake.setRollerSpeed(.5);					// 	  -----\/-----
    	else if (dual.getRawButton(1))					// 	  -----\/-----
    		intake.setRollerSpeed(-.5);					// 	  -----\/-----
    	else if(dual.getRawButton(3))					// 	  -----\/-----
    		intake.setRollerSpeed(-1);					// 	  -----\/-----
    	else											// 	  -----\/-----
    		intake.setRollerSpeed(0);					//	  -----\/-----
    	
    }
    
    
    public void testPeriodic() {
    
    }
    
    public void disabledInit(){
    	
    }
    
}
