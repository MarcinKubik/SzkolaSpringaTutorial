package pl.sztukakodu.bookstore.catalog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private final Integer year;

}
