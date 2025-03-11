package com.interview.controller;

import cn.hutool.core.convert.Convert;
import com.interview.i18n.ApiResultI18n;
import com.interview.pojo.entity.Student;
import com.interview.pojo.param.StudentParam;
import com.interview.service.StudentService;
import io.swagger.annotations.Api;
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
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    private ApiResultI18n add(@RequestBody @Valid StudentParam studentParam) {
        Student student = Convert.convert(Student.class, studentParam);
        return ApiResultI18n.success(studentService.save(student));
    }

    @GetMapping("/list")
    private ApiResultI18n<List<Student>> list() {
        return ApiResultI18n.success(studentService.list());
    }

}
