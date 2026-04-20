package com.epam.automation.api.business.model.mapper;

import com.epam.automation.api.business.model.domain.Item;
import com.epam.automation.api.business.model.dto.PostItemRequestDto;
import org.modelmapper.ModelMapper;

public class ItemsMapper {
    static ModelMapper mapper = new ModelMapper();

    static {
        mapper.typeMap(Item.class, PostItemRequestDto.class);
    }

    public static <T> T map(Item item, Class<T> targetClass) {
        return mapper.map(item, targetClass);
    }

}
