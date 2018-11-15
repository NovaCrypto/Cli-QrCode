[![Build Status](https://travis-ci.org/NovaCrypto/Cli-QrCode.svg?branch=master)](https://travis-ci.org/NovaCrypto/Cli-QrCode)

QR Code generator
==

Creates a QR code from the command line.

Usage
==

```
usage: -i input -o output.png [-ec L/M/Q/H] [-s w,h] [-t]
 -ec,--errorCorrection <arg>   Error correction, L,M,Q or H, default M:
                               L = ~7% correction
                               M = ~15% correction
                               Q = ~25% correction
                               H = ~30% correction
 -i,--input <arg>              input text
 -o,--output <arg>             output png file
 -s,--size <arg>               width and height: w,h, default 300,300
 -t,--transparent              transparent back colour
```

Example:

```
java -jar QRCodeGen-1.03.jar -i "Text to encode" -o outputImage.png
```
