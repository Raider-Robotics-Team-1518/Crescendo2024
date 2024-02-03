package frc.robot.commands.fmu;

import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Arm extends Command {
    private double power = Constants.MotorSpeeds.armPower;

    public Arm() {
        addRequirements(RobotContainer.fmu);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //RobotContainer.fmu.move_arm(power);
        RobotContainer.fmu.move_arm(Robot.robotContainer.getDriverAxis(Axis.kRightY) * power);
        SmartDashboard.putNumber("ARM ANGLE", RobotContainer.fmu.get_arm_position());

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        //RobotContainer.fmu.stop_arm();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        return false;
    }

}
