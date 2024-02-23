package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.Hook
import org.firstinspires.ftc.teamcode.api.PixelPlacer
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

@TeleOp(name = "TeleOpMain")
class TeleOpMain : OpMode() {
    override fun init() {
        Telemetry.init(this)
        TriWheels.init(this)
        PixelPlacer.init(this)
        Hook.init(this)

        // Log that we are initialized
        Telemetry.sayInitialized()
    }

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
            Hook.moveHook(0.5)
        } else if (gamepad.b) {
            Hook.moveHook(-0.5)
        } else {
            Hook.stop()
        }

        if (gamepad.x && gamepad.y) {
            // TODO: Release drone
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
