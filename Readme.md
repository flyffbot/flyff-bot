# Flyff Bot

This is a simple Flyff bot that allows to automate key pressing.
It allows to play with up to 4 different Flyff windows, scheduling periodic actions on each of them, without impacting the others.

Application has been tested on x32 and x64 Chrome and Mozilla Firefox

- Chrome
  Requires window to be active to process key events. It means if you minimize it, actions sent by bot to browser will be ignored by Chrome browser.
- Firefox: works 100% in background

## Features

Flyff Bot offers two main features:

- Hotkeys Loop
- Custom Action Slot (CAS)

For details read features details reported below

### Feature: Applicaiton Hotkeys

To interact with a new Flyff Window you have to add a Pipeline.
To do so you have to use shortcuts repoted below:

- Add pipeline: {Alt + A}
  It allows to process another Flyff window simultaneously
- Remove pipeline: {Alt + D}
  Remove last inserted pipelines stopping all scheduled actions

### Feature: Hotkeys Loop

Configure auto key press of the following key combination.
This feature can be paused for a specific pipeline via additional preconfigured hotkey.
To pause/resume this feature in pipeline 1 hit {Shift + 1}
To pause/resume this feature in pipeline 2 hit {Shift + 3}
To pause/resume this feature in pipeline 3 hit {Shift + 5}
To pause/resume this feature in pipeline 4 hit {Shift + 7}

Allowed keys combinations:

- Ctrl + {0 - 9 key}
- Alt + {0 - 9 key}
- {0 - 9 key}

### Feature: Custom Action Slot (CAS)

Configure a list of key (and cast time) of skills to execute sequentially.
This feature can be paused for a specific pipeline via additional preconfigured hotkey.
To pause/resume this feature in pipeline 1 hit {Shift + 2}
To pause/resume this feature in pipeline 2 hit {Shift + 4}
To pause/resume this feature in pipeline 3 hit {Shift + 6}
To pause/resume this feature in pipeline 4 hit {Shift + 8}

Allowed keys combinations:

- Ctrl + {0 - 9 key}
- Alt + {0 - 9 key}
- {0 - 9 key}

### Feature: Auto save configuration

Bot configuration is stored locally. That means you have to configure bot behaviour only the first time.
If you close and re-open the application you will be ready to go!

### Planned features

- Auto-refresh follow
- Auto-detect Captcha (only click on box)
- Randimize delays between actions to avoid RECAPTCHA popup

## Develop / Build / Release

Want to customize, build and release your own version? Follow the wiki reported below.

### Build Native API for Windows (command line)

- Install GCC MinGW64
- Run build.bat inside NativeAPI/Windows folder

### Build & Run/Debug Native API for Windows (VS Code guide)

- Install GCC MinGW64
- Create launch.json file inside .vscode folder
- Pase following configuration:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "name": "g++.exe build and debug active file",
      "type": "cppdbg",
      "request": "launch",
      "program": "${fileDirname}\\${fileBasenameNoExtension}.exe",
      "args": ["api=get-window-list", "window-search-key='Flyff'"],
      //"args": ["api=send-key-down", "selected-hwnd-id=0x61060a", "keystroke-id=0x31", "keystroke-id=0x32"],
      "stopAtEntry": false,
      "cwd": "${workspaceFolder}",
      "environment": [],
      "externalConsole": false, //set to true to see output in cmd instead
      "MIMode": "gdb",
      "miDebuggerPath": "PATH_TO_DEBUGGER_EXE", //E.g. "C:\\MinGW64\\bin\\gdb.exe",
      "setupCommands": [
        {
          "description": "Enable pretty-printing for gdb",
          "text": "-enable-pretty-printing",
          "ignoreFailures": true
        }
      ],
      "preLaunchTask": "g++.exe build active file"
    },
    {
      "name": "g++ build & run active file",
      "type": "cppdbg",
      "request": "launch",
      "program": "${fileDirname}\\${fileBasenameNoExtension}.exe",
      "args": [],
      "stopAtEntry": false,
      "cwd": "${workspaceFolder}",
      "environment": [],
      "externalConsole": false, //set to true to see output in cmd instead
      "MIMode": "gdb",
      "miDebuggerPath": "PATH_TO_DEBUGGER_EXE", //E.g. "C:\\MinGW64\\bin\\gdb.exe",
      "setupCommands": [
        {
          "description": "Enable pretty-printing for gdb",
          "text": "-enable-pretty-printing",
          "ignoreFailures": true
        }
      ],
      "preLaunchTask": "g++ build & run active file"
    }
  ]
}
```

- Edit json field 'miDebuggerPath' in each section (run, debug) with the path of mingw debugger exe file
- Build & Run main.cpp

At this point you should have a file named 'main.exe' in the same directory of main.cpp.
This file is the one used by the java client to send "fetch-windows-list" and "send-key-stroke" commands to Flyff window

### Build modules

From root folder execute `cmd> build.bat`
If you want to skip single module build add following args to the previous command:

- Skip ReactJs module: `--skip-fe`
- Skip Spring module: `--skip-full-app`
- Skip Launcher module: `--skip-launcher`
- skip Installer module: `--skip-installer`
