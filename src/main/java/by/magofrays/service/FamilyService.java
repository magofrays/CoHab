package by.magofrays.service;

import by.magofrays.dto.CreateFamilyDto;
import by.magofrays.dto.ReadFamilyDto;
import by.magofrays.dto.ReadFamilyMemberDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.entity.Family;
import by.magofrays.entity.FamilyMember;
import by.magofrays.entity.Member;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.mapper.FamilyMapper;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.repository.FamilyRepository;
import by.magofrays.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final MemberMapper memberMapper;
    private final FamilyMapper familyMapper;
    private final MemberRepository memberRepository;

    public List<ReadFamilyMemberDto> getFamilyMembersByMemberId(UUID memberId){
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND))
                .getFamilyMembers()
                .stream().map(FamilyMember::getFamily)
                .map(Family::getMembers)
                .flatMap(Collection::stream)
                .map(memberMapper::toDto)
                .toList();

    }

    @Transactional
    public ReadFamilyDto createFamily(CreateFamilyDto createFamilyDto){
        var family = familyMapper.toEntity(createFamilyDto);
        var owner = memberRepository.findById(createFamilyDto.getCreatedBy()).get();
        family.addMember(owner);
        family = familyRepository.save(family);
        return familyMapper.toDto(family);
    }
}
