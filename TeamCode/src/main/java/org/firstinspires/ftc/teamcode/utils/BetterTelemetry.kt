package org.firstinspires.ftc.teamcode.utils

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import org.firstinspires.ftc.robotcore.external.Func
import org.firstinspires.ftc.robotcore.external.Telemetry

class BetterTelemetry : Telemetry {
    private val instance = FtcDashboard.getInstance()
    private val data = mutableMapOf<String, TelemetryData>()
    private val actions = mutableSetOf<Runnable>()
    private var autoClear = true

    override fun addData(caption: String, format: String, vararg args: Any?): Telemetry.Item {
        this.data[caption] = TelemetryData.PreComputed(format.format(args))
        return TODO()
    }

    override fun addData(caption: String, value: Any?): Telemetry.Item {
        this.data[caption] = TelemetryData.PreComputed(value)
        return TODO()
    }

    override fun <T : Any?> addData(caption: String, valueProducer: Func<T>): Telemetry.Item {
        this.data[caption] = TelemetryData.Lazy(valueProducer)
        return TODO("Not yet implemented")
    }

    override fun <T : Any?> addData(
        caption: String,
        format: String,
        valueProducer: Func<T>,
    ): Telemetry.Item {
        this.data[caption] = TelemetryData.LazyFormatted(format, valueProducer)
        return TODO("Not yet implemented")
    }

    override fun removeItem(item: Telemetry.Item): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        // Only remove non-persistent items. In this case, pre-computed data
        for ((caption, _) in this.data.entries.filter { it.value is TelemetryData.PreComputed }) {
            this.data.remove(caption)
        }
    }

    override fun clearAll() {
        this.data.clear()
        this.actions.clear()
    }

    override fun addAction(action: Runnable): Any {
        this.actions.add(action)
        return action
    }

    override fun removeAction(token: Any): Boolean {
        require(token is Runnable) { "Given token is invalid, cannot remove action." }
        return this.actions.remove(token)
    }

    override fun speak(text: String) {
        TODO("Speaking is not yet supported.")
    }

    override fun speak(text: String, languageCode: String?, countryCode: String?) {
        TODO("Speaking is not yet supported.")
    }

    override fun update(): Boolean {
        this.actions.forEach { it.run() }

        val packet = TelemetryPacket()

        for ((caption, data) in this.data.entries) {
            packet.put(caption, data.value)
        }

        this.instance.sendTelemetryPacket(packet)

        if (this.autoClear) {
            this.clear()
        }

        return true
    }

    override fun addLine(): Telemetry.Line {
        TODO("Not yet implemented")
    }

    override fun addLine(lineCaption: String): Telemetry.Line {
        TODO("Not yet implemented")
    }

    override fun removeLine(line: Telemetry.Line?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAutoClear() = this.autoClear

    override fun setAutoClear(autoClear: Boolean) {
        this.autoClear = autoClear
    }

    override fun getMsTransmissionInterval(): Int = this.instance.telemetryTransmissionInterval

    override fun setMsTransmissionInterval(msTransmissionInterval: Int) {
        this.instance.telemetryTransmissionInterval = msTransmissionInterval
    }

    override fun getItemSeparator(): String? {
        throw UnsupportedOperationException("Item separators are not supported on FTC Dashboard.")
    }

    override fun setItemSeparator(itemSeparator: String?) {
        throw UnsupportedOperationException("Item separators are not supported on FTC Dashboard.")
    }

    override fun getCaptionValueSeparator(): String {
        throw UnsupportedOperationException("Caption separators are not supported on FTC Dashboard.")
    }

    override fun setCaptionValueSeparator(captionValueSeparator: String?) {
        throw UnsupportedOperationException("Caption separators are not supported on FTC Dashboard.")
    }

    override fun setDisplayFormat(displayFormat: Telemetry.DisplayFormat) {
        throw UnsupportedOperationException("Cannot set display format on FTC Dashboard.")
    }

    override fun log(): Telemetry.Log {
        TODO("Log not yet implemented")
    }

    private sealed interface TelemetryData {
        val value: Any?

        class PreComputed(override val value: Any?) : TelemetryData

        class Lazy<T: Any?>(private val producer: Func<T>) : TelemetryData {
            override val value: Any?
                get() = this.producer.value()
        }

        class LazyFormatted<T: Any?>(private val format: String, private val producer: Func<T>) : TelemetryData {
            override val value: Any
                get() = this.format.format(this.producer.value())
        }
    }
}
