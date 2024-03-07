package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Omnicoders
import kotlin.math.PI

@Autonomous(name = "Test Omnicoders")
class Test : LinearOpMode() {
    // Adjust these fields when testing.
    @Config
    object Test {
        @JvmField
        var RADIANS = PI / 2.0

        @JvmField
        var INCHES = 12.0
    }

    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Omnicoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        Omnicoders.driveTo(Test.RADIANS, Test.INCHES)
    }
}
