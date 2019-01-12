package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;

public class FourWheelDriveStyle implements DriveStyle {
    private static final String TELEMETRY_TAG = "Telemetry";

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

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

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
        encoderTicks = -encoderTicks;

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

        leftFrontDrive.setPower(.5);
        leftBackDrive.setPower(.5);
        rightFrontDrive.setPower(.5);
        rightBackDrive.setPower(.5);


        while (leftBackDrive.isBusy()) {
            telemetry.addData("leftBackDrive: ", leftBackDrive.getCurrentPosition());
            telemetry.update();
            logMessage(String.format("Left Back Drive: %s", leftBackDrive.getCurrentPosition()));

        }

        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);

    }

    private void logMessage(String message) {
        Log.i(TELEMETRY_TAG, message);
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