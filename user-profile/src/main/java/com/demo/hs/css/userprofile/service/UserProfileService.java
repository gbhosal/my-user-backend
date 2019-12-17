package com.demo.hs.css.userprofile.service;

import com.demo.hs.css.userprofile.vo.BaseResponse;
import com.demo.hs.css.userprofile.vo.UserProfile;

public interface UserProfileService {
	public UserProfile getUserProfile(String userName);
	public BaseResponse health();
}