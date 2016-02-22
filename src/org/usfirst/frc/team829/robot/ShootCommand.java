package org.usfirst.frc.team829.robot;

public class ShootCommand extends AutoCommand{
	
	private final int STOPPED = 0;
	
	public ShootCommand(SubSystem system){
		super(system);
	}
	
	@Override
	public void update(){
		Shooter shooter = Robot.getShooter();
		shooter.shootPressed();
		shooter.update(0);
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
