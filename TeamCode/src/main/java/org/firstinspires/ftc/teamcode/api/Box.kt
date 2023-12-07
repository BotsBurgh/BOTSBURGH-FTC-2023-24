package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo

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
        boxL.position = 0.5
        boxR.position = 0.5
    }

    /**
     * Moves box to upright position
     */
    fun pickUpBox() {
        boxL.position = 0.0
        boxL.position = 0.0
    }

    /**
     * Moves the grip to keep pixels in place when [pickUpBox] the box
     */
    fun gripIn() {
        grip.position = 0.2
    }

    /**
     * Resets the grip to starting position
     */
    fun gripOut() {
        grip.position = 0.0
    }
}
