package com.qf.elasticsearch.document;

import org.elasticsearch.action.index.IndexResponse;

/**
 * @Author chenzhongjun
 * 添加文档相关
 * @Date 2020/11/29
 */
public interface DocService {

    /**
     * 添加数据
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param json      数据
     */
    IndexResponse addDoc(String indexName, String typeName, String json);
}
