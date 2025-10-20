package by.magofrays.service;
import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.entity.Member;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final PersonalInfoRepository personalInfoRepository;


    public ReadMemberDto createMember(SmallMemberDto memberDto, PersonalInfoDto personalInfoDto){
        Member member = memberMapper.forCreate(memberDto, personalInfoDto);
        personalInfoRepository.save(member.getPersonalInfo());
        memberRepository.save(member);
        return memberMapper.toDto(member);
    }

    public Optional<ReadMemberDto> findByUsername(String username){
        return memberRepository.findByUsername(username).map(memberMapper::toDto);
    }


}
