package by.magofrays.mapper;

import by.magofrays.dto.CreateFamilyDto;
import by.magofrays.dto.ReadFamilyDto;
import by.magofrays.entity.Family;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.repository.MemberRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper(componentModel = "spring")
@Component
public abstract class FamilyMapper {
    @Autowired
    private MemberRepository memberRepository;

    @Mapping(target = "createdBy", expression = "java(family.getCreatedBy().getId())")
    public abstract ReadFamilyDto toDto(Family family);

    @Mapping(target = "createdBy", ignore = true)
    public abstract Family toEntity(CreateFamilyDto createFamilyDto);

    @AfterMapping
    void mapCreatedBy(@MappingTarget Family family, CreateFamilyDto createFamilyDto){
        family.setCreatedBy(memberRepository
                .findById(createFamilyDto.getCreatedBy())
                .orElseThrow( () -> new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ createFamilyDto.getCreatedBy() +" не существует.")
                ));
    }

    @AfterMapping
    protected void generateUuids(@MappingTarget Family family) {
        family.setId(UUID.randomUUID());
    }
}
