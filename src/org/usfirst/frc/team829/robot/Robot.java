
package org.usfirst.frc.team829.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


<<<<<<< HEAD
public class Robot extends IterativeRobot {
    // blakes annoying
	private double shootingSpeed = .90;													// Variable that controls the shooter's speed
	private double slowingSpeed = .20;													// Variable that controls the slowing speed
	
	enum TransmissionStatus{															// The enum that controls the transmission status
		HIGH,
		LOW
	}
	
	enum ShooterStatus{																	// The enum that controls the shooter status
		SHOOTING,
		SLOWING,
		STOPPED
	}
    
	CANTalon frontLeft, backLeft, frontRight, backRight;								// Motor controllers for the drive			(TALON SRX)
	
	DoubleSolenoid transmission;														// Solenoid used in the transmission shift
	
	ShooterStatus shooterStatus;														// Variable that stores the shooter's status
	
	TransmissionStatus transmissionStatus;												// Variable that stores the transmission's status
	
	Compressor compressor;																// The compressor
	
	Talon shooter1, shooter2;															// Motor controllers used for the shooter 	(TALON SR)
=======
public class Robot extends IterativeRobot {		// Variable that controls the slowing speed
>>>>>>> 749db40c1218f25c38848bf732fd3224363b04b4
	
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
