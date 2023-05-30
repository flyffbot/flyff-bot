#include <windows.h>
#include <string>
#include <vector>
#include <iostream>
#include <random>

#include "dto/CustomHwnd.h"
#include "dto/CmdParam.h"
#include "services/WindowListService.h"
#include "services/SendKeyStrokeService.h"
#include "services/CustomArgsService.h"
#include "enums/ApiEnum.h"
#include "enums/ArgsEnum.h"
#include "exceptions/InvalidArgumentException.h"
#include "exceptions/ParamNotFoundException.h"
#include "exceptions/HwndNotFoundException.h"
using namespace std;

string getWindowListApi(vector<CmdParam> params){
  CmdParam searchText = CustomArgsService::findParam(params, ArgsEnum::WINDOW_SEARCH_KEY);
  vector<CustomHwnd> windowList = WindowListService::findWindowByName(searchText.value);

  string out = "[";
  for(int i = 0; i < windowList.size(); i++) {
    out += "\n"+windowList[i].toJson();
    if(i < windowList.size() - 1){
      out += ",";
    }
  }
  out += "\n]";
  return out;
}


string sendKeyDownApi(vector<CmdParam> params){
  CmdParam hwndId = CustomArgsService::findParam(params, ArgsEnum::SELECTED_HWND_ID);
  vector<CmdParam> keystrokes = CustomArgsService::findAllParams(params, ArgsEnum::KEYSTROKE_ID);
  CustomHwnd customHwnd = WindowListService::findWindowById(hwndId.value);

  // Random seed
  random_device rd;
  // Initialize Mersenne Twister pseudo-random number generator
  mt19937 gen(rd());
  // Generate pseudo-random numbers uniformly distributed in range (150, 200)
  uniform_int_distribution<> dis(150, 200);

  string delays = "[";

  for(int i = 0; i < keystrokes.size(); i++){
    int delay = dis(gen);
    Sleep(delay);

    if(i < keystrokes.size() - 1){
      delays += to_string(delay) + ", ";
    }else{
      delays += to_string(delay);
    }

    CmdParam param = keystrokes[i];
    int keycode;
    stringstream ss;
    ss << hex << param.value;
    ss >> keycode;
    sendKeyDown(customHwnd.hwnd, keycode);
  }

  delays += "]";
  return delays;
}

string sendKeyUpApi(vector<CmdParam> params){
  CmdParam hwndId = CustomArgsService::findParam(params, ArgsEnum::SELECTED_HWND_ID);
  vector<CmdParam> keystrokes = CustomArgsService::findAllParams(params, ArgsEnum::KEYSTROKE_ID);
  CustomHwnd customHwnd = WindowListService::findWindowById(hwndId.value);

  // Random seed
  random_device rd;
  // Initialize Mersenne Twister pseudo-random number generator
  mt19937 gen(rd());
  // Generate pseudo-random numbers uniformly distributed in range (150, 200)
  uniform_int_distribution<> dis(150, 200);

  string delays = "[";

  for(int i = 0; i < keystrokes.size(); i++){
    int delay = dis(gen);
    Sleep(delay);

    if(i < keystrokes.size() - 1){
      delays += to_string(delay) + ", ";
    }else{
      delays += to_string(delay);
    }

    CmdParam param = keystrokes[i];
    int keycode;
    stringstream ss;
    ss << hex << param.value;
    ss >> keycode;
    sendKeyUp(customHwnd.hwnd, keycode);
  }

  delays += "]";
  return delays;
}


int main(int argc, char *argv[]){
  string version = "0.0.1";
  string out = "";
  try{
    vector<CmdParam> params = CustomArgsService::toCmdParamList(argc, argv);
    CmdParam apiParam = CustomArgsService::findParam(params, ApiEnum::API);
    if (apiParam.isNull){
      out = "{\"success\": false, \"error\": \"Param 'api' not found.\"}";
    }else if(apiParam.value.compare(ApiEnum::VERSION) == 0){
      out = "{\"success\": true, \"data\": \"v"+version+"\"}";
    }else if(apiParam.value.compare(ApiEnum::GET_WINDOW_LIST) == 0){
      string data = getWindowListApi(params);
      out = "{\"success\": true, \"data\": "+data+"}";
    }else if(apiParam.value.compare(ApiEnum::SEND_KEY_DOWN) == 0){
      string data = sendKeyDownApi(params);
      out = "{\"success\": true, \"data\": "+data+"}";
    }else if(apiParam.value.compare(ApiEnum::SEND_KEY_UP) == 0){
      string data = sendKeyUpApi(params);
      out = "{\"success\": true, \"data\": "+data+"}";
    }else{
      out = "{\"success\": false, \"error\": \"Unknown 'api' param: "+apiParam.value+".\"}";
    }
  }catch(InvalidArgumentException e){
    out = "{\"success\": false, \"error\": \""+e.what()+"\"}";
  }catch(ParamNotFoundException e){
    out = "{\"success\": false, \"error\": \""+e.what()+"\"}";
  }catch(HwndNotFoundException e){
    out = "{\"success\": false, \"error\": \""+e.what()+"\"}";
  }
  cout << out << endl;
  return 0;
}
