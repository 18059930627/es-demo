package com.qf.elasticsearch.document.impl;

import com.qf.elasticsearch.aop.MethodLog;
import com.qf.elasticsearch.document.DocService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author chenzhongjun
 * @Date 2020/11/29
 */
@Service
public class DocServiceImpl implements DocService {

    private static final Logger logger = LoggerFactory.getLogger(DocServiceImpl.class.getName());

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 添加数据
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param content   数据
     */
    @Override
    public IndexResponse addDoc(String indexName, String typeName, String content) {
        IndexRequest indexRequest = new IndexRequest(indexName, typeName);
        indexRequest.source(content, XContentType.JSON);
        IndexResponse response = null;
        try {
            response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("addDoc fail! ex:{} ", e);
        }
        return response;
    }
}
