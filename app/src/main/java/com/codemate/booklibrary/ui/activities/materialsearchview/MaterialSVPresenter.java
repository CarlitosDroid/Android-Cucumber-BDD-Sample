package com.codemate.booklibrary.ui.activities.materialsearchview;

import com.codemate.booklibrary.data.Book;
import com.codemate.booklibrary.data.Library;
import com.codemate.booklibrary.data.RandomBookGenerator;

import java.util.List;

/**
 * Created by carlos on 6/16/17.
 */

public class MaterialSVPresenter {

    private final MaterialSVView materialSVView;
    private final Library library;

    RandomBookGenerator bookGenerator;

    public MaterialSVPresenter(MaterialSVView materialSVView, Library library, RandomBookGenerator bookGenerator) {
        this.materialSVView = materialSVView;
        this.library = library;
        this.bookGenerator = bookGenerator;
    }

    public void searchForBooks(String searchQuery) {
        List<Book> searchResults = library.search(searchQuery);
        materialSVView.showBooks(searchResults);
    }

    public void fetchBooks() {
        // Populate the library with fake dummy data. In a real app
        // we would have an interactor that would fetch the books from
        // a real API.
        List<Book> books = bookGenerator.generate(45);
        library.addBooks(books);

        materialSVView.showBooks(library.getAllBooks());
    }
}
