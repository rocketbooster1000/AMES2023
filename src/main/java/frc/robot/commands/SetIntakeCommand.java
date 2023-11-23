package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.scoring.Arm;
import frc.robot.subsystems.scoring.EndEffector;

public class SetIntakeCommand extends CommandBase{

    private final EndEffector m_effector;
    private final Arm m_arm;

    public SetIntakeCommand(EndEffector effector, Arm arm){
        this.m_effector = effector;
        this.m_arm = arm;

        this.addRequirements(m_effector, m_arm);
    }

    @Override
    public void initialize(){
        m_arm.setIntakeForward();
    }

    @Override
    public void execute(){
        m_effector.startIntake();
    }

    @Override
    public boolean isFinished(){
        return m_effector.hasBall();
    }

    @Override
    public void end(boolean interrupted){
        m_effector.brake();
    }

    
    
}
