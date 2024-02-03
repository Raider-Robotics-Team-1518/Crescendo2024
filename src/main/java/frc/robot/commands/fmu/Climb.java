package frc.robot.commands.fmu;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Climb extends Command {
    private double power = Constants.MotorSpeeds.armPower;

    public Climb(double power) {
        this.power = power;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        RobotContainer.fmu.move_climb(power);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.fmu.stop_climb();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {

        return false;
    }
}
