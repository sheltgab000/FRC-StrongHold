package org.usfirst.frc.team829.robot;

public class DriveCommand extends AutoCommand{

	double leftSpeed, rightSpeed;	//the speeds for the motors to go
	int targetDeltaCount;			//the target change in the encoder value
	
	public DriveCommand(SubSystem system,double leftSpeed, double rightSpeed, int targetDeltaCount){
		super(system);
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
		this.targetDeltaCount = targetDeltaCount;
	}
	
	// TODO link drive to this class 
	@Override
	public void update() {
		((Drive) system).update(leftSpeed, rightSpeed);
	}

	@Override
	public boolean isComplete() {
		int deltaCount = 0/*get the encoder count*/;
		
		if(Math.abs(deltaCount) >= Math.abs(targetDeltaCount))
			return true;
		else 
			return false;
	}
	

}
