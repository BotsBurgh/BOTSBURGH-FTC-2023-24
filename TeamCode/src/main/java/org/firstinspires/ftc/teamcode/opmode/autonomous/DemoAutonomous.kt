package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.components.linear.DemoSpinLinear
import org.firstinspires.ftc.teamcode.utils.Reset

@Autonomous(name = "Demo Autonomous")
@Disabled
class DemoAutonomous : LinearOpMode() {
    override fun runOpMode() {
        // Reset is a special utility necessary in all opmodes.
        Reset.init(this)

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

        // Linear components are run in order
        DemoSpinLinear.run(this)
    }
}
