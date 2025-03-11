package com.interview.service.impl;

import com.interview.mapper.StudentMapper;
import com.interview.pojo.entity.Student;
import com.interview.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author qikun
 * @date 3/11/2025  11:44 AM
 */
@Service
public class MyServiceImpl implements MyService {
    @Resource
    private StudentMapper studentMapper;
    @Transactional
    @Override
    public final void add(Student student) {
        studentMapper.insert(student);
        System.out.println("Hello World");
        //throw new RuntimeException();
    }
}
