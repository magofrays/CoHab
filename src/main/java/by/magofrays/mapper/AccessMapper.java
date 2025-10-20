package by.magofrays.mapper;

import by.magofrays.dto.AccessDto;
import by.magofrays.entity.Access;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessMapper {
    AccessDto toDto(Access access);
}
