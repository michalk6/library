package pl.michal.io;

import pl.michal.model.Book;
import pl.michal.model.LibraryUser;
import pl.michal.model.Magazine;
import pl.michal.model.Publication;

import java.util.Collection;

public class ConsolePrinter {

    public void printBooks(Collection<Publication> publications) {
        int counter = 0;
        for (Publication publication : publications) {
            if (publication instanceof Book) {
                printLine(publication);
                counter++;
            }
        }
        if (counter == 0)
            printLine("No books in the library");
    }

    public void printMagazines(Collection<Publication> publications) {
        int counter = 0;
        for (Publication publication : publications) {
            if (publication instanceof Magazine) {
                printLine(publication);
                counter++;
            }
        }
        if (counter == 0)
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
        int counter = 0;
        for (LibraryUser user : users) {
            printLine(user);
            counter++;
        }
        if (counter == 0)
            printLine("No users added");
    }

    public <T> void printLine(T text) {
        System.out.println(text.toString().toUpperCase());
    }
}
