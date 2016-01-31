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
		if(targetShifterStatus ==  HIGH){
			// shift outward
		}
		else{
			// shift inward
		}
	}
	
	@Override
	public boolean isComplete(){
		// Set shifterStatus to the current status
		
		if(shifterStatus == targetShifterStatus)
			return true;
		else
			return false;
		
	}
	
}
