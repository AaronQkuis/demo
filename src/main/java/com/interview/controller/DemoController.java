package com.interview.controller;

import com.interview.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qikun
 * @since 2025-03-11
 */
@RestController
@RequestMapping("/interview/demo")
public class DemoController {
    @Autowired
    private MyService myService;
}
