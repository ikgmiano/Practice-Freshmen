import java.io.*;

public class UniqueIdGenerator implements Serializable {
    private static UniqueIdGenerator instance;
    private int currentUniqueId;
    private static final String FILE_NAME = "uniqueId.txt";

    private UniqueIdGenerator() {
        this.currentUniqueId = loadUniqueId();
    }

    private int loadUniqueId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {}
        return 1000;
    }

    public int getNextUniqueId() {
        int current = currentUniqueId++;
        saveUniqueId();
        return current;
    }

    public void saveUniqueId() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("" + currentUniqueId);
        } catch (Exception e) {}
    }

    public static UniqueIdGenerator getInstance() {
        if (instance == null) {
            instance = new UniqueIdGenerator();
        }
        return instance;
    }
}
