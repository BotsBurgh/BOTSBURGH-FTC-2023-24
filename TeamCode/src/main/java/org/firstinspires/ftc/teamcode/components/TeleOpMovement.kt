package org.firstinspires.ftc.teamcode.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.api.Box
import org.firstinspires.ftc.teamcode.api.LinearSlide
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.utils.Resettable
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Moves the robot wheels based on gamepad input.
 *
 * Requires the [TriWheels], [Box], and [LinearSlide] APIs.
 */
object TeleOpMovement : Component {
    // Whether the linear slide should remain locked in place or not.
    private var slideLocked: Boolean by Resettable { false }

    // Whether the robot should drive along the main axis or the pushbot axis.
    private var pushBotAxis: Boolean by Resettable { false }

    override fun loop(opMode: OpMode) {
        // alias gamepad1
        val gamepad = opMode.gamepad1
        val rotationPower = RobotConfig.TeleOpMovement.ROTATION_GAIN * -gamepad.right_stick_x.toDouble()

        // joystick input
        val joyX = -gamepad.left_stick_x.toDouble()
        val joyY = gamepad.left_stick_y.toDouble()

        // angle and strength
        // PI / 3 because 0 radians is right, not forward
        val joyRadians = atan2(joyY, joyX) - (PI / 3.0) + if (pushBotAxis) {
            2.0 * PI / 3.0
        } else {
            0.0
        }

        val joyMagnitude = sqrt(joyY * joyY + joyX * joyX)

        // Lock or unlock the slide "brake"
        if (gamepad.dpad_left) {
            slideLocked = true

            with(LinearSlide.slide) {
                targetPosition = currentPosition
                mode = DcMotor.RunMode.RUN_TO_POSITION
            }
        } else if (gamepad.dpad_right) {
            slideLocked = false

            LinearSlide.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
            LinearSlide.stop()
        }

        // Move linear slide
        if (slideLocked) {
            LinearSlide.slide.power = 0.4
        } else {
            // Manually move slide up and down
            if (gamepad.dpad_down) {
                LinearSlide.power(RobotConfig.TeleOpMovement.SLIDE_DOWN_POWER)
            } else if (gamepad.dpad_up) {
                LinearSlide.power(RobotConfig.TeleOpMovement.SLIDE_UP_POWER)
            } else {
                LinearSlide.stop()
            }
        }

        // Inputs for the gripper on the box
        if (gamepad.left_bumper) {
            Box.gripIn()
        } else if (gamepad.right_bumper) {
            Box.gripOut()
        }

        // Inputs for the movement of the box
        if (gamepad.a) {
            Box.dropBox()
        } else if (gamepad.b) {
            Box.pickUpBox()
        }

        if (gamepad.y) {
            pushBotAxis = true
        } else if (gamepad.x) {
            pushBotAxis = false
        }

        // movement of all wheels
        TriWheels.driveWithRotation(
            joyRadians,
            joyMagnitude * RobotConfig.TeleOpMovement.DRIVE_SPEED,
            rotationPower * RobotConfig.TeleOpMovement.ROTATE_SPEED,
        )
    }
}
