//
// Created by ygf on 2020/7/7.
//

#include "i4202rgb24.h"
#include "c.h"
#include "neon.h"
#include "asm.h"
#include <jni.h>
#include <stdbool.h>
#include <sys/auxv.h>

JNIEXPORT void JNICALL
Java_com_example_myfirstapplication_MainActivity_Native_1I4202RGB24_1C(JNIEnv *env, jobject thiz,
                                                                       jbyteArray in, jint width, jint height,
                                                                       jintArray out) {
    jbyte *i420 = (*env)->GetByteArrayElements(env, in, false);
    jint *rgb24 = (*env)->GetIntArrayElements(env, out, false);

    I4202RGB24_C(i420, width, height, rgb24);

    (*env)->ReleaseByteArrayElements(env, in, i420, 0);
    (*env)->ReleaseIntArrayElements(env, out, rgb24, 0);
}

JNIEXPORT void JNICALL
Java_com_example_myfirstapplication_MainActivity_Native_1I4202RGB24_1NEON(JNIEnv *env, jobject thiz,
                                                                          jbyteArray in, jint width, jint height,
                                                                          jintArray out) {
    jbyte *i420 = (*env)->GetByteArrayElements(env, in, false);
    jint *rgb24 = (*env)->GetIntArrayElements(env, out, false);

    I4202RGB24_NEON(i420, width, height, rgb24);

    (*env)->ReleaseByteArrayElements(env, in, i420, 0);
    (*env)->ReleaseIntArrayElements(env, out, rgb24, 0);
}

JNIEXPORT void JNICALL
Java_com_example_myfirstapplication_MainActivity_Native_1I4202RGB24_1ASM(JNIEnv *env, jobject thiz,
                                                                         jbyteArray in, jint width, jint height,
                                                                         jintArray out) {
    // TODO: implement Native_I4202RGB24_ASM()
}