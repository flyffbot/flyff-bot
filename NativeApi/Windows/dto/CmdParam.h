#include <string>
using namespace std;
#pragma once
class CmdParam {
  public:
    string name;
    string value;
    BOOL isNull;
    CmdParam(string _name, string _value) {
      name = _name;
      value = _value;
      isNull = false;
    }
    CmdParam() {
      isNull = true;
    }
};
