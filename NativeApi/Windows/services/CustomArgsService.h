#include <string>
#include <vector>
#include <codecvt>
#include <locale>
#include "../dto/CmdParam.h"
#include "../exceptions/InvalidArgumentException.h"
#include "../exceptions/ParamNotFoundException.h"
using namespace std;

#pragma once

class CustomArgsService {
  public:
    static vector<CmdParam> toCmdParamList(int argc, char *argv[]){
      vector<string> rawParamList;
      vector<CmdParam> paramList;
      if (argc > 1) {
        rawParamList.assign(argv + 1, argv + argc);
      }

      for (int i = 0; i < rawParamList.size(); i++){
        CmdParam param = toCmdParam(rawParamList[i]);
        paramList.push_back(param);
      }

      return paramList;
    }

    static CmdParam findParam(vector<CmdParam> params, string paramName){
      for(int i = 0; i < params.size(); i++){
        if(params[i].name.compare(paramName) == 0){
          return params[i];
        }
      }
      throw ParamNotFoundException("Param "+paramName+" not found");
    }

    static vector<CmdParam> findAllParams(vector<CmdParam> params, string paramName){
      vector<CmdParam> list;
      for(int i = 0; i < params.size(); i++){
        if(params[i].name.compare(paramName) == 0){
          list.push_back(params[i]);
        }
      }
      if(list.size() > 0){
        return list;
      }
      throw ParamNotFoundException("Param "+paramName+" not found");
    }

   private:
    static vector<string> splitString(string s, char delimiter) {
      vector<string> pcs;
      string temp = "";
      for(int i=0;i<s.length();++i){

        if(s[i]==delimiter){
          pcs.push_back(temp);
          temp = "";
        }
        else{
          temp.push_back(s[i]);
        }

      }
      pcs.push_back(temp);
      return pcs;
    }

    static CmdParam toCmdParam(string param){
      vector<string> pcs = splitString(param, '=');
      if(pcs.size() != 2){
        throw InvalidArgumentException("Unknow parameter: "+param);
      }
      int start = pcs[1].at(0) == '\'' ? 1 : 0;
      int end = pcs[1].at(pcs[1].size() - 1) == '\'' ? pcs[1].size() - 2 : pcs[1].size();
      string name = pcs[0];
      string value = pcs[1].substr(start, end);
      return CmdParam(name, value);
    }
};
