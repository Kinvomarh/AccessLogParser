public class LineParsing {
    private String ip, properties, date, method, kod, size, lastPage, userAgent;
    private String residue;


    //Конструктор, который вызывает метод парсинга
       public LineParsing(String residue){
        this.residue = residue;
        pars();
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

    public  String getDate() {
        return this.date;
    }

    public String getMethod() {
        return this.method;
    }

    public String getKod() {
        return this.kod;
    }

    public String getSize() {
        return this.size;
    }

    public String getLastPage() {
        return this.lastPage;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    //Метод записи подстрок в свои строки
    public void pars(){
        int start = 0;
        int end;
        //Вырезаем IP-адрес клиента, который сделал запрос к серверу
        end = residue.indexOf(" ");
        if(end!=-1) this.ip = parsline(start, end);

        //Вырезаем Два пропущенных свойства, на месте которых обычно стоят дефисы, но могут встречаться также и пустые строки ("").

        end = residue.indexOf("[");
        if(end!=-1) this.properties = parsline(start, end);

        //Вырезаем Дата и время запроса в квадратных скобках.
        end = residue.indexOf("]")+1;
        if(end!=-1) this.date = parsline(start, end);

        //Вырезаем Метод запроса (в примере выше — GET) и путь, по которому сделан запрос.
        end = residue.indexOf("\" ")+1;
        if(end!=-1) this.method = parsline(start, end);

        //Вырезаем Код HTTP-ответа (в примере выше — 200).
        end = residue.indexOf(" ");
        if(end!=-1) this.kod = parsline(start, end);;

        //Вырезаем Размер отданных данных в байтах (в примере выше — 61096).
        end = residue.indexOf(" ");
        if(end!=-1) this.size = parsline(start, end);;

        //Вырезаем Путь к странице, с которой перешли на текущую страницу, — referer
        end = residue.indexOf("\" ")+1;
        if(end!=-1) this.lastPage = parsline(start, end);;

        //Вырезаем User-Agent — информация о браузере или другом клиенте, который выполнил запрос.
        end = residue.lastIndexOf("\"")+1;
        if(end!=-1) this.userAgent = parsline(start, end);;



    }

        // Метод парсинга каждой строки, так же удаляет пробелы в начале и в конце
        private String parsline(int start, int end){
        String res = " ";
        res = this.residue.substring(start, end);
        res = res.trim();
        this.residue = residue.substring(res.length());;
        this.residue = this.residue.trim();
        return res;

        }

    @Override
    public String toString() {
        return "IP-адрес клиента, который сделал запрос к серверу: " + ip +
                "\n\rДва пропущенных свойства: " + properties +
                "\n\rДата и время запроса: " + date +
                "\n\rДва Метод запроса: " + method +
                "\n\rДва Код HTTP-ответа: " + kod +
                "\n\rДва Размер отданных данных в байтах: " + size +
                "\n\rДва Путь к странице, с которой перешли на текущую страницу: " + lastPage +
                "\n\rДва User-Agent: " + userAgent;

    }
}
