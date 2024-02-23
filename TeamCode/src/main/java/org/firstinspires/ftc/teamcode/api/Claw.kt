package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.opModeSleep

/**
 * An API for controlling the claw.
 */
@Deprecated(message = "Claw is deprecated, this is no claw on the robot.")
object Claw : API() {
    private lateinit var gripL: Servo
    private lateinit var gripR: Servo
    private lateinit var oscillator: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.gripL = this.opMode.hardwareMap.get(Servo::class.java, "clawL")
        this.gripR = this.opMode.hardwareMap.get(Servo::class.java, "clawR")
        this.oscillator = this.opMode.hardwareMap.get(Servo::class.java, "oscillator")

        gripR.direction = Servo.Direction.REVERSE

        // Open grip at beginning
        this.openLR()

        // Sleep for 0.5 seconds
        opModeSleep(opMode)(500)

        // Set initial position of the claw
        this.raiseClaw()
    }

    fun raiseClaw() {
        this.oscillator.position = RobotConfig.Claw.UP_POSITION
    }

    fun dropClaw() {
        this.oscillator.position = RobotConfig.Claw.DOWN_POSITION
    }

    fun openL() {
        this.gripL.position = RobotConfig.Claw.OPEN_POSITION_LEFT
    }

    fun openR() {
        this.gripR.position = RobotConfig.Claw.OPEN_POSITION_RIGHT
    }

    fun openLR() {
        this.openL()
        this.openR()
    }

    fun closeL() {
        this.gripL.position = RobotConfig.Claw.CLOSE_POSITION_LEFT
    }

    fun closeR() {
        this.gripR.position = RobotConfig.Claw.CLOSE_POSITION_RIGHT
    }

    fun closeLR() {
        this.closeL()
        this.closeR()
    }
}
