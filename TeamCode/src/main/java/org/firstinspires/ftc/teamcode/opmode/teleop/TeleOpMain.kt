package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.api.Claw
import org.firstinspires.ftc.teamcode.api.Drone
import org.firstinspires.ftc.teamcode.api.GamepadEx
import org.firstinspires.ftc.teamcode.api.LinearSlide
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

        LinearSlide.init(this)

        // Advanced gamepad inputs
        GamepadEx.init(this)

        // Claw controls
        Claw.init(this)

        // Drone controls
        // Drone.init(this)

        // Log that we are initialized
        Telemetry.sayInitialized()

        // Claw controls
        Claw.init(this)
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

        // Lock or unlock the slide "brake"
        if (gamepad.dpad_left) {
            this.slideLocked = true

            with(LinearSlide.slide) {
                targetPosition = currentPosition
                mode = DcMotor.RunMode.RUN_TO_POSITION
            }
        } else if (gamepad.dpad_right) {
            this.slideLocked = false

            LinearSlide.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
            LinearSlide.stop()
        }

        // Move linear slide
        if (this.slideLocked) {
            LinearSlide.slide.power = 0.4
        } else {
            // Manually move slide up and down
            if (gamepad.dpad_down) {
                LinearSlide.power(-RobotConfig.TeleOpMain.SLIDE_DOWN_POWER)
            } else if (gamepad.dpad_up) {
                LinearSlide.power(RobotConfig.TeleOpMain.SLIDE_UP_POWER)
            } else {
                LinearSlide.stop()
            }
        }

        // Inputs for the gripper on the claw
        if (gamepad.left_bumper) {
            Claw.closeLR()
        } else if (gamepad.right_bumper) {
            Claw.openLR()
        }

        // Inputs for the movement of the box
        if (gamepad.a) {
            Claw.dropClaw()
        } else if (gamepad.b) {
            Claw.raiseClaw()
        }

        // Input to launch drone.
        if (gamepad.x && gamepad.y) {
            Drone.releasePin()
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
