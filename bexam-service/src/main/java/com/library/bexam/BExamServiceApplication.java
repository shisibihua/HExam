package com.library.bexam;

import com.library.bexam.common.util.ProcessId;
import com.library.bexam.common.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.MultipartConfigElement;


@SpringBootApplication(scanBasePackages = "com.library.bexam")
@MapperScan("com.library.bexam.dao")
@ServletComponentScan
@EnableScheduling
public class BExamServiceApplication {
    private final static String START_COMPLETION="程序启动完成。。。。";
    @Autowired
    private static Logger logger = LoggerFactory.getLogger(BExamServiceApplication.class);
    @Value("${poolSize}")
    private int poolSize;

    //最大上传单文件大小
    @Value("${spring.http.multipart.maxFileSize}")
    private  String maxFileSize;
    //最大上传多文件大小
    @Value("${spring.http.multipart.maxRequestSize}")
    private  String maxRequestSize;

    public static void main(String[] args) {
        //写入PId 用于shutDown
        ProcessId.setProcessID();
        ApplicationContext app = SpringApplication.run(BExamServiceApplication.class, args);
        SpringUtil.setApplicationContext(app);
        SpringUtil.scannerBeans();
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println(START_COMPLETION);
        logger.error(START_COMPLETION);
    }

    //设置定时器
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler=new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(poolSize);
        taskScheduler.setThreadNamePrefix("springboot-task");
        return taskScheduler;
    }
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(maxFileSize); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }


}