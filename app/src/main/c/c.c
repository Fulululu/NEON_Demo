//
// Created by ygf on 2020/7/9.
//

#include "c.h"
#include <string.h>

void I4202RGB24_C(jbyte *i420, uint32_t width, uint32_t height, jint *rgb24) {
    uint32_t pixelSize = width * height;

    // I420 to NV21
    uint32_t vcount = pixelSize >> 2;
    uint32_t yStartPos = 0;
    uint32_t uStartPos = pixelSize;
    uint32_t uEndPos = pixelSize + vcount - 1;
    uint32_t vStartPos = pixelSize + vcount;
    uint32_t vEndPos = pixelSize + vcount + vcount - 1;

    uint8_t tmp[vcount];
    memcpy(tmp,i420 + vStartPos, vcount);
    uint32_t tmpEndPos = vcount - 1;

    for (uint32_t i = 0; i < vcount; ++i) {
        i420[vEndPos - (i<<1)] = i420[uEndPos - i];
        i420[vEndPos - ((i<<1)+1)] = tmp[tmpEndPos - i];
    }

    // NV21 to RGB24
    int Y, Cb = 0, Cr = 0, index = 0;
    int R, G, B;

    for (uint32_t y = 0; y < height; y++) {
        for (uint32_t x = 0; x < width; x++) {
            Y = i420[y * width + x];
            if (Y < 0) Y += 255;

            if ((x & 1) == 0) {
                Cr = i420[(y >> 1) * (width) + x + pixelSize];
                Cb = i420[(y >> 1) * (width) + x + pixelSize + 1];

                if (Cb < 0) Cb += 127; else Cb -= 128;
                if (Cr < 0) Cr += 127; else Cr -= 128;
            }

            R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
            G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1) + (Cr >> 3) + (Cr >> 4) + (Cr >> 5);
            B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);

            if (R < 0) R = 0; else if (R > 255) R = 255;
            if (G < 0) G = 0; else if (G > 255) G = 255;
            if (B < 0) B = 0; else if (B > 255) B = 255;

            rgb24[index++] = 0xff000000 + (R << 16) + (G << 8) + B;
        }
    }
}