
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
	boolean manualMode;
	long currentTime, modeTime;
	
	static Joystick dual;	// Controls being used with the robot

	Joystick leftStick;

	Joystick rightStick;
	
	//VisionHelper visionHelper;
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
    	SmartDashboard.putBoolean("Manual Mode", manualMode);
    	
    	currentTime = System.currentTimeMillis();
    	
    	shooter.update(-dual.getRawAxis(3));	//updates the shooter statuses and controls the speed
    	drive.update(-leftStick.getY(), -rightStick.getY());
    	
    	if(rightStick.getRawButton(Controller.TRANSMISSION_BUTTON)){	// Changes transmission if corresponding button is pressed
    		drive.transmissionPressed();
    	}
    	
    	if(dual.getRawButton(Controller.SHOOT_BUTTON) && dual.getRawButton(8))	//set the shooter status to SHOOTING when button is pressed
    		shooter.shootPressed();
    	
    	if(leftStick.getTrigger())
    		shooter.readyPressed();
    	
    	if(dual.getRawButton(9) && manualMode == false && currentTime - modeTime > 500){
    		manualMode = true;
    		modeTime = System.currentTimeMillis();
    	}
    	else if(dual.getRawButton(9) && manualMode == true && currentTime - modeTime > 500){
    		modeTime = System.currentTimeMillis();
    		manualMode = false;
    	}
    	
    	if(!manualMode){
	    	intake.update(-dual.getRawAxis(1));			//update the intakes movement based on state and sends the joystick value if state is User-Control	
	    	
	    	if(dual.getRawButton(Controller.INTAKE_LOAD))	//Eject the ball tout of the intake
	    		intake.upOut();
	    	else if(dual.getRawButton(Controller.INTAKE_IN))	//Move the intake down and load a ball into it
	    		intake.downIn();
	    	
	    	if(dual.getRawButton(Controller.DART_TO_IN) && !shooter.dartIn.get()){		// start the loading process
		    		shooter.dartUpPressed();
	    	}
	    	else if(dual.getRawButton(Controller.DART_TO_OUT)){		//move the shooter to the up position
	    		shooter.dartDownPressed();
	    	}
    	}
    	else{
    		
    		intake.pivotState = intake.USER;
    		
    		if(dual.getRawButton(1))
    			intake.setRollerSpeed(.5);
    		else if(dual.getRawButton(4))
    			intake.setRollerSpeed(1);
    		else if(dual.getRawButton(3))
    			intake.setRollerSpeed(-1);
    		else
    			intake.setRollerSpeed(0);
    		
    		intake.setPivotSpeed(-dual.getRawAxis(1));
    		
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