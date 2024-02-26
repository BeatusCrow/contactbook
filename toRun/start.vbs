Set WshShell = WScript.CreateObject("WScript.Shell")
CommandStr = "java\bin\java.exe -jar ContactBook.jar"
WshShell.Run CommandStr, 1, True