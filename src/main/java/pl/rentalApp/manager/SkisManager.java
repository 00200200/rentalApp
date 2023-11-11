package pl.rentalApp.manager;
import pl.rentalApp.models.Ski;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class SkisManager {
    private static final String filePath = "src/main/java/pl/rentalApp/data/skis.txt";
    public List<Ski> readSkisFromFile(){
        List<Ski> skis = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while(((line = reader.readLine()) != null)){
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String type = parts[1];
                int length = Integer.parseInt(parts[2]);
                String status = parts[3];
                skis.add(new Ski(id,type,length,status));
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return skis;
    }
    public void saveSkisToFile(List<Ski> skis){
        try{
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file,false);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(Ski ski : skis){
                String line = ski.getId() + "," + ski.getType() + ","+  ski.getLength() + "," + ski.getStatus()  + "\n";
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addSkiToFIle(Ski ski){
        try{
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String line = ski.getId() + "," + ski.getType() + ","+  ski.getLength() + "," + ski.getStatus()  + "\n";
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
