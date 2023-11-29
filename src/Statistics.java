import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Statistics {

    long  totalTraffic;
    long totalVisitUser;
    LocalDateTime minTime;
    LocalDateTime maxTime;
    int invalidRequest;
    HashSet<String> pages = new HashSet<>();
    HashSet<String> ipUsers = new HashSet<>();
    HashMap<String, Integer> systems = new HashMap<>();
    HashMap<String, Integer> browsers = new HashMap<>();




    public Statistics() {
        this.totalTraffic = 0;
        this.totalVisitUser = 0;
        this.invalidRequest =0;
    }

    //Метод расчета трафика по часам
    public long getTrafficRate(){
       if(allHours()!=0) {
           return totalTraffic / allHours();
       }
       else return totalTraffic;
    }
    //метод подсчета среднего посещения сайта с разбивкой по пользователям
    public long getVisitsRateOnHour(){
        if(allHours()!=0){
            return totalVisitUser / allHours();
        }
        else return totalVisitUser;
    }

    //Метод подсчета среднего количества ошибочных запросов за час
    public long getInvalidRequestRateOnHour(){
        if(allHours()!=0) {
            return invalidRequest / allHours();
        }
        else return invalidRequest;
    }
    //Метод расчёта средней посещаемости одним пользователем
    public long getVisitIndividualUserRate(){
        return totalVisitUser/ipUsers.size();
    }

    //Метод расчета общего трафика и выделения минимального и максимального времени
    public void addEntry(LogEntry logEntry) {
        LocalDateTime dateTime = logEntry.getDate();
        //Вычисление общего трафика

        totalTraffic += logEntry.getResponseSize();

        //Вычисление общего количества посещений пользователями (не ботами)
        //Заполнение Set уникальными IP пользователей (Не ботами)
        if(!logEntry.getUserAgent().isBot) {
            totalVisitUser += 1;
            ipUsers.add(logEntry.getIp());
        }

        // Задаем минимальную и максимальную даты
        if (minTime == null) minTime = dateTime;
        if (maxTime == null) maxTime = dateTime;

        if (maxTime.isBefore(dateTime)) {
            maxTime = dateTime;
        } else if (minTime.isAfter(dateTime)) {
            minTime = dateTime;
        }

        // заполняем коллекцию в которой хранятся все страницы сайта
        if (logEntry.getResponseCode() == 200) {
            pages.add(logEntry.getLastPage());
        }
        if (logEntry.getResponseCode() == 404) {
            pages.add(logEntry.getLastPage());
        }
        String responceCode = String.valueOf(logEntry.getResponseCode());
        if(responceCode.substring(0,1).equals("4") || responceCode.substring(0,1).equals("5")){
            this.invalidRequest+=1;
        }

        // заполняем MAP в которой хранится операционная система и количество вызовов с неё
        if (logEntry.getUserAgent().getSystem() != null && !logEntry.getUserAgent().getSystem().isEmpty()) {
            String sys = logEntry.getUserAgent().getSystem();
            if (systems.containsKey(sys)) {
                systems.put(sys, systems.get(sys) + 1);
            } else {
                systems.put(sys, 1);
            }
        }
        //Заполняем MAP в которой хранится браузер и количество вызовов с него
        if (logEntry.getUserAgent().getBrowser() != null && !logEntry.getUserAgent().getBrowser().isEmpty()) {
            String brow = logEntry.getUserAgent().getBrowser();
            if (browsers.containsKey(brow)) {
                browsers.put(brow, browsers.get(brow) + 1);
            } else {
                browsers.put(brow, 1);
            }

        }
    }


    public ArrayList<String> getPages(){
        return new ArrayList<>(pages);
    }


    //Метод для расчета доли Операционных систем
    public HashMap<String, Double> getOperSystemStat(){
        int allCount = 0;

        HashMap<String, Double> res = new HashMap<>();
        for (Map.Entry<String, Integer> entry: systems.entrySet()){
            allCount += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry: systems.entrySet()){
                        res.put(entry.getKey(), (entry.getValue()/(double)allCount)*100);
        }
        return res;
    }
    //Метод для расчета доли браузеров
    public HashMap<String, Double> getBrowserStat(){
        int allCount = 0;

        HashMap<String, Double> res = new HashMap<>();
        for (Map.Entry<String, Integer> entry: browsers.entrySet()){
            allCount += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry: browsers.entrySet()){
            res.put(entry.getKey(), (entry.getValue()/(double)allCount));
        }
        return res;
    }

    public long allHours(){
        return Duration.between(minTime, maxTime).toHours();
    }





    }
