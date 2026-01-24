package by.magofrays.service;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.MarkCheckTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.mapper.TaskMapper;
import by.magofrays.repository.FamilyMemberRepository;
import by.magofrays.repository.FamilyRepository;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.yaml.snakeyaml.error.Mark;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final TaskMapper taskMapper;
    private final FamilyRepository familyRepository;

    public List<ReadTaskDto> getTasksByIssuedToId(UUID id){
        return taskRepository.getTasksByIssuedToId(id).stream().map(taskMapper::toDto).toList();
    }

    public ReadTaskDto createTask(CreateUpdateTaskDto taskDto){
        var createdBy = familyMemberRepository.findByMember_IdAndFamily_Id(taskDto.getCreatedBy(), taskDto.getFamilyId())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NOT_FOUND,
                                "Пользователь с id: "+ taskDto.getCreatedBy() +" не состоит в семье: " + taskDto.getFamilyId()));

        var task = taskMapper.toEntity(taskDto);
        if(taskDto.getIssuedTo() != null){
            var issuedTo = createdBy
                    .getFamily()
                    .getMembers()
                    .stream()
                    .filter(member ->
                            member.getId().equals(taskDto.getIssuedTo()))
                    .findFirst()
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND,
                                    "Пользователь с id: "+ taskDto.getIssuedTo() +" не состоит в семье: " + taskDto.getFamilyId()));
            task.setIssuedTo(issuedTo);
        }
        else{
            task.setIssuedTo(createdBy);
        }
        task.setCreatedBy(createdBy);
        task.setId(UUID.randomUUID());
        task.setCreatedDate(LocalDate.now());
        task.setIsChecked(false);
        task.setIsMarked(false);
        return taskMapper.toDto(taskRepository.save(task));
    }

    public ReadTaskDto updateTask(CreateUpdateTaskDto taskDto){
        var task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Задачи с id: " + taskDto.getTaskId() + " не существует"));
        if(taskDto.getTaskName() != null){
            task.setTaskName(taskDto.getTaskName());
        }
        if(taskDto.getDescription() != null){
            task.setDescription(taskDto.getDescription());
        }
        if(taskDto.getDueDate() != null){
            task.setDueDate(taskDto.getDueDate());
        }
        if(taskDto.getIssuedTo() != null){
            task.setIssuedTo(familyMemberRepository
                    .findById(taskDto.getIssuedTo())
                    .orElseThrow(() ->
                            new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует!")));
        }
        return taskMapper.toDto(taskRepository.save(task));
    }

    public void markOrCheckTask(MarkCheckTaskDto markTaskDto){
        var task = taskRepository.findById(markTaskDto.getTaskId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND, "Задачи с id: " + markTaskDto.getTaskId() + " не существует!"));
        if(task.getIsMarked() && !task.getIssuedTo()
                .getId().equals(markTaskDto.getMemberId())){
            throw new BusinessException(ErrorCode.BAD_REQUEST,
                    "На пользователя с id: " + markTaskDto.getMemberId() + " задача с id: " + markTaskDto.getTaskId() + " не назначена!");
        }
        else{
            if(task.getIsChecked()){
                throw new BusinessException(ErrorCode.BAD_REQUEST,
                        "Задача с id: " + markTaskDto.getTaskId() + " уже проверена!");
            }
            task.setIsMarked(markTaskDto.getTaskMarked());
        }
        if(task.getIsChecked() && !task.getCreatedBy()
                .getId().equals(markTaskDto.getMemberId())){
            throw new BusinessException(ErrorCode.BAD_REQUEST,
                    "Пользователь с id: " + markTaskDto.getMemberId() + " не создавал задачу с id: " + markTaskDto.getTaskId() + "!");
        }else{
            if(!task.getIsMarked()){
                task.setIsMarked(markTaskDto.getTaskChecked());
            }
            task.setIsChecked(markTaskDto.getTaskChecked());
        }
        taskRepository.save(task);
    }

    public List<ReadTaskDto> getFamilyTasks(UUID familyId, UUID memberId){
        familyMemberRepository.findByMember_IdAndFamily_Id(memberId, familyId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND,
                        "Пользователь с id: " + memberId + " не состоит в семье, либо семьи с id: " + familyId + "не существует!"));
        return taskRepository
                .getTasksByCreatedBy_Family_Id(familyId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
