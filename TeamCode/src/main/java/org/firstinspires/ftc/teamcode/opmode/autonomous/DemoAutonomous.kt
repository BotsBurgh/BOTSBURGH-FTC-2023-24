package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels
import org.firstinspires.ftc.teamcode.api.Telemetry

@Autonomous(name = "Demo Autonomous")
@Disabled
class DemoAutonomous : LinearOpMode() {
    override fun runOpMode() {
        // Setup special telemetry
        Telemetry.init(this)

        // APIs are initialized here
        DemoQuadWheels.init(this)

        // Log that we are initialized
        Telemetry.sayInitialized()

        // Wait for the run button to be pressed
        waitForStart()

        // Log that we are started
        Telemetry.sayStarted()

        val runtime = ElapsedTime()

        while (runtime.seconds() < 2.0) {
            // Drive at 50% speed, slowing down the closer we get to 2 seconds.
            DemoQuadWheels.drive(0.5 * (2.0 - runtime.seconds()))
        }

        // Stop the robot.
        DemoQuadWheels.drive(0.0)
    }
}
