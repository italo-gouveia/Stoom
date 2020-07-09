package com.stoom.services;

import com.stoom.data.model.User;
import com.stoom.security.AccountCredentialsVO;
import com.stoom.security.AuthenticationVO;

public interface IUserService {
	 public User findByUsernameOrEmail(String username, String email);   
	 //public User save(AccountCredentialsVO vo);
	 //public AuthenticationVO findByUsernameOrEmail(String username, String email);
	 public AccountCredentialsVO save(AccountCredentialsVO vo);
	 public AuthenticationVO auth(AccountCredentialsVO vo);
}
