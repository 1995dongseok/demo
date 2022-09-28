package com.example.demo.config.auth;

import com.example.demo.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class PrincipalDetail implements UserDetails {

    private Member member;

    Collection<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
    public PrincipalDetail (Member member,List<GrantedAuthority> authorities) {
        this.member =member;
        this.authorities = authorities;
    }



    //계정이 갖고있는 권한 목록은 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }


    public int getUserIdx() {
        return member.getUserIdx();
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
