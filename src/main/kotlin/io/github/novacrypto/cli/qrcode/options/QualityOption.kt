package io.github.novacrypto.cli.qrcode.options

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import org.apache.commons.cli.ParseException

private val defaultQualityLevel = ErrorCorrectionLevel.M

internal val qualityOption: Option
    get() = Option
            .builder("ec")
            .longOpt("errorCorrection")
            .desc(String.format("Error correction, L,M,Q or H, default $defaultQualityLevel:%n" +
                    "L = ~7%% correction%n" +
                    "M = ~15%% correction%n" +
                    "Q = ~25%% correction%n" +
                    "H = ~30%% correction"))
            .numberOfArgs(1)
            .build()

internal val CommandLine.errorCorrectionLevel: ErrorCorrectionLevel
    get() {
        val errorCorrection = getOptionValue("errorCorrection") ?: return defaultQualityLevel
        return parseEcLevel(errorCorrection)
    }

private fun parseEcLevel(errorCorrection: String) =
        when (if (errorCorrection.length == 1) errorCorrection[0] else ' ') {
            'L' -> ErrorCorrectionLevel.L
            'M' -> ErrorCorrectionLevel.M
            'H' -> ErrorCorrectionLevel.H
            'Q' -> ErrorCorrectionLevel.Q
            else -> throw ParseException("errorCorrection must be 1 of L,M,H,Q")
        }
