# CLion CUDA Run Patcher

This plugin provides a simple fix for [CPP-10292](https://youtrack.jetbrains.com/issue/CPP-10292) until JetBrains fixes CLion.

When a `CMakeFile.txt` specifies CUDA as a language with `enable_language(CUDA)` or `project(foo CUDA CXX)`, CLion tries to run an intermediate object file `cmake_device_link.o` instead of the correct target executable.

This plugin changes incorrect executable path  
`path/to/cmake-build-[configuration]/CMakeFiles/[project].dir/cmake_device_link.o`  
to  
`path/to/cmake-build-[configuration]/[target]`  
on run/debug.

CMake `OUTPUT_NAME` target property is correctly supported by this plugin as of v1.1.  
Boost Test (Boost.Test), Catch Test (Catch), and Google Test run configurations supported as of v1.2.0.

### Install

To install within CLion using the JetBrains plugin repository:
1. go to `File`->`Settings` menu
1. click `Plugins` on the left
1. click `Marketplace` on the top-right
1. click in the `Search plugins in marketplace` field at the top-right
1. type `CUDA` in the search field
1. select `CLion CUDA Run Patcher`
1. click `Install` or `Update`
1. click `Restart IDE` button
1. click `Restart` when prompted

###Troubleshooting

If you still get an error that CLion cannot run `cmake_device_link.o`, close the project and open it again.
