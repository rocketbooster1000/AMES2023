package frc.robot.subsystems.scoring;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("unused")
public class Arm extends SubsystemBase{
    private static Arm instance;
    private final CANSparkMax armMotor = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);//filler motor id
    
    private static final double MAX_ANGLE = 1000;//-----
    private static final double MIN_ANGLE = -1000;
    private static final double SCORE_ANGLE_L_18 = 0;
    private static final double SCORE_ANGLE_R_18 = 0;
    private static final double SCORE_ANGLE_L_24 = 0;
    private static final double SCORE_ANGLE_R_24 = 0;
    private static final double INTAKE_ANGLE_L = 0;
    private static final double INTAKE_ANGLE_R = 0;
    private static final double REST_ANGLE = 0;//all values between these comments are filler angles and are to be adjusted during tuning

    private static double kP = 0;
    private static double kI = 0;
    private static double kD = 0;
    
    private PIDController controller = new PIDController(kP, kI, kD);

    private double currentAngle;

    private double target = 0;



    private Arm() {
        this.setName("Arm");
        this.register();

        this.armMotor.restoreFactoryDefaults();
        this.armMotor.setInverted(false);
        this.armMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    @Override
    public void periodic() {
        //if not homing
            //default to REST_ANGLE

        //^ use a default command in robot container

        controller.setPID(kP, kI, kD);

        //pid code here, must be run every loop regardless
        
    }

    public void setTarget(double target){
        //changes target for the pid
        //this method will be called every time an arm position change is desired
        this.target = target;
    }

    public void setIntakeForward(){
        //set target to forward intake position
        //call the setTarget method
        //this.setTarget(FORWARD_INTAKE_POSITION);
    }

    public void setIntakeRear(){
        //same as setIntakeForward() but for the rear intake position
    }

    public void stow(){
        //default position for arm 
        //arm will be facing upwards
        //set as default command for this subsystem in RobotContainer
    }


    public static Arm getInstance(){
        if (instance == null) instance = new Arm();
        return instance;
    }
    
}