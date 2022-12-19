package pl.michal.io;

import pl.michal.model.Book;
import pl.michal.model.LibraryUser;
import pl.michal.model.Magazine;
import pl.michal.model.Publication;

import java.util.Collection;

public class ConsolePrinter {

    public void printBooks(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(publication -> publication instanceof Book)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("No books in the library");
    }

    public void printMagazines(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(publication -> publication instanceof Magazine)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("No magazines in the library");
    }

    public void printPublications(Collection<Publication> publications) {
        printLine("Books:");
        printBooks(publications);
        printLine("");
        printLine("Magazines:");
        printMagazines(publications);
    }

    public void printUsers(Collection<LibraryUser> users) {
        users.forEach(this::printLine);
    }

    public <T> void printLine(T text) {
        System.out.println(text.toString().toUpperCase());
    }
}
