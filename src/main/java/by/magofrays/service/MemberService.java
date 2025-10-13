package by.magofrays.service;

import by.magofrays.dto.PersonalInfoDto;
import by.magofrays.dto.ReadMemberDto;
import by.magofrays.dto.SmallMemberDto;
import by.magofrays.entity.Member;
import by.magofrays.entity.PersonalInfo;
import by.magofrays.mapper.MemberMapper;
import by.magofrays.mapper.PersonalInfoMapper;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.PersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username).map(member ->
                new User(
                        member.getUsername(),
                        member.getPassword(),
                        member.getAccesses()
                )).orElseThrow(() ->
                        new UsernameNotFoundException("Failed to retrieve user: " + username)
        );
    }
}
