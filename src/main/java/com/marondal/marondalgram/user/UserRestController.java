package com.marondal.marondalgram.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marondal.marondalgram.user.bo.UserBO;
import com.marondal.marondalgram.user.model.User;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@GetMapping("/duplicate_id")
	public Map<String, Boolean> isDuplicateId(@RequestParam("loginId") String loingId) {
//		boolean isDuplicate = userBO.isDuplicateId(loingId);
		
		Map<String, Boolean> resultMap = new HashMap<>();
//		
//		if(isDuplicate) {
//			resultMap.put("is_duplicate", true);
//		} else {
//			resultMap.put("is_duplicate", false);
//		}
		
		resultMap.put("is_duplicate", userBO.isDuplicateId(loingId));
		
		return resultMap;
		
	}
	
	
	
	@PostMapping("/signup")
	public Map<String, String> signup(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		
		int count = userBO.addUser(loginId, password, name, email);
		
		Map<String, String> resultMap = new HashMap<>();
		if(count == 1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@PostMapping("/signin")
	public Map<String, String> signin(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpSession session) {
		
		User user = userBO.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result", "success");
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());	
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	

}
