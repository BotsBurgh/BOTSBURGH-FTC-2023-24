package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import kotlin.math.PI
import kotlin.math.sin

/**
 * An API for controlling the wheels of a triangle robot.
 */
object TriWheels : API() {
    // All 3 wheels on the robot
    lateinit var red: DcMotor
        private set
    lateinit var green: DcMotor
        private set
    lateinit var blue: DcMotor
        private set

    // The angles of each wheel
    const val RED_ANGLE: Double = 0.0
    const val GREEN_ANGLE: Double = PI * (4.0 / 3.0)
    const val BLUE_ANGLE: Double = PI * (2.0 / 3.0)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.red = hardwareMap.get(DcMotor::class.java, "redWheel")
        this.green = hardwareMap.get(DcMotor::class.java, "greenWheel")
        this.blue = hardwareMap.get(DcMotor::class.java, "blueWheel")
    }

    /**
     * Sets the power of each wheel respectively.
     */
    fun power(redPower: Double, greenPower: Double, bluePower: Double) {
        this.red.power = redPower
        this.green.power = greenPower
        this.blue.power = bluePower
    }

    /**
     * Sets the power of all 3 wheels to the same value.
     */
    fun power(power: Double) {
        this.power(power, power, power)
    }

    /**
     * Makes the robot move in a certain direction [radians] with a given strength [magnitude].
     */
    fun moveDirection(radians: Double, magnitude: Double) {
        this.power(
            magnitude * sin(this.RED_ANGLE - radians),
            magnitude * sin(this.GREEN_ANGLE - radians),
            magnitude * sin(this.BLUE_ANGLE - radians),
        )
    }

    /**
     * Makes all 3 wheels stop.
     *
     * This is shorthand for setting the power to 0.
     */
    fun stop() {
        this.power(0.0)
    }
}
