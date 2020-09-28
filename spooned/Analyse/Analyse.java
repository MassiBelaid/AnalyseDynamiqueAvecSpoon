package analyse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Analyse {
    public static void logAppel(String cls, String md) {
        System.out.println((cls + " -> ") + md);
        try {
            File myObj = new File("appels.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
            FileWriter myWriter = new FileWriter("appels.txt", true);
            myWriter.write(((cls + "->") + md) + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}