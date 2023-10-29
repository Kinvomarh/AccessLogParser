import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path;
        int count=0;
        File file;
        int countline;
        int maxlengh;
        int minlengh;


        //В цикле проверяем все введенные пути к файлу
        while (true){
            countline=0;
            maxlengh=0;
            minlengh=0;
            path = new Scanner(System.in).nextLine();
            file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("Это путь к директории");
                continue;
            }
            //Считаем количество попыткок. Выводим на экран номер попытки, где файл указан верно
            if(!fileExists){
                    System.out.println("файл не найден");
                }

                else {
                    System.out.println("Путь указан верно");
                    count++;
                    System.out.println("Это файл номер " + count);


            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null){
                    countline++;
                    int lenght = line.length();
                    if(lenght>1024){
                        throw new LenghtExeption("строка превышает длину в 1024 символа");
                    }
                    if(maxlengh<lenght){
                        maxlengh=lenght;
                    }
                    if(minlengh==0){
                        minlengh = lenght;
                    }else {
                        if(minlengh>lenght){
                        minlengh=lenght;
                    }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("общее количество строк в фале: " + countline);
            System.out.println("Самая длинная строка: " + maxlengh);
            System.out.println("Самая короткая строка: " + minlengh);

        }
        }








    }
}
