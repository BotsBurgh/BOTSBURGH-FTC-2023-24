package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders

@Autonomous(name = "AutoPark")
class AutoPark : LinearOpMode() {
    private val forward = Encoders.Direction.Red

    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        Encoders.driveTo2(
            forward,
            42.0,
        )
    }
}
