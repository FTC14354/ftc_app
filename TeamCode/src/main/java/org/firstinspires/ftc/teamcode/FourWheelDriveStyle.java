package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.io.File;

import static java.lang.Thread.sleep;

public class FourWheelDriveStyle implements DriveStyle {
    private static final String TELEMETRY_TAG = "Telemetry";

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private Telemetry telemetry;
    private OpMode opMode;
    private Runtime runtime;
    int NewLeftFrontTarget;
    int NewRightFrontTarget;
    int NewRightBackTarget;
    int NewLeftBackTarget;
    int RightPosition;
    int LeftPosition;
    double LeftFrontPower;
    double RightFrontPower;
    double RightBackPower;
    double LeftBackPower;
    double CountsPerInch;
    private volatile boolean   stopRequested   = false;
    private volatile boolean   isStarted       = false;

    public String getencoderValues() {
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

    public FourWheelDriveStyle(HardwareMap hardwareMap, Telemetry telemetry, String leftFrontDriveName, String rightFrontDriveName, String leftBackDriveName, String rightBackDriveName, OpMode opMode, Runtime runtime) {
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

        this.runtime = runtime;
        this.opMode = opMode;
        this.telemetry = telemetry;
    }
    public final void idle() {
        // Otherwise, yield back our thread scheduling quantum and give other threads at
        // our priority level a chance to run
        Thread.yield();
    }
    public final boolean isStopRequested() {
        return this.stopRequested || Thread.currentThread().isInterrupted();
    }
    public final boolean isStarted() {
        return this.isStarted || Thread.currentThread().isInterrupted();
    }

    public final boolean opModeIsActive() {
        boolean isActive = !this.isStopRequested() && this.isStarted();
        if (isActive) {
            idle();
        }
        return isActive;
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

    public void EncoderDrive(double speed, double leftInches, double rightInches, double AccelerationInches, int Direction) {
        // Declares variables that are used for this method


        // Resets encoders to 0
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Checks to make sure that encoders are reset.
        while (leftFrontDrive.getCurrentPosition() > 1 && rightFrontDrive.getCurrentPosition() > 1 && leftBackDrive.getCurrentPosition() > 1 && rightBackDrive.getCurrentPosition() > 1) {
            try {
                sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        if (this.opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            // Calculates the needed encoder ticks by multiplying a pre-determined amount of CountsPerInches,
            // and the method input gets the actual distance travel in inches
            NewLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (leftInches * CountsPerInch);
            NewRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (rightInches * CountsPerInch);
            NewRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (rightInches * CountsPerInch);
            NewLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (leftInches * CountsPerInch);

            RightPosition = rightFrontDrive.getCurrentPosition();
            LeftPosition = leftFrontDrive.getCurrentPosition();

            leftFrontDrive.setTargetPosition(NewLeftFrontTarget);
            rightFrontDrive.setTargetPosition(NewRightFrontTarget);
            leftBackDrive.setTargetPosition(NewLeftBackTarget);
            rightBackDrive.setTargetPosition(NewRightBackTarget);

            rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            while (leftFrontDrive.getCurrentPosition() > 1 && leftBackDrive.getCurrentPosition() > 1) {
                try {
                    sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            // Turn On RUN_TO_POSITION
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // reset the timeout time and start motion.
           runtime.reset();
            // This gets where the motor encoders will be at full position when it will be at full speed.
            double LeftEncoderPositionAtFullSpeed = ((AccelerationInches * (CountsPerInch)) + LeftPosition);
            double RightEncoderPositionAtFullSpeed = ((AccelerationInches * (CountsPerInch)) + RightPosition);
            boolean Running = true;


            // This gets the absolute value of the encoder positions at full speed - the current speed, and while it's greater than 0, it will continues increasing the speed.
            // This allows the robot to accelerate over a set number of inches, which reduces wheel slippage and increases overall reliability
            while (leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightBackDrive.isBusy() && rightFrontDrive.isBusy() && Running && this.opModeIsActive()){

            // While encoders are not at position
            if (((Math.abs(speed)) - (Math.abs(leftBackDrive.getPower()))) > .05 && ((Math.abs(speed)) - (Math.abs(leftFrontDrive.getPower()))) > .05) {
                // This allows the robot to accelerate over a set distance, rather than going full speed.  This reduces wheel slippage and increases reliability.
                LeftFrontPower = (Range.clip(Math.abs((leftFrontDrive.getCurrentPosition()) / (LeftEncoderPositionAtFullSpeed)), .15, speed));
                RightFrontPower = (Range.clip(Math.abs((rightFrontDrive.getCurrentPosition()) / (RightEncoderPositionAtFullSpeed)), .15, speed));
                LeftBackPower = (Range.clip(Math.abs((leftBackDrive.getCurrentPosition()) / (LeftEncoderPositionAtFullSpeed)), .15, speed));
                RightBackPower = (Range.clip(Math.abs((rightBackDrive.getCurrentPosition()) / (RightEncoderPositionAtFullSpeed)), .15, speed));


                leftFrontDrive.setPower(LeftFrontPower * Direction);
                rightFrontDrive.setPower(RightFrontPower * Direction);
                leftBackDrive.setPower(LeftBackPower * Direction);
                rightBackDrive.setPower(RightBackPower * Direction);



                telemetry.addData("Accelerating", RightEncoderPositionAtFullSpeed);
                telemetry.addData("Path1", "Running to %7d :%7d", NewLeftFrontTarget, NewRightFrontTarget, NewLeftBackTarget, NewRightBackTarget);
                telemetry.addData("Path2", "Running at %7d :%7d", leftFrontDrive.getCurrentPosition(), rightFrontDrive.getCurrentPosition(), leftBackDrive.getCurrentPosition(), rightBackDrive.getCurrentPosition());
                telemetry.update();
            } else if (Math.abs(NewLeftFrontTarget) - Math.abs(leftFrontDrive.getCurrentPosition()) < -1 && Math.abs(NewLeftBackTarget) - Math.abs(leftBackDrive.getCurrentPosition()) < -1) {
                Running = false;
            } else {
                leftFrontDrive.setPower((speed * Direction));
                leftBackDrive.setPower((speed * Direction));
                rightFrontDrive.setPower((speed * Direction));
                rightBackDrive.setPower((speed * Direction));


                telemetry.addData("Path1", "Running to %7d :%7d", NewLeftBackTarget, NewRightBackTarget, NewRightFrontTarget, NewLeftFrontTarget);
                telemetry.addData("Path2", "Running at %7d :%7d", leftBackDrive.getCurrentPosition(), rightBackDrive.getCurrentPosition(), leftFrontDrive.getCurrentPosition(), rightFrontDrive.getCurrentPosition());
                telemetry.update();
            }

        }
//                // Display information for the driver.


        // Stops all motion
        // Set to run without encoder, so it's not necessary to declare this every time after the method is used
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set power to 0
        leftBackDrive.setPower(0);
        leftFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
        rightFrontDrive.setPower(0
        );

    }

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