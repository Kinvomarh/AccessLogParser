import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path;
        int count=0;
        while (true){
            path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("Это путь к директории");
                continue;
            }
            else {
                if(!fileExists){
                    System.out.println("файл не найден");
                    continue;
                }
                else {
                    System.out.println("Путь указан верно");
                    count++;
                    System.out.println("Это файл номер " + count);
                }
            }
        }
    }
}
