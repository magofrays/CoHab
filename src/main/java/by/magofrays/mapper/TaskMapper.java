package by.magofrays.mapper;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.entity.Task;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.repository.MemberRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface TaskMapper {

    ReadTaskDto toDto(Task task);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "issuedTo", ignore = true)
    Task toEntity(CreateUpdateTaskDto task);

}
