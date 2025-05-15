package com.example.system.demos.web.course;

import com.example.system.jdbc.dao.CourseDao;
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
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }
}


