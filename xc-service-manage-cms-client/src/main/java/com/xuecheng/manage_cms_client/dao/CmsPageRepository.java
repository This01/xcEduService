package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 创建时间:2019/12/23
 * Name:ZHANGFENG
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    //根据页面名称、站点id、页面访问路径查询
    public CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
