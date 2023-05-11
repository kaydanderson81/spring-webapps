package com.main.discgolf.service.userInfo;

import com.main.discgolf.model.Round;
import com.main.discgolf.model.Score;
import com.main.discgolf.model.UserInfo;
import com.main.discgolf.repository.CourseRepository;
import com.main.library.model.User;
import com.main.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<UserInfo> getListOfUserInfoByListOfUser(List<User> users) {
        List<UserInfo> userInfoList = new ArrayList<>();
        for (User user : users) {
            List<Round> rounds = userService.getUserById(user.getId()).getRounds();
            List<Score> scoreList = userService.getListOfAllScoresForUserByUserId(user.getId());
            UserInfo newUserInfo = new UserInfo();
            newUserInfo.setUserId(user.getId());
            newUserInfo.setName(user.getFirstName());
            newUserInfo.setNumberCoursesPlayed(courseRepository.findAllCoursesAUserHasPlayedByUserId(user.getId()).size());
            newUserInfo.setRoundsPlayed(rounds.size());
            newUserInfo.setScoreList(scoreList);
            newUserInfo.setHoles(scoreList.size());
            newUserInfo.setPlayerAverage(userService.getAverageScoreByUser(user.getId()));
            newUserInfo.setAces(userService.getNumberOfAces(user.getId()));
            userInfoList.add(newUserInfo);
        }
        return userInfoList;
    }
}
