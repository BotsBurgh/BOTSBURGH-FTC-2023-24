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

        this.red = hardwareMap.get(DcMotor::class.java, "redWheel")
        this.green = hardwareMap.get(DcMotor::class.java, "greenWheel")
        this.blue = hardwareMap.get(DcMotor::class.java, "blueWheel")

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
    ) {
        this.driveWithRotation(radians, magnitude, 0.0)
    }

    /**
     * Does the same thing as [drive] but it rotates the robot with a given [rotation] too.
     */
    fun driveWithRotation(
        radians: Double,
        magnitude: Double,
        rotation: Double,
    ) {
        this.power(
            magnitude * sin(this.RED_ANGLE - radians) + rotation,
            magnitude * sin(this.GREEN_ANGLE - radians) + rotation,
            magnitude * sin(this.BLUE_ANGLE - radians) + rotation,
        )
    }

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
        stop()

        // Reset encoder values
        this.red.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        this.blue.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        this.green.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        // Use encoders to make all wheels consistent
        this.red.mode = DcMotor.RunMode.RUN_USING_ENCODER
        this.green.mode = DcMotor.RunMode.RUN_USING_ENCODER
        this.blue.mode = DcMotor.RunMode.RUN_USING_ENCODER

        // Make wheels brake when stopped
        this.red.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        this.green.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        this.blue.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        // Configure direction
        this.red.direction = DcMotorSimple.Direction.FORWARD
        this.green.direction = DcMotorSimple.Direction.FORWARD
        this.blue.direction = DcMotorSimple.Direction.FORWARD
    }

    @Deprecated(
        message = "This function has been renamed.",
        replaceWith = ReplaceWith("this.rotate(power)"),
        level = DeprecationLevel.ERROR,
    )
    fun power(power: Double) {
        this.rotate(power)
    }

    @Deprecated(
        message = "This function has been renamed.",
        replaceWith = ReplaceWith("this.drive(radians, magnitude)"),
        level = DeprecationLevel.ERROR,
    )
    fun moveDirection(
        radians: Double,
        magnitude: Double,
    ) {
        this.drive(radians, magnitude)
    }
}
