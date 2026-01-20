package by.magofrays.mapper;

import by.magofrays.dto.RoleDto;
import by.magofrays.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);
}
