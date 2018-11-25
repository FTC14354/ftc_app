
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.modules.DeployTheBoi;

@Autonomous (name = "AutonO", group = "Auton opmode")
public class Auton extends LinearOpMode {
    public void runOpMode(){
        lowerFromLander();

        deployTheBoi();
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

    private void lowerFromLander() {

    }

    private void detectMinerals() {

    }

    private void alignToMineral() {

    }

    private void sampleMineral() {

    }

    private void depositMarker() {

    }

    private void parkInCrater() {

    }
}
