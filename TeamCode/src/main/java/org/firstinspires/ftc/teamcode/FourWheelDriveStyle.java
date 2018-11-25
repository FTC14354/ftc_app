package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FourWheelDriveStyle implements DriveStyle {
    private HardwareMap hardwareMap = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor rightMiddleDrive = null;
    private DcMotor leftMiddleDrive = null;

    public FourWheelDriveStyle() {
        // Do Nothing
    }

    public FourWheelDriveStyle(HardwareMap hardwareMap, String leftFrontDriveName, String rightFrontDriveName, String leftBackDriveName, String rightBackDriveName) {

        leftFrontDrive = hardwareMap.get(DcMotor.class, leftFrontDriveName);
        rightFrontDrive = hardwareMap.get(DcMotor.class, rightFrontDriveName);
        leftBackDrive = hardwareMap.get(DcMotor.class, leftBackDriveName);
        rightBackDrive = hardwareMap.get(DcMotor.class, rightBackDriveName);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void setDriveValues(double leftPower, double rightPower) {
        leftFrontDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
        leftBackDrive.setPower(leftPower);
        rightBackDrive.setPower(rightPower);
    }
    @Override
    public void stop (){
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}