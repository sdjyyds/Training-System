package com.example.system.demos.web.course;

import com.example.system.jdbc.dao.UserCourseDao;
import com.example.system.jdbc.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseDao userCourseDao;


    public boolean buyCourse(int userId, int courseId) {
        if (userCourseDao.exists(userId, courseId)) return false;
        userCourseDao.insert(userId, courseId);
        return true;
    }
    public List<Course> getCoursesByUserId(int userId) {
        return userCourseDao.selectCoursesByUserId(userId);
    }
}

