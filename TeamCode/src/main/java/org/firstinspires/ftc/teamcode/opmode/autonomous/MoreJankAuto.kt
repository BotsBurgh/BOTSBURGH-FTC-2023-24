package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.RobotConfig

@Autonomous(name = "Most Jank Auto")
class MoreJankAuto : LinearOpMode() {

    private val forward =
        when (RobotConfig.model) {
            RobotConfig.Model.RobotA -> Encoders.Direction.Green
            RobotConfig.Model.RobotB -> Encoders.Direction.Red
        }
    override fun runOpMode() {

        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        Encoders.driveTo(
            forward,
            42.0,
        )


    }
}