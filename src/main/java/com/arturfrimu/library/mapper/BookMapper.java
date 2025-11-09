package com.arturfrimu.library.mapper;

import com.arturfrimu.library.dto.response.BookResponse;
import com.arturfrimu.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", source = "author.name")
    BookResponse toResponse(Book book);
}

