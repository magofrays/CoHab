package by.magofrays.service;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.DeleteTaskDto;
import by.magofrays.dto.MarkCheckTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.mapper.TaskMapper;
import by.magofrays.repository.FamilyMemberRepository;
import by.magofrays.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final TaskMapper taskMapper;


    public Boolean isCreatedBy(UUID memberId, UUID taskId){
        return taskRepository.isCreatedBy(memberId, taskId);
    }

    public List<ReadTaskDto> getTasksByIssuedToId(UUID id){
        return taskRepository.getTasksByIssuedToId(id).stream().map(taskMapper::toDto).toList();
    }


    public ReadTaskDto createTask(CreateUpdateTaskDto taskDto){
        log.debug("Member: {} trying to create task", taskDto.getCreatedBy());
        var createdBy = familyMemberRepository.findByMember_IdAndFamily_Id(taskDto.getCreatedBy(), taskDto.getFamilyId())
                .orElseThrow(() ->{
                        log.debug("Member: {} for createdBy does not exist in family: {}", taskDto.getCreatedBy(), taskDto.getFamilyId());
                        return new BusinessException(ErrorCode.NOT_FOUND,
                                "Пользователь с id: "+ taskDto.getCreatedBy() +" не состоит в семье: " + taskDto.getFamilyId());
                });

        var task = taskMapper.toEntity(taskDto);
        if(taskDto.getIssuedTo() != null){
            var issuedTo = createdBy
                    .getFamily()
                    .getMembers()
                    .stream()
                    .filter(member ->
                            member.getId().equals(taskDto.getIssuedTo()))
                    .findFirst()
                    .orElseThrow(() -> {
                            log.debug("Member: {} for issuedTo does not exist in family: {}", taskDto.getIssuedTo(), taskDto.getFamilyId());
                            return new BusinessException(ErrorCode.NOT_FOUND,
                                    "Пользователь с id: "+ taskDto.getIssuedTo() +" не состоит в семье: " + taskDto.getFamilyId());
                    });
            task.setIssuedTo(issuedTo);
        }
        else{
            task.setIssuedTo(createdBy);
        }
        task.setCreatedBy(createdBy);
        task.setCreatedDate(LocalDateTime.now());
        task.setIsChecked(false);
        task.setIsMarked(false);
        log.info("Member: {} created task", taskDto.getTaskId());
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReadTaskDto updateTask(CreateUpdateTaskDto taskDto){
        log.debug("Member: {} trying to update task: {}", taskDto.getCreatedBy(), taskDto.getTaskId());
        var task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> {
                    log.debug("Task: {} to update does not exist", taskDto.getTaskId());
                    return new BusinessException(ErrorCode.NOT_FOUND, "Задачи с id: " + taskDto.getTaskId() + " не существует");});
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
                    .orElseThrow(() -> {
                        log.debug("Member: {} for issuedTo does not exist", taskDto.getIssuedTo());
                            return new BusinessException(ErrorCode.NOT_FOUND, "Пользователь с id: "+ taskDto.getCreatedBy() +" не существует!");
                    }));
        }
        log.info("Member: {} updating task: {}", taskDto.getCreatedBy(), taskDto.getTaskId());
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void markOrCheckTask(MarkCheckTaskDto markTaskDto, UUID memberId){
        var task = taskRepository.findById(markTaskDto.getTaskId())
                .orElseThrow(() -> {
                    log.debug("Task: {} does not exist", markTaskDto.getTaskId());
                    return new BusinessException(ErrorCode.NOT_FOUND, "Задачи с id: " + markTaskDto.getTaskId() + " не существует!");
                });
        if(markTaskDto.getTaskMarked() != null){
            if(!task.getIssuedTo().getMember().getId().equals(memberId)){

                throw new BusinessException(ErrorCode.BAD_REQUEST,
                        "На пользователя с id: " + memberId + " задача с id: " + markTaskDto.getTaskId() + " не назначена!");
            }
            else{
                if(task.getIsChecked() != null && task.getIsChecked()){
                    throw new BusinessException(ErrorCode.BAD_REQUEST,
                            "Задача с id: " + markTaskDto.getTaskId() + " уже проверена!");
                }
                task.setIsMarked(markTaskDto.getTaskMarked());
            }
        }
        else{
            if(markTaskDto.getTaskChecked() != null && !task.getCreatedBy().getMember().getId().equals(memberId)){
                throw new BusinessException(ErrorCode.BAD_REQUEST,
                        "Пользователь с id: " + memberId + " не создавал задачу с id: " + markTaskDto.getTaskId() + "!");
            }else{
                if(task.getIsMarked() != null && !task.getIsMarked()){
                    task.setIsMarked(markTaskDto.getTaskChecked());
                }
                task.setIsChecked(markTaskDto.getTaskChecked());
            }
        }
        taskRepository.save(task);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<ReadTaskDto> getFamilyTasks(UUID familyId, UUID memberId){
        if(!familyMemberRepository.memberInFamily(memberId, familyId)){
                throw new BusinessException(ErrorCode.NOT_FOUND,
                        "Пользователь с id: " + memberId + " не состоит в семье, либо семьи с id: " + familyId + "не существует!");
        }
        return taskRepository
                .getTasksByCreatedBy_Family_Id(familyId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public void deleteTask(DeleteTaskDto taskDto){
        var task = taskRepository.findById(taskDto.getTaskId()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND,
                "Задачи с id: " + taskDto.getTaskId() + " не существует!"));
        taskRepository.delete(task);
    }
}
