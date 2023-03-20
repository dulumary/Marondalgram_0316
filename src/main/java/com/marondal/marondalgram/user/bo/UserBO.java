package com.marondal.marondalgram.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marondal.marondalgram.common.EncryptService;
import com.marondal.marondalgram.user.dao.UserDAO;

@Service
public class UserBO {
	
	@Autowired
	private UserDAO userDAO;
	
	public int addUser(
			String loginId
			, String password
			, String name
			, String email) {
		
		String encryptPassword = EncryptService.md5(password);
		
		return userDAO.insertUser(loginId, encryptPassword, name, email);
		
	}
	
	public boolean isDuplicateId(String loginId) {
		
//		 int count = userDAO.selectCountByLoginId(loginId);
//		 
//		 if(count == 0) {
//			 return false;
//		 } else {
//			 return true;
//		 }
		
		return userDAO.selectCountByLoginId(loginId) != 0;
		
	}

}
