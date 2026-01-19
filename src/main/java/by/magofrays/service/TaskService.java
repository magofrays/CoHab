package by.magofrays.service;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.mapper.TaskMapper;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;
    private final TaskMapper taskMapper;

    public List<ReadTaskDto> getTasksByIssuedToId(UUID id){
        return taskRepository.getTasksByIssuedToId(id).stream().map(taskMapper::toDto).toList();
    }

    public ReadTaskDto createTask(CreateUpdateTaskDto taskDto){
        var task = taskMapper.toEntity(taskDto);
        if(taskDto.getIssuedTo() != null){
            task.setIssuedTo(memberRepository.findById(taskDto.getIssuedTo())
                    .map(member -> member.getFamilyMembers().stream()
                            .filter(familyMember -> familyMember
                                    .getFamily()
                                    .getFamilyName()
                                    .equals(taskDto.getFamilyName()))
                            .findFirst()
                            .map(familyMember -> member)
                            .orElseThrow(() -> new BusinessException(
                                    ErrorCode.BAD_REQUEST,
                                    "Назначенный пользователь не состоит в этой семье."
                            ))
                    )
                    .orElseThrow(() -> new BusinessException(
                            ErrorCode.NOT_FOUND,
                            "Пользователь с id " + taskDto.getIssuedTo() + " не найден"
                    )));
        }
        task.setCreatedBy(memberRepository
                .findById(taskDto.getCreatedBy())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует.")));
        return taskMapper.toDto(taskRepository.save(task));
    }
}
