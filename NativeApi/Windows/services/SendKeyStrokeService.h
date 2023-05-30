#include <windows.h>

#pragma once
// KeyCode list:
// https://docs.microsoft.com/en-us/windows/win32/inputdev/virtual-key-codes

void sendKeyDown(HWND hwnd, int keyCode){
  PostMessage(hwnd, WM_KEYDOWN, keyCode, NULL);
}

void sendKeyUp(HWND hwnd, int keyCode){
  PostMessage(hwnd, WM_KEYUP, keyCode, NULL);
}
