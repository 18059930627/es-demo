package com.qf.elasticsearch.document;

import com.qf.elasticsearch.doman.SmsLog;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import java.util.List;
import java.util.Map;

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

    /**
     * 添加数据(带有ID)
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param json      数据
     */
    IndexResponse addDocWithId(String indexName, String typeName, String json, String id);

    /**
     * 更新数据
     *
     * @param indexName      索引名
     * @param typeName       表名
     * @param id             ID
     * @param updateContents 更新数据内容
     * @return
     */
    UpdateResponse updateDoc(String indexName, String typeName, String id, Map<String, Object> updateContents);

    /**
     * 删除指定文档
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param id        Id
     * @return
     */
    DeleteResponse deleteDocById(String indexName, String typeName, String id);

    /**
     * 批量操作API
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param ids       ids
     */
    BulkResponse bulkApi(String indexName, String typeName, List<String> ids);

    /**
     * 批量获取
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param ids       ids
     * @return
     */
    List<SmsLog> mGet(String indexName, String typeName, List<String> ids);
}
