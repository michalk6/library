package pl.michal.app;

import pl.michal.exception.DataImportException;
import pl.michal.exception.InvalidDataException;
import pl.michal.exception.NoSuchOptionException;
import pl.michal.io.ConsolePrinter;
import pl.michal.io.DataReader;
import pl.michal.io.file.FileManager;
import pl.michal.io.file.FileManagerBuilder;
import pl.michal.model.Book;
import pl.michal.model.Library;
import pl.michal.model.Magazine;

import java.util.InputMismatchException;

class LibraryControl {

    private final ConsolePrinter printer = new ConsolePrinter();
    private final DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Library library;

    public LibraryControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Imported data from file");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("New database initialized");
            library = new Library();
        }
    }

    public void control() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK -> addBook();
                case PRINT_BOOKS -> printBooks();
                case ADD_MAGAZINE -> addMagazine();
                case PRINT_MAGAZINES -> printMagazines();
                case PRINT_BOOKS_AND_MAGAZINES -> printBooksAndMagazines();
                case EXIT -> exit();
            }

        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.choseByIntValue(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLine("Select an option:");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book toAdd = dataReader.readAndCreateBook();
            library.addPublication(toAdd);
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printBooks() {
        printer.printBooks(library.getPublications());
    }

    private void addMagazine() {
        try {
            Magazine toAdd = dataReader.readAndCreateMagazine();
            library.addPublication(toAdd);
        } catch (InputMismatchException e) {
            printer.printLine("Incorrect data has been given, magazine creation failed");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printMagazines() {
        printer.printMagazines(library.getPublications());
    }

    private void printBooksAndMagazines() {
        printer.printPublications(library.getPublications());
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export data to file successful");
        } catch (DataImportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Application stopped");
        dataReader.close();
    }


    public enum Option {
        EXIT("exit", 0),
        ADD_BOOK("Add new book", 1),
        PRINT_BOOKS("display available books", 2),
        ADD_MAGAZINE("add new magazine", 3),
        PRINT_MAGAZINES("display available magazines", 4),
        PRINT_BOOKS_AND_MAGAZINES("display available books and magazines", 5);

        private final String description;
        private final int value;

        Option(String description, int number) {
            this.description = description;
            this.value = number;
        }

        static Option choseByIntValue(int value) throws NoSuchOptionException {
            try {
                return Option.values()[value];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("There is no option with given id: " + value);
            }
        }

        String getDescription() {
            return description;
        }

        int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }
    }
}