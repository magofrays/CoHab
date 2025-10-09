package by.magofrays.mapper;

import by.magofrays.dto.FamilyDto;
import by.magofrays.entity.Family;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class FamilyMapper {
    public abstract FamilyDto toDto(Family family);
    public abstract Family toEntity(FamilyDto familyDto);
}
