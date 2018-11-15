package io.github.novacrypto.cli.qrcode.options

import com.google.zxing.client.j2se.MatrixToImageConfig
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

internal val transparentOption = Option
        .builder("t")
        .longOpt("transparent")
        .desc("transparent back colour")
        .build()

internal val CommandLine.backgroundColor: Int
    get() = if (hasOption("t")) 0x00000000 else MatrixToImageConfig.WHITE
