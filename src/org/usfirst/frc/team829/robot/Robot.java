
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
	
	Shooter shooter;
	
	//Ultrasonic rangeFinder;
	
	//double range; 
	
	Intake intake;
	
	Compressor compressor;		// Limit switches used for the shooter
	
	Joystick dual, leftStick, rightStick;	// Controls being used with the robot
	
	//VisionHelper visionHelper;
	
	AnalogInput pot;
	
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
	       
	     //  rangeFinder.setAutomaticMode(true);
	       
	       pot = new AnalogInput(1);
	       
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
        					
    	SmartDashboard.putBoolean("Fire Button", dual.getRawButton(2));	
    	
    	//range = rangeFinder.getRangeInches(); 
    	
    	drive.update(-leftStick.getY(), -rightStick.getY());	// Drives using joysticks, and changes transmission if needed
    	
    	SmartDashboard.putNumber("leftStick", leftStick.getY());
    	SmartDashboard.putNumber("rightStick", rightStick.getY());
    	
    	if(rightStick.getRawButton(2)){		// Changes transmission if Button 3 is pressed
    		drive.transmissionPressed();
    	}
    	
    	if(dual.getRawButton(2))
    		shooter.shootPressed();
    	
    	shooter.update();
    	
    	intake.setPivotSpeed(-dual.getRawAxis(1));
    	shooter.setDartSpeed(-dual.getRawAxis(3));
    	
    	
    	
    	if(dual.getRawButton(4))
    		intake.setRollerSpeed(1);
    	else if(dual.getRawButton(2))
    		intake.setRollerSpeed(.5);
    	else if (dual.getRawButton(1))
    		intake.setRollerSpeed(-.5);
    	else if(dual.getRawButton(3))
    		intake.setRollerSpeed(-1);
    	else
    		intake.setRollerSpeed(0);
    	
    	SmartDashboard.putNumber("pot", pot.getValue());
    }
    
    
    public void testPeriodic() {
    
    }
    
    public void disabledInit(){
    	
    }
    
}
