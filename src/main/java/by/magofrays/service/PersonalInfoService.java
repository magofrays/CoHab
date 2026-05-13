package by.magofrays.service;

import by.magofrays.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalInfoService {
    private final PersonalInfoRepository personalInfoRepository;

}
