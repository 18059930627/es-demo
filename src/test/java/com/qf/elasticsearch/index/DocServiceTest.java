package com.qf.elasticsearch.index;

import com.qf.elasticsearch.ElasticSearchApplication;
import com.qf.elasticsearch.document.DocService;
import com.qf.elasticsearch.doman.SmsLog;
import com.qf.elasticsearch.util.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author chenzhongjun
 * @Date 2020/11/29
 */
@SpringBootTest(classes = ElasticSearchApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DocServiceTest {

    private String indexName = "sms-logs-index";

    private String typeName = "sms-logs-type";

    @Autowired
    private DocService docService;

    /**
     * 添加数据
     */
    @Test
    public void testAddDoc() {
        SmsLog smsLog = new SmsLog();
        smsLog.setCorpName("test add doc");
        smsLog.setMobile("18059930627");
        smsLog.setCreateDate(System.currentTimeMillis());
        smsLog.setSendDate(System.currentTimeMillis());
        smsLog.setLongCode("123");
        smsLog.setSmsContent("一言难尽啊");
        smsLog.setState(0);
        smsLog.setProvince("福建");
        smsLog.setOperatorId(2);
        smsLog.setIpAddr("192.168.40.188");
        smsLog.setFee(3);
        docService.addDoc(indexName, typeName, JSONUtils.objectToJson(smsLog));
    }

    /**
     * 添加文档（带有ID）
     */
    @Test
    public void testAddDocWithId() {
        SmsLog smsLog = new SmsLog();  //对象传输，如果属性值为Null，还是会保存到ES的文档中。 需要使用JSON排除null的属性
        smsLog.setCorpName("test add doc with id");
        smsLog.setMobile("18059930627");
        smsLog.setCreateDate(System.currentTimeMillis());
        smsLog.setSendDate(System.currentTimeMillis());
        smsLog.setLongCode("123");
        smsLog.setSmsContent("一言难尽啊");
        smsLog.setState(0);
        smsLog.setProvince("福建");
        smsLog.setOperatorId(2);
        smsLog.setIpAddr("192.168.40.188");
        smsLog.setFee(3);
        docService.addDocWithId(indexName, typeName, JSONUtils.objectToJson(smsLog), "2");
    }


    /**
     * 更新数据
     */
    @Test
    public void testUpdateDoc() {
        Map<String, Object> updateContents = new HashMap<>();
        updateContents.put("ipAddr", "1.2.3.4");
        updateContents.put("age", 11);
        docService.updateDoc(indexName, typeName, "2", updateContents);
    }

    /**
     * 删除文档
     */
    @Test
    public void testDeleteDocById(){
        docService.deleteDocById(indexName, typeName, "2");
    }

    /**
     * 测试批量操作
     */
    @Test
    public void testBulkApi(){
        docService.bulkApi(indexName, typeName, Arrays.asList("1","2"));
    }

    /**
     * 批量获取
     */
    @Test
    public void mGet(){
        docService.mGet(indexName, typeName, Arrays.asList("1", "2"));
    }
}
