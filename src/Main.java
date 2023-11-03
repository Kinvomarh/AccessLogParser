import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            while (true) {
                int countLine;
                int countYandexBot = 0;
                int countGoogleBot = 0;
                System.out.println("Введите путь к файлу:");
                String path = "D:\\access.log";//new Scanner(System.in).nextLine(); //считываем путь к файлу
                FileParsing fileParsing = FileParsing.fileParsing(path); // Создаем объект парсинга файла
                if (fileParsing != null) { // проверяем что объект создан
                    countLine = fileParsing.getCountLine(); // сохраняем количество строк в файле
                    for (int i = 0; i < countLine; i++) { // пробегаемся по строкам
                          if(!fileParsing.getListLine().get(i).equals("")) {
                            LineParsing lineParsing = new LineParsing(fileParsing.getListLine().get(i));//на каждую создаем объект парсинга строки
                            UserAgent userAgent = new UserAgent(lineParsing.getUserAgent()); //Создаем Обект парсинга User-Agent
                                if (userAgent.getBot() != null && userAgent.getBot().equals("YandexBot")) countYandexBot++; // Подсчитываем количество YandexBot
                                if (userAgent.getBot() != null && userAgent.getBot().equals("Googlebot")) countGoogleBot++; // Подсчитываем количество Googlebot
                        }


                    }
                    System.out.println("общее количество строк в файле: " + countLine);
                    System.out.println("доля YandexBot: " + (double) countYandexBot / countLine * 100 + "%");
                    System.out.println("доля GoogleBot: " + (double) countGoogleBot / countLine * 100 + "%");
                }
            }








        }
}
