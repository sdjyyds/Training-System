package com.example.system.demos.web.course;

import com.example.system.jdbc.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/courses")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 显示所有课程
     * @return 返回所有课程
     */
    @GetMapping
    public List<Course> getAllCourses() {
        log.info("开始获取所有课程");
        List<Course> courses = courseService.getAllCourses();
        log.info("获取所有课程成功，共{}门课程", courses.size());
        return courses;
    }
}