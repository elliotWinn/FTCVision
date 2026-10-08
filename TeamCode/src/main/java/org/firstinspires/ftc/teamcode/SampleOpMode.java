package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class SampleOpMode extends LinearOpMode {
    //TODO: define motors, sensors, processors here


    @Override
    public void runOpMode() throws InterruptedException {
        // TODO: add initialise code here


        waitForStart();
        resetRuntime();

        if (opModeIsActive()) {
            while(opModeIsActive()) {
                //TODO: add loop code here
                telemetry.addData("Runtime: ", getRuntime());
                telemetry.update();
            }
        }
    }

    private void drive(double turn) {
        // drive code here
    }
}
