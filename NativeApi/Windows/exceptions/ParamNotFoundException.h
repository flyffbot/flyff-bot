#include <cstring>
#include <iostream>
using namespace std;
#pragma once

class ParamNotFoundException : public exception {
  public:
    string message;
    ParamNotFoundException(string _message){
      message = (char*) _message.c_str();
    }
    string what () {
      return message;
    }
};

