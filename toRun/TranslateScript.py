def Translate(str, lang):
    if lang == 'en':
        with open('en.ftl') as f:
            s = f.readlines()
    elif lang == 'ru':
        with open('ru.ftl') as f:
            s = f.readlines()
    for i in range(len(s)):
        mass_str = s[i].split('=')
        for j in range(len(mass_str)):
            mass_str[j] = mass_str[j].strip()
        if mass_str[0] == str:
            return mass_str[1]