package org.firstinspires.ftc.teamcode.api.linear

import org.firstinspires.ftc.teamcode.api.OutTake

object LinearOutTake : LinearAPI() {
    fun beltPosition(final: Int) {
        while (OutTake.outTakeBelt.position < final) {
            OutTake.drop()
        }
    }
}