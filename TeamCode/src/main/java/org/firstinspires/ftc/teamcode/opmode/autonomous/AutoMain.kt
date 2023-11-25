package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.Telemetry
import org.firstinspires.ftc.teamcode.api.TriWheels
import org.firstinspires.ftc.teamcode.api.linear.Encoders
import org.firstinspires.ftc.teamcode.utils.Reset
import org.firstinspires.ftc.teamcode.utils.Team

abstract class AutoMain : LinearOpMode() {
    abstract val config: Config

    /** The axis the robot should move along. */
    private val forward = Encoders.Direction.Red

    /** How many inches the robot is from the starting tile */
    private val startingOffset = 2.0

    /** The length and width of a tile in inches. */
    private val tileSize = 24.0

    override fun runOpMode() {
        Reset.init(this)

        Telemetry.init(this)
        TriWheels.init(this)
        Encoders.init(this)

        Telemetry.sayInitialized()

        waitForStart()

        // TODO: Scan team game element
        // val teamElementPos: Int = ...

        when (config.startPos) {
            StartPos.BackStage -> {
                Encoders.driveTo(forward, startingOffset)

                Encoders.spinTo(
                    when (config.team) {
                        Team.Red -> 90.0
                        Team.Blue -> -90.0
                    }
                )

                Encoders.driveTo(forward, tiles(1))

                Encoders.spinTo(
                    when (config.team) {
                        Team.Red -> -90.0
                        Team.Blue -> 90.0
                    }
                )

                Encoders.driveTo(forward, tiles(1))

                Encoders.spinTo(
                    when (config.team) {
                        Team.Red -> 90.0
                        Team.Blue -> -90.0
                    }
                )

                Encoders.driveTo(forward, tiles(2))
            }

            StartPos.FrontStage -> {
                // Drive forward two tiles
                Encoders.driveTo(forward, tiles(2) + startingOffset)

                // Spin to face backdrop
                Encoders.spinTo(
                    when (config.team) {
                        Team.Red -> 90.0
                        Team.Blue -> -90.0
                    }
                )

                Encoders.driveTo(forward, tiles(1))
            }
        }

        // TODO: Deposit pixel using april tag
        // Red: 1, 2, 3. Blue: 4, 5, 6
        // DepositPixel.run(this, teamElementPos + when (config.team) { Team.Red -> 3; Team.Blue -> 0 })

        // Back up to view all april tags
        Encoders.driveTo(forward, tiles(-1))

        // TODO: Center robot on middle april tag
        when (config.team) {
            Team.Red -> {
                // centerOnAprilTag(5, DepositPixel.APRIL_TAG_DISTANCE)
            }

            Team.Blue -> {
                // centerOnAprilTag(2, DepositPixel.APRIL_TAG_DISTANCE)
            }
        }

        // Navigate to parking spot
        Encoders.spinTo(
            when (config.parkPos) {
                ParkPos.LeftOfBackdrop -> -90.0
                ParkPos.RightOfBackdrop -> 90.0
            }
        )

        Encoders.driveTo(forward, tiles(1))

        Encoders.spinTo(
            when (config.parkPos) {
                ParkPos.LeftOfBackdrop -> 90.0
                ParkPos.RightOfBackdrop -> -90.0
            }
        )

        // Park in spot
        Encoders.driveTo(forward, tiles(1))
    }

    /** Converts an amount of tiles to inches. */
    private fun tiles(amount: Double) = amount * tileSize

    /** Converts an amount of tiles to inches. */
    private fun tiles(amount: Int) = tiles(amount.toDouble())

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
