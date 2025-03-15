package com.interview.web.controller;

import cn.hutool.core.convert.Convert;
import com.interview.starter.service.GreetingService;
import com.interview.web.i18n.ApiResultI18n;
import com.interview.web.pojo.entity.ArticleDoc;
import com.interview.web.pojo.entity.Student;
import com.interview.web.pojo.param.StudentParam;
import com.interview.web.repository.ArticleDocRepository;
import com.interview.web.service.StudentService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;
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

    private final Counter requestCounter;
    private final MeterRegistry meterRegistry;
    private final Random random = new Random();

    @Autowired
    public StudentController(Counter requestCounter, MeterRegistry meterRegistry) {
        this.requestCounter = requestCounter;
        this.meterRegistry = meterRegistry;
    }
    @Autowired
    private StudentService studentService;

    @Autowired
    private GreetingService greetingService;

    @Resource
    private ArticleDocRepository articleDocRepository;

    @PostMapping("/add")
    private ApiResultI18n add(@RequestBody @Valid StudentParam studentParam) {
        // 模拟错误计数
        meterRegistry.counter("app.errors.count").increment();

        log.info(greetingService.greet("student add"));
        Student student = Convert.convert(Student.class, studentParam);
        return ApiResultI18n.success(studentService.save(student));
    }

    @GetMapping("/list")
    private ApiResultI18n<List<Student>> list() {
        // 增加请求计数器
        requestCounter.increment();
        AtomicReference<List<Student>> list = new AtomicReference<>(new ArrayList<>());
        // 记录响应时间 (模拟)
        meterRegistry.timer("app.hello.timer").record(() -> {
            try {
                Thread.sleep(random.nextInt(200) + 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            list.set(studentService.list());
        });
        log.info(greetingService.greet("student list"));
        return ApiResultI18n.success(list);
    }

    @GetMapping("/bye")
    private ApiResultI18n hello() {
        // 增加请求计数器
        requestCounter.increment();
        // 记录响应时间 (模拟)
        meterRegistry.timer("app.bye.timer").record(() -> {
            try {
                Thread.sleep(random.nextInt(100) + 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return ApiResultI18n.success(true);
    }

    @GetMapping("/test")
    private ApiResultI18n testEs() {
        ArticleDoc articleDoc = ArticleDoc.builder()
                .id(1)//文章id
                .createTime(new Date())//创建时间
                .status(1)//状态
                .title("测试标题")//标题
                .description("测试简介")//简介
                .content("测试内容")//内容
                .authorName("作者名字")//作者名字
                .type("文章")//类型
                .build();

        return ApiResultI18n.success(articleDocRepository.save(articleDoc));
    }

}
