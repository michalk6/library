package pl.michal.io;

import pl.michal.model.Book;
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
        String title = input.nextLine();
        printer.printLine("Author:");
        String author = input.nextLine();
        printer.printLine("Publisher:");
        String publisher = input.nextLine();
        printer.printLine("ISBN (optional):");
        String isbn = input.nextLine();
        if (isbn.equals("")) isbn = null;
        printer.printLine("Release date:");
        int year = getInt();
        printer.printLine("Number of pages:");
        int pages = getInt();

        return new Book(year, title, publisher, author, pages, isbn);
    }

    public Magazine readAndCreateMagazine() {
        printer.printLine("Title:");
        String title = input.nextLine();
        printer.printLine("Publisher:");
        String publisher = input.nextLine();
        printer.printLine("Language:");
        String language = input.nextLine();
        printer.printLine("Year:");
        int year = input.nextInt();
        printer.printLine("Month:");
        int month = input.nextInt();
        printer.printLine("Day:");
        int day = input.nextInt();

        return new Magazine(year, title, publisher, month, day, language);
    }
}
