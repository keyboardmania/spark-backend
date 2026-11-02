package com.example.spring.test.security.user;

import org.springframework.security.core.userdetails.User; // 임포트 추가
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring.test.member.entity.Member;
import com.example.spring.test.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return toUserDetails(member);
    }

    public UserDetails loadUserByUuid(String uuid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException(uuid));
        return toUserDetails(member);
    }

    private UserDetails toUserDetails(Member member) {
        return User.builder()
                .username(member.getUuid())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
    }
}