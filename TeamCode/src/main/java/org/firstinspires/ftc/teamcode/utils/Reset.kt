package org.firstinspires.ftc.teamcode.utils

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import kotlin.reflect.KProperty

/**
 * A set of functions that, when called, will reset the state of all APIs and components.
 */
private val resetFunctions = mutableSetOf<() -> Unit>()

/**
 * An object that resets the property states of the robot, used between opmode runs.
 *
 * **This must be initialized first, before any other code is run!**
 *
 * @see Resettable
 */
object Reset {
    /**
     * A reference to the last-initialized opmode, used purely to ensure that [Reset] is not called
     * more than once per run.
     */
    private var opModeReference: OpMode? = null

    /** Resets any registered [Resettable] properties. */
    fun init(opMode: OpMode) {
        // Check that, if Reset has been called before, it is not called on the same opmode run by
        // ensuring the opmode references are different.
        if (opModeReference == opMode) {
            throw IllegalStateException("Tried to initialize the Reset API more than once in a single run.")
        }

        // Set new opmode reference.
        opModeReference = opMode

        // Reset any registered properties.
        resetAll()
    }

    private fun resetAll() {
        // Call each reset function
        resetFunctions.forEach { it() }
    }
}

/**
 * A property delegate that instruments resetting it between opmode runs.
 *
 * This is used in [delegate properties](https://kotlinlang.org/docs/delegated-properties.html). You
 * must pass a function when constructing the resettable property that returns its default value.
 *
 * ```
 * val property: Type by Resettable {
 *     // This is a function body.
 *     // You can run anything in here.
 *     val x = 1 + 2
 *
 *     // You do not need the `return` keyword.
 *     // The last expression in the block, will be returned.
 *     x + 2 // will return 5
 * }
 * ```
 *
 * # Example
 *
 * ```
 * object MyAPI: API() {
 *     // The starting value of someState is 10
 *     private var someState: Int by Resettable { 10 }
 * }
 * ```
 */
class Resettable<T>(private val default: () -> T) {
    /** The actual value of the resettable property. */
    private var inner: T = this.default()

    init {
        // Add the reset function to a global set.
        resetFunctions.add(this::reset)
    }

    // Simply return the inner value.
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        return this.inner
    }

    // Simply set the inner value.
    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        this.inner = value
    }

    /** A function that resets the value of [inner] to [default]'s return. */
    private fun reset() {
        this.inner = this.default()
    }
}
