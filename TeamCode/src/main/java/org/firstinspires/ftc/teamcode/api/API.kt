package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareMap

/**
 * An API is shared code used by components.
 *
 * An API needs to be initialized with the [init] function before it can be used. In general, APIs
 * should not have side-effects unless told to by a component.
 *
 * If an API depends on another API, it should say so in the object documentation.
 */
abstract class API {
    private var uninitializedOpMode: OpMode? = null

    protected val opMode: OpMode
        get() = uninitializedOpMode
            ?: throw NullPointerException("<API> has not been initialized with the OpMode before being used.")

    protected val hardwareMap: HardwareMap
        get() = this.opMode.hardwareMap

    /**
     * Initializes the API to use the given [opMode].
     *
     * This must be called before any other function provided by the API.
     *
     * ## Note to Implementors
     *
     * Make sure to call `super.init(opMode)` at the beginning of the overloaded function, or the
     * API will not properly store a reference to the op-mode.
     *
     * @throws IllegalStateException If called more than once.
     */
    open fun init(opMode: OpMode) {
        // You can only initialize an API once. If it is initialized more than once, throw an error.
        if (uninitializedOpMode != null) {
            throw IllegalStateException("Tried to initialize an <API> more than once.")
        }

        this.uninitializedOpMode = opMode
    }
}
