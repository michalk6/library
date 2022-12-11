package pl.michal.io.file;

import pl.michal.exception.NoSuchFileTypeException;
import pl.michal.io.ConsolePrinter;
import pl.michal.io.DataReader;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Chose data format:");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL -> {
                return new SerializableFileManager();
            }
            case CSV -> {
                return new CsvFileManager();
            }
            default -> throw new NoSuchFileTypeException("Incorrect data type entered");
        }
    }

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOk = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Incorrect data type entered, please try again");
            }
        } while (!typeOk);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
