import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

// Класс парсинга файла
public class FileParsing {
    private String path;
    private File file;
    private ArrayList<LogEntry> listLine;

    private int countLine;

    //Приватный конструктор объект создается отдельным методом

    private FileParsing(String path, File file, ArrayList<LogEntry> pars) {
        this.path = path;
        this.file = file;
        this.listLine = new ArrayList<>(pars);
        this.countLine = this.listLine.size();

    }

    //метод создания объекта парсинга файла с проверкой файла и пути
    public static FileParsing fileParsing(String path) {
        File file = new File(path);
        if (checkFile(file)) {
            return new FileParsing(path, file, pars(path));
        }
        return null;
    }


    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public ArrayList<LogEntry> getListLine() {
        return listLine;
    }

    public int getCountLine() {
        return countLine;
    }

    //метод парсинга файла возвращающий лист LogEntry
    private static ArrayList<LogEntry> pars(String path) {
        ArrayList<LogEntry> listRes = new ArrayList<>();
        int count = 0;

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;

            while ((line = reader.readLine()) != null) {
                LogEntry.chekLine(line, count++);
                listRes.add(new LogEntry(line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listRes;
    }

    //Метод проверки файла и пути
    private static boolean checkFile(File file) {
        if (file.isDirectory()) {
            System.out.println("Это путь к директории");
            return false;
        }
        if (!file.exists()) {
            System.out.println("файл не найден");
            return false;
        } else {
            System.out.println("Путь указан верно");
            return true;
        }
    }
}
