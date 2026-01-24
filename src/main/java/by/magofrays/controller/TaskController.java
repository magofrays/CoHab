package by.magofrays.controller;

import by.magofrays.dto.CreateUpdateTaskDto;
import by.magofrays.dto.MarkCheckTaskDto;
import by.magofrays.dto.ReadTaskDto;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.security.MemberPrincipal;
import by.magofrays.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public List<ReadTaskDto> getTasksByMember(@AuthenticationPrincipal MemberPrincipal principal){
        return taskService.getTasksByIssuedToId(principal.getId());
    }

    @PostMapping("/create")
    @PreAuthorize("""
        hasAuthority('USER') && hasPermission(#createTaskDto.familyId, 'family', 'CREATE_TASK')
        && (#createTaskDto.issuedTo == null || hasPermission(#createTaskDto.familyId, 'family', 'ASSIGN_TASK'))
    """)
    public ReadTaskDto createTask(@AuthenticationPrincipal MemberPrincipal principal,
                           @Validated @RequestBody CreateUpdateTaskDto createTaskDto){
        createTaskDto.setCreatedBy(principal.getId());
        return taskService.createTask(createTaskDto);
    }

    @PutMapping("/update")
    @PreAuthorize(
            """
                hasAuthority('USER') && (hasPermission(#updateTaskDto.familyId, 'family', 'UPDATE_TASK')
                || principal.id.equals(updateTaskDto.createdBy))
                && (#updateTaskDto.issuedTo == null || hasPermission(#updateTaskDto.familyId, 'family', 'ASSIGN_TASK'))
            """
    )
    public ReadTaskDto changeTask(@AuthenticationPrincipal MemberPrincipal principal,
                                  @Validated @RequestBody CreateUpdateTaskDto updateTaskDto) {
        return taskService.updateTask(updateTaskDto);
    }

    @PostMapping("/mark-check")
    @PreAuthorize("""
            hasAuthority('USER')
        """
    )
    public void markOrCheckTask(
            @AuthenticationPrincipal MemberPrincipal principal,
            @Validated @RequestBody MarkCheckTaskDto markOrCheckTaskDto){
        if(markOrCheckTaskDto.getMemberId() == null){
            markOrCheckTaskDto.setMemberId(principal.getId());
        }
        if(markOrCheckTaskDto.getTaskMarked() == null && markOrCheckTaskDto.getTaskChecked() == null){
            throw new BusinessException(ErrorCode.BAD_REQUEST, "В запросе должна быть отметка проверки либо отметка выполненности!");
        }
        taskService.markOrCheckTask(markOrCheckTaskDto);
    }

    @GetMapping("/{familyId}")
    public List<ReadTaskDto> getFamilyTasks(@PathVariable UUID familyId,
    @AuthenticationPrincipal MemberPrincipal principal){
        return taskService.getFamilyTasks(familyId, principal.getId());
    }
}
