package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.TokenBlackList;
import com.example.andiamo_a_teatro.jobs.TokenBlackListJob;
import com.example.andiamo_a_teatro.repositories.TokenBlackListRepository;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TokenBlackListService {
    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;
    @Autowired
    private Scheduler scheduler;
    private static final Object lock = new Object();

    public List<String> tokenNotValidFromUtenteById(Long id_utente) {
        return tokenBlackListRepository.getTokenBlackListFromUtenteId(id_utente)
                .stream()
                .map(TokenBlackList::getToken)
                .toList();
    }

    public void createTokenBlackList(TokenBlackList tokenBlackList) {
        tokenBlackList.setInsertTime(LocalDateTime.now());
        tokenBlackListRepository.saveAndFlush(tokenBlackList);
    }

    public Boolean isTokenPresent(String token) {
        return tokenBlackListRepository.findAll().stream().map(TokenBlackList::getToken).toList().contains(token);
    }

    public List<TokenBlackList> getAll() {
        return tokenBlackListRepository.findAll();
    }

    @PostConstruct
    public void startScheduledJob() throws SchedulerException {
        JobDetail jobDetail = buildJobDetail();
        Trigger trigger = buildJobTrigger(jobDetail, new Date());
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private JobDetail buildJobDetail() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("tokenBlackListService", this);

        return JobBuilder.newJob(TokenBlackListJob.class)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, Date targetDate) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(targetDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(900)     //ogni 15 minuti
                        .repeatForever())
                .build();
    }

    public void executeScheduledTask() {
        synchronized (lock) {
            if (tokenBlackListRepository == null) return;
            if (!getAll().isEmpty()) {
                deleteTokens();
            }
        }
    }

    public void deleteTokens() {
        List<TokenBlackList> tokens = tokenBlackListRepository.findAll();
        if (tokens.isEmpty()) throw new NullPointerException();

        List<TokenBlackList> deleteTokens = tokens.stream()
                .filter(t -> Objects.nonNull(t.getInsertTime()) &&
                        Duration.between(t.getInsertTime(), LocalDateTime.now()).getSeconds() >= 20)
                .toList();

        tokenBlackListRepository.deleteAllInBatch(deleteTokens);
    }
}
