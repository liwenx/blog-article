package com.yy.blog.article.dao.article;

import com.yy.blog.article.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author liwenxing
 * @date 2018/1/24 12:11
 */
@Repository
public interface ArticleDao extends JpaRepository<Article,Serializable>{

}