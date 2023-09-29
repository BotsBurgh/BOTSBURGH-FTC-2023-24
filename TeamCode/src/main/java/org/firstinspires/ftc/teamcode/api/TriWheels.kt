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
    private lateinit var wheel1: DcMotor
    private lateinit var wheel2: DcMotor
    private lateinit var wheel3: DcMotor

    // The angles of each wheel
    const val MOTOR_1_ANGLE: Double = 0.0
    const val MOTOR_2_ANGLE: Double = PI * (2.0 / 3.0)
    const val MOTOR_3_ANGLE: Double = 2.0 * MOTOR_2_ANGLE

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.wheel1 = hardwareMap.get(DcMotor::class.java, "wheel1")
        this.wheel2 = hardwareMap.get(DcMotor::class.java, "wheel2")
        this.wheel3 = hardwareMap.get(DcMotor::class.java, "wheel3")
    }

    /**
     * Sets the power of each wheel respectively.
     */
    fun power(power1: Double, power2: Double, power3: Double) {
        this.wheel1.power = power1
        this.wheel2.power = power2
        this.wheel3.power = power3
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
            magnitude * sin(this.MOTOR_1_ANGLE - radians),
            magnitude * sin(this.MOTOR_2_ANGLE - radians),
            magnitude * sin(this.MOTOR_3_ANGLE - radians),
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
