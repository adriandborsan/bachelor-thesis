package com.adriandborsan.clientback.log.aspects;

import com.adriandborsan.clientback.log.dto.LogEntry;
import com.adriandborsan.clientback.log.senders.LogEntrySender;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggerAspect {

    private final LogEntrySender logEntrySender;

    @Pointcut("execution(* *(..))")
    private void anyOperation() {
    }

    @Pointcut("within(com.adriandborsan.clientback.post.controllers..*)")
    private void inControllers() {
    }

    @Around("anyOperation() &&  inControllers()")
    private Object logAll(ProceedingJoinPoint pjp) throws Throwable {
        logEntrySender.send(new LogEntry(pjp.toLongString()));
        return pjp.proceed();
    }
}
