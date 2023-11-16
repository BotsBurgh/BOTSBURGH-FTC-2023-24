package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.dashboard.config.Config

object RobotConfig {
    val model: Model = Model.Mark0

    @Config
    object Encoders {
        @JvmField
        var TICKS_PER_INCH: Double = 44.0

        @JvmField
        var TICKS_PER_DEGREE: Double = 6.64

        @JvmField
        var ENCODER_GAIN: Double = 0.0003

        @JvmField
        var ENCODER_ERROR: Int = 10

        @JvmField
        var MAX_DRIVE_SPEED: Double = 0.3

        @JvmField
        var MAX_SPIN_SPEED: Double = 0.8

        @JvmField
        var TIME_GAIN: Double = 0.4
    }

    enum class Model {
        // This year's robot's first version.
        Mark1,

        // Last year's version.
        Mark0,
    }
}
