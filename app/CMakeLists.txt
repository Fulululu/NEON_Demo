cmake_minimum_required(VERSION 3.6.0)

file(GLOB SOURCE_FILES ${CMAKE_CURRENT_SOURCE_DIR}/src/main/c/*.c)

add_library(i4202rgb24 SHARED ${SOURCE_FILES})

if(ANDROID_ABI STREQUAL armeabi-v7a)
    set_source_files_properties(neon.c PROPERTIES COMPILE_FLAGS -mfpu=neon)
endif()