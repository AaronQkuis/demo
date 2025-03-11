package com.interview.handle;


import com.interview.pojo.vo.ExceptVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述: 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptVo globalException(IllegalArgumentException e) {
        logErrorStack(e, 2);
        ExceptVo exceptVo = new ExceptVo();
        exceptVo.setCode(-1);
        exceptVo.setMessage(e.getMessage());
        return exceptVo;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptVo validException(MethodArgumentNotValidException e) {
        ExceptVo exceptVo = new ExceptVo();
        exceptVo.setCode(500);
        exceptVo.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        return exceptVo;
    }

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(Exception ex) {
        log.error("message:{}", ex.getMessage(), ex);
        ExceptVo exceptVo = new ExceptVo();
        exceptVo.setCode(500);
        exceptVo.setMessage(ex.getMessage());
        return exceptVo;
    }

    /**
     * 打印指定异常栈行数
     */
    private void logErrorStack(Throwable e, int line) {
        StringBuilder msg = new StringBuilder(e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        int n = 0;
        while (line > 0 && stackTrace != null && n < stackTrace.length) {
            msg.append("\n  ").append(stackTrace[n]);
            n++;
        }
        log.warn(msg.toString());
    }

}
