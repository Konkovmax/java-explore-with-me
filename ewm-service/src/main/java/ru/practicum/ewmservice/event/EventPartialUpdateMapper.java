package ru.practicum.ewmservice.event;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventPartialUpdateMapper {

        @Mapping(target = "category", ignore = true)
        @Mapping(target =  "locationLon", ignore = true)
        @Mapping(target =  "locationLat", ignore = true)
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        Event partialUpdate(NewEventDto dto);
//        @Mapping(target = "category", ignore = true)
//        @Mapping(target =  "locationLon", ignore = true)
//        @Mapping(target =  "locationLat", ignore = true)
//        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//        void partialUpdate(NewEventDto dto, @MappingTarget() Event entity);

}
