package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Course;

import java.util.*;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface CourseDao {
    List<Course> findAll();
}
