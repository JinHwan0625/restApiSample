package com.jinhwan.sampletest.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jinhwan.sampletest.entity.Member;
import com.jinhwan.sampletest.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
 
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Member member = memberRepository.findByUserId(username);
    	if (member == null) {
    		throw new UsernameNotFoundException("not found user");
    	}
    	
    	return createUserDetails(memberRepository.findByUserId(username));    
    }
 
    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }
}
