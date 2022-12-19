package pl.michal.io.file;

import pl.michal.exception.DataExportException;
import pl.michal.exception.DataImportException;
import pl.michal.exception.InvalidDataException;
import pl.michal.model.*;

import java.io.*;
import java.util.Collection;

class CsvFileManager implements FileManager {
    private static final String FILE_NAME = "Library.csv";
    private static final String USERS_FILE_NAME = "Library_users.csv";


    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }

    private void exportUsers(Library library) {
        Collection<LibraryUser> users = library.getLibraryUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }

    private void exportPublications(Library library) {
        Collection<Publication> publications = library.getPublications().values();
        exportToCsv(publications, FILE_NAME);
    }

    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (
                FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            for (T t : collection) {
                bufferedWriter.write(t.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Exporting data to " + fileName + " failed");
        }
    }

    @Override
    public Library importData() {
        Library library = new Library();
        importPublications(library);
        importUsers(library);
        return library;
    }

    private void importPublications(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createPublicationFromCsvLine)
                    .forEach(library::addPublication);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Cannot find file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Cannot read file " + FILE_NAME);
        }
    }

    private Publication createPublicationFromCsvLine(String line) throws InvalidDataException {
        String[] split = line.split(";");
        String type = split[0];
        if (type.equals(Book.TYPE))
            return createBook(split);
        else if (type.equals(Magazine.TYPE))
            return createMagazine(split);
        throw new InvalidDataException("Publication type not recognized " + type);
    }

    private Book createBook(String[] publication) {
        String title = publication[1];
        String author = publication[2];
        String publisher = publication[3];
        int year = Integer.parseInt(publication[4]);
        int pages = Integer.parseInt(publication[5]);
        String isbn = publication[6];

        return new Book(year, title, publisher, author, pages, isbn);
    }

    private Magazine createMagazine(String[] publication) {
        String title = publication[1];
        String publisher = publication[2];
        int year = Integer.parseInt(publication[3]);
        int month = Integer.parseInt(publication[4]);
        int day = Integer.parseInt(publication[5]);
        String language = publication[6];

        return new Magazine(year, title, publisher, month, day, language);
    }

    private void importUsers(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createUserFromCsvLine)
                    .forEach(library::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Cannot find file " + USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Cannot read file " + USERS_FILE_NAME);
        }
    }

    private LibraryUser createUserFromCsvLine(String line) {
        String[] split = line.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new LibraryUser(firstName, lastName, pesel);
    }

}
