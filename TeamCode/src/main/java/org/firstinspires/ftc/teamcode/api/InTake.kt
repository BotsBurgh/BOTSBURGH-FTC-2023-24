package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.DcMotor

object InTake : API() {
    lateinit var flyWheel1: CRServo
    lateinit var flyWheel2: CRServo

    lateinit var inTakeConvey: DcMotor

    //lateinit var flyWheelEncoder:
    override fun init(opMode: OpMode) {
        super.init(opMode)

        this.flyWheel1 = hardwareMap.get(CRServo::class.java, "flyWheel1")
        this.flyWheel2 = hardwareMap.get(CRServo::class.java, "flyWheel2")
        this.inTakeConvey = hardwareMap.get(DcMotor::class.java, "inTakeConvey")
        resetEncoders()
    }

    fun resetEncoders() {
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
}   stop movement of Conveyor Belt
    */
    fun conveyorStop() {
        inTakeConvey.power = 0.0
    }
    /*
    Moves the flyWheel
    */
    fun flyWheelMovement() {
        flyWheel1.power = 1.0
        flyWheel2.power = 1.0
    }
    /*
    moves the flyWheel in reverse
     */
    fun flyWheelReverseMovement() {
        flyWheel1.power = -1.0
        flyWheel2.power = -1.0
    }

    // functions to be used during auto to move flyWheel and Conveyor Belt
    fun flyWheelPosition(final: Int) {
        // commented out as no current way to check for position
        //while ( flyWheelEncoder.currentPosition < final) {
            //flyWheelMovement()
       // }
    }
    fun conveyPosition(final: Int) {
        while (inTakeConvey.currentPosition < final) {
            conveyMovement()
        }
    }
}