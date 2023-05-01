package com.main.discgolf.service.userInfo;

import com.main.discgolf.model.UserInfo;
import com.main.library.model.User;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> getListOfUserInfoByListOfUser(List<User> users);


}
