package org.usfirst.frc.team829.robot;

public class ShifterCommand extends AutoCommand{

	int shifterStatus;
	public static int HIGH = 0;
	public static final int LOW = 1;
	
	int targetShifterStatus;
	
	public ShifterCommand(SubSystem system, int targetShifterStatus){
		super(system);
		this.targetShifterStatus = targetShifterStatus;
		((Drive) system).transmissionPressed();
	}
	
	@Override
	public void update(){
		Drive drive = Robot.getDrive();
		drive.update(0, 0);
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
