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

@Component
@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    private MemberRepository memberRepository;

    @Mapping(target = "createdBy", expression = "java(task.getCreatedBy().getId())")
    @Mapping(target = "issuedTo", expression = "java(task.getIssuedTo().getId())")
    public abstract ReadTaskDto toDto(Task task); // todo лучше отправлять таски с профилями

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "issuedTo", ignore = true)
    public abstract Task toEntity(CreateUpdateTaskDto task);

}
