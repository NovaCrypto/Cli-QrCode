package io.github.novacrypto.cli.qrcode.options

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

internal val inputOption = Option
        .builder("i")
        .longOpt("input")
        .desc("input text")
        .required()
        .numberOfArgs(1)
        .build()

internal val CommandLine.inputText: String
    get() = getOptionValue("input")
