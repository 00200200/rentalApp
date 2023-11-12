package pl.rentalApp.manager;

import pl.rentalApp.models.Client;
import pl.rentalApp.observer.Observer;
import pl.rentalApp.observer.Subject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private static int lastClientId = findLastClientId();
    public ClientManager() {}
    private static String filePath = "src/main/java/pl/rentalApp/data/clients.txt";


    public Client createClient(String name) {
        lastClientId++;
        Client newClient = new Client(lastClientId, name, false);
        saveClientToFile(newClient);
        notifyObservers();
        return newClient;
    }

    private static int findLastClientId() {
        try {
            Path path = Paths.get("src/main/java/pl/rentalApp/data/clients.txt");
            if (Files.exists(path)) {
                BufferedReader reader = Files.newBufferedReader(path);
                String line;
                int nextId = 0;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int clientId = Integer.parseInt(parts[0]);
                    if (clientId >= nextId) {
                        nextId = clientId;
                    }
                }
                return nextId;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public List<Client> readClients() {
        List<Client> clients = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while (((line = reader.readLine()) != null)) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                Boolean registered = Boolean.parseBoolean(parts[2]);
                clients.add(new Client(id, name, registered));
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clients;
    }
    public void saveClientToFile(Client client) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(client.getId() + "," + client.getName() + "," + client.getRegistered() + "" +
                    "\n");
            writer.close();
            notifyObservers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateClients(List<Client> clients) throws IOException {
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (Client client : clients) {
            String line = client.getId() + "," + client.getName() + "," + client.getRegistered() + "\n";
            writer.write(line);
        }
        notifyObservers();
        writer.close();

    }
    @Override
    public void addObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }
    @Override
    public void removeObserver(Observer o) {
    }
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
