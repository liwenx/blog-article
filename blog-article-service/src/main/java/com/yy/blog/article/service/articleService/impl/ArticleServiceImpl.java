package com.yy.blog.article.service.articleService.impl;

import com.yy.blog.article.dao.article.ArticleDao;
import com.yy.blog.article.domain.article.Article;
import com.yy.blog.article.domain.article.query.ArticleQuery;
import com.yy.blog.article.service.articleService.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liwenxing
 * @date 2018/1/24 11:12
 */
@Component("articleService")
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private KafkaTemplate<String, Object> template;

    @Override
    public void insertArticle(ArticleQuery articleQuery) {
        Article article = new Article();
        BeanUtils.copyProperties(articleQuery, article);
        articleDao.save(article);
        template.send("zjmTest", article.getId(), article);
    }
}
