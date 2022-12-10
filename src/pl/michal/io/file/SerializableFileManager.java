package pl.michal.io.file;

import pl.michal.exception.DataExportException;
import pl.michal.exception.DataImportException;
import pl.michal.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "Library.obj";

    @Override
    public Library importData() {
        try (
                FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (Library) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("Cannot find file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Importing data to " + FILE_NAME + " failed");
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Data type doesn't match");
        }
    }

    @Override
    public void exportData(Library library) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(library);
        } catch (FileNotFoundException e) {
            throw new DataExportException("Cannot find file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Exporting data to " + FILE_NAME + " failed");
        }
    }
}
