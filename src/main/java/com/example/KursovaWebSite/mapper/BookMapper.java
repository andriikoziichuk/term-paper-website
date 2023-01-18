package com.example.KursovaWebSite.mapper;

import com.example.KursovaWebSite.models.book.Book;
import com.example.KursovaWebSite.dto.BookDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {

    BookMapper MAPPER = Mappers.getMapper(BookMapper.class);

    Book toProduct(BookDTO dto);

    @InheritInverseConfiguration
    BookDTO fromBook(Book book);

    List<Book> toProductList(List<BookDTO> bookDTOS);

    List<BookDTO> fromBookList(List<Book> books);
}
