package org.firstinspires.ftc.teamcode.api.linear

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.utils.Resettable

/**
 * A linear API is shared code used by linear components.
 *
 * It is specifically designed to only work in autonomous scenarios. It allows linear components to
 * call specific methods unique to [LinearOpMode].
 *
 * If a linear API depends on another API, it should say so in the documentation.
 *
 * @see API
 */
abstract class LinearAPI {
    private var uninitializedOpMode: LinearOpMode? by Resettable { null }

    protected val linearOpMode: LinearOpMode
        get() =
            uninitializedOpMode
                ?: throw NullPointerException("<LinearAPI> has not been initialized with the OpMode before being used.")

    open fun init(linearOpMode: LinearOpMode) {
        // You can only initialize an API once. If it is initialized more than once, throw an error.
        if (uninitializedOpMode != null) {
            throw IllegalStateException("Tried to initialize a <LinearAPI> more than once.")
        }

        this.uninitializedOpMode = linearOpMode
    }
}
