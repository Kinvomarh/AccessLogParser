//Класс для выделения USER Agent
public class UserAgent {

    String userAgent;
    String bot;




    public UserAgent(String userAgent){
        this.userAgent = userAgent;
        pars();


    }

    public String getBot() {
        return bot;
    }

    //Метод парсинга подстроки с User Agent в результате в строку bot записывается второй фрагмент подстроки (compatible ;;)
    public void pars(){
        if(userAgent.indexOf("(compatible")!=-1) {
            userAgent = userAgent.substring(userAgent.indexOf("(compatible") + 1);
        }
        if(userAgent.indexOf(")")!=-1) {
            userAgent = userAgent.substring(0, userAgent.indexOf(")"));
        }
            String[] bot = userAgent.split(";");
            for (int i = 0; i < bot.length; i++) {
                bot[i] = bot[i].trim();
            }
            if(bot.length>1 && bot[1].indexOf("/") !=-1) {
                this.bot = bot[1].substring(0, bot[1].indexOf("/"));
            }

    }

    @Override
    public String toString() {
        return "UserAgent: " + bot;
    }
}


