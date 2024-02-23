package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.Drone
import org.firstinspires.ftc.teamcode.api.GamepadEx
import org.firstinspires.ftc.teamcode.api.Hanger
import org.firstinspires.ftc.teamcode.api.LinearSlide
import org.firstinspires.ftc.teamcode.api.PixelPlacer
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

@TeleOp(name = "TeleOpMain")
class TeleOpMain : OpMode() {
    // Whether the linear slide should remain locked in place or not.
    private var slideLocked = false

    // init will run once
    override fun init() {
        // Setup special telemetry
        Telemetry.init(this)

        // Triangle wheel controls
        TriWheels.init(this)

        //Linear Slide controls
        //LinearSlide.init(this)

        // Advanced gamepad inputs
        GamepadEx.init(this)

        // Drone controls
        //Drone.init(this)

        // Log that we are initialized
        Telemetry.sayInitialized()

        // Claw controls
        //Claw.init(this)

        // Pixel Placer init
        PixelPlacer.init(this)

        // Hanger init
        Hanger.init(this)
    }

    // loop will run repetitively overtime while the robot runs
    override fun loop() {
        // alias gamepad1
        val gamepad = this.gamepad1

        // joystick input
        val joyX = -gamepad.left_stick_x.toDouble()
        val joyY = gamepad.left_stick_y.toDouble()

        // angle and strength
        // PI / 3 because 0 radians is right, not forward
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0)

        val joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        val rotationPower = -gamepad.right_stick_x.toDouble()




        // Inputs for the movement of the box
        if (gamepad.a) {
            Hanger.hang(1.0)
        }
        if (gamepad.b) {
            Hanger.hang(-1.0)
        } else {
            Hanger.stop()
        }



        // movement of all wheels
        TriWheels.driveWithRotation(
            joyRadians,
            joyMagnitude * RobotConfig.TeleOpMain.DRIVE_SPEED,
            rotationPower * RobotConfig.TeleOpMain.ROTATE_SPEED,
        )

        // Log that we are running
        Telemetry.sayRunning()
    }
}
