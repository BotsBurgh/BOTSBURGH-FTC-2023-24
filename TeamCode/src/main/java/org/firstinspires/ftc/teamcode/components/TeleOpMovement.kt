package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.sqrt

object TeleOpMovement : Component {
    private const val ROTATION_GAIN = 0.6
    private const val SLOW_TURN_AMOUNT = 0.3

    override fun loop(opMode: OpMode) {
        // renaming gamepad1
        val gamepad = opMode.gamepad1
        var rotationPower = ROTATION_GAIN * -gamepad.right_stick_x.toDouble()

        // right bumper and left bumper movement
        // will slowly turn the robot
        if (gamepad.left_bumper) {
            rotationPower += SLOW_TURN_AMOUNT
        } else if (gamepad.right_bumper) {
            rotationPower -= SLOW_TURN_AMOUNT
        }

        // joystick input
        val joyX = gamepad.left_stick_x.toDouble()
        val joyY = gamepad.left_stick_y.toDouble()

        // angle and strength
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0) - (PI / 2.0)
        val joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        // all movement of the wheels
        TriWheels.power(
            joyMagnitude * sin(TriWheels.MOTOR_1_ANGLE - joyRadians) + rotationPower,
            joyMagnitude * sin(TriWheels.MOTOR_2_ANGLE - joyRadians) + rotationPower,
            joyMagnitude * sin(TriWheels.MOTOR_3_ANGLE - joyRadians) + rotationPower,
        )
    }
}