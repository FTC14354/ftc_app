package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "PrimaryTeleOpMode", group = "TeleOp opmode")
public class Teleop extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor liftMotor = null;
    private DriveStyle driveStyle = null;
    private DcMotor intakeMotor = null;


    @Override
    public void init(){
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        telemetry.addData("Status", "Initialized");

        driveStyle = new FourWheelDriveStyle(hardwareMap,
                "left_front_drive",
                "right_front_drive",
                "left_back_drive",
                "right_back_drive" );
    }

    @Override
    public void start () { runtime.reset();}

    @Override
    public void loop () {
        if (gamepad1.a) {
            liftMotor.setPower(-0.5);
        } else if (gamepad1.y) {
            liftMotor.setPower(0.5);
        } else {
            liftMotor.setPower(0);
        }

        double leftPower;
        double rightPower;

        double drive = -gamepad2.left_stick_y;
        double turn = -gamepad2.right_stick_x;
        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);
        driveStyle.setDriveValues(leftPower, rightPower);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);

        if (gamepad1.x) {
            intakeMotor.setPower (0.5);
        } else if (gamepad1.b) {
            intakeMotor.setPower (-0.5);
        } else {
            intakeMotor.setPower(0);
        }
    }
    @Override
    public void stop() {
        driveStyle.setDriveValues(0, 0);
        intakeMotor.setPower(0);
        liftMotor.setPower(0);
    }

}
