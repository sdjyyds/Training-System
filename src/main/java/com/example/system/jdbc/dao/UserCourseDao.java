package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Course;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface UserCourseDao {
    boolean exists(@Param("userId") int userId, @Param("courseId") int courseId);
    void insert(@Param("userId") int userId, @Param("courseId") int courseId);
    List<Course> selectCoursesByUserId(@Param("userId") int userId);
}
