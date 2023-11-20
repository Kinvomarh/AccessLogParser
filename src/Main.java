import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
            while (true) {
                int countLine;
                int countYandexBot = 0;
                int countGoogleBot = 0;
                System.out.println("Введите путь к файлу:");
                String path =new Scanner(System.in).nextLine(); //считываем путь к файлу
                FileParsing fileParsing = FileParsing.fileParsing(path); // Создаем объект парсинга файла
                if (fileParsing != null) { // проверяем что объект создан
                    countLine = fileParsing.getCountLine(); // сохраняем количество строк в файле
                    Statistics statistic = new Statistics(); // создаем объект статистики
                    for (int i = 0; i < countLine; i++) { // пробегаемся по строкам
                        if (!fileParsing.getListLine().get(i).equals("")) {
                            statistic.addEntry(fileParsing.getListLine().get(i));
                            if (fileParsing.getListLine().get(i).getUserAgent().getBot() != null && fileParsing.getListLine().get(i).getUserAgent().getBot().equals("YandexBot"))
                                countYandexBot++; // Подсчитываем количество YandexBot
                            if (fileParsing.getListLine().get(i).getUserAgent().getBot() != null && fileParsing.getListLine().get(i).getUserAgent().getBot().equals("Googlebot"))
                                countGoogleBot++; // Подсчитываем количество Googlebot

                            }
                        }




                    System.out.println("Общее количество строк в файле: " + countLine);
                    System.out.println("Доля YandexBot: " + (double) countYandexBot / countLine * 100 + "%");
                    System.out.println("Доля GoogleBot: " + (double) countGoogleBot / countLine * 100 + "%");
                    System.out.println(statistic.minTime);
                    System.out.println(statistic.maxTime);
                    System.out.println(statistic.totalTraffic);
                    System.out.println(statistic.getTrafficRate());

                    HashMap<String, Double> systemsMap = statistic.getOperSystemStat();
                    for (Map.Entry<String, Double> entry: systemsMap.entrySet()){
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        System.out.println("Система: " + key + "; доля: " + value);
                    }


                    ArrayList<String> pages = statistic.getPages();
                    for(String page: pages){
                        System.out.println(page);
                    }

                }
            }
        }
}
