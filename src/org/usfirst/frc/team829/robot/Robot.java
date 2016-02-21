
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
	boolean loadMode;
	boolean manualMode;
	
	static Joystick dual;	// Controls being used with the robot

	Joystick leftStick;

	Joystick rightStick;
	
	//VisionHelper visionHelper;
	   public void robotInit() {	
		   
	       leftStick = new Joystick(Controller.LEFT_STICK);		// Initializes the Joysticks used
	       rightStick = new Joystick(Controller.RIGHT_STICK);	// ...
	       dual = new Joystick(Controller.DUAL);			
	       
	       Compressor compressor = new Compressor();	//set up the compressor
	       compressor.start();							// START COMPRESSOR
	       
	       manualMode = false;
	       loadMode = false;
	       
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
    	SmartDashboard.putBoolean("Manual Mde", manualMode);
    	
    	if(dual.getRawButton(9) && manualMode == false)
    		manualMode = true;
    	else if(dual.getRawButton(9) && manualMode == true)
    		manualMode = false;
    	
    	if(!manualMode){
	    	drive.update(-leftStick.getY(), -rightStick.getY());	// Drives using joysticks, and changes transmission if needed
	    	
	    	if(rightStick.getRawButton(Controller.TRANSMISSION_BUTTON)){	// Changes transmission if corresponding button is pressed
	    		drive.transmissionPressed();
	    	}
	    	
	    	shooter.update(-dual.getRawAxis(3));	//updates the shooter statuses and controls the speeds
	    	
	    	
	    	if(dual.getRawButton(Controller.SHOOT_BUTTON) && dual.getRawButton(10))	//set the shooter status to SHOOTING when button is pressed
	    		shooter.shootPressed();
	    	
	    	if(leftStick.getTrigger())
	    		shooter.readyPressed();
	    	
	    	intake.update(-dual.getRawAxis(1));			//update the intakes movement based on state and sends the joystick value if state is User-Control	
	    	
	    	if(dual.getRawButton(Controller.INTAKE_LOAD))	//Eject the ball tout of the intake
	    		intake.upOut();
	    	else if(dual.getRawButton(Controller.INTAKE_IN))	//Move the intake down and load a ball into it
	    		intake.downIn();
	    		
	    	if(loadMode){		// When in load mode go up and dispense ball 
		    	if(!shooter.dartOut.get())	// When shooter all the way in push up intake and dispense ball
		    		intake.upOut();
		    	else if(!intake.ball.get())	// Once the ball is no longer seen turn load mode to false
		    		loadMode = false;
		    	else						// Push the dart all the way in
		    		shooter.dartUpPressed();
	    	}
	    	
	    	if(dual.getRawButton(Controller.DART_TO_IN)){		// start the loading process 
	    		loadMode = true;
	    	}
	    	else if(dual.getRawButton(Controller.DART_TO_OUT)){		//move the shooter to the up position
	    		shooter.dartDownPressed();
	    	}
    	}
    	else{
    		
    		drive.update(-leftStick.getY(), -rightStick.getY());
    		
    		shooter.update(-dual.getRawAxis(3));
    		
    		if(rightStick.getRawButton(Controller.TRANSMISSION_BUTTON)){	// Changes transmission if corresponding button is pressed
	    		drive.transmissionPressed();
	    	}
    		
	    	if(dual.getRawButton(Controller.SHOOT_BUTTON) && dual.getRawButton(10))	//set the shooter status to SHOOTING when button is pressed
	    		shooter.shootPressed();
	    	
	    	if(leftStick.getTrigger())
	    		shooter.readyPressed();
    		
    		if(dual.getRawButton(1))
    			intake.setRollerSpeed(.5);
    		else if(dual.getRawButton(4))
    			intake.setRollerSpeed(1);
    		else if(dual.getRawButton(2))
    			intake.setRollerSpeed(-.5);
    		else if(dual.getRawButton(3))
    			intake.setRollerSpeed(-1);
    		else
    			intake.setRollerSpeed(0);
    		
    		intake.setPivotSpeed(-dual.getRawAxis(1));
    		shooter.setDartSpeed(-dual.getRawAxis(3));
    		
    		if(dual.getRawButton(5))
    			shooter.shoot(.2);
    		else if(dual.getRawButton(6))
    			shooter.shoot(-.2);
    		else
    			shooter.shoot(0);
    		
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