//
// Created by ygf on 2020/7/9.
//

#ifndef MY_FIRST_APPLICATION_NEON_H
#define MY_FIRST_APPLICATION_NEON_H

#include <jni.h>

void I4202RGB24_NEON(jbyte *i420, uint32_t width, uint32_t height, jint *rgb24);

#endif //MY_FIRST_APPLICATION_NEON_H
