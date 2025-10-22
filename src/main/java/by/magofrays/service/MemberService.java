package by.magofrays.service;
import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.RegistrationDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final PasswordEncoder encoder;


    public ReadMemberDto createMember(SmallMemberDto memberDto, PersonalInfoDto personalInfoDto){
        Member member = memberMapper.forCreate(memberDto, personalInfoDto);
        personalInfoRepository.save(member.getPersonalInfo());
        memberRepository.save(member);
        return memberMapper.toDto(member);
    }

    public ReadMemberDto createMember(RegistrationDto registrationDto){
        PersonalInfo personalInfo = PersonalInfo.builder()
                .uuid(UUID.randomUUID())
                .firstname(registrationDto.getFirstname())
                .lastname(registrationDto.getLastname())
                .birthDate(registrationDto.getBirthDate())
                .build();
        Member member = Member.builder()
                .uuid(UUID.randomUUID())
                .username(registrationDto.getUsername())
                .password(encoder.encode(registrationDto.getPassword()))
                .build();
        personalInfoRepository.save(personalInfo);
        member.setPersonalInfo(personalInfo);
        memberRepository.save(member);
        return memberMapper.toDto(member);
    }

    public Optional<ReadMemberDto> findByUsername(String username){
        return memberRepository.findByUsername(username).map(memberMapper::toDto);
    }


}
