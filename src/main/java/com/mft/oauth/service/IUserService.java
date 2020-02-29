package com.mft.oauth.service;

import com.fattahi.general.service.IGenericService;
import com.mft.oauth.model.User;

public interface IUserService extends IGenericService<User, Long> {

	User findByUserName(String userName);
}
