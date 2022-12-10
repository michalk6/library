package pl.michal.app;

public class LibraryApp {
    final static String APP_NAME = "Library v1.8";

    public static void main(String[] args) {
        System.out.println(APP_NAME);

        LibraryControl libraryControl = new LibraryControl();
        libraryControl.control();
    }
}