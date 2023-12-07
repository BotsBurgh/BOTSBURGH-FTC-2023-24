package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Moves the robot wheels based on gamepad input.
 *
 * Requires the [TriWheels] API.
 */
object TeleOpMovement : Component {
    private const val ROTATION_GAIN = 0.6
    private const val SLOW_MULTIPLIER = 0.4

    override fun loop(opMode: OpMode) {
        // alias gamepad1
        val gamepad = opMode.gamepad1
        val rotationPower = ROTATION_GAIN * -gamepad.right_stick_x.toDouble()

        // joystick input
        val joyX = -gamepad.left_stick_x.toDouble()
        val joyY = gamepad.left_stick_y.toDouble()

        // angle and strength
        // PI / 3 because 0 radians is right, not forward
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0)
        var joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        // if the right bumper is pressed move slower
        if (gamepad.right_bumper) {
            joyMagnitude *= SLOW_MULTIPLIER
        }

        // movement of all wheels
        TriWheels.driveWithRotation(
            joyRadians,
            joyMagnitude * RobotConfig.TeleOpMovement.DRIVE_SPEED,
            rotationPower * RobotConfig.TeleOpMovement.ROTATE_SPEED
        )
    }
}
