package pl.michal.model;

import java.io.Serializable;

public class Library implements Serializable {
    private static final int MAX_PUBLICATIONS = 1000;
    private Publication[] publications = new Publication[MAX_PUBLICATIONS];
    private int publicationNumber = 0;

    public Publication[] getPublications() {
        Publication[] result = new Publication[publicationNumber];
        for (int i = 0; i < result.length; i++) {
            result[i] = publications[i];
        }
        return result;
    }

    public void addBook(Book book) {
        addPublication(book);
    }

    public void addMagazine(Magazine magazine) {
        addPublication(magazine);
    }

    void addPublication(Publication publication) {
        if (publicationNumber >= MAX_PUBLICATIONS)
            throw new ArrayIndexOutOfBoundsException("Maximum number of publications exceeded " + MAX_PUBLICATIONS);
        publications[publicationNumber++] = publication;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < publicationNumber; i++) {
            builder.append(publications[i])
                    .append("\n");
        }
        return builder.toString();
    }
}
