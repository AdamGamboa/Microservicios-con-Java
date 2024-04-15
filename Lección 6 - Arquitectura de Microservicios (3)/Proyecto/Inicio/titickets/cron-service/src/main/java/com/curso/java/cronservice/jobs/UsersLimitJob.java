package com.curso.java.cronservice.jobs;

import com.curso.java.cronservice.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UsersLimitJob {
    
    private final UserService userService;
    
    public UsersLimitJob(UserService userService){
        this.userService = userService;
    }
    
    @Scheduled(cron = "0 0 * * * ?")
    public void resetLimitEveryHour() {
        this.userService.resetUserLimits();
    }
    
}
