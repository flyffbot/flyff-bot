#include <windows.h>
#include <sstream>
#include <string>
using namespace std;

#pragma once
class CustomHwnd {
  public:
    string title;
    HWND hwnd;
    BOOL isNull;
    CustomHwnd(string _title, HWND _hwnd) {
      title = _title;
      hwnd = _hwnd;
      isNull = false;
    }
    CustomHwnd() {
      isNull = true;
    }
    string toJson(){
      string id = getId();
      return "{\"title\": \""+title+"\", \"hwnd\":\""+id+"\", \"isNull\": "+(isNull ? "true" : "false")+"}";
    }
    string getId(){
      stringstream stringstreamBuffer;
      stringstreamBuffer << hwnd;
      return stringstreamBuffer.str();
    }
};
