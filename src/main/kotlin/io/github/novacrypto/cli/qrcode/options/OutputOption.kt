package io.github.novacrypto.cli.qrcode.options

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

internal val outputOption = Option
        .builder("o")
        .longOpt("output")
        .desc("output png file")
        .required()
        .numberOfArgs(1)
        .build()

internal val CommandLine.outputFilePath: String
    get() = getOptionValue("output")
