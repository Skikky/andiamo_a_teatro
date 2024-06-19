package com.example.andiamo_a_teatro.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        // Configurazioni aggiuntive, se necessarie
        return factoryBean;
    }
}
