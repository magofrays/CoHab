package by.magofrays.service;
import by.magofrays.dto.*;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.FamilyMapper;
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
    private final FamilyMapper familyMapper;


    public ReadMemberDto createMember(RegistrationDto registrationDto){
        Member member = memberMapper.forCreate(registrationDto);
        personalInfoRepository.save(member.getPersonalInfo());
        memberRepository.save(member);
        return memberMapper.toDto(member);
    }

    public List<ReadFamilyDto> findMemberFamilies(UUID memberId){
        return memberRepository
                .findById(memberId)
                .map(Member::getFamilies)
                .map(families ->
                        families
                                .stream()
                                .map(familyMapper::toDto)
                                .toList()
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
    }

    public boolean memberHasFamily(UUID memberID){
        return !memberRepository
                .findById(memberID)
                .map(Member::getFamilies).map(List::isEmpty)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
    }

    public Optional<ReadMemberDto> findByUsername(String username){
        return memberRepository.findByUsername(username).map(memberMapper::toDto);
    }


}
