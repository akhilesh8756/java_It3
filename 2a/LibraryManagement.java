import java.util.ArrayList;
import java.util.List;

// Abstract class for library items
abstract class LibraryItem {
    protected String title;
    protected int itemID;
    protected boolean isAvailable;

    public LibraryItem(String title, int itemID) {
        this.title = title;
        this.itemID = itemID;
        this.isAvailable = true;
    }

    public abstract void borrow();

    public abstract void returnItem();

    public String getTitle() {
        return title;
    }

    public int getItemID() {
        return itemID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "ID: " + itemID + ", Title: " + title + ", Available: " + isAvailable;
    }
}

// Book class
class Book extends LibraryItem {
    private String author;
    private String genre;

    public Book(String title, int itemID, String author, String genre) {
        super(title, itemID);
        this.author = author;
        this.genre = genre;
    }

    @Override
    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Book borrowed: " + title);
        } else {
            throw new ItemNotAvailableException("Book not available: " + title);
        }
    }

    @Override
    public void returnItem() {
        isAvailable = true;
        System.out.println("Book returned: " + title);
    }

    @Override
    public String toString() {
        return super.toString() + ", Author: " + author + ", Genre: " + genre;
    }
}

// DVD class
class DVD extends LibraryItem {
    private String director;
    private int duration;

    public DVD(String title, int itemID, String director, int duration) {
        super(title, itemID);
        this.director = director;
        this.duration = duration;
    }

    @Override
    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("DVD borrowed: " + title);
        } else {
            throw new ItemNotAvailableException("DVD not available: " + title);
        }
    }

    @Override
    public void returnItem() {
        isAvailable = true;
        System.out.println("DVD returned: " + title);
    }

    @Override
    public String toString() {
        return super.toString() + ", Director: " + director + ", Duration: " + duration + " mins";
    }
}

// Library class
class Library {
    private List<LibraryItem> items = new ArrayList<>();

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void borrowItem(int itemID) {
        for (LibraryItem item : items) {
            if (item.getItemID() == itemID) {
                try {
                    item.borrow();
                } catch (ItemNotAvailableException e) {
                    System.out.println(e.getMessage());
                } finally {
                    System.out.println("Borrow operation logged.");
                }
                return;
            }
        }
        System.out.println("Item not found.");
    }

    public void returnItem(int itemID) {
        for (LibraryItem item : items) {
            if (item.getItemID() == itemID) {
                item.returnItem();
                return;
            }
        }
        System.out.println("Item not found.");
    }

    public void searchByTitle(String title) {
        boolean found = false;
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                System.out.println(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found with title: " + title);
        }
    }

    public void searchByID(int itemID) {
        for (LibraryItem item : items) {
            if (item.getItemID() == itemID) {
                System.out.println(item);
                return;
            }
        }
        System.out.println("No items found with ID: " + itemID);
    }

    public void displayItemsByType() {
        System.out.println("Books:");
        for (LibraryItem item : items) {
            if (item instanceof Book) {
                System.out.println(item);
            }
        }

        System.out.println("DVDs:");
        for (LibraryItem item : items) {
            if (item instanceof DVD) {
                System.out.println(item);
            }
        }
    }
}

// Custom exception for unavailable items
class ItemNotAvailableException extends RuntimeException {
    public ItemNotAvailableException(String message) {
        super(message);
    }
}

// Main class
public class LibraryManagement {
    public static void main(String[] args) {
        Library library = new Library();

        // Add books and DVDs to the library
        library.addItem(new Book("The Great Gatsby", 101, "F. Scott Fitzgerald", "Classic"));
        library.addItem(new Book("1984", 102, "George Orwell", "Dystopian"));
        library.addItem(new DVD("Inception", 201, "Christopher Nolan", 148));
        library.addItem(new DVD("The Matrix", 202, "The Wachowskis", 136));

        // Display items by type
        System.out.println("Displaying all library items:");
        library.displayItemsByType();

        // Borrow an item
        System.out.println("\nBorrowing item with ID 101:");
        library.borrowItem(101);

        // Try borrowing the same item again
        System.out.println("\nAttempting to borrow the same item again:");
        library.borrowItem(101);

        // Return an item
        System.out.println("\nReturning item with ID 101:");
        library.returnItem(101);

        // Search for an item by title
        System.out.println("\nSearching for '1984':");
        library.searchByTitle("1984");

        // Search for an item by ID
        System.out.println("\nSearching for item with ID 202:");
        library.searchByID(202);
    }
}
