package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;

public class FourWheelDriveStyle implements DriveStyle {
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private Telemetry telemetry;

    public String getaencoderValues() {
        String returnValue;
        returnValue = String.format("leftBackDrive: %s\n", leftBackDrive.getCurrentPosition());
returnValue += String.format("rightBackDrive: %s\n", rightBackDrive.getCurrentPosition());
returnValue += String.format("rightFrontDrive: %s\n", rightFrontDrive.getCurrentPosition());
returnValue += String.format("leftFrontDrive: %s\n", leftFrontDrive.getCurrentPosition());

        return returnValue;
    }

    public FourWheelDriveStyle() {
        // Do Nothing
    }

    public FourWheelDriveStyle(HardwareMap hardwareMap, Telemetry telemetry, String leftFrontDriveName, String rightFrontDriveName, String leftBackDriveName, String rightBackDriveName) {
        leftFrontDrive = hardwareMap.get(DcMotor.class, leftFrontDriveName);
        rightFrontDrive = hardwareMap.get(DcMotor.class, rightFrontDriveName);
        leftBackDrive = hardwareMap.get(DcMotor.class, leftBackDriveName);
        rightBackDrive = hardwareMap.get(DcMotor.class, rightBackDriveName);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.telemetry = telemetry;
    }

    @Override
    public void setDriveValues(double leftPower, double rightPower) {
        leftFrontDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
        leftBackDrive.setPower(leftPower);
        rightBackDrive.setPower(rightPower);
    }

    @Override
    public void stop() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }

    @Override
    public void driveToPosition(int encoderTicks, File file) {
        int direction = 1;
        StringBuffer sb = new StringBuffer();
        if (0 > encoderTicks) {
            direction = -1;
        }

        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setTargetPosition(encoderTicks);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setTargetPosition(encoderTicks);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setTargetPosition(encoderTicks);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setTargetPosition(encoderTicks);

        leftFrontDrive.setPower(.5 * direction);
        leftBackDrive.setPower(.5 * direction);
        rightFrontDrive.setPower(.5 * direction);
        rightBackDrive.setPower(.5 * direction);


        while (leftBackDrive.isBusy()) {
            sb.append("position: " + leftBackDrive.getCurrentPosition());
            if(file != null)
                ReadWriteFile.writeFile(file,sb.toString());

            telemetry.addData("leftBackDrive: ", leftBackDrive.getCurrentPosition());
            telemetry.update();
        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

    }

    @Override
    public int getEncoderValue() {
        return leftBackDrive.getCurrentPosition();
    }

    @Override
    public void driveToPosition(int i) {
        driveToPosition(i, null);
    }
}