package com.merger;

import com.merger.merge.MergeByDOMService;
import com.merger.merge.MergeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Yevhen on 2015-06-28.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MergeService mergeService(){
        return new MergeByDOMService(new ConfigProvider().get());
    }
}