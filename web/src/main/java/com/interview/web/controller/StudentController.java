package com.interview.web.controller;

import cn.hutool.core.convert.Convert;
import com.interview.starter.service.GreetingService;
import com.interview.web.i18n.ApiResultI18n;
import com.interview.web.pojo.entity.Student;
import com.interview.web.pojo.param.StudentParam;
import com.interview.web.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qikun
 * @since 2025-03-11
 */
@Api
@RestController
@RequestMapping("/interview/student")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private GreetingService greetingService;

    @PostMapping("/add")
    private ApiResultI18n add(@RequestBody @Valid StudentParam studentParam) {
        log.info(greetingService.greet("student add"));
        Student student = Convert.convert(Student.class, studentParam);
        return ApiResultI18n.success(studentService.save(student));
    }

    @GetMapping("/list")
    private ApiResultI18n<List<Student>> list() {
        log.info(greetingService.greet("student list"));
        return ApiResultI18n.success(studentService.list());
    }

}
