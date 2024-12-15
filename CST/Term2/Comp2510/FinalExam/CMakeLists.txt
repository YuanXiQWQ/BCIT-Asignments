cmake_minimum_required(VERSION 3.30)
project(FinalExam C)

set(CMAKE_C_STANDARD 23)

add_library(FinalExam STATIC main.c
        array_utils.h
        array_utils.c
        q3.c)
