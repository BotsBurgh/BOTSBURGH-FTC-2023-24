@file:Suppress("ktlint:standard:property-naming")

package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.dashboard.config.Config
import org.firstinspires.ftc.teamcode.utils.RobotConfig.model

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
        var TICKS_PER_INCH: Double =
            when (model) {
                Model.RobotA -> 27.0
                Model.RobotB -> 44.0
            }

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
        var MAX_SPIN_SPEED: Double = 0.8

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
    }

    // ** Configuration related to the LinearSlide API. */
    @Config
    object LinearSlide {
        @JvmField
        var MIN = 0.0

        @JvmField
        var MAX = 4500
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
