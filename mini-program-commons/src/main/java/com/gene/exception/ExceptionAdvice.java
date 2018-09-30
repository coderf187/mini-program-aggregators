package com.gene.exception;

import com.gene.dto.base.ResponseVo;
import com.gene.enums.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

/**
 * 统一错误处理入口
 */
@ControllerAdvice
public class ExceptionAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseVo<String> handleControllerException(Exception ex, HandlerMethod handlerMethod) {
        LOG.error("接口错误统一处理 controller出错，类名：{} 方法名：{}", handlerMethod.getMethod().getDeclaringClass().getName(),
                handlerMethod.getMethod().getName());
        LOG.error("接口错误统一处理：", ex);
        String errorCode;
        String errorMessage;
        if (ex instanceof BusinessException) {
            errorCode = ((BusinessException) ex).getErrorCode();
            errorMessage = ex.getMessage();
        } else if (ex instanceof IllegalArgumentException) {
            errorCode = ResponseCode.BAD_REQUEST.getValue();
            errorMessage = ex.getMessage();
        } else {
            errorCode = ResponseCode.INTERNAL_SERVER_ERROR.getValue();
            errorMessage = ResponseCode.INTERNAL_SERVER_ERROR.getMessage();
        }
        return ResponseVo.ofError(errorCode, errorMessage);
    }
}
