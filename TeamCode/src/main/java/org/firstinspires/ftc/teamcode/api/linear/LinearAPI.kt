package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.utils.Resettable

abstract class LinearAPI {
    private var uninitializedOpMode: LinearOpMode? by Resettable { null }

    protected val linearOpMode: LinearOpMode
        get() = uninitializedOpMode
            ?: throw NullPointerException("<API> has not been initialized with the OpMode before being used.")

    open fun init(linearOpMode: LinearOpMode) {
        // You can only initialize an API once. If it is initialized more than once, throw an error.
        if (uninitializedOpMode != null) {
            throw IllegalStateException("Tried to initialize an <API> more than once.")
        }

        this.uninitializedOpMode = linearOpMode
    }
}
