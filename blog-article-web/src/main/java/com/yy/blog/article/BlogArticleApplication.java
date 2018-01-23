package com.yy.blog.article;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author liwenxing
 * @date 2018/1/23 15:54
 */
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@Import(FdfsClientConfig.class)
@SpringBootApplication
public class BlogArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogArticleApplication.class, args);
    }
}
