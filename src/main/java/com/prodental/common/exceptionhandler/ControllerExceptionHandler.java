package com.prodental.common.exceptionhandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    public static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        //Initializing the HttpServletRequest object to get the path of the request that caused the error.
        HttpServletRequest requestServlet = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = requestServlet.getRequestURI();

        //Creating the body of the response
        CustomErrorMessage customErrorMessage = CustomErrorMessage.builder()
                .timestamp(LocalDateTime.now().format(formatter))
                .status(status.value())
                .error(status.toString())
                .message("The request contains invalid data, probably left some field empty or null.")
                .path(path)
                .build();

        return ResponseEntity.badRequest().body(customErrorMessage);
    }

    protected ResponseEntity<Object> handleUserNotFound(MethodArgumentNotValidException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status,
                                                        WebRequest request) {

        HttpServletRequest requestServlet = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = requestServlet.getRequestURI();

        CustomErrorMessage customErrorMessage = CustomErrorMessage.builder()
                .timestamp(LocalDateTime.now().format(formatter))
                .status(404)
                .error("Not Found")
                .message("The user was not found.")
                .path(path)
                .build();

        return ResponseEntity.status(404).body(customErrorMessage);
    }
}
