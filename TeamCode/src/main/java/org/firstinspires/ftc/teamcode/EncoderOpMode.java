package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Encoder OpMode", group = "Auton opmode")
public class EncoderOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        while (!opModeIsActive()&&!isStopRequested()) {
            telemetry.addData("Status", "Waiting in Init");
            telemetry.update();
        }

        DriveStyle driveStyle = new FourWheelDriveStyle(hardwareMap,
                telemetry,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive", this);

        if (opModeIsActive()) {
            driveStyle.driveToPosition(1120);
            sleep(4000);
        }
    }
}
