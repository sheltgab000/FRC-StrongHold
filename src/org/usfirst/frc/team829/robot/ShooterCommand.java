package org.usfirst.frc.team829.robot;

public class ShooterCommand extends AutoCommand{
	
	private final int STOPPED = 0;
	
	public ShooterCommand(){
	}
	
	@Override
	public void update(SubSystem system){
		Shooter shooter = Robot.getShooter();
		shooter.shootPressed();
		shooter.update();
	}
	
	@Override
	public boolean isComplete(){
		Shooter shooter = Robot.getShooter();
		
		if(shooter.getShooterStatus() == STOPPED)
			return true;
		else
			return false;
		
	}
	
}
