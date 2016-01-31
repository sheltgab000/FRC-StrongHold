package org.usfirst.frc.team829.robot;

public class ShifterCommand extends AutoCommand{

	int shifterStatus;
	public static int HIGH = 0;
	public static final int LOW = 1;
	
	int targetShifterStatus;
	
	public ShifterCommand(int targetShifterStatus){
		this.targetShifterStatus = targetShifterStatus;
	}
	
	@Override
	public void update(){
		Drive drive = Robot.getDrive();
		if(targetShifterStatus ==  HIGH){
			drive.transmissionStatus = HIGH;
			drive.update(0, 0);
		}
		else{
			drive.transmissionStatus = LOW;
			drive.update(0, 0);
		}
	}
	
	@Override
	public boolean isComplete(){
		Drive drive = Robot.getDrive();
		shifterStatus = drive.transmissionStatus;
		
		if(shifterStatus == targetShifterStatus)
			return true;
		else
			return false;
		
	}
	
}
