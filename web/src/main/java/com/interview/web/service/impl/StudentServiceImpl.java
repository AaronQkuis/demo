package com.interview.web.service.impl;

import com.interview.web.pojo.entity.Student;
import com.interview.web.mapper.StudentMapper;
import com.interview.web.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qikun
 * @since 2025-03-11
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
