package by.magofrays.mapper;

import by.magofrays.dto.request.CreateUpdateTaskRequest;
import by.magofrays.dto.response.ReadTaskDto;
import by.magofrays.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {MemberMapper.class})
public interface TaskMapper {

    ReadTaskDto toDto(Task task);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "issuedTo", ignore = true)
    Task toEntity(CreateUpdateTaskRequest task);

}
