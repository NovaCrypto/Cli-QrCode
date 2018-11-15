package io.github.novacrypto.cli.qrcode

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import io.github.novacrypto.cli.qrcode.options.*
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.ParseException
import java.io.IOException
import java.nio.file.FileSystems

fun main(args: Array<String>) {
    val options = buildOptions()

    val parser = DefaultParser()
    val formatter = HelpFormatter()

    try {
        val cmd = parser.parse(options, args)
        val (width, height) = cmd.size
        generateQRCodeImage(cmd, width, height)
    } catch (e: ParseException) {
        println(e.message)
        formatter.printHelp("-i input -o output.png [-ec L/M/Q/H] [-s w,h] [-t]", options)

        System.exit(1)
    }
}

private fun generateQRCodeImage(cmd: CommandLine, width: Int, height: Int) {
    val outputFilePath = cmd.outputFilePath
    val ecLevel = cmd.errorCorrectionLevel

    val bitMatrix = getBitMatrix(width, height, cmd.inputText, getHints(ecLevel)) ?: return

    saveImage(bitMatrix, outputFilePath, cmd.backgroundColor)

    println(String.format("Written QR png, size %d x %d, EC: %s: %s", width, height, ecLevel, outputFilePath))
}

private fun getHints(ecLevel: ErrorCorrectionLevel): Map<EncodeHintType, *> =
        mapOf(EncodeHintType.ERROR_CORRECTION to ecLevel)

private fun getBitMatrix(width: Int, height: Int, input: String,
                         hints: Map<EncodeHintType, *>): BitMatrix? {
    val qrCodeWriter = QRCodeWriter()
    return try {
        qrCodeWriter.encode(input, BarcodeFormat.QR_CODE, width, height, hints)
    } catch (e: WriterException) {
        println("Could not generate QR Code: " + e.message)
        System.exit(2)
        null
    }
}

private fun saveImage(bitMatrix: BitMatrix, outputFilePath: String, offColor: Int) {
    try {
        val path = FileSystems.getDefault().getPath(outputFilePath)
        val config = MatrixToImageConfig(MatrixToImageConfig.BLACK, offColor)
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path, config)
    } catch (e: IOException) {
        println("Could not write QR Code file: " + e.message)
        System.exit(3)
    }
}
