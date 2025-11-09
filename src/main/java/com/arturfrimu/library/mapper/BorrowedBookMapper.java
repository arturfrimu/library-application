package com.arturfrimu.library.mapper;

import com.arturfrimu.library.dto.response.BorrowedBookResponse;
import com.arturfrimu.library.entity.BorrowRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowedBookMapper {
    @Mapping(target = "borrowRecordId", source = "id")
    @Mapping(target = "borrowDate", source = "borrowDate")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "userFirstName", source = "user.firstName")
    @Mapping(target = "userLastName", source = "user.lastName")
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookIsbn", source = "book.isbn")
    @Mapping(target = "bookPublishedYear", source = "book.publishedYear")
    @Mapping(target = "authorId", source = "book.author.id")
    @Mapping(target = "authorName", source = "book.author.name")
    BorrowedBookResponse toResponse(BorrowRecord borrowRecord);
}

