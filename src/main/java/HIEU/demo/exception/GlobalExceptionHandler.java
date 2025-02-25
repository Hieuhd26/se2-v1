package HIEU.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IOException.class)
    public String handleNotFoundException(IOException ex, Model model) {
        model.addAttribute("errorMessage", "Error when working with file");
        return "error";
    }
    @ExceptionHandler(AppException.class)
    public String handleNotFoundException(AppException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "Error, you can try again");
        return "error";
    }
}
