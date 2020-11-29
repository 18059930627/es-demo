package com.qf.elasticsearch.document.impl;

import com.qf.elasticsearch.aop.MethodLog;
import com.qf.elasticsearch.document.DocService;
import com.qf.elasticsearch.doman.SmsLog;
import com.qf.elasticsearch.util.JSONUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    @MethodLog
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

    /**
     * 添加文档（带有ID）
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param json      数据
     * @param id
     * @return
     */
    @Override
    @MethodLog
    public IndexResponse addDocWithId(String indexName, String typeName, String json, String id) {
        IndexRequest indexRequest = new IndexRequest(indexName, typeName, id);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse response = null;
        try {
            response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("addDocWithId fail! ex:{} ", e);
        }
        return response;
    }

    /**
     * 更新数据
     *
     * @param indexName      索引名
     * @param typeName       表名
     * @param id             ID
     * @param updateContents 更新数据内容
     * @return
     */
    @Override
    @MethodLog
    public UpdateResponse updateDoc(String indexName, String typeName, String id, Map<String, Object> updateContents) {
        UpdateRequest updateRequest = new UpdateRequest(indexName, typeName, id)
                .doc(updateContents);  //根据语法规则
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("updateDoc fail. ex:{}", e);
        }
        return updateResponse;
    }

    /**
     * 删除指定文档
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param id        Id
     * @return
     */
    @Override
    @MethodLog
    public DeleteResponse deleteDocById(String indexName, String typeName, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, typeName, id);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("deleteDocById fail. ex:{} ", e);
        }
        return deleteResponse;
    }


    /**
     * 批量操作API 根据业务需求来写(可以传个操作类型的枚举:UPDATE、DELETE、CREATE、INDEX)
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param ids       ids
     */
    @Override
    @MethodLog
    public BulkResponse bulkApi(String indexName, String typeName, List<String> ids) {
        BulkRequest bulkRequest = new BulkRequest();
        ids.forEach((id) -> {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, typeName, id);
            bulkRequest.add(deleteRequest);  //IndexRequest、UpdateRequest
        });
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("bulkApi fail. ex:{} ", e);
        }
        return bulkResponse;
    }


    /**
     * 批量获取
     *
     * @param indexName 索引名
     * @param typeName  表名
     * @param ids       ids
     * @return
     */
    @Override
    @MethodLog
    public List<SmsLog> mGet(String indexName, String typeName, List<String> ids) {
        MultiGetRequest multiGetRequest = new MultiGetRequest();
        List<SmsLog> smsLogs = new ArrayList<>();
        ids.forEach((id) -> {
            multiGetRequest.add(indexName, typeName, id);
        });
        try {
            MultiGetResponse response = restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
            Iterator<MultiGetItemResponse> iterator = response.iterator();
            while (iterator.hasNext()) {
                String source = iterator.next().getResponse().getSourceAsString();
                if(!StringUtils.isEmpty(source)){
                    smsLogs.add(JSONUtils.JsonToObject(source, SmsLog.class));
                }
            }
        } catch (IOException e) {
            logger.error("mget fail. ex:{} ", e);
        }
        return smsLogs;
    }


}
