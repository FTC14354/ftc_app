package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SixWheelDriveStyle implements DriveStyle {
   private HardwareMap hardwareMap = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor rightMiddleDrive= null;
    private DcMotor leftMiddleDrive= null;

    public SixWheelDriveStyle(){
        // Do Nothing
    }

    public SixWheelDriveStyle (HardwareMap hardwareMap, String leftFrontDriveName, String rightFrontDriveName, String leftBackDriveName, String rightBackDriveName, String rightMiddleDriveName, String leftMiddleDriveName){
        leftFrontDrive  = hardwareMap.get(DcMotor.class, leftFrontDriveName);
        rightFrontDrive  = hardwareMap.get(DcMotor.class, rightFrontDriveName);
        leftBackDrive = hardwareMap.get(DcMotor.class, leftBackDriveName);
        rightBackDrive= hardwareMap.get(DcMotor.class, rightBackDriveName);
        rightMiddleDrive  = hardwareMap.get(DcMotor.class, rightMiddleDriveName);
        leftMiddleDrive  = hardwareMap.get(DcMotor.class, leftMiddleDriveName);

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftMiddleDrive.setDirection(DcMotor.Direction.FORWARD);
        rightMiddleDrive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void setDriveValues(double leftPower, double rightPower) {
        leftFrontDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
        leftBackDrive.setPower(leftPower);
        rightBackDrive.setPower(rightPower);
        leftMiddleDrive.setPower(leftPower);
        rightMiddleDrive.setPower(rightPower);
    }
    @Override
    public void stop (){
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
        leftMiddleDrive.setPower(0);
        rightMiddleDrive.setPower(0);
    }
}
