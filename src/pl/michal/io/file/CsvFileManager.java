package pl.michal.io.file;

import pl.michal.exception.DataExportException;
import pl.michal.exception.DataImportException;
import pl.michal.exception.InvalidDataException;
import pl.michal.model.Book;
import pl.michal.model.Library;
import pl.michal.model.Magazine;
import pl.michal.model.Publication;

import java.io.*;
import java.util.Scanner;

class CsvFileManager implements FileManager {
    private static final String FILE_NAME = "Library.csv";


    @Override
    public void exportData(Library library) {
        try (
                FileWriter fileWriter = new FileWriter(FILE_NAME);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            for (Publication publication : library.getPublications()) {
                bufferedWriter.write(publication.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Exporting data to " + FILE_NAME + " failed");
        }
    }

    @Override
    public Library importData() {
        Library library = new Library();
        try (Scanner fileReader = new Scanner(new File(FILE_NAME))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                Publication publication = createPublicationFromCsvLine(line);
                library.addPublication(publication);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Cannot find file " + FILE_NAME);
        }
        return library;
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

}
