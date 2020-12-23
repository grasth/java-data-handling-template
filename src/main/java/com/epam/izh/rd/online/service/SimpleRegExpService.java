package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
         * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        StringBuilder filecontent;

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\" + "sensitive_data.txt")))
        {
            filecontent = new StringBuilder(reader.readLine());
        }
        catch (IOException e) {
            return e.getMessage();
        }

        Pattern pattern = Pattern.compile("\\d{4}\\s(\\d{4}?)\\s(\\d{4}?)\\s\\d{4}");
        Matcher match = pattern.matcher(filecontent);

        String str = filecontent.toString();
        String hide = "****";

        while (match.find()){
           str = str.replace(match.group(1), hide).replace(match.group(2), hide);
        }

       return str;


}

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        StringBuilder filecontent;

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\" + "sensitive_data.txt")))
        {
            filecontent = new StringBuilder(reader.readLine());
        }
        catch (IOException e) {
            return e.getMessage();
        }

        Pattern pattern = Pattern.compile("(\\$\\{.*?})");
        Matcher match = pattern.matcher(filecontent);

        String str = filecontent.toString();

        while (match.find()){
            if(match.group(1).contains("payment_amount"))
                str = str.replace(match.group(1), String.valueOf((int)paymentAmount));

            else if (match.group(1).contains("balance"))
                str = str.replace(match.group(1), String.valueOf((int)balance));
         }
        return str;
    }
}
