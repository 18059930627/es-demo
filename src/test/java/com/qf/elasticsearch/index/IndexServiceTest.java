package com.qf.elasticsearch.index;

import com.qf.elasticsearch.ElasticSearchApplication;
import com.qf.elasticsearch.document.IndexService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

/**
 * @Author chenzhongjun
 * @Date 2020/11/28
 */
@SpringBootTest(classes = ElasticSearchApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexServiceTest {

    private String indexName = "sms-logs-index";

    private String typeName = "sms-logs-type";

    @Autowired
    private IndexService indexService;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        buildSettings(request);
        buildMapping(request);
        indexService.createIndex(indexName, typeName, request);
    }

    /**
     * type(表)的配置 - 表字段、字段类型  mapping规则
     * @param request
     */
    private void buildMapping(CreateIndexRequest request) throws IOException {
        XContentBuilder contentBuilder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")

                .startObject("createDate")
                .field("type","long")
                .endObject()

                .startObject("sendDate")
                .field("type","long")
                .endObject()

                .startObject("longCode")
                .field("type","keyword")
                .endObject()

                .startObject("mobile")
                .field("type","keyword")
                .endObject()

                .startObject("corpName")
                .field("type","text")
                .field("analyzer","ik_max_word")
                .endObject()

                .startObject("smsContent")
                .field("type","text")
                .field("analyzer","ik_max_word")
                .endObject()

                .startObject("state")
                .field("type","integer")
                .endObject()

                .startObject("operatorId")
                .field("type","integer")
                .endObject()

                .startObject("province")
                .field("type","keyword")
                .endObject()


                .startObject("ipAddr")
                .field("type","ip")
                .field("index","false")
                .endObject()

                .startObject("replyTotal")
                .field("type","integer")
                .endObject()


                .startObject("fee")
                .field("type","integer")
                .endObject()

                .endObject()
                .endObject();
        request.mapping(typeName, contentBuilder);
    }

    /**
     * 索引的配置  setting规则
     * @param request
     */
    private void buildSettings(CreateIndexRequest request) {
        request.settings(Settings.builder()
                .put("number_of_shards",3)  //主分片
                .put("number_of_replicas",2)); //副分片
    }

    @Test
    public void testDeleteIndex(){
        indexService.deleteIndex(indexName);
    }

    @Test
    public void testExitIndex(){
        System.out.println(indexService.existIndex(indexName));
    }
}
