package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.GamepadEx
import org.firstinspires.ftc.teamcode.api.TeleOpState
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.OutTake
import org.firstinspires.ftc.teamcode.api.GamepadEx.justPressed
import org.firstinspires.ftc.teamcode.api.GamepadEx.update
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Moves the robot wheels based on gamepad input.
 *
 * Requires the [TriWheels] API. It optionally uses the [TeleOpState] API.
 */
object TeleOpMovement : Component {
    private const val ROTATION_GAIN = 0.6
    private const val SLOW_MULTIPLIER = 0.4

    override fun loop(opMode: OpMode) {
        // This prevents the normal movement from running when the robot is target-locking an april
        // tag or executing some other DPad command.
        when (TeleOpState.state) {
            // When in the default state, do normal controls
            TeleOpState.State.Default -> default_loop(opMode)
        }
    }

    /**
     * The movement functionality run by default when [TeleOpState] is default.
     */
    private fun default_loop(opMode: OpMode) {
        // alias gamepad1
        val gamepad = opMode.gamepad1
        val rotationPower = ROTATION_GAIN * -gamepad.right_stick_x.toDouble()

        // joystick input
        val joyX = gamepad.left_stick_x.toDouble()
        val joyY = gamepad.left_stick_y.toDouble()

        // angle and strength
        // PI / 3 because 0 radians is right, not forward
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0)
        var joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        update()

        // if the right bumper is pressed move slower
        if (gamepad.left_bumper) {
            joyMagnitude *= SLOW_MULTIPLIER
        }

        // OutTake drop
        if (justPressed(GamepadEx.Inputs.RightBumper)) {
            OutTake.drop()
        }

        // movement of all wheels
        TriWheels.driveWithRotation(joyRadians, joyMagnitude, rotationPower)
    }
}
