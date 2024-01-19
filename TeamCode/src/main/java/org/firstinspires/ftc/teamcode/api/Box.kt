package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

/**
 * An API for controlling the Box on the robot.
 */
object Box : API() {
    private lateinit var boxL: Servo
    private lateinit var boxR: Servo
    private lateinit var grip: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.boxL = this.hardwareMap.get(Servo::class.java, "boxL")
        this.boxR = this.hardwareMap.get(Servo::class.java, "boxR")
        this.grip = this.hardwareMap.get(Servo::class.java, "grip")
    }

    /**
     * Moves the box to the ground
     */
    fun dropBox() {
        this.boxL.position = 0.4
        this.boxR.position = 0.35
    }

    /**
     * Moves box to upright position
     */
    fun pickUpBox() {
        this.boxL.position = 0.76
        this.boxR.position = 0.0
    }

    /**
     * Moves the grip to keep pixels in place when  [pickUpBox] the box
     */
    fun gripIn() {
        this.grip.position = 0.23
    }

    /**
     * Resets the grip to starting position
     */
    fun gripOut() {
        this.grip.position = 0.18
    }
}
