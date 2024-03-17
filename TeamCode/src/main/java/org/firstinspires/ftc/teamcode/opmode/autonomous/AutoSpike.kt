package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.auto.Team

abstract class AutoSpike : LinearOpMode() {
    abstract val team: Team

    private val forward = Encoders.Direction.Red
    private val tileSize = 24.0

    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        // Drop of spike pixel
        Encoders.driveTo2(forward, tiles(1.65))
        Encoders.driveTo2(forward, tiles(-0.3))
    }

    private fun tiles(amount: Double) = amount * tileSize

    private fun tiles(amount: Int) = tiles(amount.toDouble())

    private fun <T> pickTeam(
        red: T,
        blue: T,
    ): T =
        when (team) {
            Team.Red -> red
            Team.Blue -> blue
        }
}

@Autonomous(name = "AutoSpike - Red", group = "AutoSpike")
class AutoSpikeRed : AutoSpike() {
    override val team = Team.Red
}

@Autonomous(name = "AutoSpike - Blue", group = "AutoSpike")
class AutoSpikeBlue : AutoSpike() {
    override val team = Team.Blue
}
