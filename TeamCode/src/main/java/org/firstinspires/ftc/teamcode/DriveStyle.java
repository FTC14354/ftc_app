package org.firstinspires.ftc.teamcode;

import java.io.File;

public interface DriveStyle {

    void setDriveValues(double leftPower, double rightPower);

    void stop();

    void driveToPosition(int encoderTicks);

    int getEncoderValue();

    void driveToPosition(int i, File file);
}
