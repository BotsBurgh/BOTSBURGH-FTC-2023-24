package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.opModeSleep

/**
 * An API for controlling the claw
 */
object Claw : API() {
    private lateinit var gripL: Servo
    private lateinit var gripR: Servo
    private lateinit var oscillator: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.gripL = this.opMode.hardwareMap.get(Servo::class.java, "gripL")
        this.gripR = this.opMode.hardwareMap.get(Servo::class.java, "gripR")
        this.oscillator = this.opMode.hardwareMap.get(Servo::class.java, "oscillator")

        // Open grip at beginning
        this.openLR()

        // Sleep for 0.5 seconds
        opModeSleep(opMode)(500)

        // Set inital position of the claw
        this.raiseClaw()
    }

    /**
     * Moves the box to the ground
     */

    fun dropClaw() {
        this.oscillator.position = RobotConfig.Claw.DOWN_POSITION
    }

    fun raiseClaw() {
        this.oscillator.position = RobotConfig.Claw.UP_POSITION
    }

    fun openL() {
        this.gripL.position = RobotConfig.Claw.OPEN_POSITION
    }

    fun openR() {
        this.gripR.position = RobotConfig.Claw.OPEN_POSITION
    }

    fun openLR() {
        this.gripL.position = RobotConfig.Claw.OPEN_POSITION
        this.gripR.position = RobotConfig.Claw.OPEN_POSITION
    }

    fun closeL() {
        this.gripL.position = RobotConfig.Claw.CLOSE_POSITION
    }

    fun closeR() {
        this.gripR.position = RobotConfig.Claw.CLOSE_POSITION
    }

    fun closeLR() {
        this.gripL.position = RobotConfig.Claw.CLOSE_POSITION
        this.gripR.position = RobotConfig.Claw.CLOSE_POSITION
    }
}
