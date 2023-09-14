package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.api.TriWheels
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sin
import kotlin.math.sqrt

object TeleOpMovement : Component {
    override fun loop(opMode : OpMode) {

        // renaming gamepad1
        val gamepad = opMode.gamepad1
        var rotationPower = 0.6 * -gamepad.right_stick_x.toDouble()

        // right bumper and left bumper movement
        // will slowly turn the robot
        if (gamepad.left_bumper) {
            rotationPower += 0.3
        } else if (gamepad.right_bumper) {
            rotationPower -= 0.3
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