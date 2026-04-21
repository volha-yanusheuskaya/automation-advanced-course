package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.Item;
import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import org.modelmapper.ModelMapper;

public class ItemsMapper {
    private static final ModelMapper MAPPER = new ModelMapper();

    private ItemsMapper() {
    }

    public static PostItemRequestDto map(Item item) {
        return MAPPER.map(item, PostItemRequestDto.class);
    }

}
