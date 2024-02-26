package sample;

import java.io.*;

public class Translate {

    public static String translate(String text, String language) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            String script = "\nprint(Translate('" + text.strip() + "', '" + language.strip() + "'))";
            BufferedWriter bufferedWriter= new BufferedWriter(
                    new FileWriter("TranslateScript.py", true));
            bufferedWriter.write(script);
            bufferedWriter.close();

            Process process = Runtime.getRuntime().exec("python TranslateScript.py");
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            process.getInputStream()
                    ));
        }
        catch(Exception ignored){
        }

        String out_line = bufferedReader.readLine();

        try {
            String script = "def Translate(str, lang):\n" +
                            "    if lang == 'en':\n" +
                            "        with open('en.ftl') as f:\n" +
                            "            s = f.readlines()\n" +
                            "    elif lang == 'ru':\n" +
                            "        with open('ru.ftl') as f:\n" +
                            "            s = f.readlines()\n" +
                            "    for i in range(len(s)):\n" +
                            "        mass_str = s[i].split('=')\n" +
                            "        for j in range(len(mass_str)):\n" +
                            "            mass_str[j] = mass_str[j].strip()\n" +
                            "        if mass_str[0] == str:\n" +
                            "            return mass_str[1]";
            BufferedWriter bufferedWriter= new BufferedWriter(
                    new FileWriter("TranslateScript.py"));
            bufferedWriter.write(script);
            bufferedWriter.close();
        }
        catch(Exception ignored){
        }

        return out_line;
    }
}
