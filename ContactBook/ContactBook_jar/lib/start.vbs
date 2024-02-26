Set WshShell = WScript.CreateObject("WScript.Shell")
CommandStr = "java -jar ContactBook.jar"
WshShell.Run CommandStr, 0