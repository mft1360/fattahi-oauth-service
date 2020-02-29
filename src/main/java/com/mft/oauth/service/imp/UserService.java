package com.mft.oauth.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.fattahi.general.service.imp.GenericService;
import com.mft.oauth.dao.UserRepository;
import com.mft.oauth.model.User;
import com.mft.oauth.service.IUserService;

@Service
public class UserService extends GenericService<User, Long> implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected JpaRepository<User, Long> getGenericRepo() {
		return userRepository;
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}
