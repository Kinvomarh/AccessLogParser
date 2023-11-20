import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Statistics {

    long  totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;
    HashSet<String> pages = new HashSet<>();
    HashMap<String, Integer> systems = new HashMap<>();


    public Statistics() {
        this.totalTraffic = 0;
    }

    //Метод расчета трафика по часам
    public long getTrafficRate(){
       Duration duration = Duration.between(minTime, maxTime);
       long diffHour = duration.toHours();
       System.out.println(diffHour);
       if(diffHour!=0) {
           return totalTraffic / diffHour;
       }
       else return totalTraffic;
    }
    //Метод расчета общего трафика и выделения минимального и максимального метода
    public void addEntry(LogEntry logEntry){

        LocalDateTime dateTime = logEntry.getDate();
        totalTraffic += logEntry.getResponseSize();
        // Задаем минимальную и максимальную даты
        if(minTime==null) minTime = dateTime;
        if(maxTime==null) maxTime = dateTime;

        if(maxTime.isBefore(dateTime)){
            maxTime = dateTime;
        } else if (minTime.isAfter(dateTime)) {
            minTime = dateTime;
        }
        // заполняем коллекцию в которой хранятся все страницы сайта
        if(logEntry.getResponseCode() == 200){
            pages.add(logEntry.getLastPage());
        }
        // заполняем MAP в которой хранится операционная система и количество вызовов с неё
        if(logEntry.getUserAgent().getSystem()!=null && !logEntry.getUserAgent().getSystem().isEmpty()) {
            String sys = logEntry.getUserAgent().getSystem();
            if (systems.containsKey(sys)) {
                systems.put(sys, systems.get(sys) + 1);
            } else {
                systems.put(sys, 1);
            }
        }
    }

    public ArrayList<String> getPages (){
        return new ArrayList<>(pages);
    }

    public HashMap<String, Double> getOperSystemStat(){
        int allCount = 0;

        HashMap<String, Double> res = new HashMap<>();
        for (Map.Entry<String, Integer> entry: systems.entrySet()){
            allCount += entry.getValue();
        }
        for (Map.Entry<String, Integer> entry: systems.entrySet()){
            double d1 = entry.getValue();
            double result = entry.getValue()/(double) allCount;
            double res2 = result*100;
            Double procent = (double) ((entry.getValue()/(double) allCount)*100);

            res.put(entry.getKey(), (entry.getValue()/(double)allCount)*100);
        }
        return res;
    }





    }
