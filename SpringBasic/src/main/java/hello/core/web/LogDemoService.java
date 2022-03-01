package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class LogDemoService {
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;


    @Autowired
//    public LogDemoService(ObjectProvider<MyLogger> myLoggerProvider) {
    public LogDemoService(MyLogger myLogger) {
//        this.myLoggerProvider = myLoggerProvider;
        this.myLogger = myLogger;
    }

    public void logic(String id) {
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
