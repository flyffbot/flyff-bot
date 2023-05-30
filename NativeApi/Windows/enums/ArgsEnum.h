#include <string>
using namespace std;
#pragma once

class ArgsEnum {
  public:
    static const string WINDOW_SEARCH_KEY;
    static const string SELECTED_HWND_ID;
    static const string KEYSTROKE_ID;
};

const string ArgsEnum::WINDOW_SEARCH_KEY = "window-search-key";
const string ArgsEnum::SELECTED_HWND_ID = "selected-hwnd-id";
const string ArgsEnum::KEYSTROKE_ID = "keystroke-id";
