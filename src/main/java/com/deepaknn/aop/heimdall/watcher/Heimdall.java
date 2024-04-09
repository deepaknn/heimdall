package com.deepaknn.aop.heimdall.watcher;

import com.deepaknn.aop.heimdall.watcher.alert.EmailService;
import com.deepaknn.aop.heimdall.watcher.logger.LogDetails;
import com.deepaknn.aop.heimdall.watcher.logger.SharedLogQueueManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Aspect
@Component
public class Heimdall {

    private static final ThreadLocal<String> apiIdThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> emailSentThreadLocal = ThreadLocal.withInitial(() -> false);
    private final Lock emailLock = new ReentrantLock();

    @Value("${email.from}")
    private String from;

    @Value("${email.to}")
    private String to;

    @Value("${email.subject:Error Alert}")
    private String subject;

    @Value("${log.print:false}")
    private boolean printLog;

    @Pointcut("@within(com.deepaknn.aop.heimdall.watcher.EnableHeimdall)")
    public void enableHeimdall(){}

    @Autowired
    private EmailService emailService;

    @Autowired
    SharedLogQueueManager sharedLogQueueManager;

    @Around("enableHeimdall()")
    public Object logMethodCall(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        // Generate and set API ID if not already set
        if (apiIdThreadLocal.get() == null) {
            String apiId = UUID.randomUUID().toString();
            apiIdThreadLocal.set(UUID.randomUUID().toString());
        }

        String apiId = apiIdThreadLocal.get();
        String methodName = pjp.getSignature().getName();

        // Get method arguments
        Object[] args = pjp.getArgs();
        StringBuilder argumentStr = new StringBuilder();
        for (Object arg : args) {
            if (argumentStr.length() > 0) {
                argumentStr.append(", ");
            }
            argumentStr.append(arg.toString());
        }

        // Print method name and arguments
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        LogDetails inputLog = new LogDetails(apiId, methodName, DataDirection.INPUT.getValue(), argumentStr.toString(), "", currentTimestamp, null);
        sharedLogQueueManager.enqueue(inputLog);

        if(printLog){
            System.out.println(inputLog);
        }

        Object output;
        try {
            output = pjp.proceed();
        } catch (Exception e) {
            // Alert about the error, Send email in a synchronized block
            emailLock.lock();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString();
            try {
                if (!emailSentThreadLocal.get()) {
                    emailService.sendEmail(to, subject + " " + apiId, e.getMessage(), from);
                    emailSentThreadLocal.set(true); // Set the flag to indicate email has been sent for this thread
                }
            } finally {
                emailLock.unlock();
                currentTimestamp = new Timestamp(System.currentTimeMillis());
                long elapsedTime = System.currentTimeMillis() - start;
                LogDetails errorDetails = new LogDetails(apiIdThreadLocal.get(), methodName, DataDirection.OUTPUT.getValue(), "", stackTrace, currentTimestamp, elapsedTime);
                sharedLogQueueManager.enqueue(errorDetails);
                if (printLog){
                    System.out.println(errorDetails);
                }
                throw e;
            }
        }

        currentTimestamp = new Timestamp(System.currentTimeMillis());
        long elapsedTime = System.currentTimeMillis() - start;

        LogDetails outputDetails = new LogDetails(apiIdThreadLocal.get(), methodName,DataDirection.OUTPUT.getValue(), output.toString(), "",currentTimestamp, elapsedTime);
        sharedLogQueueManager.enqueue(outputDetails);
        if (printLog){
            System.out.println(outputDetails);
        }
        return output;
    }
}

