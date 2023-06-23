package com.adriandborsan.clientback.log.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

//    private final LogEntrySender logEntrySender;
//
//    public LoggerAspect(LogEntrySender logEntrySender) {
//        this.logEntrySender = logEntrySender;
//    }
//
//    @Pointcut("execution(* *(..))")
//    private void anyOperation() {
//    }
//
//    @Pointcut("within(com.adriandborsan.clientback.post.controllers..*)")
//    private void inControllers() {
//    }
//
//    @Around("anyOperation() &&  inControllers()")
//    private Object logAll(ProceedingJoinPoint pjp) throws Throwable {
//        logEntrySender.send(new LogEntry(pjp.toLongString()));
//        return pjp.proceed();
//    }
}
