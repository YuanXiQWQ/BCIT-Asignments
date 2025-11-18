# 最低 CMake 版本
cmake_minimum_required(VERSION 3.28)
# 项目名称
project(example)

# 必须用 17 以上
set(CMAKE_CXX_STANDARD 17)
# 如果没找到 17 以上，不能自动降级
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# TensorFlow 路径（WSL）
set(TENSORFLOW_INCLUDE_DIR /usr/local/include)
set(TENSORFLOW_LIBRARY /usr/local/lib/libtensorflow.so)

# 找到系统里的 OpenCV 库
find_package(OpenCV REQUIRED)

include_directories(.)

add_executable(example
        facedetectcnn-data.cpp
        facedetectcnn-model.cpp
        facedetectcnn.cpp
        facedetectcnn.h
        main.cpp
        facedetection_export.h)

target_include_directories(example PRIVATE
        ${TENSORFLOW_INCLUDE_DIR}
        ${OpenCV_INCLUDE_DIRS})

target_link_libraries(example PRIVATE
        ${TENSORFLOW_LIBRARY}
        ${OpenCV_LIBS})