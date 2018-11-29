
package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.DeployTheBoi;
import org.firstinspires.ftc.teamcode.modules.DetectMineral;
import org.firstinspires.ftc.teamcode.modules.LowerFromLander;

@Autonomous (name = "AutonO", group = "Auton opmode")
public class Auton extends LinearOpMode {

    private DcMotor liftMotor;
    private DriveStyle driveStyle;

    public void runOpMode() {
        waitForStart();
        telemetry.addLine("turnLeft, turnRight");
        driveStyle = new FourWheelDriveStyle(hardwareMap,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
//
//        lowerFromLander();

        GoldAlignDetector detector = initMineralDetector();

        alignToMineral(detector);

        driveToDepot();

        deployTheBoi();

        parkInCrater();
    }
private GoldAlignDetector initMineralDetector (){
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

//    private void lowerFromLander() {
//        LowerFromLander lowerFromLander = new LowerFromLander(hardwareMap, driveStyle);
//        lowerFromLander.landRobot();
//    }

    private void alignToMineral (GoldAlignDetector detector){
        while (opModeIsActive() && !detector.getAligned()){
            double x = detector.getXPosition();
            if (x < 320) {
                driveStyle.setDriveValues(.8, -.8);
            }
            else {
                driveStyle.setDriveValues(-.8, .8);
            }

            driveStyle.setDriveValues (-1,-1);
        }
    }
    private void driveToDepot (){

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
