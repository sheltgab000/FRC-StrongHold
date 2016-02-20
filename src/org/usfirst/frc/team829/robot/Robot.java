
package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {		// Variable that controls the slowing speed
	
	static Drive drive;		
	
	static Shooter shooter;
	
	static Intake intake;
	
	Compressor compressor;		// Limit switches used for the shooter
	
	Joystick dual, leftStick, rightStick;	// Controls being used with the robot
	
	//VisionHelper visionHelper;
	   public void robotInit() {	
		   
	       leftStick = new Joystick(Controller.LEFT_STICK);		// Initializes the Joysticks used
	       rightStick = new Joystick(Controller.RIGHT_STICK);	// ...
	       dual = new Joystick(Controller.DUAL);			
	       
	       Compressor compressor = new Compressor();	//set up the compressor
	       compressor.start();							// START COMPRESSOR
	       
	       drive = new Drive();
	       
	       shooter = new Shooter();
	       
	       intake = new Intake();
	       
	       /*visionHelper = new VisionHelper();		//Vision Code - disabled for now  because there is no camera
	       visionHelper.setHueRange(100, 155);
	       visionHelper.setSatRange(67, 255);
	       visionHelper.setValRange(200, 255);
	       visionHelper.setUploadingToServer(true);*/
        
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {	// Autonomous period
    	
    }

    
    public void teleopInit(){

        
        
    }
    
    public void teleopPeriodic() {			// Teleop Period
        					
    	SmartDashboard.putBoolean("Fire Button", dual.getRawButton(Controller.SHOOT_BUTTON));	//Debugging display to SmartDashboard
    	SmartDashboard.putNumber("Intake Potentiometer", intake.intakePot.getValue());			//			  \/
    	SmartDashboard.putBoolean("Dart Home", intake.homeSwitch.get());						//			  \/
    	SmartDashboard.putBoolean("Ball Viewer", intake.ball.get());							//			  \/
    	
    	drive.update(-leftStick.getY(), -rightStick.getY());	// Drives using joysticks, and changes transmission if needed
    	
    	if(dual.getRawButton(Controller.TRANSMISSION_BUTTON)){	// Changes transmission if corresponding button is pressed
    		drive.transmissionPressed();
    	}
    	
    	//shooter.setDartSpeed(-dual.getRawAxis(3));	//manual control of dart - Disabled for now
    	shooter.update();	//updates the shooter statuses and controls the speeds
    	
    	
    	if(dual.getRawButton(Controller.SHOOT_BUTTON))	//set the shooter status to SHOOTING when button is pressed
    		shooter.shootPressed();	
    	
    	intake.update(-dual.getRawAxis(1));			//update the intakes movement based on state and sends the joystick value if state is User-Control	
    	
    	if(dual.getRawButton(Controller.INTAKE_LOAD))		//Move the shooter to load and load ball to intake
    		intake.upOut();
    	else if(dual.getRawButton(Controller.INTAKE_EJECT))	//Eject the ball tout of the intake
    		intake.ejecting();
    	else if(dual.getRawButton(Controller.INTAKE_IN))	//Move the intake down and load a ball into it
    		intake.downIn();
    	/*else
    		intake.setRollerSpeed(0);*/
    	
    	if(dual.getRawButton(Controller.DART_TO_IN)){		//move the shooter to the down position
    		if(shooter.dartIn.get())
    			shooter.dartMotor.set(-1);
    		else if(!shooter.dartIn.get())
    			shooter.dartMotor.set(0);
    	}
    	else if(dual.getRawButton(Controller.DART_TO_OUT)){		//move the shooter to the up position
    		System.out.println("Moving to out position");
    		if(shooter.dartOut.get())
    			shooter.dartMotor.set(1);
    		else if(!shooter.dartOut.get())
    			shooter.dartMotor.set(0);
    	}
    			
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