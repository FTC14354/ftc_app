package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.modules.DeployTheBoi;
import org.firstinspires.ftc.teamcode.modules.LowerFromLander;

import android.util.Log;

import java.io.File;

@Autonomous(name = "Test1Auton", group = "Auton opmode")
public class TestAuton extends LinearOpMode {
    private static final String TELEMETRY_TAG = "Telemetry";

    private DcMotor liftMotor;
    private DriveStyle driveStyle;
    private BNO055IMU imu;
    private final double DRIVE_MOTOR_MAX = 0.5;
    private File file;

    private double initialMineralAngle = 0;

    public void runOpMode() throws InterruptedException {
        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in Init");
            telemetry.update();
        }

        String filename = "sweepLog.log";
        file = AppUtil.getInstance().getSettingsFile(filename);

        driveStyle = new FourWheelDriveStyle(hardwareMap,
                telemetry,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

//        lowerFromLander();

//        GoldAlignDetector detector = initMineralDetector();
//
//        alignToMineral(detector);

        driveToDepot();

//        deployTheBoi();
//
//        parkInCrater();
    }

    private GoldAlignDetector initMineralDetector() {
        ReadWriteFile.writeFile(file, "Starting initMineralDetector\n");
        GoldAlignDetector detector = new GoldAlignDetector();
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        detector.useDefaults();
        detector.alignSize = 100;
        detector.alignPosOffset = 0;
        detector.downscale = 0.4;
        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA;
        detector.maxAreaScorer.weight = 0.005;
        detector.ratioScorer.weight = 5;
        detector.ratioScorer.perfectRatio = 1.0;
        detector.enable();
        logMessage("Leaving initMineralDetector\r\n");
        return detector;
    }

//    private void lowerFromLander() {
//        LowerFromLander lowerFromLander = new LowerFromLander(hardwareMap, driveStyle);
//        lowerFromLander.landRobot();
//    }

    private void alignToMineral(GoldAlignDetector detector) throws InterruptedException {
        boolean abort = false;
        logMessage("Starting alignToMineral\r\n");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
        logMessage("imu init complete\r\n");

        if (opModeIsActive() && !abort && !detector.getAligned()) {
            int dir = 0;
            while (opModeIsActive() && !abort && !detector.isFound()) {
                float currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
                telemetry.addData("currentAngle:", currentAngle);
                telemetry.update();
                if (dir == 0 && currentAngle < 35) {
                    driveStyle.setDriveValues(.5, -.5);
                } else {
                    dir = 1;
                    if (currentAngle > -35) {
                        driveStyle.setDriveValues(-.5, .5);
                    } else {
                        abort = true;
                    }
                }
            }
        }
        logMessage("Scanning complete\r\n");

        telemetry.addLine().addData("Finished scanning abort is ", abort);
        telemetry.update();
        driveStyle.stop();

        if (opModeIsActive() && detector.isFound()) {
            initialMineralAngle = detector.getXPosition();
            telemetry.addData("detector X Position:", initialMineralAngle);
            while (!detector.getAligned()) {

                if (initialMineralAngle < 290) {
                    driveStyle.setDriveValues(DRIVE_MOTOR_MAX, -DRIVE_MOTOR_MAX);
                } else if (initialMineralAngle > 350) {
                    driveStyle.setDriveValues(-DRIVE_MOTOR_MAX, DRIVE_MOTOR_MAX);
                }
            }
            driveStyle.setDriveValues(-.6, -.6);
            sleep(500);

        }

        logMessage("Leaving alignToMineral abort = " + abort + "\r\n");
    }

    private void driveToDepot() {
        Log.i(TELEMETRY_TAG, "Forward");
        driveStyle.driveToPosition(-1508);
        Log.i(TELEMETRY_TAG, "Backward");
        driveStyle.driveToPosition(1508);
//        float currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//        if (currentAngle > 10) {
//            logMessage("Left of Depot\r\n");
//            driveStyle.setDriveValues(.5, -.5);
//            while (currentAngle > 170) {
//                currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//            }
//            driveStyle.setDriveValues(-.3, -.3);
//            sleep(250);
//        } else if (currentAngle < -10) {
//            logMessage("Right of Depot\r\n");
//            driveStyle.setDriveValues(-.5, .5);
//            while (currentAngle < 25) {
//                currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//            }
//            driveStyle.setDriveValues(-.3, -.3);
//            sleep(250);
//        } else {
//            logMessage("Aligned with Depot");
//            driveStyle.setDriveValues(-.3, -.3);
//            sleep(250);
//        }
//
//        logMessage("Drive encoder: " + driveStyle.getEncoderValue() + "\r\n");
    }

    private void deployTheBoi() {
        if (opModeIsActive()) {
            DeployTheBoi boiDeployer = new DeployTheBoi(hardwareMap);
            boiDeployer.doTheThing();
            idle();
            sleep(2000);
            idle();
            boiDeployer.stopDoingThing();
            sleep(2000);
            idle();
        }

    }

    private void logMessage(String message) {
        Log.i(TELEMETRY_TAG, message);
    }

    public void parkInCrater() {

    }
}





