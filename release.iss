[Setup]
AppName=FlyffBot
AppVersion=5.0
DefaultDirName={pf}\FlyffBot
OutputDir=.\

[Files]
Source: "release\data\*"; DestDir: "{app}\data"; Flags: recursesubdirs
Source: "release\FlyffBot.exe"; DestDir: "{app}"

[Icons]
Name: "{commondesktop}\FlyffBot"; Filename: "{app}\FlyffBot.exe"; WorkingDir: "{app}"

[Code]
function InitializeSetup(): Boolean;
begin
  Result := True;
end;

function NextButtonClick(CurPageID: Integer): Boolean;
begin
  Result := True;
end;

function ShouldSkipPage(PageID: Integer): Boolean;
begin
  Result := False;
end;
