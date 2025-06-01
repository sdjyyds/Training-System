package com.example.system.demos.web.course;

import com.example.system.jdbc.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/user-courses")
@Slf4j
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyCourse(int courseId, int userId) {
        log.info("用户{}尝试购买课程{}", userId, courseId);
        boolean success = userCourseService.buyCourse(userId, courseId);
        if (success) {
            log.info("用户{}购买课程{}成功", userId, courseId);
            return ResponseEntity.ok("购买成功");
        } else {
            log.info("用户{}已购买课程{}", userId, courseId);
            return ResponseEntity.ok("你已购买该课程");
        }
    }

    @GetMapping("/my")
    public List<Course> getMyCourses(int userId) {
        log.info("用户{}获取自己的课程列表", userId);
        List<Course> courses = userCourseService.getCoursesByUserId(userId);
        log.info("用户{}获取自己的课程列表成功，共{}门课程", userId, courses.size());
        return courses;
    }
}