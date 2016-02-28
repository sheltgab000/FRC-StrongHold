
package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {		// Variable that controls the slowing speed
	
	static Drive drive;	// Creates the drive object: controls driving and transmission		
	
	static Shooter shooter;	// Creates the shooter object: controls shooting and the DART
	
	static Intake intake;	// Creates the intake object: controls the intake's pivot and rollers
	
	Compressor compressor;		// Limit switches used for the shooter
	boolean manualMode;	// Keeps track of whether or not the controls are in Manual Mode
	long currentTime, modeTime;	// Variables that stor currentTie and modeTime to create a delay for toggling modes
	long modeDelay = 500;
	
	static Joystick dual;	// Creates the joystick object that allows you to set the controls for driving

	Joystick leftStick;	// Creates the joystick object for the leftStick

	Joystick rightStick;	// Creates the joystick object for the rightStick
	
	VisionHelper visionHelper;	// Vision and stuff
	public void robotInit() {	
		   
		leftStick = new Joystick(Controller.LEFT_STICK);		// Initializes the Joysticks used
	    rightStick = new Joystick(Controller.RIGHT_STICK);	// ...
	    dual = new Joystick(Controller.DUAL);			
	       
	    Compressor compressor = new Compressor(7);	//set up the compressor
	    compressor.start();							// START COMPRESSOR
	       
	    manualMode = false;
	     
	    drive = new Drive();
	       
	    shooter = new Shooter();
	       
	    intake = new Intake();
	       
	    visionHelper = new VisionHelper();		//Vision Code - disabled for now  because there is no camera
	    /*visionHelper.setHueRange(100, 155);
	    visionHelper.setSatRange(67, 255);
	    visionHelper.setValRange(200, 255);*/
	    visionHelper.setUploadingToServer(true);
        
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {	// Autonomous period
    	
    }

    
    public void teleopInit(){

        visionHelper.startAquisition();
        
    }
    
    public void teleopPeriodic() {			// Teleop Period
        
    	visionHelper.createBinaryImage();						//---Upload Values to SmartDashboard---
    	SmartDashboard.putBoolean("Manual Mode", manualMode);	//				   \/
    	
    	currentTime = System.currentTimeMillis();	// Gets the current time every 20 milliseconds
    	
    	shooter.update(-dual.getRawAxis(3));	//updates the shooter statuses and controls the speed
    	drive.update(-leftStick.getY(), -rightStick.getY());	// Allows the drive to be controlled by the joysticks  
    	
    	if(rightStick.getRawButton(Controller.TRANSMISSION_BUTTON)){	// Changes transmission if corresponding button is pressed
    		drive.transmissionPressed();
    	}
    	
    	if(dual.getRawButton(Controller.SHOOT_BUTTON) && dual.getRawButton(Controller.SHOOT_SAFETY))	//set the shooter status to SHOOTING when button is pressed
    		if(intake.pivotState == intake.USER)
    			shooter.shootPressed();
    		else
    			intake.pivotState = intake.USER;
    	
    	if(dual.getRawButton(10))
    		if(intake.pivotState == intake.USER && !shooter.dartOut.get())
    			shooter.readyPressed();
    		else{
    			intake.pivotState = intake.USER;
    			// shooter.setDartSpeed(1);
    		}
    	
    	if(dual.getRawButton(Controller.MODE_BUTTON) && manualMode == false && currentTime - modeTime > modeDelay){
    		manualMode = true;
    		modeTime = System.currentTimeMillis();
    	}
    	else if(dual.getRawButton(Controller.MODE_BUTTON) && manualMode == true && currentTime - modeTime > modeDelay){
    		modeTime = System.currentTimeMillis();	// Sets modeTime to the time in which you pressed the button
    		manualMode = false;	// Disables the manual mode
    	}
    	
    	if(!manualMode){
	    	intake.update(-dual.getRawAxis(1));			//update the intakes movement based on state and sends the joystick value if state is User-Control	
	    	
	    	if(dual.getRawButton(Controller.INTAKE_LOAD))	//Eject the ball out of the intake
	    		if(!shooter.dartIn.get() && shooter.shooterReady)
	    			intake.upOut();
	    		else
	    			shooter.setDartSpeed(-1);
	    	else if(dual.getRawButton(Controller.INTAKE_IN))	//Move the intake down and load a ball into it
	    		if(intake.pivotState != intake.LOADING)
	    			intake.downIn();
	    		else
	    			intake.setRollerSpeed(.2);
	    	
	    	if(dual.getRawButton(Controller.SHOOT_BUTTON) && !dual.getRawButton(Controller.SHOOT_SAFETY) && intake.ball.get())
	    		intake.setRollerSpeed(.4);
	    	
	    	if(dual.getRawButton(Controller.DART_TO_IN) && !shooter.dartIn.get()){		// start the loading process
		    	if(intake.pivotState == intake.USER)	
		    		shooter.dartUpPressed();
		    	else
		    		intake.pivotState = intake.USER;
	    	}
	    	else if(dual.getRawButton(Controller.DART_TO_OUT)){		//move the shooter to the up position
	    		shooter.dartDownPressed();
	    	}
    	}
    	else{
    		
    		intake.pivotState = intake.USER;
    		
    		if(dual.getRawButton(Controller.INTAKE_IN))
    			intake.setRollerSpeed(.5);
    		else if(dual.getRawButton(Controller.INTAKE_LOAD))
    			intake.setRollerSpeed(1);
    		else if(dual.getRawButton(3))
    			intake.setRollerSpeed(-1);
    		else
    			intake.setRollerSpeed(0);
    		
    		intake.setPivotSpeed(-dual.getRawAxis(1));
    		
    		if(dual.getRawButton(5))
    			shooter.shoot(.2);	// Push the fingers forward
    		else if(dual.getRawButton(6))
    			shooter.shoot(-.2);	// Push the fingers backward
    	}
    			
    }
    
    
    public void testPeriodic() {
    
    }
    
    public void disabledInit(){
    	visionHelper.stopAquisition();	// Stops viewing and disables images
    }
    
    public static Drive getDrive(){
    	return drive;	// Returns the drive to be used in other classes
    }
    
    public static Shooter getShooter(){
    	return shooter;	// Returns the shooter to be used in other classes
    }
    
}