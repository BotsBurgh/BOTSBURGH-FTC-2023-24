package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.Reset
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.Team

abstract class AutoMain : LinearOpMode() {
    abstract val config: Config

    /** The axis the robot should move along. */
    private val forward = Encoders.Direction.Blue

    /** How many inches the robot is from the starting tile */
    private val startingOffset = 2.0

    /** The length and width of a tile in inches. */
    private val tileSize = 24.0

    override fun runOpMode() {
        Reset.init(this)

        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        // Vision APIs
        // AprilVision.init(this)
        // Vision.init(this, AprilVision)

        // AprilMovement.init(this)

        // Modify camera exposure for april tags
        // This may interfere with other vision processes like Tensorflow
        // Vision.optimizeForAprilTags()

        // Stream camera to FTC Dashboard
        // val camera = getVisionPortalCamera(Vision.portal)!!
        // FtcDashboard.getInstance().startCameraStream(camera, 0.0)

        Telemetry.sayInitialized()

        waitForStart()

        /*
        // TODO: Scan team game element
        val teamElementPos = 2
         */

        sleep(RobotConfig.AutoMain.WAIT_TIME)

        telemetry.log().add("No team game element scanned, defaulting to 2!")
        telemetry.update()

        when (config.startPos) {
            StartPos.BackStage -> {
                Encoders.driveTo(forward, startingOffset)
                Encoders.spinTo(pickTeam(90.0, -90.0))
                Encoders.driveTo(forward, tiles(1))
                Encoders.spinTo(pickTeam(-90.0, 90.0))
                Encoders.driveTo(forward, tiles(1))
                Encoders.spinTo(pickTeam(90.0, -90.0))
                Encoders.driveTo(forward, tiles(2))
            }

            StartPos.FrontStage -> {
                // Drive forward two tiles
                Encoders.driveTo(forward, tiles(2) + startingOffset)

                // Spin to face backdrop
                Encoders.spinTo(pickTeam(90.0, -90.0))

                Encoders.driveTo(forward, tiles(1))
            }
        }

        /*

        // Deposit pixel using april tag
        // Red: 1, 2, 3. Blue: 4, 5, 6
        AprilMovement.driveTo(teamElementPos + pickTeam(0, 3))

        // TODO: Deposit pixel

        // Back up to view all april tags
        Encoders.driveTo(forward, tiles(-1))

        // Center 1 tile away from middle april tag
        when (config.team) {
            Team.Red -> AprilMovement.driveTo(5, tiles(1))
            Team.Blue -> AprilMovement.driveTo(2, tiles(1))
        }

         */

        // Navigate to parking spot
        Encoders.spinTo(pickParkPos(-90.0, 90.0))
        Encoders.driveTo(forward, tiles(1))
        Encoders.spinTo(pickParkPos(90.0, -90.0))

        // Park in spot
        Encoders.driveTo(forward, tiles(1))
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
    private fun <T> pickTeam(red: T, blue: T): T = when (config.team) {
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
    private fun <T> pickParkPos(left: T, right: T): T = when (config.parkPos) {
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
class AutoMainRedFrontLeft : AutoMain() {
    override val config = Config(Team.Red, StartPos.FrontStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueFrontLeft")
class AutoMainBlueFrontLeft : AutoMain() {
    override val config = Config(Team.Blue, StartPos.FrontStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - RedBackLeft")
class AutoMainRedBackLeft : AutoMain() {
    override val config = Config(Team.Red, StartPos.BackStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueBackLeft")
class AutoMainBlueBackLeft : AutoMain() {
    override val config = Config(Team.Blue, StartPos.BackStage, ParkPos.LeftOfBackdrop)
}

@Autonomous(name = "AutoMain - RedFrontRight")
class AutoMainRedFrontRight : AutoMain() {
    override val config = Config(Team.Red, StartPos.FrontStage, ParkPos.RightOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueFrontRight")
class AutoMainBlueFrontRight : AutoMain() {
    override val config = Config(Team.Blue, StartPos.FrontStage, ParkPos.RightOfBackdrop)
}

@Autonomous(name = "AutoMain - RedBackRight")
class AutoMainRedBackRight : AutoMain() {
    override val config = Config(Team.Red, StartPos.BackStage, ParkPos.RightOfBackdrop)
}

@Autonomous(name = "AutoMain - BlueBackRight")
class AutoMainBlueBackRight : AutoMain() {
    override val config = Config(Team.Blue, StartPos.BackStage, ParkPos.RightOfBackdrop)
}
