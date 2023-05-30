#include <windows.h>
#include <assert.h>
#include <tchar.h>
#include <string>
#include <vector>
#include <iostream>
#include <locale>
#include <codecvt>
#include "../dto/CustomHwnd.h"
#include "../exceptions/HwndNotFoundException.h"

#pragma once

string ws2s(const wstring& wstr);
wstring s2ws(const string& str);
BOOL CALLBACK speichereFenster(HWND hwnd, LPARAM lParam);
vector<CustomHwnd> getWindowList();

class WindowListService {
  public:
    static vector<CustomHwnd> findWindowByName(string text){
      vector<CustomHwnd> list = getWindowList();
      vector<CustomHwnd> subList;
      for ( const auto& item : list ) {
        if(item.title.find(text) != string::npos){
          subList.push_back(item);
        }
      }
      return subList;
    }
    static CustomHwnd findWindowById(string hwndId){
      vector<CustomHwnd> list = getWindowList();
      for (int i = 0; i < list.size(); i++) {
        CustomHwnd item = list[i];
        if(item.getId().compare(hwndId) == 0){
          return item;
        }
      }
      throw HwndNotFoundException("HWND with id: "+hwndId+" not found");
    }
};

string ws2s(const wstring& wstr) {
  using convert_typeX = codecvt_utf8<wchar_t>;
  wstring_convert<convert_typeX, wchar_t> converterX;
  return converterX.to_bytes(wstr);
}

wstring s2ws(const string& str){
  using convert_typeX = codecvt_utf8<wchar_t>;
  wstring_convert<convert_typeX, wchar_t> converterX;
  return converterX.from_bytes(str);
}

BOOL CALLBACK speichereFenster(HWND hwnd, LPARAM lParam){
  const DWORD TITLE_SIZE = 1024;
  WCHAR windowTitle[TITLE_SIZE];

  GetWindowTextW(hwnd, windowTitle, TITLE_SIZE);

  int length = ::GetWindowTextLength(hwnd);

  wstring title(&windowTitle[0]);
  if (!IsWindowVisible(hwnd) || length == 0 || title == L"Program Manager") {
    return TRUE;
  }

  // Retrieve the pointer passed into this callback, and re-'type' it.
  // The only way for a C API to pass arbitrary data is by means of a void*.
  vector<CustomHwnd>& titles = *reinterpret_cast<vector<CustomHwnd>*>(lParam);
  CustomHwnd item(ws2s(title), hwnd);
  titles.push_back(item);

  return TRUE;
}

vector<CustomHwnd> getWindowList(){
  vector<CustomHwnd> windowList;
  EnumWindows(speichereFenster, reinterpret_cast<LPARAM>(&windowList));
  return windowList;
}



