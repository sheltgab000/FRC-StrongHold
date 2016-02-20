
package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {		// Variable that controls the slowing speed
	
	static Drive drive;		// Variable that stores the shooter's status
	
	static Shooter shooter;
	
	Ultrasonic ultra;
	double range;
	
	static Intake intake;
	
	Compressor compressor;		// Limit switches used for the shooter
	
	Joystick dual, leftStick, rightStick;	// Controls being used with the robot

	VisionHelper visionHelper;
	
	public void robotInit() {
	//VisionHelper visionHelper;
		leftStick = new Joystick(Controller.LEFT_STICK);		// Initializes the Joysticks used
		rightStick = new Joystick(Controller.RIGHT_STICK);	// ...
	    dual = new Joystick(Controller.DUAL);			
	       
	    Compressor compressor = new Compressor();	//set up the compressor
	    compressor.start();							// START COMPRESSOR
	       
	    drive = new Drive();
	       
	    shooter = new Shooter();

	    ultra = new Ultrasonic(Ports.RANGEFINDER_OUTPUT, Ports.RANGEFINDER_INPUT);
	    ultra.setAutomaticMode(true);
	    intake = new Intake();
	
	    /*visionHelper = new VisionHelper();
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
        					
    	SmartDashboard.putBoolean("Fire Button", dual.getRawButton(Controller.FIRE_BUTTON));
    	SmartDashboard.putNumber("Intake Potentiometer", intake.intakePot.getValue());
    	SmartDashboard.putBoolean("Dart Home", intake.homeSwitch.get());
    	SmartDashboard.putBoolean("Ball Viewer", intake.ball.get());
    	
    	drive.update(-leftStick.getY(), -rightStick.getY());	// Drives using joysticks, and changes transmission if needed
    	
    	if(dual.getRawButton(Controller.TRANSMISSION_BUTTON)){		// Changes transmission if Button 3 is pressed
    		drive.transmissionPressed();
    	}
    	
    	shooter.update();
    	shooter.setDartSpeed(-dual.getRawAxis(3));
    	
    	range = ultra.getRangeInches();
    	
    	intake.update(-dual.getRawAxis(1));
    	
    	if(dual.getRawButton(4))
    		intake.setRollerSpeed(-.4);
    	else if(dual.getRawButton(8))
    		intake.setRollerSpeed(1);
    	else if(dual.getRawButton(1)){
    		intake.downIn();
    		if(intake.ball.get())
    			intake.setRollerSpeed(0);
    		else
    			intake.setRollerSpeed(-1);
    	}
    	else
    		intake.setRollerSpeed(0);
    	
    	if(dual.getRawButton(7)){
    		System.out.println("Moving to in position");
    		if(shooter.dartIn.get())
    			shooter.dartMotor.set(-1);
    		else if(!shooter.dartIn.get())
    			shooter.dartMotor.set(0);
    	}
    	else if(dual.getRawButton(5)){
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