cmake_minimum_required(VERSION 3.29)
project(code C)

set(CMAKE_C_STANDARD 23)

add_executable(code a2.c
        student.h
        student.c)
