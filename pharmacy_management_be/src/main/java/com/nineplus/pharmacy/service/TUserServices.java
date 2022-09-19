package com.nineplus.pharmacy.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nineplus.pharmacy.model.TUser;
import com.nineplus.pharmacy.repository.TUserRepository;
import com.nineplus.pharmacy.service.TUserServices;

@Service
@Transactional
public class TUserServices implements UserDetailsService {
	
	@Autowired
	TUserRepository tUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser user = tUserRepo.findByUserNm(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new User(user.getUserNm(), user.getPassword(), authorities);
    }
    
    public TUser getUserByUsername(String username) {
        return tUserRepo.findByUserNm(username);
    }

}
