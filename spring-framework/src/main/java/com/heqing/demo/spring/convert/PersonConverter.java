package com.heqing.demo.spring.convert;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper//(uses=DateMapper.class)
public interface PersonConverter {

    PersonConverter INSTANCE = Mappers.getMapper(PersonConverter.class);

    @Mappings({
            @Mapping(source = "sex", target = "gender"),
            @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    PersonDTO do2dto(PersonBO personBO);

    @Mappings({
            @Mapping(source = "gender", target = "sex"),
            @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    PersonBO dto2do(PersonDTO personDTO);

    List<PersonBO> dtos2dos(List<PersonDTO> personDTOS);

}
