package by.magofrays.mapper;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.entity.PersonalInfo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class PersonalInfoMapper {
    public abstract PersonalInfoDto toDto(PersonalInfo personalInfo);
    public abstract PersonalInfo toEntity(PersonalInfoDto personalInfoDto);
}
