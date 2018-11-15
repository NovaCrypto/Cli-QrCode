package io.github.novacrypto.cli.qrcode.options

import org.apache.commons.cli.Options

internal fun buildOptions() = Options()
        .addOption(inputOption)
        .addOption(outputOption)
        .addOption(sizeOption)
        .addOption(qualityOption)
        .addOption(transparentOption)
