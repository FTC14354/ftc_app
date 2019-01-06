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

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.modules.LowerFromLander;

import java.io.File;



    @Autonomous(name = "TestAuton", group = "Auton opmode")
    public class TestAuton extends LinearOpMode {

        private DcMotor liftMotor;
        private DriveStyle driveStyle;
        private BNO055IMU imu;
        private final double DRIVE_MOTOR_MAX = 0.5;
        private File file;
        private boolean targetAccquired = false;
        private boolean ableToGoStraight = true;
        private boolean AbleToGoStraight2 = true;
        private StringBuilder sb = new StringBuilder();

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

//            lowerFromLander();
//
//            GoldAlignDetector detector = initMineralDetector();
//
//            alignToMineral(detector);

driveStyle.setDriveValues(.5, .5);
sleep (500);
driveStyle.setDriveValues(-.5, -.5);
sleep (500);//        driveToDepot();
//
//        deployTheBoi();
//        parkInCrater();
            ReadWriteFile.writeFile(file, sb.toString());

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
            sb.append("Leaving initMineralDetector\r\n");
            return detector;
        }

        private void lowerFromLander() {
            LowerFromLander lowerFromLander = new LowerFromLander(hardwareMap, driveStyle);
            lowerFromLander.landRobot();
        }

        private void alignToMineral(GoldAlignDetector detector) throws InterruptedException {
            boolean abort = false;
            sb.append("Starting alignToMineral\r\n");

            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
            imu.initialize(parameters);
            sb.append("imu init complete\r\n");


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
            sb.append("Scanning complete\r\n");

            telemetry.addLine().addData("Finished scanning abort is ", abort);
            telemetry.update();
            driveStyle.stop();

            if (opModeIsActive() && detector.isFound()) {
                double x = detector.getXPosition();
                telemetry.addData("detector X Position:", x);
                while (!detector.getAligned()) {


                    if (x < 290) {
                        driveStyle.setDriveValues(DRIVE_MOTOR_MAX, -DRIVE_MOTOR_MAX);
                        ableToGoStraight = false;
                    } else if (x > 350) {
                        driveStyle.setDriveValues(-DRIVE_MOTOR_MAX, DRIVE_MOTOR_MAX);
                        AbleToGoStraight2 = false;
                    }
                }
                driveStyle.setDriveValues(-.6, -.6);
                sleep(500);

            }


            sb.append("Leaving alignToMineral abort = " + abort + "\r\n");
        }}

//    private void driveToDepot() {
//        float currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//        if (currentAngle > 10) {
//            sb.append("Not straight 1\r\n");
//            driveStyle.setDriveValues(-.5, .5);
//            while (currentAngle > -190) {
//                currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//            }
//            driveStyle.driveToPosition(100, file);
//        } else if (currentAngle < -10) {
//            sb.append("Not straight 2\r\n");
//            driveStyle.setDriveValues(.5, -.5);
//            while (currentAngle < 30) {
//                currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//            }
//            driveStyle.driveToPosition(-100, file);
//        } else {
//            driveStyle.driveToPosition(-100, file);
//        }
//
//        sb.append("Drive encoder: " + driveStyle.getEncoderValue() + "\r\n");
//
//
//        ReadWriteFile.writeFile(file, sb.toString());
//
//    }
//
//    private void deployTheBoi() {
//        if (opModeIsActive()) {
//            DeployTheBoi boiDeployer = new DeployTheBoi(hardwareMap);
//            boiDeployer.doTheThing();
//            idle();
//            sleep(2000);
//            idle();
//            boiDeployer.stopDoingThing();
//            sleep(2000);
//            idle();
//        }
//
//    }
//}
//
//
//
//
//


