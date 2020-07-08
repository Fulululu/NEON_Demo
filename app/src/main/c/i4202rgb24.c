//
// Created by ygf on 2020/7/7.
//

#include "i4202rgb24.h"
#include <jni.h>

JNIEXPORT void JNICALL
Java_com_example_myfirstapplication_MainActivity_Native_1I4202RGB24_1C(JNIEnv *env, jobject thiz,
                                                                       jbyteArray in,
                                                                       jintArray out) {
    // TODO: implement Native_I4202RGB24_C()
}

JNIEXPORT void JNICALL
Java_com_example_myfirstapplication_MainActivity_Native_1I4202RGB24_1NEON(JNIEnv *env, jobject thiz,
                                                                          jbyteArray in,
                                                                          jintArray out) {
    // TODO: implement Native_I4202RGB24_NEON()
}

JNIEXPORT void JNICALL
Java_com_example_myfirstapplication_MainActivity_Native_1I4202RGB24_1ASM(JNIEnv *env, jobject thiz,
                                                                         jbyteArray in,
                                                                         jintArray out) {
    // TODO: implement Native_I4202RGB24_ASM()
}