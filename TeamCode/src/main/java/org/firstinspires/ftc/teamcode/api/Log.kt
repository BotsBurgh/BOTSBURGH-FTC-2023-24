package org.firstinspires.ftc.teamcode.api

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

object Log : API() {
    private val telemetry: Telemetry
        get() = this.opMode.telemetry

    private val telemetryLog: Telemetry.Log
        get() = this.opMode.telemetry.log()

    private val dashboard: FtcDashboard
        get() = FtcDashboard.getInstance()

    private const val ROBOT_LOG_TAG = "BOTSBURGH"

    private val botsburghFolder = File(AppUtil.ROOT_FOLDER, "/BotsBurgh/")
    private val logFile = File(this.botsburghFolder, "/latest.log")
    private val logWriter = BufferedWriter(FileWriter(this.logFile))

    init {
        this.botsburghFolder.mkdirs()
        this.logFile.createNewFile()
    }

    override fun init(opMode: OpMode) {
        super.init(opMode)

        with(this.logWriter) {
            write("BotsBurgh 11792 FTC 2023-24")
            newLine()

            newLine()

            flush()
        }
    }

    fun flush() {
        this.logWriter.flush()
    }

    fun debug(msg: Any) = this.log(Level.Debug, msg)

    fun info(msg: Any) = this.log(Level.Info, msg)

    fun warn(msg: Any) = this.log(Level.Warn, msg)

    fun error(msg: Any) = this.log(Level.Error, msg)

    private fun log(
        level: Level,
        msg: Any,
    ) {
        val msgString = msg.toString()
        val formatted = "[$level] $msgString"

        // Log to driver hub
        this.telemetryLog.add(formatted)

        // Log to FTC Dashboard
        this.dashboard.sendTelemetryPacket(
            TelemetryPacket().apply {
                this.addLine(formatted)
            },
        )

        // Log to BotsBurgh latest.log
        with(this.logWriter) {
            write(formatted)
            newLine()
        }

        // Log to Logcat / internal FTC log
        RobotLog.internalLog(level.toAndroid(), this.ROBOT_LOG_TAG, msgString)
    }

    enum class Level {
        Debug,
        Info,
        Warn,
        Error,
        ;

        fun toAndroid(): Int =
            when (this) {
                Debug -> android.util.Log.DEBUG
                Info -> android.util.Log.INFO
                Warn -> android.util.Log.WARN
                Error -> android.util.Log.ERROR
            }
    }
}
