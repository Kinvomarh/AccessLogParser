//Класс для выделения USER Agent
public class UserAgent {

    private String userAgent;
    final String bot;
    final String system;
    final String browser;




    public UserAgent(String userAgent){
        this.userAgent = userAgent;
        if(userAgent!=null) {
            this.bot = parsBot(userAgent);
            this.system = parsSystem(userAgent);
            this.browser = parsbrowser(userAgent);
        }else {
            this.bot = "";
            this.system = "";
            this.browser = "";
        }
    }

    public String getBot() {
        return bot;
    }

    public String getSystem() {
        return system;
    }

    public String getBrowser() {
        return browser;
    }

    //Метод парсинга подстроки с User Agent в результате в строку bot записывается второй фрагмент подстроки (compatible ;;)
    public String parsBot(String userAgent){

        String pars = userAgent;
        String res = "";
        if(pars.indexOf("(compatible")!=-1) {
            pars = pars.substring(pars.indexOf("(compatible") + 1);
        }
        if(pars.indexOf(")")!=-1) {
            pars = pars.substring(0, pars.indexOf(")"));
        }
            String[] bot = pars.split(";");
            for (int i = 0; i < bot.length; i++) {
                bot[i] = bot[i].trim();
            }
            if(bot.length>1 && bot[1].indexOf("/") !=-1) {
                res = bot[1].substring(0, bot[1].indexOf("/"));
            }
            return res;
    }
    // метод выделения системы
    public String parsSystem (String userAgent){
        String pars = userAgent;
        String res = "";
        if(pars.indexOf("(")!=-1 && pars.indexOf(")")!=-1) {
            pars = pars.substring(pars.indexOf("(") + 1, pars.indexOf(")"));
            String[] parsingSystem;
            if (!pars.startsWith("compatible")) {
                parsingSystem = pars.split(";");
                res = parsingSystem[0];
            }
        }
        return res;
    }

    // метод выделения браузера
    public String parsbrowser(String userAgent){

        String res = "";
        String sub = userAgent;

        if(sub.indexOf("Gecko")!=-1) {
            res = sub.substring(sub.indexOf("Gecko") + 6);
            if (res.indexOf("(compatible") != -1) {
                res = res.substring(0, res.indexOf("(compatible"));
            }
            res = res.trim();
            if(res.indexOf("\"")!=-1){
                res= res.substring(0, res.indexOf("\""));
            }
            String[] pars = res.split(" ");
            for (int i = pars.length-1; i>=0; i--){
                if(!isNumber(pars[i])){
                    if (pars[i].indexOf("/")!=-1){
                        res = pars[i].substring(0, pars[i].indexOf("/")-1);
                        break;
                    } else {
                        res = pars[i];
                        break;
                    }
                }
            }
        }
    return res;
    }

    @Override
    public String toString() {
        return "UserAgent: " + bot;
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


