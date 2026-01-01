package by.magofrays.mapper;

import by.magofrays.dto.CreateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.entity.Task;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.repository.MemberRepository;
import org.hibernate.annotations.Comment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;

@Component
@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    private MemberRepository memberRepository;

    @Mapping(target = "createdBy", expression = "java(task.getCreatedBy().getId())")
    @Mapping(target = "issuedTo", expression = "java(task.getIssuedTo().getId())")
    public abstract ReadTaskDto toDto(Task task);
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "issuedTo", ignore = true)
    public abstract Task toEntity(CreateTaskDto task);

    @AfterMapping
    void mapCreatedByAndIssuedTo(@MappingTarget Task task, CreateTaskDto createTaskDto){
        task.setCreatedBy(memberRepository
                .findById(createTaskDto.getCreatedBy())
                .orElseThrow( () -> new BusinessException(ErrorCode.NOT_FOUND)
                ));
        task.setIssuedTo(memberRepository
                .findById(createTaskDto.getIssuedTo())
                .orElseThrow( () -> new BusinessException(ErrorCode.NOT_FOUND)
                ));
    }

}
