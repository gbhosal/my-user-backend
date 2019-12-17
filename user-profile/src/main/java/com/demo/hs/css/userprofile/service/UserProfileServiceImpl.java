package com.demo.hs.css.userprofile.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.hs.css.userprofile.utils.ReturnCode;
import com.demo.hs.css.userprofile.vo.BaseResponse;
import com.demo.hs.css.userprofile.vo.Profile;
import com.demo.hs.css.userprofile.vo.UserProfile;

@Component("UserProfileService")
public class UserProfileServiceImpl implements UserProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Override
	public UserProfile getUserProfile(String userName) {
		LOGGER.info("UserName = {}", userName);
		UserProfile userProfile = new UserProfile();
		userProfile.setReturnCode(ReturnCode.SUCCESS.getReturnCode());
		userProfile.setReturnMessage(ReturnCode.SUCCESS.getReturnMessage());

		List<Profile> profileList = new ArrayList<>();
		profileList.add(new Profile("127", "SUPERVISOR"));
		profileList.add(new Profile("160", "ADMINISTRATOR"));
		userProfile.setProfile(profileList);

		return userProfile;
	}

	/**
	 * Purpose of this function is to keep the lambda function warm so lambda
	 * requests don't run into cold start problem.
	 * 
	 * @return <b>Instance of String</b> that indicates status of health
	 */
	@Override
	public BaseResponse health() {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setReturnCode(ReturnCode.SUCCESS.getReturnCode());
		baseResponse.setReturnMessage(ReturnCode.SUCCESS.getReturnMessage());
		return baseResponse;
	}
}