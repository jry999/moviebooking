package com.wipro.lms.entity;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Add a new book to the library
    public void addBook(Book book) {
        this.books.add(book);
    }

    // Remove a book from the library
    public boolean removeBook(String isbn) {
        return books.removeIf(book -> book.getBook_id().equals(isbn));
    }

    // Search for a book by ISBN
    public Book findBookByIsbn(String isbn) {
        return books.stream()
                    .filter(book -> book.getBook_id().equals(isbn))
                    .findFirst()
                    .orElse(null);
    }

    // Add a new member to the library
    public void addMember(Member member) {
        this.members.add(member);
    }

    // Remove a member from the library
    public boolean removeMember(String memberId) {
        return members.removeIf(member -> member.getMemberId().equals(memberId));
    }

    // Find a member by member ID
    public Member findMemberById(String memberId) {
        return members.stream()
                      .filter(member -> member.getMemberId().equals(memberId))
                      .findFirst()
                      .orElse(null);
    }

    // Check out a book to a member
    public boolean checkoutBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);
        if (book != null && member != null) {
            // Logic to check out the book to the member
            // This might involve updating the available copies of the book
            // and adding the book to the member's list of currently borrowed books
            return true;
        }
        return false;
    }

    // Return a book to the library
    public boolean returnBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);
        if (book != null && member != null) {
            // Logic to return the book
            // This might involve updating the available copies of the book
            // and removing the book from the member's list of currently borrowed books
            return true;
        }
        return false;
    }

    // Additional methods as needed...
}
