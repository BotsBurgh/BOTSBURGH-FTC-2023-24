package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Deprecated(
    message = "LinearAPI is deprecated. Please use the API class and override isLinear to true.",
    level = DeprecationLevel.ERROR,
)
abstract class LinearAPI {
    protected val linearOpMode: LinearOpMode
        get() = throw NotImplementedError()

    open fun init(linearOpMode: LinearOpMode) {
        throw NotImplementedError("LinearAPI is deprecated.")
    }
}
