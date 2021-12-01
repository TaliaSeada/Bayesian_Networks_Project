import java.io.FileWriter;
import java.io.IOException;

public class Ex1 {

    public static void main(String[] args) {
        // read this file then write the output to another file
        String path = "input.txt";
        readTextfile reader = new readTextfile(path);
        String s = reader.readfile();
//        System.out.println(s);
        writeToFile(s);
    }

    public static void writeToFile(String s) {
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
