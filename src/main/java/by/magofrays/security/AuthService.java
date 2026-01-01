package by.magofrays.security;

import by.magofrays.mapper.AccessMapper;
import by.magofrays.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AccessMapper accessMapper;
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("here");
        return memberRepository.findByUsername(username).map(member -> MemberPrincipal.builder()
                .id(member.getId())
                .username(member.getUsername())
                .accesses(member.getAccesses().stream().map(accessMapper::toDto).toList())
                .password(member.getPassword())
                .build()
        ).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
