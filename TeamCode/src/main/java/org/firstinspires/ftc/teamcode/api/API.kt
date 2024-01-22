package org.firstinspires.ftc.teamcode.api

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.utils.APIDependencies
import org.firstinspires.ftc.teamcode.utils.Resettable

/**
 * An API is shared code used by components.
 *
 * An API needs to be initialized with the [init] function before it can be used. In general, APIs
 * should not have side-effects unless told to by a component.
 *
 * If an API depends on another API, it should say so in the object documentation.
 */
abstract class API {
    private var uninitializedOpMode: OpMode? by Resettable { null }

    protected val opMode: OpMode
        get() =
            this.uninitializedOpMode
                ?: throw NullPointerException("API has not been initialized with the OpMode before being used.")

    /**
     * @throws TypeCastException If [isLinear] is not set to true.
     */
    protected val linearOpMode: LinearOpMode
        get() = this.opMode as LinearOpMode

    @Deprecated(
        message = "Accessing the hardwareMap indirectly is not supported.",
        replaceWith = ReplaceWith("this.opMode.hardwareMap"),
        level = DeprecationLevel.WARNING,
    )
    protected val hardwareMap: HardwareMap
        get() = this.opMode.hardwareMap

    /**
     * Specifies whether this API requires a [LinearOpMode] to function.
     */
    open val isLinear: Boolean = false

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
     * @throws IllegalArgumentException If API requires a [LinearOpMode] but one was not passed in.
     */
    open fun init(opMode: OpMode) {
        // You can only initialize an API once. If it is initialized more than once, throw an error.
        if (this.uninitializedOpMode != null) {
            throw IllegalStateException("Tried to initialize an API more than once.")
        }

        // If this API requires LinearOpMode, but only a regular OpMode was passed.
        if (this.isLinear && opMode !is LinearOpMode) {
            throw IllegalArgumentException(
                """
                Tried to initialized a linear API without a LinearOpMode.
                Please make sure that your opmode extends LinearOpMode.
                Also please check that the API has not accidentally set isLinear to true.
                """.trimIndent(),
            )
        }

        this.uninitializedOpMode = opMode

        APIDependencies.registerAPI(this)
    }

    open fun dependencies(): Set<API> = emptySet()
}
