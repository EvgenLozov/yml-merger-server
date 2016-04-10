package com.modifier.web.controller;

import com.company.exception.ModifierException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import company.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Yevhen
 */
@ControllerAdvice
public class ErrorHandler {

    private static final Logger logger = Logger.getLogger(ErrorHandler.class.getName());
    private static ObjectWriter jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @ExceptionHandler(ModifierException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleModifierException(ModifierException ex, HttpServletResponse response){
        writeErrorMessage(500, ex.getMessage(), response);
    }

    public static void writeErrorMessage(int code, String message, HttpServletResponse response) {
        if (code != 0 && message!= null && response != null){
            ErrorMessage errorMessage = new ErrorMessage(code, message);
            logger.severe(message);

            try {
                response.setStatus(code);
                response.getWriter().write(jsonWriter.writeValueAsString(errorMessage));
            } catch (IOException e) {
                logger.severe("Error writing \"" + errorMessage.getErrorCode().toString()
                        +"\" state to response: " + e.getMessage());
            }
        }
    }
}
