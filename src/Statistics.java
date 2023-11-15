import java.time.Duration;
import java.time.LocalDateTime;



public class Statistics {

    long  totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;


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
        System.out.println(totalTraffic);

        if(minTime==null) minTime = dateTime;
        if(maxTime==null) maxTime = dateTime;

        if(maxTime.isBefore(dateTime)){
            maxTime = dateTime;
        } else if (minTime.isAfter(dateTime)) {
            minTime = dateTime;
        }

    }
    }
