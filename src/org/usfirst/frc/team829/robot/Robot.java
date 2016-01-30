
package org.usfirst.frc.team829.robot;

import com.ni.vision.NIVision;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
    
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
	
	DigitalInput stopped, slowing;														// Limit switches used for the shooter
	
	Joystick dual, leftStick, rightStick;												// Controls being used with the robot
	
	//CameraServer camera;
	
	int session;																		// Used for vision or something
	
	VisionHelper visionHelper;															// Used for vision or something
	
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(100, 155);						//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(67, 255);						//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(200, 255);						//Default value range for yellow tote
	
	/*double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area

	Image frame;
	Image binaryFrame;
	int imaqError;
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);*/
    
	   public void robotInit() {
        shooterStatus = ShooterStatus.STOPPED;											// Sets the statuses to their default values
        transmissionStatus = TransmissionStatus.HIGH;									// ...
        
        transmission = new DoubleSolenoid(0, 1);										// Initializes the transmission Solenoid
        
        shooter1 = new Talon(0);														// Initializes the shooter motors
        shooter2 = new Talon(1);														// ...
        
        stopped = new DigitalInput(0);													// Initializes the limit switches
        slowing = new DigitalInput(1);													// ...
        
        frontLeft = new CANTalon(0);													// Initializes the drive motors
        backLeft = new CANTalon(1);														// ...
        frontRight = new CANTalon(2);													// ...
        backRight = new CANTalon(3);													// ...
        
        leftStick = new Joystick(0);													// Initializes the Joysticks used
        rightStick = new Joystick(1);													// ...
        dual = new Joystick(2);															// ...
        
        SmartDashboard.putNumber("shooting speed", shootingSpeed);						// SmartDashboard variables						
        SmartDashboard.putNumber("slowing speed", slowingSpeed);						// ...
        
        compressor.start();																// START COMPRESSOR
        
        /*camera = CameraServer.getInstance();
        camera.setQuality(50);
        camera.startAutomaticCapture();
        */

        /*visionHelper = new VisionHelper();
        visionHelper.setHueRange(100, 155);
        visionHelper.setSatRange(min, max);
        */
        // the camera name (ex "cam0") can be found through the roborio web interface
        /*session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0); 
		
		//Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);*/
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {													// Autonomous period
    	
    }

    
    public void teleopInit(){
    	//NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
        
    }
    
    public void teleopPeriodic() {														// Teleop Period
        
    	SmartDashboard.putBoolean("Stop Switch", stopped.get());						// SmartDashboard used to view variables
    	SmartDashboard.putBoolean("slowing Switch", slowing.get());						// ...
    	SmartDashboard.putBoolean("Fire Button", dual.getRawButton(2));					// ...
    	SmartDashboard.putNumber("shooterMotor1", shooter2.get());						// ...
    	SmartDashboard.putNumber("shooterMotor2", shooter1.get());						// ...
    	
    	shootingSpeed = SmartDashboard.getNumber("shooting speed");						// ...
    	slowingSpeed = SmartDashboard.getNumber("slowing speed");						// ...
    	
    	drive(-leftStick.getY(), -rightStick.getY());									// Control the drive using the Joysticks
    	
    	switch(transmissionStatus){														// Controls the transmission using Button 4
    	case HIGH:
    		if(dual.getRawButton(4))
    			transmissionStatus = TransmissionStatus.LOW;
    		break;
    	case LOW:
    		if(dual.getRawButton(4))
    			transmissionStatus = TransmissionStatus.HIGH;
    		break;
    	}
    	
    	switch(transmissionStatus){														// Changes the solenoid depending on transmission status
    	case HIGH:
    		transmission.set(DoubleSolenoid.Value.kForward);
    		break;
    	case LOW:
    		transmission.set(DoubleSolenoid.Value.kReverse);
    		break;
    	}
    	
    	switch(shooterStatus){															// Changes the shooter using Button 2 and the limit switches
    	case STOPPED :
    		if(dual.getRawButton(2))
    			shooterStatus = ShooterStatus.SHOOTING;
    		break;
    	case SHOOTING:
    		if(slowing.get())
    			shooterStatus = ShooterStatus.SLOWING;
    		break;
    		
    	case SLOWING:
    		if(stopped.get())
    			shooterStatus = ShooterStatus.STOPPED;
    		break;
    	}
    	
    	
    	switch(shooterStatus){															// Changes the shooter status depending on the SHOOTER STATUS
    	case STOPPED :
    		shooter1.set(0);
    		shooter2.set(0);
    		break;
    	case SHOOTING:
    		shooter1.set(shootingSpeed);
    		shooter2.set(shootingSpeed);
    		break;
    		
    	case SLOWING:
    		shooter1.set(slowingSpeed);
    		shooter2.set(slowingSpeed);
    		break;
    	}
    	
    	
    	/*NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    	NIVision.IMAQdxGrab(session, frame, 1);
        NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
        //CameraServer.getInstance().setImage(frame);
    	
    	
    	//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
		TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);

		//Threshold the image looking for yellow (tote color)
		NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

		criteria[0].lower = (float)AREA_MINIMUM;
		//imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);
		CameraServer.getInstance().setImage(binaryFrame);*/
    }
    
    
    public void testPeriodic() {
    
    }
    
    public void disabledInit(){
    	//NIVision.IMAQdxStopAcquisition(session);
    }
    
    public void drive(double leftSpeed, double rightSpeed){										// Controls the drive speed
    	frontLeft.set(leftSpeed);
    	backLeft.set(leftSpeed);
    	frontRight.set(rightSpeed);
    	backRight.set(rightSpeed);
    }
    
}
