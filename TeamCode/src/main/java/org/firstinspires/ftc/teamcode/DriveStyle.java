package org.firstinspires.ftc.teamcode;

public interface DriveStyle {

    void setDriveValues(double leftPower, double rightPower);

    void stop();

    void driveToPosition(int encoderTicks);
}
