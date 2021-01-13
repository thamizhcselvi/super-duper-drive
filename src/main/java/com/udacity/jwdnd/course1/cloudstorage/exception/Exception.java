package com.udacity.jwdnd.course1.cloudstorage.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class Exception {

    @ExceptionHandler(StorageException.class)
    public ModelAndView exception(StorageException exception, RedirectAttributes redirectAttributes)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
