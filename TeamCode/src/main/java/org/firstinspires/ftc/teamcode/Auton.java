
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.DeployTheBoi;
import org.firstinspires.ftc.teamcode.modules.DetectMineral;
//import org.firstinspires.ftc.teamcode.modules.LowerFromLander;

@Autonomous (name = "AutonO", group = "Auton opmode")
public class Auton extends LinearOpMode {

    private DcMotor liftMotor = null;
    private DriveStyle driveStyle = null;

    public void runOpMode(){
        driveStyle = new FourWheelDriveStyle(hardwareMap,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive");

        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

//        lowerFromLander();

        detectMinerals();

        alignToMineral();

        driveToDepot();

        deployTheBoi();

        parkInCrater();
    }



//    private void lowerFromLander() {
//        LowerFromLander lowerFromLander = new LowerFromLander(hardwareMap, driveStyle);
//lowerFromLander.landRobot();
//    }

    private void detectMinerals() {
        DetectMineral detectMineral = new DetectMineral(telemetry);
    }

    private void alignToMineral (){

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
