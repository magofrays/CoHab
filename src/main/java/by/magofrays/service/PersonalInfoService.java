package by.magofrays.service;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonalInfoService {
    private final PersonalInfoRepository personalInfoRepository;

}
