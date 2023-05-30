windres launcher.rc -O coff -o launcher.res
gcc -o FlyffBot.exe launcher.cpp launcher.res -lstdc++ -static -static-libgcc -static-libstdc++