package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.entities.ScheduledNews;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.request.ScheduledNewsRequest;
import com.example.andiamo_a_teatro.repositories.ScheduledNewsRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledNewsService implements Job {
    @Autowired
    private ScheduledNewsRepository scheduledNewsRepository;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private NewsService newsService;

    public ScheduledNews getScheduledNewsById(Long id) throws EntityNotFoundException {
        Optional<ScheduledNews> optionalScheduledNews = scheduledNewsRepository.findById(id);
        return optionalScheduledNews.orElseThrow(() -> new EntityNotFoundException(id,"ScheduledNews"));
    }

    public List<ScheduledNews> getAllScheduledNews() {
        return scheduledNewsRepository.findAll();
    }

    public ScheduledNewsRequest createScheduledNews(ScheduledNewsRequest request) throws SchedulerException {
        ScheduledNews scheduledNews = ScheduledNews
                .builder()
                .title(request.getTitle())
                .body(request.getBody())
                .publishTime(request.getTargetDate())
                .build();
        scheduledNewsRepository.saveAndFlush(scheduledNews);
        JobDetail jobDetail = buildJobDetail(scheduledNews);
        Trigger trigger = buildJobTrigger(jobDetail, request.getTargetDate());
        scheduler.scheduleJob(jobDetail, trigger);
        return request;
    }

    private JobDetail buildJobDetail(ScheduledNews news) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("entity", news);
        jobDataMap.put("id", news.getId());
        return JobBuilder.newJob(ScheduledNewsService.class)
                .withIdentity(String.valueOf(news.getId()), "news")
                .storeDurably()
                .setJobData(jobDataMap)
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, Date targetDate) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(targetDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        ScheduledNews scheduledNews = (ScheduledNews) jobDataMap.get("entity");
        try {
            deleteScheduledNewsById(scheduledNews.getId());
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        News news = News
                .builder()
                .title(scheduledNews.getTitle())
                .body(scheduledNews.getBody())
                .build();
        newsService.createNews(news);
        System.out.println("News inserita con successo!");
    }

    public ScheduledNewsRequest updateScheduledNews(Long id, ScheduledNewsRequest request) throws SchedulerException {
        deleteScheduledNewsById(id);
        return createScheduledNews(request);
    }

    public void deleteScheduledNewsById(Long id) throws SchedulerException {
        JobKey jobKey = new JobKey(String.valueOf(id), "news");
        scheduler.deleteJob(jobKey);
        scheduledNewsRepository.deleteById(id);
    }
}
