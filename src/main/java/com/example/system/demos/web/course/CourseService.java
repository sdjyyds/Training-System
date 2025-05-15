package com.example.system.demos.web.course;

import com.example.system.jdbc.entity.Course;

import java.util.*;


/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface CourseService {
    List<Course> getAllCourses();
}
