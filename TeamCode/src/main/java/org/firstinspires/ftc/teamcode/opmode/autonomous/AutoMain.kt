package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.Team

abstract class AutoMain : LinearOpMode() {
    abstract val team: Team

    /** The axis the robot should move along. */
    private val forward =
        when (RobotConfig.model) {
            RobotConfig.Model.MiniRobot -> Encoders.Direction.Red
            RobotConfig.Model.RobotA -> Encoders.Direction.Blue
            RobotConfig.Model.RobotB -> Encoders.Direction.Red
        }

    /** The length and width of a tile in inches. */
    private val tileSize = 24.0

    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        Telemetry.sayStarted()

        sleep(RobotConfig.AutoMain.WAIT_TIME)

        // TODO: Rest of the autonomous.
    }

    /** Converts an amount of tiles to inches. */
    private fun tiles(amount: Double) = amount * tileSize

    /** Converts an amount of tiles to inches. */
    private fun tiles(amount: Int) = tiles(amount.toDouble())

    /**
     * A utility that returns the value correlating to the configured team.
     *
     * It is a lot easier to understand by reading the code than explaining it here.
     */
    private fun <T> pickTeam(
        red: T,
        blue: T,
    ): T =
        when (team) {
            Team.Red -> red
            Team.Blue -> blue
        }

    data class Config(val team: Team)
}

@Autonomous(name = "AutoMain - Red")
class AutoMainRed : AutoMain() {
    override val team = Team.Red
}

@Autonomous(name = "AutoMain - Blue")
class AutoMainBlue : AutoMain() {
    override val team = Team.Blue
}
