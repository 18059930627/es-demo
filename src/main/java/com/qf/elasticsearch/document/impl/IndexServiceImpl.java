package com.qf.elasticsearch.document.impl;

import com.qf.elasticsearch.aop.MethodLog;
import com.qf.elasticsearch.document.IndexService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


/**
 * @Author chenzhongjun
 * @Date 2020/11/28
 */
@Service
public class IndexServiceImpl implements IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class.getName());

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @param indexName 索引名字
     * @param typeName 类型名字（表名）
     * @param request 创建索引请求
     */
    @Override
    public void createIndex(String indexName, String typeName, CreateIndexRequest request) {
        try {
            if(!existIndex(indexName)){
                CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
                logger.info("index:[{}], create result:{}", indexName, response.isAcknowledged());
            }else {
                logger.warn("index:[{}] is already exit.", indexName);
            }
        } catch (IOException e) {
            logger.error("createIndex fail. ex:{} ", e);
        }

    }

    @Override
    public void deleteIndex(String indexName) {
        if(existIndex(indexName)){
            DeleteIndexRequest deleteRequest = new DeleteIndexRequest(indexName);
            try {
                AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteRequest, RequestOptions.DEFAULT);
                logger.info("delete Index:[{}] result:{}", indexName, response.isAcknowledged());
            } catch (IOException e) {
                 logger.error("delete index:{} error. ex:{}", indexName, e);
            }
        }else {
            logger.warn("index:[{}] is not exit.", indexName);
        }
    }

    /**
     * 索引是否存在
     * @param indexName 索引名字
     * @return
     */
    @Override
    @MethodLog
    public boolean existIndex(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        boolean result = false;
        try {
            result = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            return result;
        } catch (IOException e) {
            logger.error("existIndex fail. ex:{} ", e);
        }
        logger.info("index:[{}] exist result:{}", indexName, result);
        return false;
    }

}
