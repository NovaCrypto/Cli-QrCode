package io.github.novacrypto.cli.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class MakeQRCode {

    public static void main(final String[] args) {

        final Options options = getOptions();

        final CommandLineParser parser = new DefaultParser();
        final HelpFormatter formatter = new HelpFormatter();

        try {
            final CommandLine cmd = parser.parse(options, args);
            final String[] size = cmd.getOptionValues("size");
            final int width, height;
            if (size == null) {
                width = 300;
                height = 300;
            } else {
                width = Integer.parseInt(size[0]);
                height = Integer.parseInt(size[1]);
            }
            generateQRCodeImage(cmd, width, height);
        } catch (final ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("-i input -o output.png [-ec L/M/Q/H] [-s w,h]", options);

            System.exit(1);
        }
    }

    private static void generateQRCodeImage(final CommandLine cmd, final int width, final int height) throws ParseException {
        final String input = cmd.getOptionValue("input");
        final String outputFilePath = cmd.getOptionValue("output");
        final ErrorCorrectionLevel ecLevel = getErrorCorrectionLevel(cmd);

        final BitMatrix bitMatrix = getBitMatrix(width, height, input, getHints(ecLevel));
        if (bitMatrix == null) return;

        saveImage(bitMatrix, outputFilePath);

        System.out.println(String.format("Written QR png, size %d x %d, EC: %s: %s", width, height, ecLevel, outputFilePath));
    }

    private static Map<EncodeHintType, ?> getHints(final ErrorCorrectionLevel ecLevel) {
        final HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ecLevel);
        return hints;
    }

    private static ErrorCorrectionLevel getErrorCorrectionLevel(CommandLine cmd) throws ParseException {
        final String errorCorrection = cmd.getOptionValue("errorCorrection");
        if (errorCorrection == null) return ErrorCorrectionLevel.M;
        return parseEcLevel(errorCorrection);
    }

    private static ErrorCorrectionLevel parseEcLevel(final String errorCorrection) throws ParseException {
        switch (errorCorrection.length() == 1 ? errorCorrection.charAt(0) : ' ') {
            case 'L':
                return ErrorCorrectionLevel.L;
            case 'M':
                return ErrorCorrectionLevel.M;
            case 'H':
                return ErrorCorrectionLevel.H;
            case 'Q':
                return ErrorCorrectionLevel.Q;
            default:
                throw new ParseException("errorCorrection must be 1 of L,M,H,Q");
        }
    }

    private static BitMatrix getBitMatrix(final int width, final int height, final String input,
                                          final Map<EncodeHintType, ?> hints) {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            return qrCodeWriter.encode(input, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (final WriterException e) {
            System.out.println("Could not generate QR Code: " + e.getMessage());
            System.exit(2);
            return null;
        }
    }

    private static void saveImage(final BitMatrix bitMatrix, final String outputFilePath) {
        try {
            final Path path = FileSystems.getDefault().getPath(outputFilePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (final IOException e) {
            System.out.println("Could not write QR Code file: " + e.getMessage());
            System.exit(3);
        }
    }

    private static Options getOptions() {
        final Options options = new Options();
        options.addOption(getInputOption());
        options.addOption(getOutputOption());
        options.addOption(getSizeOption());
        options.addOption(getQualityOption());
        return options;
    }

    private static Option getInputOption() {
        return Option
                .builder("i")
                .longOpt("input")
                .desc("input text")
                .required()
                .numberOfArgs(1)
                .build();
    }

    private static Option getOutputOption() {
        return Option
                .builder("o")
                .longOpt("output")
                .desc("output png file")
                .required()
                .numberOfArgs(1)
                .build();
    }

    private static Option getSizeOption() {
        return Option
                .builder("s")
                .longOpt("size")
                .desc("width and height: w,h")
                .valueSeparator(',')
                .numberOfArgs(2)
                .build();
    }

    private static Option getQualityOption() {
        return Option
                .builder("ec")
                .longOpt("errorCorrection")
                .desc(String.format("Error correction, L,M,Q or H, default M:%n" +
                        "L = ~7%% correction%n" +
                        "M = ~15%% correction%n" +
                        "Q = ~25%% correction%n" +
                        "H = ~30%% correction"))
                .numberOfArgs(1)
                .build();
    }
}