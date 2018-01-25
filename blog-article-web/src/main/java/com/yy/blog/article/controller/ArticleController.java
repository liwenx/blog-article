package com.yy.blog.article.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yy.blog.article.commons.util.Result;
import com.yy.blog.article.domain.article.query.ArticleQuery;
import com.yy.blog.article.service.articleService.ArticleService;
import com.yy.rpc.domain.ElasticsearchQueryParam;
import com.yy.rpc.elasticsearch.BlogElasticsearchServiceRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liwenxing
 * @date 2018/1/24 10:14
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Reference
    private BlogElasticsearchServiceRpc blogElasticsearchServiceRpc;

    @RequestMapping(value = "/insertArticle", method = {RequestMethod.GET, RequestMethod.POST})
    public Result insertArticle(ArticleQuery articleQuery) {
        Result result = new Result(false);
        articleService.insertArticle(articleQuery);
        result.setSuccess(true);
        result.setSuccessMessage("文章保存成功！");
        return result;
    }

    @RequestMapping(value = "/queryArticle", method = {RequestMethod.GET, RequestMethod.POST})
    public Result queryArticle(ArticleQuery articleQuery) {
        Result result = new Result(false);
        ElasticsearchQueryParam elasticsearchQueryParam = new ElasticsearchQueryParam();
        elasticsearchQueryParam.setKeyWord("这是");
        blogElasticsearchServiceRpc.queryListArticleByWord(elasticsearchQueryParam).get("articleList");
        return result;
    }
}
