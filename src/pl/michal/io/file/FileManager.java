package pl.michal.io.file;

import pl.michal.model.Library;

public interface FileManager {
    Library importData();

    void exportData(Library library);
}
