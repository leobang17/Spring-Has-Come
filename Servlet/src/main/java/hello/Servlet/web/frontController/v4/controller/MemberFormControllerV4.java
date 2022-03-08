package hello.servlet.web.frontController.v4.controller;

import hello.servlet.web.frontController.v4.ControllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        // viewName만 return 해주면 된다.
        return "new-form";
    }
}
