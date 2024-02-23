package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor

object Hanger: API() {
    private lateinit var hanger : DcMotor

    override fun init(opMode: OpMode){
        super.init(opMode)

        this.hanger =  this.opMode.hardwareMap.get(DcMotor::class.java, "hanger")
    }

    fun hang(power: Double){
        hanger.power = power
    }

    fun stop() {
        hanger.power = 0.0
    }
}