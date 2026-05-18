package uz.java.kpisystem.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.java.kpisystem.exception.CustomNotFoundException;
import uz.java.kpisystem.util.ErrorUtil;
import uz.java.kpisystem.util.Translator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice // gloabal chiqqan exception handle qiladi(faqat @Valid dan tashqari)
@Slf4j
public class GlobalExceptionHandler {
    private final Translator translator;

    public GlobalExceptionHandler(Translator translator) {
        this.translator = translator;
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Object> handleCustomNotFoundException(CustomNotFoundException ex) {
        log.error("GenericNotFoundException on: {}", ErrorUtil.getStacktrace(ex));
        return new ResponseEntity<>(Map.of("message", translator.toLocale(ex.getMessage())),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidError(final MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException on: {}", ErrorUtil.getStacktrace(ex));
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError ->
                {
                    if (!translator.toLocale(fieldError.getDefaultMessage()).equals(fieldError.getDefaultMessage())) {
                        return Objects.requireNonNull(translator.toLocale(fieldError.getDefaultMessage()));
                    } else {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }

                }).toList();
        return new ResponseEntity<>(Map.of("message", errors.get(0)), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
