package pl.michal.io;

import pl.michal.model.Book;
import pl.michal.model.LibraryUser;
import pl.michal.model.Magazine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DataReader {
    private final ConsolePrinter printer;
    private final Scanner input = new Scanner(System.in);

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void close() {
        input.close();
    }

    public String getString() {
        return input.nextLine();
    }

    public int getInt() {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                printer.printLine("You need to enter the number");
            } finally {
                input.nextLine();
            }
        }
    }

    public Book readAndCreateBook() {
        printer.printLine("Title:");
        String title = getString();
        printer.printLine("Author:");
        String author = getString();
        printer.printLine("Publisher:");
        String publisher = getString();
        printer.printLine("ISBN (optional):");
        String isbn = getString();
        printer.printLine("Release date:");
        int year = getInt();
        printer.printLine("Number of pages:");
        int pages = getInt();

        return new Book(year, title, publisher, author, pages, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Title:");
        String title = getString();
        printer.printLine("Publisher:");
        String publisher = getString();
        printer.printLine("Language:");
        String language = getString();
        printer.printLine("Year:");
        int year = getInt();
        printer.printLine("Month:");
        int month = getInt();
        printer.printLine("Day:");
        int day = getInt();

        return new Magazine(year, title, publisher, month, day, language);
    }

    public LibraryUser readAndCreateLibraryUser() {
        printer.printLine("FirstName:");
        String firstName = getString();
        printer.printLine("LastName:");
        String lastName = getString();
        printer.printLine("PESEL:");
        String pesel = getString();

        return new LibraryUser(firstName, lastName, pesel);
    }
}
