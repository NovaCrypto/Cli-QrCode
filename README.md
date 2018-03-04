QR Code generator
==

Creates a QR code from the command line.

Usage
==

```
usage: -i input -o output.png [-ec L/M/Q/H] [-s w,h]
 -ec,--errorCorrection <arg>   Error correction, L,M,Q or H, default M:
                               L = ~7% correction
                               M = ~15% correction
                               Q = ~25% correction
                               H = ~30% correction
 -i,--input <arg>              input text
 -o,--output <arg>             output png file
 -s,--size <arg>               width and height: w,h
```

Example:

```
java -j QRCodeGen-1.0.jar -o outputImage.png -i "Text to encode"
```
