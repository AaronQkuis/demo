package com.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.interview")
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        /*MyService myService = context.getBean(MyService.class);
        Student student = new Student();
        student.setName("aaron");
        student.setSex(1);
        student.setAddress("青岛市");
        student.setBirthday(new Date());
        myService.add(student);*/
    }

}
