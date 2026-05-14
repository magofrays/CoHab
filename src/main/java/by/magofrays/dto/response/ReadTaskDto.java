package by.magofrays.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReadTaskDto(
        UUID id,
        String taskName,
        String description,
        SmallFamilyMemberDto createdBy,
        SmallFamilyMemberDto issuedTo,
        LocalDate createdDate,
        LocalDate dueDate,
        Boolean isMarked,
        Boolean isChecked
) {
}
