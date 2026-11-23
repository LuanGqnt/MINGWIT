import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveStorage {

    private static final String SAVE_FILE_PATH = "../mingwit_saves.dat";

    public static SaveFile[] loadAll() {
        File file = new File(SAVE_FILE_PATH);
        if (!file.exists()) {
            return new SaveFile[3];
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof SaveFile[]) {
                SaveFile[] loaded = (SaveFile[]) obj;
                SaveFile[] result = new SaveFile[3];
                for (int i = 0; i < Math.min(loaded.length, 3); i++) {
                    result[i] = loaded[i];
                }
                return result;
            }
        } catch (Exception e) {
            System.out.println("Could not load saves, starting fresh: " + e.getMessage());
        }

        return new SaveFile[3];
    }

    public static void saveAll(SaveFile[] saves) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
            out.writeObject(saves);
            System.out.println("Saved successfully");
        } catch (Exception e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }
}