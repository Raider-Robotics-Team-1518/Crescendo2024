// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.auto.AutoAimArm;
import frc.robot.commands.auto.AutoIntake;
import frc.robot.commands.auto.AutoShootSpeaker;
import frc.robot.commands.auto.AutoStopIntake;
import frc.robot.commands.drive.DriveFieldRelative;
import frc.robot.commands.drive.DriveRobotCentric;
import frc.robot.commands.drive.util.DriveAdjustModulesManually;
import frc.robot.commands.drive.util.DriveResetAllModulePositionsToZero;
import frc.robot.commands.drive.util.pid.DriveRotationExport;
import frc.robot.commands.drive.util.pid.DriveTranslationExport;
import frc.robot.commands.fmu.MoveArm;
import frc.robot.commands.fmu.MoveArmToAngle;
import frc.robot.commands.fmu.Climb;
import frc.robot.commands.fmu.Shooter;
import frc.robot.commands.fmu.ShooterIntake;
import frc.robot.subsystems.FieldManipulationUnit;
import frc.robot.subsystems.Blinkies;
import frc.robot.subsystems.base.SwerveDrive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's gamepads are defined here...
  
  public static final Joystick joystick = new Joystick(0);
  // static final CommandXboxController driver = new CommandXboxController(Constants.DRIVER_CONTROLLER_PORT);
  static final CommandXboxController codriver = new CommandXboxController(Constants.CODRIVER_CONTROLLER_PORT);
  
/*
 * DRIVER BUTTONS are accessed like this:
 *  driver.a().onTrue(...) // accesses the A button on the driver controller
 *  codriver.y().whileTrue(...) // accesses the Y button on the co-driver controller
 */

  //The robot's subsystems are instantiated here
  //public static SwerveDrive swerveDrive;
  public static SwerveDrive swerveDrive; 
  public static FieldManipulationUnit fmu;
  public static double optimalArmAngle = 161.0d;

  /* Command Choosers */
  public static SendableChooser<Command> autoChooser;   // Autonomous

  /* LED Lights */
  public static Blinkies m_blinkies = new Blinkies();

  public RobotContainer() {
    joystick.setTwistChannel(2);

    // Auto mode - Register Named Commands - needs to be at top of class
    NamedCommands.registerCommand("AutoIntake", new AutoIntake());
    NamedCommands.registerCommand("AutoShootSpeaker", new AutoShootSpeaker());
    NamedCommands.registerCommand("AutoAimArm", new AutoAimArm());
    NamedCommands.registerCommand("AutoStopIntake", new AutoStopIntake());

    swerveDrive = new SwerveDrive();
    // swerveDrive.setDefaultCommand(new DriveFieldRelative(false));
    swerveDrive.setDefaultCommand(new DriveRobotCentric(false)); // for joystick testing

    fmu = new FieldManipulationUnit();
    fmu.setDefaultCommand(new MoveArm());

    configureSwerveSetup();
    configureSetupModes();
    configureAutoModes();
    configureButtonBindings();
    
    //NetworkTableInstance
    //NetworkTableInstance.getDefault().flush();
    CameraServer.startAutomaticCapture();

  }

  private void configureButtonBindings() {
    /* ==================== DRIVER BUTTONS ==================== */

    JoystickButton button3 = new JoystickButton(joystick, 3);
    button3.toggleOnTrue(new DriveFieldRelative(false));
    button3.toggleOnFalse(new DriveRobotCentric(false));

    // driver.povUp().whileTrue(new Climb(Constants.MotorSpeeds.climbPower));
    // driver.povDown().whileTrue(new Climb(-Constants.MotorSpeeds.climbPower));

    codriver.a().whileTrue(new ShooterIntake(Constants.MotorSpeeds.intakeSpeed));
    codriver.y().debounce(0.05d).whileTrue(new Shooter(Constants.MotorSpeeds.shooterSpeedForSpeaker)); //.onFalse(new Shooter(0));
    codriver.b().whileTrue(new ShooterIntake(Constants.MotorSpeeds.intakeReverse));
    codriver.leftBumper().onTrue(new MoveArmToAngle(optimalArmAngle));

    /* =================== CODRIVER BUTTONS =================== */

  }



  /**
   * Define all autonomous modes here to have them 
   * appear in the autonomous select drop down menu.
   * They will appear in the order entered
   */
  private void configureAutoModes() {
    // Build an auto chooser. This will use Commands.none() as the default option.
    // autoChooser = AutoBuilder.buildAutoChooser();
    // SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  private void configureSwerveSetup() {
    SmartDashboard.putData(new DriveResetAllModulePositionsToZero());

    // 1518
    SmartDashboard.putData(new DriveTranslationExport());
    SmartDashboard.putData(new DriveRotationExport());
    // SmartDashboard.putData("Drive Straight", Commands.sequence(Autos.autoDriveStraight()));
  }

  private void configureSetupModes() {
    /*
     * General Debugging/Measuring
     */

  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
  
  /**
   * A method to return the value of a driver joystick axis,
   * which runs from -1.0 to 1.0, with a .1 dead zone(a 0 
   * value returned if the joystick value is between -.1 and 
   * .1)
   * @param axis
   * @return value of the joystick, from -1.0 to 1.0 where 0.0 is centered
   */
  public double getDriverAxis(Axis axis) {
    // return (driver.getRawAxis(axis.value) < -.1 || driver.getRawAxis(axis.value) > .1)
    //     ? driver.getRawAxis(axis.value)
    //     : 0.0;
    return 0.0;
  }

  /**
   * Accessor method to set driver rumble function
   * 
   * @param leftRumble
   * @param rightRumble
   */
  // public static void setDriverRumble(double leftRumble, double rightRumble) {
  //   driver.getHID().setRumble(RumbleType.kLeftRumble, leftRumble);
  //   driver.getHID().setRumble(RumbleType.kRightRumble, rightRumble);
  // }

  /**
   * Returns the int position of the DPad/POVhat based
   * on the following table:
   *    input    |return
   * not pressed |  -1
   *     up      |   0
   *   up right  |  45
   *    right    |  90
   *  down right | 135
   *    down     | 180
   *  down left  | 225
   *    left     | 270
   *   up left   | 315
   * @return
   */
  public int getDriverDPad() {
    return 0; // (driver.getHID().getPOV());
  }

  /**
   * A method to return the value of a codriver joystick axis,
   * which runs from -1.0 to 1.0, with a .1 dead zone(a 0 
   * value returned if the joystick value is between -.1 and 
   * .1) 
   * @param axis
   * @return
   */
  public double getCoDriverAxis(Axis axis) {
    return (codriver.getHID().getRawAxis(axis.value) < -.1 || codriver.getHID().getRawAxis(axis.value) > .1)
        ? codriver.getHID().getRawAxis(axis.value)
        : 0;
  }

  /**
   * Accessor method to set codriver rumble function
   * 
   * @param leftRumble
   * @param rightRumble
   */
  public static void setCoDriverRumble(double leftRumble, double rightRumble) {
    codriver.getHID().setRumble(RumbleType.kLeftRumble, leftRumble);
    codriver.getHID().setRumble(RumbleType.kRightRumble, rightRumble);
  }

  /**
   * accessor to get the true/false of the buttonNum 
   * on the coDriver control
   * @param buttonNum
   * @return the value of the button
   */
  public boolean getCoDriverButton(int buttonNum) {
    return codriver.getHID().getRawButton(buttonNum);
  }

}
