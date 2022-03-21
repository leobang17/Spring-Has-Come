package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/error-page")
public class ErrorPageController {
    private static final String ERROR_EXCEPTION = RequestDispatcher.ERROR_EXCEPTION;
    private static final String ERROR_EXCEPTION_TYPE = RequestDispatcher.ERROR_EXCEPTION_TYPE;
    private static final String ERROR_MESSAGE = RequestDispatcher.ERROR_MESSAGE;
    private static final String ERROR_REQUEST_URI = RequestDispatcher.ERROR_REQUEST_URI;
    private static final String ERROR_SERVLET_NAME = RequestDispatcher.ERROR_SERVLET_NAME;
    private static final String ERROR_STATUS_CODE = RequestDispatcher.ERROR_STATUS_CODE;



    @RequestMapping("/400")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404\n\n\n\n\n\n");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info(("errorPage 500"));
        printErrorInfo(request);
        return "error-page/500";
    }

    // client에서 http request header에 accepts = application/json이라고 명시해 두면, 이 곳으로 응답하는 것.
    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500API(HttpServletRequest request, HttpServletResponse response) {
        log.info("API ErrorPage 500");
        Map<String, Object> result = new HashMap<>();
        Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
        result.put("status", request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("dispatchType = {}", request.getDispatcherType());
    }
}
