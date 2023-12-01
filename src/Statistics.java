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
    HashMap<String, Integer> systems = new HashMap<>();
    HashMap<String, Integer> browsers = new HashMap<>();
    HashMap<Integer, Integer> visitPerSecond = new HashMap<>();
    HashMap<String, Integer> peakIpVisitUsers = new HashMap<>();
    HashSet<String> domainName = new HashSet<>();







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
        return totalVisitUser/ peakIpVisitUsers.size();
    }

    //Метод расчета общего трафика и выделения минимального и максимального времени
    public void addEntry(LogEntry logEntry) {

        LocalDateTime dateTime = logEntry.getDate();
        //Вычисление общего трафика

        totalTraffic += logEntry.getResponseSize();

        //заполняем SET доменными именами
        String domain = parsDomainName(logEntry);
        if(domain!=null) {
            domainName.add(domain);
        }

        //Вычисление общего количества посещений пользователями (не ботами)
        //Заполняем Map количеством посещений каждую секунду
        //Заполняем Map количеством посещений каждым пользователем
        if(!logEntry.getUserAgent().isBot) {
            totalVisitUser += 1;

            if(peakIpVisitUsers.containsKey(logEntry.getIp())){
                int countVisitUser = peakIpVisitUsers.get(logEntry.getIp()) + 1;
                peakIpVisitUsers.put(logEntry.getIp(), countVisitUser);
            }
            else peakIpVisitUsers.put(logEntry.getIp(), 1);

            if(visitPerSecond.containsKey(logEntry.getDate().getSecond())) {
                int countPerSecond = visitPerSecond.get(logEntry.getDate().getSecond()) + 1;
                visitPerSecond.put(logEntry.getDate().getSecond(), countPerSecond);
            }
            else visitPerSecond.put(logEntry.getDate().getSecond(), 1);
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
            pages.add(logEntry.getReferer());
        }
        if (logEntry.getResponseCode() == 404) {
            pages.add(logEntry.getReferer());
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


    //Метод расчёта пиковой посещаемости сайта (в секунду)

    public ArrayList<Integer> peakVisitPerSecond(){
        int max = 0;
        int second = 0;
        for (Map.Entry<Integer, Integer> entry : visitPerSecond.entrySet()){
            if(entry.getValue()>max){
                max = entry.getValue();
                second = entry.getKey();
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        res.add(second);
        res.add(max);
        return res;
    }



    public String parsDomainName(LogEntry logEntry){
        String referer = logEntry.getReferer();
        String res = null;
        if(referer!=null){
            if(referer.contains("https://")  || referer.contains("http://")){
                referer = referer.substring(referer.indexOf("/")+2);
                res = referer.substring(0, referer.indexOf("/"));
            }
        }
        return res;


    }

    //Метод, возвращающий список сайтов, со страниц которых есть ссылки на текущий сайт.
    public HashSet<String> getDomainName(){
        return domainName;
    }


    //Метод расчёта максимальной посещаемости одним пользователем.

    public HashMap<String, Integer> getPeakIpAndVisitUser() {
        HashMap<String, Integer> res = new HashMap<>();

        int peakVisitUser = 0;
        String ipUser = null;


        for (Map.Entry<String, Integer> entry: peakIpVisitUsers.entrySet()){
            if(entry.getValue()>peakVisitUser){
                peakVisitUser = entry.getValue();
                ipUser = entry.getKey();
            }
        }
        res.put(ipUser, peakVisitUser);

        return res;
    }





    }
