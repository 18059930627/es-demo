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

    @Test
    public void testAddDoc(){
        SmsLog smsLog = new SmsLog();
        smsLog.setCorpName("");

        docService.addDoc(indexName, typeName, JSONUtils.objectToJson(smsLog));
    }
}
