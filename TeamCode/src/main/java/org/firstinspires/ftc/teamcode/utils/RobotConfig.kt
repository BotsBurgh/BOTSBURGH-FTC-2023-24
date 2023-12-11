@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.utils.RobotConfig.model
import org.opencv.core.Rect
import org.opencv.core.Scalar

/**
 * This is an immutable object representing robot configuration.
 *
 * It is meant to orchestrate FTC Dashboard and other configuration together, as well as enable the
 * use of multiple different robots with the same code. (See [model].)
 *
 * Certain sub-objects are annotated with `@Config`. This designates them as FTC Dashboard
 * configuration that can be modified at runtime. **The permanently change these values, you must
 * also modify the code!** The configuration can also change during initialization depending on the
 * [model] of the robot and other values.
 */
object RobotConfig {
    /**
     * Which model of the robot is running the code.
     *
     * @see Model
     */
    val model: Model = Model.RobotA

    /**
     * When true, enables debugging features like camera streaming and more logs.
     *
     * This should be disabled for competitions.
     */
    const val debug: Boolean = true

    /**
     * Creates a string representing the current robot configuration.
     *
     * This should include all standalone properties like [model] and [debug], but should not
     * contain any FTC Dashboard configuration due to how much more complicated they are.
     */
    override fun toString() = "RobotConfig(model=$model, debug=$debug)"

    /** Configuration related to moving the wheels using encoders. */
    @Config
    object Encoders {
        /**
         * How many ticks a wheel needs to rotate for the robot to travel an inch when moving along
         * one of it's three axis.
         *
         * This value was calculated by guessing and checking, and may be further changed to
         * increase accuracy.
         */
        @JvmField
        var TICKS_PER_INCH: Double = 44.0

        /**
         * How many ticks a wheel needs to rotate for the robot to spin a single degree.
         *
         * This value was calculated by guessing and checking, and may be further changed to
         * increase accuracy.
         */
        @JvmField
        var TICKS_PER_DEGREE: Double = 6.64

        /**
         * A multiplier that calculates the power of the wheel relative to the amount it needs to
         * rotate.
         */
        @JvmField
        var ENCODER_GAIN: Double = 0.0003

        /**
         * How many ticks a wheel needs to be within the target to be considered finished.
         */
        @JvmField
        var ENCODER_ERROR: Int = 10

        /**
         * The maximum power a wheel can spin at when the robot is driving with encoders.
         */
        @JvmField
        var MAX_DRIVE_SPEED: Double = 0.3

        /**
         * The maximum power a wheel can spin at when the robot spinning with encoders.
         */
        @JvmField
        var MAX_SPIN_SPEED: Double = 0.4

        /**
         * A multiplier that calculates the power of the wheel relative to the amount of time that
         * has passed.
         */
        @JvmField
        var TIME_GAIN: Double = 0.4
    }

    /** Configuration related to moving using april tags. */
    @Config
    object AprilMovement {
        @JvmField
        var RANGE_GAIN: Double = 0.01

        @JvmField
        var HEADING_GAIN: Double = 0.01

        @JvmField
        var STRAFE_GAIN: Double = 0.005

        @JvmField
        var RANGE_ERROR: Double = 2.0

        @JvmField
        var HEADING_ERROR: Double = 5.0

        @JvmField
        var STRAFE_ERROR: Double = 5.0
    }

    /** Configuration related to scanning april tags. */
    @Config
    object AprilVision {
        @JvmField
        var OPTIMUM_EXPOSURE: Long = 1

        @JvmField
        var OPTIMUM_GAIN: Int = 255
    }

    /** Configuration related to the main autonomous. */
    @Config
    object CubeVision {
        @JvmField
        var LEFT_REGION = Rect(100, 200, 100, 100)

        @JvmField
        var CENTER_REGION = Rect(250, 200, 100, 100)

        @JvmField
        var RIGHT_REGION = Rect(400, 200, 100, 100)

        @JvmField
        var RED_WEIGHT = Scalar(1.0, -0.2, -0.2)

        @JvmField
        var BLUE_WEIGHT = Scalar(-0.2, -0.2, 1.0)
    }

    @Config
    object AutoMain {
        @JvmField
        var WAIT_TIME: Long = 0
    }

    /** Configuration related to the TeleOpMovement component. */
    @Config
    object TeleOpMovement {
        @JvmField
        var DRIVE_SPEED: Double = 0.6

        @JvmField
        var ROTATE_SPEED: Double = 0.5

        @JvmField
        var ROTATION_GAIN: Double = 0.6

        @JvmField
        var SLOW_MULTIPLIER: Double = 0.4

        @JvmField
        var SLIDE_UP_POWER: Double = 0.6

        @JvmField
        var SLIDE_DOWN_POWER: Double = -0.4
    }

    /**
     * Represents what model of robot is running the code.
     *
     * @see model
     */
    enum class Model {
        /** This year's triangle robot. */
        RobotA,

        /** Last year's triangle robot. */
        RobotB,
    }
}
