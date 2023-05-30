#include <cstring>
#include <iostream>
using namespace std;
#pragma once

class InvalidArgumentException : public exception {
  public:
    string message;
    InvalidArgumentException(string _message){
      message = (char*) _message.c_str();
    }
    string what () {
      return message;
    }
};

