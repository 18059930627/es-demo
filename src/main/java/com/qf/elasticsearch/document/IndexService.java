package com.qf.elasticsearch.document;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;

/**
 * @Author chenzhongjun
 * 索引服务
 * @Date 2020/11/28
 */
public interface IndexService {

    /**
     * 创建索引
     * @param indexName 索引名字
     * @param typeName 类型名字（表名）
     * @param request 创建索引请求
     */
    void createIndex(String indexName, String typeName, CreateIndexRequest request);

    /**
     * 删除索引
     * @param indexName 索引名字
     */
    void deleteIndex(String indexName);

    /**
     * 索引是否存在
     * @param indexName 索引名字
     * @return
     */
    boolean existIndex(String indexName);
}
