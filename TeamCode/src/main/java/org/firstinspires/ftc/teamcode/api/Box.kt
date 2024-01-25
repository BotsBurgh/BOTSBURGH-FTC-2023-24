package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.utils.opModeSleep

/**
 * An API for controlling the Box on the robot.
 */
@Deprecated(
    message = "The Box hardware component has been removed.",
    level = DeprecationLevel.WARNING,
)
object Box : API() {
    private lateinit var box: Servo
    private lateinit var grip: Servo

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.box = this.opMode.hardwareMap.get(Servo::class.java, "box")
        this.grip = this.opMode.hardwareMap.get(Servo::class.java, "grip")

        // Close grip at the beginning.
        this.gripOut()

        // Sleep for 0.5 seconds
        opModeSleep(opMode)(500)

        // Set initial position of box to be open.
        this.pickUpBox()
    }

    /**
     * Moves the box to the ground
     */
    fun dropBox() {
        this.box.position = 1.0
    }

    /**
     * Moves box to upright position
     */
    fun pickUpBox() {
        this.box.position = 0.6
    }

    /**
     * Moves the grip to keep pixels in place when  [pickUpBox] the box
     */
    fun gripIn() {
        this.grip.position = 0.5
    }

    /**
     * Resets the grip to starting position
     */
    fun gripOut() {
        this.grip.position = 0.31
    }
}
