import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private final String ip;
    private final String properties;
    private final LocalDateTime date;
    private final HTTPMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;
    private String residue;

    //Конструктор, который вызывает метод парсинга
       public LogEntry(String residue){
           this.residue = residue;
           String[] str = pars();
           this.ip = str[0];
           this.properties = str[1];
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
           this.date = LocalDateTime.parse(str[2], formatter);
           this.method = HTTPMethod.valueOf(str[3]);
           this.path = str[4];
           this.responseCode = Integer.parseInt(str[5]);
           this.responseSize = Integer.parseInt(str[6]);
           this.referer = str[7];
           this.userAgent = new UserAgent(str[8]);




           //25/Sep/2022:06:25:04 +0300

    }

    //Метод проверки строки на длинну
    public static void chekLine(String line, int count) throws LenghtExeption{
        if(line.length()>1024){
            throw new LenghtExeption("строка " + ++count + " превышает длину в 1024 символа");
        }
    }

    public String getIp() {
        return this.ip;
    }

    public String getProperties() {
        return this.properties;
    }

    public  LocalDateTime getDate() {
        return this.date;
    }

    public HTTPMethod getMethod() {
        return this.method;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public int getResponseSize() {
        return this.responseSize;
    }

    public String getReferer() {
        return this.referer;
    }

    public UserAgent getUserAgent() {
        return this.userAgent;
    }

    //Метод записи подстрок в свои строки
    public String[] pars(){
        String[] str = new  String[9];
        int start = 0;
        int end;
        //1. Вырезаем IP-адрес клиента, который сделал запрос к серверу
        end = residue.indexOf(" ");
        if(end!=-1) str[0] = parsline(start, end);

        //2. Вырезаем Два пропущенных свойства, на месте которых обычно стоят дефисы, но могут встречаться также и пустые строки ("").
        end = residue.indexOf("[");
        if(end!=-1) str[1] = parsline(start, end);

        //3. Вырезаем Дата и время запроса в квадратных скобках.
        end = residue.indexOf("]");
        if(end!=-1) str[2] = parsline(1, end);

        //4. Вырезаем Метод запроса (в примере выше — GET) и путь, по которому сделан запрос.
        end = residue.indexOf("/");
        if(end!=-1) str[3] = parsline(3, end);

        //5. Вырезаем путь
        start = residue.indexOf("/");
        end = residue.indexOf(" ");
        if(end!=-1) str[4] = parsline(start, end);
        start=0;

        //6. Вырезаем Код HTTP-ответа (в примере выше — 200).
        residue = residue.substring(residue.indexOf("HTTP/1.0\" ")+10);
        end = residue.indexOf(" ");
        if(end!=-1) str[5] = parsline(start, end);;

        //7. Вырезаем Размер отданных данных в байтах (в примере выше — 61096).
        end = residue.indexOf(" ");
        if(end!=-1) str[6] = parsline(start, end);;

        //8. Вырезаем Путь к странице, с которой перешли на текущую страницу, — referer
        end = residue.indexOf("\" ")+1;
        if(end!=-1) str[7] = parsline(start, end);;

        //9. Вырезаем User-Agent — информация о браузере или другом клиенте, который выполнил запрос.

        if(residue.indexOf("\"Mozilla")!=-1) {
            str[8] = residue.substring(1);
        }else str[8]=null;
        return str;
    }

        // Метод парсинга каждой строки, так же удаляет пробелы в начале и в конце
        private String parsline(int start, int end){
        String res = "";
        res = this.residue.substring(start, end);
        res = res.trim();
        this.residue = residue.substring(start + res.length());;
        this.residue = this.residue.trim();
        return res;

        }

        public LocalDateTime createLocalDateTime(String dateTime){

        return null;
        }

    @Override
    public String toString() {
        return "IP-адрес клиента, который сделал запрос к серверу: " + ip +
                "\n\rДва пропущенных свойства: " + properties +
                "\n\rДата и время запроса: " + date +
                "\n\rМетод запроса: " + method +
                "\n\rПуть запроса: " + path +
                "\n\rКод HTTP-ответа: " + responseCode +
                "\n\rРазмер отданных данных в байтах: " + responseSize +
                "\n\rПуть к странице, с которой перешли на текущую страницу: " + referer +
                "\n\rUser-Agent: " + userAgent;
    }
}
