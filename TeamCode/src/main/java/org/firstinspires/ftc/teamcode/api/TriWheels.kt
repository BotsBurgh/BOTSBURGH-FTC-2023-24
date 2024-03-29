package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
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
    private const val RED_ANGLE: Double = PI / 2.0
    private const val GREEN_ANGLE: Double = PI * (11.0 / 6.0)
    private const val BLUE_ANGLE: Double = PI * (7.0 / 6.0)

    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.red = this.opMode.hardwareMap.get(DcMotor::class.java, "redWheel")
        this.green = this.opMode.hardwareMap.get(DcMotor::class.java, "greenWheel")
        this.blue = this.opMode.hardwareMap.get(DcMotor::class.java, "blueWheel")

        this.stopAndResetMotors()
    }

    /**
     * Sets the power of each wheel respectively.
     */
    fun power(
        redPower: Double,
        greenPower: Double,
        bluePower: Double,
    ) {
        this.red.power = redPower
        this.green.power = greenPower
        this.blue.power = bluePower
    }

    /**
     * Rotates the wheels with a given power.
     */
    fun rotate(power: Double) {
        this.power(power, power, power)
    }

    /**
     * Makes the robot drive in a certain direction [radians] with a given strength [magnitude].
     */
    fun drive(
        radians: Double,
        magnitude: Double,
        rotation: Double = 0.0,
    ) {
        val (r, g, b) = this.wheelRatio(radians, magnitude, rotation)
        this.power(r, g, b)
    }

    /**
     * Does the same thing as [drive] but it rotates the robot with a given [rotation] too.
     */
    @Deprecated(
        message = "driveWithRotation is deprecated, use drive with rotation parameter instead.",
        replaceWith = ReplaceWith("TriWheels.drive(radius, magnitude, rotation = rotation)"),
        level = DeprecationLevel.ERROR,
    )
    fun driveWithRotation(
        radians: Double,
        magnitude: Double,
        rotation: Double,
    ) {
        this.drive(radians, magnitude, rotation)
    }

    private fun wheelRatio(
        radians: Double,
        magnitude: Double,
        rotation: Double = 0.0,
    ): Triple<Double, Double, Double> =
        Triple(
            magnitude * sin(this.RED_ANGLE - radians) + rotation,
            magnitude * sin(this.GREEN_ANGLE - radians) + rotation,
            magnitude * sin(this.BLUE_ANGLE - radians) + rotation,
        )

    /**
     * Makes all 3 wheels stop.
     *
     * This is shorthand for setting the power to 0.
     */
    fun stop() {
        this.power(0.0, 0.0, 0.0)
    }

    /**
     * This function resets all 3 motors.
     *
     * That includes:
     *
     * - Their encoder position
     * - Their run mode
     * - Their braking behavior
     * - Their direction
     *
     * If you manually changed something like a motor's direction, you'll have to do it again after
     * calling this function. This also stops all the wheels, due to the nature of resetting
     * encoder values.
     */
    fun stopAndResetMotors() {
        this.stop()

        for (motor in arrayOf(this.red, this.green, this.blue)) {
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            motor.direction = DcMotorSimple.Direction.FORWARD
        }
    }
}
