package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Drone
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.Team

abstract class AutoMain : LinearOpMode() {
    abstract val config: Config

    /** The axis the robot should move along. */
    private val forward =
        when (RobotConfig.model) {
            RobotConfig.Model.RobotA -> Encoders.Direction.Blue
            RobotConfig.Model.RobotB -> Encoders.Direction.Red
        }

    /** How many inches the robot is from the starting tile */
    private val startingOffset = 2.0

    /** The length and width of a tile in inches. */
    private val tileSize = 24.0

    override fun runOpMode() {
        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)
        Drone.init(this)

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
     *
     * @see pickParkPos
     */
    private fun <T> pickTeam(
        red: T,
        blue: T,
    ): T =
        when (config.team) {
            Team.Red -> red
            Team.Blue -> blue
        }

    /**
     * A utility that returns the value correlating to the configured parking position.
     *
     * It is a lot easier to understand by reading the code than explaining it here.
     *
     * @see pickTeam
     */
    private fun <T> pickParkPos(
        left: T,
        right: T,
    ): T =
        when (config.parkPos) {
            ParkPos.LeftOfBackdrop -> left
            ParkPos.RightOfBackdrop -> right
        }

    data class Config(val team: Team, val startPos: StartPos, val parkPos: ParkPos)

    enum class StartPos {
        BackStage,
        FrontStage,
    }

    enum class ParkPos {
        LeftOfBackdrop,
        RightOfBackdrop,
    }
}

// Matrix of run configurations
// {Red,Blue}{Front,Back}{Left,Right}

@Autonomous(name = "AutoMain - RedFrontLeft")
@Disabled
class AutoMainRedFrontLeft : AutoMain() {
    override val config = Config(Team.Red, StartPos.FrontStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueFrontLeft")
@Disabled
class AutoMainBlueFrontLeft : AutoMain() {
    override val config = Config(Team.Blue, StartPos.FrontStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - RedBackLeft")
@Disabled
class AutoMainRedBackLeft : AutoMain() {
    override val config = Config(Team.Red, StartPos.BackStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueBackLeft")
@Disabled
class AutoMainBlueBackLeft : AutoMain() {
    override val config = Config(Team.Blue, StartPos.BackStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - RedFrontRight")
@Disabled
class AutoMainRedFrontRight : AutoMain() {
    override val config = Config(Team.Red, StartPos.FrontStage, ParkPos.RightOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueFrontRight")
@Disabled
class AutoMainBlueFrontRight : AutoMain() {
    override val config = Config(Team.Blue, StartPos.FrontStage, ParkPos.RightOfBackdrop)
}

@Autonomous(name = "AutoMain - RedBackRight")
@Disabled
class AutoMainRedBackRight : AutoMain() {
    override val config = Config(Team.Red, StartPos.BackStage, ParkPos.RightOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueBackRight")
@Disabled
class AutoMainBlueBackRight : AutoMain() {
    override val config = Config(Team.Blue, StartPos.BackStage, ParkPos.RightOfBackdrop)
}
