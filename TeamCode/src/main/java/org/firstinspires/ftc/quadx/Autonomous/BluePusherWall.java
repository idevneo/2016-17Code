package org.firstinspires.ftc.quadx.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.quadx.Libraries.MyOpMode;

/**
 * Created by jspspike on 11/3/16.
 */


@Autonomous(name="Blue Pusher Wall", group="Blue")
public class BluePusherWall extends MyOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        hardwareMap();
        initServos();
        initSensors();

        resetGyro();

        int delay = 0;
        double flyPow = .633;
        int block = 0;
        String blockStat = "";

        while (!opModeIsActive()) {

            if (gamepad1.dpad_left) {
                delay -= 1;
                delay(250);
            }
            else if (gamepad1.dpad_right) {
                delay += 1;
                delay(250);
            }

            if(gamepad1.a) {
                block = 0;
            }
            if(gamepad1.b) {
                block = 1;
            }
            if(gamepad1.x) {
                block = 2;
            }

            if(block == 0){
                blockStat = "center";
            } else if(block == 1){
                blockStat = "block";
            } else if (block == 2){
                blockStat = "ramp";
            }


            telemetry.addData("Delay", delay);
            telemetry.addData("Gyro", getGyroYaw());
            telemetry.addData("Block", blockStat);
            telemetry.update();
            idle();
        }
        telemetry.addData("Gyro", "Completed");
        telemetry.update();

        if (hardwareMap.voltageSensor.get("Motor Controller 5").getVoltage() > 13.85) {
            flyPow = .633;
        }

        else if (hardwareMap.voltageSensor.get("Motor Controller 5").getVoltage() > 13.6) {
            flyPow = .638;
        }

        else {
            flyPow = .645;
        }

        waitForStart();

        winch.setPower(-1);
        delay(1000);
        hold.setPosition(HOLD_HOLD);
        winch.setPower(0);

        delay(delay * 1000);

        flywheel.setPower(flyPow);
        manip.setPower(.3);
        moveTo(.35, 1560, .6, 1.5);
        delay(500);
        door.setPosition(DOOR_OPEN);
        delay(2000);
        flywheel.setPower(0);
        arcTurnPID(.3, 48, 2500);
        moveToSlow(.35, 5490, 6, 1.5);
        manip.setPower(0);
        arcTurnPID(-.37, -34, 1800);
        manip.setPower(.3);
        resetGyro();
        manip.setPower(0);
        if (!fail)
            untilWhiteAlign(.3, .16, 1420, 5200);
            moveTo(-.2, 170, .6, 1.5);
        pressBlue();
        untilWhiteAlign(-.3, -.16, 1750, 6150);
        if (!fail)
            moveTo(.2, 150, .6, 1.5);
        pressBlue();
        manip.setPower(.3);
        if(block == 1){
            arcTurnPID(.3, -60, 2500);
            moveTo(.4,5100,.6,1.5);
            manip.setPower(0);
        } else if(block == 2){
            arcTurnPID(.3, -90, 1500);
            arcTurnPID(.3, -93, 1500);
            moveToSlow(.35, 5500, 6, 1.5);
            manip.setPower(0);
        } else {
            arcTurnPID(.3, -68, 3000);
            moveTo(.35, 2700, .6, 1.5);
            manip.setPower(0);
            arcTurnPID(.4, -60, 2000);
        }


        winch.setPower(1);
        hold.setPosition(HOLD_DISABLED);
        delay(1000);
        winch.setPower(0);
    }
}