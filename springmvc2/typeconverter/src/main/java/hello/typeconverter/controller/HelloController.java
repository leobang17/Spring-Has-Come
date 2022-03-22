package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data"); // String type 조회.
        Integer intValue = Integer.valueOf(data);

        System.out.println("intValue = " + intValue);
        return "ok";
    }

    @GetMapping("/hello-v2") // "1,000"
    public String helloV2(@RequestParam Integer data) {
        System.out.println("data = " + data);
        return "ok";
    }


    @GetMapping("/ip-port")
    public String helloV3(@RequestParam IpPort data) {
        System.out.println("data.getIp() = " + data.getIp());
        System.out.println("data.getPort() = " + data.getPort());
        return "ok";
    }
}
