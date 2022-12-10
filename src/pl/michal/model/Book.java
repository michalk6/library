package pl.michal.model;

import java.util.Objects;

public class Book extends Publication {
    private String author;
    private int pages;
    private String isbn;

    public Book(int year, String title, String publisher, String author, int pages, String isbn) {
        this(year, title, publisher, author, pages);
        this.isbn = isbn;
    }

    public Book(int year, String title, String publisher, String author, int pages) {
        super(year, title, publisher);
        this.author = author;
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        String toString = getTitle() + "; " + author + "; " + getYear() + "; " + pages + "; " + getPublisher();
        if (isbn != null) toString = toString + "; " + isbn;
        return toString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return pages == book.pages && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, pages, isbn);
    }
}