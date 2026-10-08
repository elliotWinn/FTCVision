package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class ShooterMotorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here
        //TODO: define motors, sensors, processors here
        DcMotor shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");

        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                //TODO: add loop code here
                if (gamepad1.right_bumper) {
                    shooterMotor.setPower(1);
                } else if (gamepad1.left_bumper) {
                    shooterMotor.setPower(-1);
                } else {
                    shooterMotor.setPower(0);
                }

                telemetry.addData("Runtime: ", getRuntime());
                telemetry.addData("Shooter Speed: ", shooterMotor.getPower());
                telemetry.update();
            }
        }
    }
}