package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.DcMotor

object InTake : API() {
    lateinit var flyWheel1: Servo
    lateinit var flyWheel2: Servo

    lateinit var inTakeConvey: DcMotor
    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.flyWheel1 = hardwareMap.get(Servo::class.java, "flyWheel1")
        this.flyWheel2 = hardwareMap.get(Servo::class.java, "flyWheel2")
        this.inTakeConvey = hardwareMap.get(DcMotor::class.java, "inTakeConvey")
        inTakeConvey.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        inTakeConvey.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }
    /*
    movement of Conveyor Belt
     */
    fun conveyMovement() {
        inTakeConvey.power = 1.0
}
    /*
    Moves the flyWheel
    */
    fun flyWheelMovement() {
        flyWheel1.position += 5.0
        flyWheel2.position += 5.0
    }
}