package org.firstinspires.ftc.teamcode.opmode.teleop

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.api.DemoQuadWheels
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.vision.AprilVision
import org.firstinspires.ftc.teamcode.api.vision.Vision
// import org.firstinspires.ftc.teamcode.components.DemoForward

@TeleOp(name = "Demo TeleOp")
@Disabled
class DemoTeleOp : OpMode() {
    // Run once, when "Init" is pressed on driver hub.
    override fun init() {
        // Setup special telemetry
        Telemetry.init(this)

        // Initializes an API, shared code used by components.
        DemoQuadWheels.init(this)

        // Initialize the April Vision API
        AprilVision.init(this)

        // Initialize the Computer Vision API and register the April Tag processor
        Vision.init(this, AprilVision)

        // Log that we are initialized
        Telemetry.sayInitialized()
    }

    // Run repeatedly while the robot is running.
    override fun loop() {
        // Runs the component.
        // DemoForward.loop(this)

        // Log that we are running
        Telemetry.sayRunning()
    }
}
