package com.yy.blog.article.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 文章实体类
 * @author liwenxing
 * @date 2018/1/23 16:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "blog_article")
public class Article implements Serializable{
    public static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 浏览量
     */
    private String pageviews;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String describes;

    /**
     * 点赞量
     */
    private String greatNumber;

    /**
     * 踩量
     */
    private String badNumber;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建日期
     */
    private String createdDate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 类别
     */
    private Integer category;

    /**
     * 是否已发布
     */
    private Integer isPublish;

    /**
     * 时间戳
     */
    private String ts;

    /**
     * 删除标志
     */
    private Integer dr;
}
