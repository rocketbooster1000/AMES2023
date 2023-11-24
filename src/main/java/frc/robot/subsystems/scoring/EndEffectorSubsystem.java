package frc.robot.subsystems.scoring;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffectorSubsystem extends SubsystemBase {
    public static double BALL_SPIKE_CURRENT = 11;
    public static double ACCEL_SPIKE_CURRENT = 15;

    public static double NORMAL_ACTIVE_SPEED = 0.25;

    private final CANSparkMax leftMotor = new CANSparkMax(59, CANSparkMax.MotorType.kBrushed);
    private final CANSparkMax rightMotor = new CANSparkMax(26, CANSparkMax.MotorType.kBrushed);


    private static EndEffectorSubsystem instance;

    private EndEffectorSubsystem() {
        this.setName("End Effector");
        this.register();

        this.leftMotor.restoreFactoryDefaults();
        this.leftMotor.setInverted(false);
        this.leftMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        this.rightMotor.restoreFactoryDefaults();
        this.rightMotor.setInverted(true);
        this.rightMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void periodic() {

    }

    public void startIntake(double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public void brake() {
        this.leftMotor.set(0);
        this.rightMotor.set(0);
    }

    public void startOutput(double speed) {
        this.leftMotor.set(-speed);
        this.rightMotor.set(-speed);
    }

    public void startIntake(){
        this.startIntake(NORMAL_ACTIVE_SPEED);
    }

    public void startOutput(){
        this.startOutput(NORMAL_ACTIVE_SPEED);
    }


    public boolean hasBall(){
        return (leftMotor.getOutputCurrent() > BALL_SPIKE_CURRENT && leftMotor.getOutputCurrent() < ACCEL_SPIKE_CURRENT) 
                || (rightMotor.getOutputCurrent() > BALL_SPIKE_CURRENT && rightMotor.getOutputCurrent() < ACCEL_SPIKE_CURRENT);
    }

    public boolean aboveSpikeLimit(){
        return leftMotor.getOutputCurrent() > BALL_SPIKE_CURRENT || rightMotor.getOutputCurrent() > BALL_SPIKE_CURRENT;
    }

    public static EndEffectorSubsystem getInstance(){
        if (instance == null) instance = new EndEffectorSubsystem();
        return instance;
    }
}