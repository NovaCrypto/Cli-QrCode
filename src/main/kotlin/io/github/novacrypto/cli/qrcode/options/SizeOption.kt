package io.github.novacrypto.cli.qrcode.options

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

private const val defaultWidth = 300
private const val defaultHeight = 300

internal val sizeOption = Option
        .builder("s")
        .longOpt("size")
        .desc("width and height: w,h, default $defaultWidth,$defaultHeight")
        .valueSeparator(',')
        .numberOfArgs(2)
        .build()

internal val CommandLine.size
    get() =
        getOptionValues("size").let { size ->
            if (size == null) {
                defaultWidth to defaultHeight
            } else {
                size[0].toInt() to size[1].toInt()
            }
        }
