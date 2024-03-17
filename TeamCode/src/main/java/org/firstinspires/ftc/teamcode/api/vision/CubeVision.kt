package org.firstinspires.ftc.teamcode.api.vision

import android.graphics.Canvas
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration
import org.firstinspires.ftc.teamcode.api.API
import org.firstinspires.ftc.teamcode.utils.RobotConfig
import org.firstinspires.ftc.teamcode.utils.auto.Team
import org.firstinspires.ftc.vision.VisionProcessor
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc
import kotlin.concurrent.Volatile
import kotlin.math.max

/**
 * An API for scanning the cube team piece.
 *
 * This requires the [Vision] API.
 */
object CubeVision : API(), VisionAPI {
    override val processor: VisionProcessor
        get() = this.cubeProcessor

    /**
     * Returns the last scanned placement of the cube.
     */
    val output: CubePlacement
        get() = this.cubeProcessor.placement

    private lateinit var cubeProcessor: CubeProcessor

    fun init(
        opMode: OpMode,
        cubeColor: Team,
    ) {
        super.init(opMode)
        this.cubeProcessor = CubeProcessor(cubeColor, opMode.telemetry)
    }

    fun disable() {
        this.cubeProcessor.disabled = true
    }

    @Deprecated(
        message = "Please initialize CubeVision with cubeColor.",
        replaceWith = ReplaceWith("CubeVision.init(this, cubeColor)"),
        level = DeprecationLevel.HIDDEN,
    )
    override fun init(opMode: OpMode) {
    }

    override fun dependencies() = setOf(Vision)

    private class CubeProcessor(private val cubeColor: Team, private val t: Telemetry) :
        VisionProcessor {
        @Volatile
        var placement = CubePlacement.Center

        @Volatile
        var disabled = false

        private var initialized = false

        private val rgb = Mat()

        private lateinit var regionLeft: Mat
        private lateinit var regionCenter: Mat
        private lateinit var regionRight: Mat

        private val white = Scalar(255.0, 255.0, 255.0, 255.0)

        private val colorWeight =
            when (this.cubeColor) {
                Team.Red -> RobotConfig.CubeVision.RED_WEIGHT
                Team.Blue -> RobotConfig.CubeVision.BLUE_WEIGHT
            }

        override fun init(
            width: Int,
            height: Int,
            calibration: CameraCalibration?,
        ) {}

        override fun processFrame(
            frame: Mat,
            captureTimeNanos: Long,
        ): CubePlacement {
            if (this.disabled) {
                // Default to center after disabled
                return CubePlacement.Center
            }

            frame.copyTo(this.rgb)

            // Create sub-regions only once.
            if (!this.initialized) {
                this.regionLeft = this.rgb.submat(RobotConfig.CubeVision.LEFT_REGION)
                this.regionCenter = this.rgb.submat(RobotConfig.CubeVision.CENTER_REGION)
                this.regionRight = this.rgb.submat(RobotConfig.CubeVision.RIGHT_REGION)

                this.initialized = true
            }

            // Calculate score. The greater, the more likely it is to be the cube we want.
            val scoreLeft = Core.mean(this.regionLeft).mul(colorWeight).sumRGB()
            val scoreCenter = Core.mean(this.regionCenter).mul(colorWeight).sumRGB()
            val scoreRight = Core.mean(this.regionRight).mul(colorWeight).sumRGB()

            /*
            // Possible data-race, commenting out

            with(t) {
                addData("Score Left", scoreLeft)
                addData("Score Center", scoreCenter)
                addData("Score Right", scoreRight)
                update()
            }
             */

            this.placement =
                when (max(max(scoreLeft, scoreCenter), scoreRight)) {
                    scoreLeft -> CubePlacement.Left
                    scoreCenter -> CubePlacement.Center
                    scoreRight -> CubePlacement.Right
                    else -> throw RuntimeException("Unreachable")
                }

            Imgproc.rectangle(
                frame,
                RobotConfig.CubeVision.LEFT_REGION,
                white,
            )
            Imgproc.rectangle(
                frame,
                RobotConfig.CubeVision.CENTER_REGION,
                white,
            )
            Imgproc.rectangle(
                frame,
                RobotConfig.CubeVision.RIGHT_REGION,
                white,
            )

            // This return value is passed to [onDrawFrame].
            return placement
        }

        override fun onDrawFrame(
            canvas: Canvas,
            onscreenWidth: Int,
            onscreenHeight: Int,
            scaleBmpPxToCanvasPx: Float,
            scaleCanvasDensity: Float,
            userContext: Any?,
        ) {
        }

        private fun Scalar.sumRGB(): Double = this.`val`.sum()

        private operator fun Rect.times(rhs: Int): Rect = Rect(this.x * rhs, this.y * rhs, this.width * rhs, this.height * rhs)
    }

    /**
     * Represents the position where the cube is placed, relative to the camera of the robot.
     */
    enum class CubePlacement {
        Left,
        Center,
        Right, ;

        fun toInt(): Int =
            when (this) {
                Left -> 1
                Center -> 2
                Right -> 3
            }
    }
}
