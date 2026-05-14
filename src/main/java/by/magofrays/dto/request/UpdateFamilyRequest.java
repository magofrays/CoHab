package by.magofrays.dto.request;

import java.util.UUID;

public record UpdateFamilyRequest(
   UUID familyId,
   String familyName
) {

}
