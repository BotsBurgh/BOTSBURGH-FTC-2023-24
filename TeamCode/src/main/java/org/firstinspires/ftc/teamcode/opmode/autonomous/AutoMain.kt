package org.firstinspires.ftc.teamcode.opmode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.utils.Team

abstract class AutoMain : LinearOpMode() {
    abstract val config: Config

    override fun runOpMode() {
        TODO("Not yet implemented")
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
