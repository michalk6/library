package pl.michal.model;

import pl.michal.exception.PublicationAlreadyExistsException;
import pl.michal.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {
    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> libraryUsers = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator) {
        List<Publication> list = new ArrayList<>(publications.values());
        list.sort(comparator);
        return list;
    }

    public Map<String, LibraryUser> getLibraryUsers() {
        return libraryUsers;
    }

    public Collection<LibraryUser> getSortedLibraryUser(Comparator<LibraryUser> comparator) {
        List<LibraryUser> list = new ArrayList<>(libraryUsers.values());
        list.sort(comparator);
        return list;
    }

    public Optional<Publication> findPublicationByTitle(String title) {
        return Optional.ofNullable(publications.get(title));
    }

    public void addPublication(Publication publication) {
        if (publications.containsKey(publication.getTitle())) {
            throw new PublicationAlreadyExistsException("Publication with given title already exists");
        }
        publications.put(publication.getTitle(), publication);
    }

    public void addUser(LibraryUser user) {
        if (libraryUsers.containsKey(user.getPesel())) {
            throw new UserAlreadyExistsException("User with given PESEL already exists");
        }
        libraryUsers.put(user.getPesel(), user);
    }

    public boolean removePublication(Publication toRemove) {
        if (publications.containsValue(toRemove)) {
            publications.remove(toRemove.getTitle());
            return true;
        }
        return false;
    }
}
