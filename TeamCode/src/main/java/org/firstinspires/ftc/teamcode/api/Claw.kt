package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.api.Box.pickUpBox
import org.firstinspires.ftc.teamcode.utils.RobotConfig

/**
 * An API for controlling the Claw on the robot.
 */
object Claw : API() {
    private lateinit var clawL : Servo
    private lateinit var clawR : Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.clawL = this.hardwareMap.get(Servo::class.java, "clawL")
        this.clawR = this.hardwareMap.get(Servo::class.java, "clawR")
    }

    /**
     * Moves the claw to keep pixels in place
     */
    fun inLR() {
        this.clawL.position = RobotConfig.Claw.IN_POSITION
        this.clawR.position = RobotConfig.Claw.IN_POSITION
    }

    /**
     * Moves the claw to keep left pixel in place
     */
    fun inL() {
        this.clawL.position = RobotConfig.Claw.IN_POSITION
    }

    /**
     * Moves the claw to keep right pixel in place
     */
    fun inR() {
        this.clawR.position = RobotConfig.Claw.IN_POSITION
    }

    /**
     * Moves the claws to starting position
     */
    fun outLR() {
        this.clawL.position = RobotConfig.Claw.OUT_POSITION
        this.clawR.position = RobotConfig.Claw.OUT_POSITION
    }

    /**
     * Moves the left claw to starting position
     */
    fun outL() {
        this.clawL.position = RobotConfig.Claw.OUT_POSITION
    }

    /**
     * Moves the right claw to starting position
     */
    fun outR() {
        this.clawR.position = RobotConfig.Claw.OUT_POSITION
    }
}