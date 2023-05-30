#include <string>
using namespace std;
#pragma once

class ApiEnum {
  public:
    static const string VERSION;
    static const string API;
    static const string GET_WINDOW_LIST;
    static const string SEND_KEY_UP;
    static const string SEND_KEY_DOWN;
};

const string ApiEnum::VERSION = "version";
const string ApiEnum::API = "api";
const string ApiEnum::GET_WINDOW_LIST = "get-window-list";
const string ApiEnum::SEND_KEY_UP = "send-key-up";
const string ApiEnum::SEND_KEY_DOWN = "send-key-down";
