// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.EndEffector;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.swerve.Drivetrain;
import frc.robot.subsystems.swerve.Gyroscope;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final CommandXboxController mechStick = new CommandXboxController(1);
  EndEffector endEffector = new EndEffector();

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private Drivetrain m_swerve = Drivetrain.getInstance();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);


  private RunCommand driveCommand = null;
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));


    //reset gyro
    m_driverController.y().onTrue(new InstantCommand(() -> Gyroscope.getPigeonInstance().setYaw(0)));

    
    //main drive
    //rightbumper press down for robot centric, let go for field
    m_driverController.rightBumper()
    .onTrue(new InstantCommand(() -> {
      RunCommand dc = new RunCommand(
        () -> m_swerve.drive(
          m_driverController.getLeftX()*4, 
          m_driverController.getLeftY()*4, 
          Math.pow(m_driverController.getRightX(),3)*2,
          false
        ),
        m_swerve
      );
      dc.setName("Joystick Control");

      this.driveCommand = dc;
      m_swerve.getDefaultCommand().cancel();
      m_swerve.setDefaultCommand(dc);
    }))
    .onFalse(new InstantCommand(() -> {
      RunCommand dc = new RunCommand(
        () -> m_swerve.drive(
          m_driverController.getLeftX()*4, 
          m_driverController.getLeftY()*4, 
          Math.pow(m_driverController.getRightX(),3)*100,
          true
        ),
        m_swerve
      );
      dc.setName("Joystick Control");

      this.driveCommand = dc;
      m_swerve.getDefaultCommand().cancel();
      m_swerve.setDefaultCommand(dc);
    }));

    mechStick.leftBumper()
      .onTrue(new InstantCommand(() -> {endEffector.startOutput(0.25);}))
      .onFalse(new InstantCommand(() -> {endEffector.setBall(false);}));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }
  public void enableControllers() {
    if(m_swerve.getDefaultCommand() != null) m_swerve.getDefaultCommand().cancel();

    RunCommand dc = new RunCommand(
      () -> m_swerve.drive(
        m_driverController.getLeftX()*4, 
        m_driverController.getLeftY()*4, 
        Math.pow(m_driverController.getRightX(),3)*150,
        true
      ),
      m_swerve
    );
    dc.setName("Joystick Control");

    this.driveCommand = dc;
    m_swerve.setDefaultCommand(dc);


  }
  public void disableControllers() {
    if(this.driveCommand == null && this.driveCommand.getName().equals("Joystick Control")) {
      this.driveCommand.cancel();
      this.driveCommand = null;
    }
  }

  public void zeroAllOutputs() {
    if(m_swerve.getDefaultCommand() != null) m_swerve.getDefaultCommand().cancel();

    RunCommand dc = new RunCommand(
      () -> m_swerve.zeroWheels(),
      m_swerve
    );
    dc.setName("Zeroing Control");

    this.driveCommand = dc;
    m_swerve.setDefaultCommand(dc);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_exampleSubsystem);
    return null;
  }
}
