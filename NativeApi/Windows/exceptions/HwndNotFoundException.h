#include <cstring>
#include <iostream>
using namespace std;
#pragma once

class HwndNotFoundException : public exception {
  public:
    string message;
    HwndNotFoundException(string _message){
      message = (char*) _message.c_str();
    }
    string what () {
      return message;
    }
};

