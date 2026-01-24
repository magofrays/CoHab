package by.magofrays.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class ReadTaskDto {
    UUID id;
    String taskName;
    String description;
    SmallFamilyMemberDto createdBy;
    SmallFamilyMemberDto issuedTo;
    LocalDate createdDate;
    LocalDate dueDate;
    Boolean isMarked;
    Boolean isChecked;
}
