package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CmsConfigService {
    @Autowired
    CmsConfigRepository cmsConfigRepository;

    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> all = cmsConfigRepository.findById(id);
        if(all.isPresent()){
            CmsConfig cmsConfig = all.get();
            return cmsConfig;
        }
        return null;
    }
}
