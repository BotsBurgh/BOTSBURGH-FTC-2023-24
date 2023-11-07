package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.InTake

object LinearInTake : LinearAPI() {

    fun flyWheelPosition(final: Int) {
        while (InTake.flyWheel1.position < final) {
            InTake.flyWheelMovement()
        }
    }

    fun conveyPosition(final: Int) {
        while (InTake.inTakeConvey.currentPosition < final) {
            InTake.conveyMovement()
        }
    }
}