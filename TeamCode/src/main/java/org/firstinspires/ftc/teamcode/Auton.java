
package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.modules.DeployTheBoi;
import org.firstinspires.ftc.teamcode.modules.LowerFromLander;

@Autonomous(name = "AutonO", group = "Auton opmode")
public class Auton extends LinearOpMode {

    private DcMotor liftMotor;
    private DriveStyle driveStyle;
    private final double DRIVE_MOTOR_MAX = 0.8;
    private BNO055IMU imu;

    public void runOpMode() {
        while (!opModeIsActive()&&!isStopRequested()) {
            telemetry.addData("Status", "Waiting in Init");
            telemetry.update();
        }

        driveStyle = new FourWheelDriveStyle(hardwareMap,
                telemetry,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");


        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        lowerFromLander();


        GoldAlignDetector detector = initMineralDetector();

        alignToMineral(detector);

        driveToDepot();

        deployTheBoi();

        parkInCrater();

    }

    private GoldAlignDetector initMineralDetector() {
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
        return detector;
    }

    private void lowerFromLander() {
        LowerFromLander lowerFromLander = new LowerFromLander(hardwareMap, driveStyle);
        lowerFromLander.landRobot();
    }

    private void alignToMineral(GoldAlignDetector detector) {
        boolean abort = false;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);

        while (opModeIsActive() && !abort && !detector.getAligned()) {
            double x = detector.getXPosition();
            telemetry.addData("detector X Position:", x);

            int dir = 0;

            while(opModeIsActive() && !abort && !detector.isFound()) {
                float currentAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
                telemetry.addData("currentAngle:", currentAngle);
                telemetry.update();
                if(dir == 0 && currentAngle < 35) {
                    driveStyle.setDriveValues(.5, -.5);
                } else {
                    dir=1;
                    if(currentAngle > -35){
                        driveStyle.setDriveValues(-.5, .5);
                    } else {
                        abort = true;
                    }
                }
            }
            telemetry.addLine().addData("Finished scanning abort is ", abort);
            telemetry.update();
            driveStyle.stop();

            if (detector.isFound()) {
                if (x < 320) {
                    driveStyle.setDriveValues(DRIVE_MOTOR_MAX, -DRIVE_MOTOR_MAX);
                } else {
                    driveStyle.setDriveValues(-DRIVE_MOTOR_MAX, DRIVE_MOTOR_MAX);
                }

                driveStyle.driveToPosition(1400);
            }
        }
    }

    private void driveToDepot() {

    }

    private void deployTheBoi() {
        DeployTheBoi boiDeployer = new DeployTheBoi(hardwareMap);
        boiDeployer.doTheThing();
        idle();
        sleep(2000);
        idle();
        boiDeployer.stopDoingThing();
        sleep(2000);
        idle();
    }

    private void parkInCrater() {

    }

}
