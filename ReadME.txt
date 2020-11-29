# -------------------------------------创建映射字段(创建表) ---------------------------------

1.type:类型  long、integer、float... keyword(该属性不会被分词)
2.index:是否被索引。默认true -> 相当于sql语句中select * from where xxx = ?; 中的xxx字段
3.store：是否做存储。默认false -> 相当于sql语句中selectxxx中的xxx，可以作为搜索结果显示。
4.analyzer:分词器。默认选择Standard分词器。中文单字分词
注意：
    (1)在solr和lucene中，store设置为false，用户的搜索结果中是不会显示出来的。而在es中，在创建文档索引时，会将文档的原始数据备份，
保存到一个叫做_source的属性中。可以通过过滤_source选择哪些可以显示/不显示。如果设置为true，会在_source以外额外存储一份数据。
即便store设置为false，也可以搜到结果,所以设置为true是多余的。
    (2)index和store不能同为false


# ---------------------------------------bulk(批量操作) -------------------------------------

使用Bulk API 实现批量操作,bulk的格式：
{action:{metadata}} \n
{requestbody}\n

1.action:(行为)

    (1)create：文档不存在时创建

    (2)update:更新文档

    (3)index:创建新文档或替换已有文档

    (4)delete:删除一个文档

2.metadata：_index,_type,_id

注意：create 和 index的区别。如果数据存在，使用create操作失败，会提示文档已经存在，使用index则可以成功执行。

eg:
(1){"delete":{"_index":"lib","_type":"user","_id":"1"}}

(2)POST /lib2/books/_bulk
{"delete":{"_index":"lib2","_type":"books","_id":4}}
{"create":{"_index":"tt","_type":"ttt","_id":"100"}}
{"name":"lisi"}
{"index":{"_index":"tt","_type":"ttt"}}
{"name":"zhaosi"}
{"update":{"_index":"lib2","_type":"books","_id":"4"}}
{"doc":{"price":58}}






#创建索引
#   ------------------  start ----------------------
PUT /qf
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 2
  }
}

#删除索引
DELETE /qf

#查看索引
GET /qf

#查看索引是否存在
HEAD /qf
#   ------------------  ending ----------------------




#创建映射字段(创建表)
#   ------------------  start ----------------------
#type:类型  long、integer、float... keyword(该属性不会被分词)
#index:是否被索引 默认true                     相当于sql语句中select * from where xxx = ?; 中的xxx字段
#store：是否做存储 默认false         相当于sql语句中selectxxx中的xxx，可以作为搜索结果显示。 在solr和lucene中，store设置为false，用户的搜索结果中是不会显示出来的。而在es中，在创建文档索引时，会将文档的原始数据备份，保存到一个叫做_source的属性中。可以通过过滤_source选择哪些可以显示/不显示。如果设置为true，会在_source以外额外存储一份数据。即便store设置为false，也可以搜到结果,所以设置为true是多余的。
#analyzer:分词器 默认选择Standard分词器。中文单字分词
#注意: index和store不能同为false
PUT /qf/_mapping/items
{
  "properties":{
    "title":{
      "type":"text",
      "index":true,
      "store":false,
      "analyzer":"ik_max_word"
    },
    "sellpoint":{
      "type":"text",
      "index":true,
      "store":true
    },
    "images":{
      "type":"keyword",
      "index":false
    },
    "price":{
      "type":"float"
    }
  }
}

#查看映射关系(查看表结构)
GET qf/_mapping/items

#   ------------------  ending ----------------------




#例子
#   ------------------  start ----------------------
PUT /sms-logs-index
{
  "settings": {
    "number_of_shards": 3,
    "number_of_replicas": 2
  }
}

PUT sms-logs-index/_mapping/sms-logs-type
{
  "properties": {
    "createDate":{
      "type":"long"
    },
    "sendDate":{
      "type":"long"
    },
    "longCode":{
      "type":"keyword"
    },
    "mobile":{
      "type":"keyword"
    },
    "corpName":{
      "type":"text",
      "analyzer":"ik_max_word"
    },
    "smsContent":{
      "type":"text",
      "analyzer":"ik_max_word"
    },
    "state":{
      "type":"integer"
    },
    "operatorId":{
      "type":"integer"
    },
    "province":{
      "type":"keyword"
    },
    "ipAddr":{
      "type":"ip",
      "index":false
    },
    "replyTotal":{
      "type":"integer"
    },
    "fee":{
      "type":"integer"
    }
  }
}

GET sms-logs-index/_mapping/sms-logs-type

HEAD sms-logs-index/_mapping/sms-logs-type
#   ------------------  ending ----------------------


# --------------------  start  ----------------------

#往type里添加数据  相同Id可以继续添加，但是后面添加的会覆盖前面本身存在的数据。
POST /sms-logs-index/sms-logs-type/2
{
  "corpName":"盒马鲜生",
  "mobile":"13100000000",
  "createDate":"",
  "sendDate":"",
  "longCode":"10660000988",
  "smsContent":"【盒马】您尾号12345678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：刘三，电话：13800000000",
  "state":0,
  "province":"北京",
  "operatorId":2,
  "ipAddr":"10.126.2.9",
  "replyTotal":15,
  "fee":5

}

#获取数据
GET /sms-logs-index/sms-logs-type/1



#更新数据
#没有的字段  会不会添加? -> 会
POST /sms-logs-index/sms-logs-type/2/_update
{
  "doc":{
    "fee":4
  }
}

#删除数据
DELETE /sms-logs-index/sms-logs-type/1

#批量操作
#使用Bulk API 实现批量操作,bulk的格式：
#{action:{metadata}} \n
#{requestbody}\n
#action:(行为)
#create：文档不存在时创建
#update:更新文档
#index:创建新文档或替换已有文档
#delete:删除一个文档
#metadata：_index,_type,_id
#create 和index的区别
#如果数据存在，使用create操作失败，会提示文档已经存在，使用index则可以成功执行。

#bulk 批量添加,批量的时候第一行为id 列,第二行为数据列,中间不能出现换行
POST /sms-logs-index/sms_logs_type/_bulk
{"index":{"_id":1}}
{"corpName":"途虎养车","mobile":"13800000000","createDate":"2020-02-18 19:19:19","sendDate":"2020-02-18 19:19:19","longCode":"10690000988","smsContent":"【途虎养车】亲爱的张三先生/女士，您在途虎购买的货品(单号TH123456)已到指定安装店多日，现需与您确认订单的安装情况，请点击链接按实际情况选择（此链接有效期为72H）。您也可以登录途虎APP进入“我的-待安装订单”进行预约安装。若您在服务过程中有任何疑问，请致电400-111-8868向途虎咨询。","state":0,"province":"北京","operatorId":1,"ipAddr":"10.126.2.10","replyTotal":10,"fee":3}
{"index":{"_id":2}}
{"corpName":"盒马鲜生","mobile":"13100000000","createDate":"2020-02-18 19:19:20","sendDate":"2020-02-18 19:19:21","longCode":"10660000988","smsContent":"【盒马】您尾号12345678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：刘三，电话：13800000000","state":0,"province":"北京","operatorId":2,"ipAddr":"10.126.2.9","replyTotal":15,"fee":5}

#可以指定不同的 index 和 type
GET /_mget
{
"docs":[
   {
       "_index": "sms-logs-index", #索引
       "_type": "sms_logs_type", #数据类型
       "_id": 1 #要查询的主键
   },
     {
       "_index": "sms-logs-index",
       "_type": "sms_logs_type",
       "_id": 2
   }
 ]
}









