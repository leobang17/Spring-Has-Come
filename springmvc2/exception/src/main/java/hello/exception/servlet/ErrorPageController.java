package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/error-page")
public class ErrorPageController {

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

    private void printErrorInfo(HttpServletRequest request) {
        log.info("dispatchType = {}", request.getDispatcherType());
    }
}
