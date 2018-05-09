# CLion CUDA Run Patcher

> 
This plugin provides a simple fix for [CPP-10292](https://youtrack.jetbrains.com/issue/CPP-10292) until JetBrains fixes CLion.

When a `CMakeFile.txt` specifies CUDA as a language with `enable_language(CUDA)` or `project(foo CUDA CXX)`, CLion tries to run an intermediate object file `cmake_device_link.o` instead of the correct target executable.

This plugin changes incorrect executable path  
`path/to/cmake-build-[configuration]/CMakeFiles/[project].dir/cmake_device_link.o`  
to  
`path/to/cmake-build-[configuration]/[target]`  
on run/debug.

CMake `OUTPUT_NAME` target property is correctly supported by this plugin as of v1.1.
