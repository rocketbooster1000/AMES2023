// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SetIntakeCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.scoring.Arm;
import frc.robot.subsystems.scoring.EndEffector;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
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
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private EndEffector m_endEffector = EndEffector.getInstance();
  private Arm m_arm = Arm.getInstance();
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed

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

    mechStick.a()
      .onTrue(new FunctionalCommand(
        () -> m_arm.setIntakeForward(), //init
        () -> m_endEffector.startIntake(), //execute
        (interrupted) -> m_arm.stow(), //end
        () -> m_endEffector.hasBall(), //isFinished
        m_arm, m_endEffector)); //requirements
        
    mechStick.b()
      .onTrue(new FunctionalCommand(
        () -> m_arm.setIntakeRear(),
        () -> m_endEffector.startIntake(),
        (interrupted) -> m_arm.stow(),
        () -> m_endEffector.hasBall(),
        m_arm, m_endEffector));
    
    

        


    //default behavior to not do anything
    m_endEffector.setDefaultCommand(new RunCommand(() -> m_endEffector.brake(), m_endEffector));
    m_arm.setDefaultCommand(new RunCommand(() -> m_arm.stow(), m_arm));

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
