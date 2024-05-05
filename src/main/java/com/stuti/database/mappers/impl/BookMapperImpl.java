package com.stuti.database.mappers.impl;

import com.stuti.database.domain.dto.BookDto;
import com.stuti.database.domain.entities.BookEntity;
import com.stuti.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapfrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
