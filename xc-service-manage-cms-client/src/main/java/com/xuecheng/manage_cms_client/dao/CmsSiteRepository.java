package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 创建时间:2019/12/26
 * Name:ZHANGFENG
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
