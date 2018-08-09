# yeta开放平台开发接口

# 目录

1. Part1:外呼能力调用 
2. Part2:结果数据推送
3. Part3:呼入上下文动态数据获取


# Part1: 外呼能力调用

由企业请求yeta,调用对应能力


-----


# 一、获取token
功能：获取授权信息  
接口地址：https://www.xfyeta.com/openapi/oauth/v1/token  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## 请求参数说明
名称| 必填| 类型| 说明
---|:-:|:-:|:-:
app_key|是|string|应用的app_key
app_secret|是|string|应用的app_secret
## 请求示例
~~~
{
   "app_key": "ODM1ZTk4ODAtYTMyZC00ZjBiLTkzMDQtY2VjNWU0ZDUyZWQ5",
   "app_secret": "MTM5NUM3NjlGQ0M2REUwN0FBREE3QjUxMkU1Qzg5NUQ="
}
~~~
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集
## 返回结果集说明
名称| 类型|说明|备注
---|:-:|:-:|:-:
token|string|token|
time_expire|long|过期时间|单位秒，默认3600（一个小时）

## JSON返回示例
~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       "token": "08236d0aeeee4d5b566db5f4adc41a63",
       "time_expire": 3600
    }  
    
}
~~~


# 二、查询企业配置信息
功能：查询企业配置信息  
接口地址：https://www.xfyeta.com/openapi/config/v1/query?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json 
 
## URL参数说明
名称|必填|类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求示例
~~~
https://www.xfyeta.com/openapi/config/v1/query?token=08236d0aeeee4d5b566db5f4adc41a63
~~~

## 返回参数说明
名称|类型|说明 
---|:-:|:-: 
code|int|返回码  
message| string|返回描述  
result|object|返回结果集  

## 返回结果集说明
名称| 类型|说明|备注
---|:-:|:-:|:-:
lines|数组|线路|
robots|数组|话术|
urls|数组|接收接口|
line_num|string|线路号码|
concurrents|int|并发数|
time_work|数组|工作时间|
status|int|状态|0未使用，1使用中，注意使用中的线路不能再用于创建外呼任务
time_apply|long|申请时间|时间毫秒数
time_expire|long|过期时间|单位秒,-1表示永久
robot_id|string|话术id|
robot_name|string|话术名称|
status|int|状态|1审核中，2未通过，3待发布，4已发布
type|int|话术类型|1普通话术2动态话术
deleted|int|删除状态|0未删除，1已删除
time_create|long|创建时间|时间毫秒数
time_update|long|更新时间|时间毫秒数
url|string|url 地址
url_module|string|url 模块


## JSON返回示例
~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       "lines": [
           {
               "line_num": "055169101407",
               "concurrents": 10,
               "time_work": ["09:00:00-12:00:00", "13:00:00-17:59:59"],
               "status": 1,
               "time_apply": 1527321492000,
               "time_expire": 2592000
           },
           {
               "line_num": "055169101408",
               "concurrents": 100,
               "time_work": ["09:00:00-12:00:00", "13:00:00-17:59:59"],
               "status": 0,
               "time_apply": 1527321492000,
               "time_expire": 2592000
           }
       ],
       "robots": [
           {
               "robot_id": "111",
               "robot_name": "金融外呼",
               "status": 4,
               "type": 1,
               "deleted": 0,
               "time_create": 1527321492000,
               "time_update": 1527325092000
           }
       ],
       "urls": [
           {
               "url": "https://www.xfyeta.com",
               "url_module": "receive"
           }
       ]
    }  
    
}  
~~~

# 三、创建外呼任务
功能：创建外呼任务
接口地址：https://www.xfyeta.com/openapi/outbound/v1/task/create?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## URL参数说明
名称|必填| 类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明|备注
---|:-:|:-:|:-:|:-:
task_name|是|string|任务名称|
line_num|是|string|线路号码|如果是多个，逗号分隔
robot_id|是|string|话术id|
recall_count|否|int|外呼失败后重复外呼次数|最大3次，默认0
time_recall_wait|否|long|重复外呼等待时间|单位秒
time_range|否|string[]|外呼时间段|
time_begin|是|long|任务开始时间|时间毫秒数
time_end|否|long|任务结束时间|时间毫秒数

## 请求示例
~~~
{
   "task_name": "测试外呼",
   "line_num": "055169101407",
   "robot_id": "57cb5178-ec83-414c-a2a9-50af51397e83",
   "recall_count": 1,
   "time_recall_wait": 600,
   "time_range": ["09:00:00-12:00:00", "13:00:00-17:30:00"],
   "time_begin": 1527321492000,
   "time_end": 1527325092000
}
~~~

## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集
## 返回结果集说明
名称| 类型|说明
---|:-:|:-:
task_id|string|任务Id

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       "task_id": "129"
    }     
}
~~~ 

# 四、上传外呼数据
功能：上传外呼数据  
接口地址：https://www.xfyeta.com/openapi/outbound/v1/task/insert?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json 

## URL参数说明
名称|必填| 类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明|备注
---|:-:|:-:|:-:|:-:
task_id|是|string|任务id|
call_column|是|string[]|数据列映射|第一列必须是客户手机号码，其他列是话术动态信息 
call_list|是|string[][]|数据行|最多50条数据

## 请求示例
~~~
{
   "task_id": "129",
   "call_column": ["客户手机号码","列2", "列3"],
   "call_list": [
         ["13000000001","t2","t3"],
         ["13000000002","t2","t3"]
     ]
}
~~~  
 
## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集
## 返回结果集说明
名称| 类型|说明|备注
---|:-:|:-:|:-:
total|int|总数|
task_data_ids|数组|taskDataId（任务数据标识）数组|依次对应每条数据

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       "total": 2,
       "task_data_ids": [87,88]
    }  
}
~~~

# 五、启动外呼任务
功能：启动外呼任务  
接口地址：https://www.xfyeta.com/openapi/outbound/v1/task/start?token=08236d0aeeee4d5b566db5f4adc41a63   
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## URL参数说明
名称| 必填| 类型| 说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明
---|:-:|:-:|:-:
task_id|是|string|任务id

## 请求示例
~~~
{
   "task_id": "129"
}
~~~

## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {}     
}
~~~ 

# 六、删除外呼任务
功能：删除外呼任务  
接口地址：https://www.xfyeta.com/openapi/outbound/v1/task/delete?token=08236d0aeeee4d5b566db5f4adc41a63   
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## URL参数说明
名称| 必填| 类型| 说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明
---|:-:|:-:|:-:
task_id|是|string|任务id

## 请求示例
~~~
{
   "task_id": "129"
}
~~~

## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {}     
}
~~~ 

# 七、暂停外呼任务
功能：暂停外呼任务  
接口地址：https://www.xfyeta.com/openapi/outbound/v1/task/pause?token=08236d0aeeee4d5b566db5f4adc41a63   
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## URL参数说明
名称| 必填| 类型| 说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明
---|:-:|:-:|:-:
task_id|是|string|任务id

## 请求示例
~~~
{
   "task_id": "129"
}
~~~

## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {}     
}
~~~ 


# 八、查询话术
功能：查询话术信息  
接口地址：https://www.xfyeta.com/openapi/config/v1/queryRobots?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json 
 
## URL参数说明
名称|必填|类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明
---|:-:|:-:|:-:
robot_name|否|string|话术名称
robot_status|否|int|话术状态
page_index|否|int|分页页码
page_size|否|int|分页大小

## 请求示例
~~~
{
   "robot_name": "金融催缴"
   "page_index": 1,
   "page_size": 10
}
~~~

## 返回参数说明
名称|类型|说明 
---|:-:|:-: 
code|int|返回码  
message| string|返回描述  
result|object|返回结果集  

## 返回结果集说明
名称| 类型|说明|备注
---|:-:|:-:|:-:
robot_id|string|话术id|
robot_name|string|话术名称|
status|int|状态|1审核中，2未通过，3待发布，4已发布
type|int|话术类型|1普通话术2动态话术
deleted|int|删除状态|0未删除，1已删除
time_create|long|创建时间|时间毫秒数
time_update|long|更新时间|时间毫秒数


## JSON返回示例
~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       [
           {
               "robot_id": "111",
               "robot_name": "金融催缴",
               "status": 4,
               "type": 1,
               "deleted": 0,
               "time_create": 1527321492000,
               "time_update": 1527325092000
           }
       ]
    }  
    
}  
~~~


# 九、查询线路
功能：查询线路信息  
接口地址：https://www.xfyeta.com/openapi/config/v1/queryLines?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json 
 
## URL参数说明
名称|必填|类型|说明
---|:-:|:-:|:-:
token|是|string|token参数


## 返回参数说明
名称|类型|说明 
---|:-:|:-: 
code|int|返回码  
message| string|返回描述  
result|object|返回结果集  

## 返回结果集说明
名称| 类型|说明|备注
---|:-:|:-:|:-:
line_num|string|线路号码|
concurrents|int|并发数|
time_work|数组|工作时间|
status|int|状态|0未使用，1使用中
time_apply|long|申请时间|时间毫秒数
time_expire|long|过期时间|单位秒,-1表示永久


## JSON返回示例
~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       [
           {
               "line_num": "055169101407",
               "concurrents": 10,
               "time_work": ["09:00:00-12:00:00", "13:00:00-17:59:59"],
               "status": 1,
               "time_apply": 1527321492000,
               "time_expire": 2592000
           },
           {
               "line_num": "055169101408",
               "concurrents": 100,
               "time_work": ["09:00:00-12:00:00", "13:00:00-17:59:59"],
               "status": 0,
               "time_apply": 1527321492000,
               "time_expire": 2592000
           }
       ]
    }  
    
}  
~~~

# 十、查询url
功能：查询url  
接口地址：https://www.xfyeta.com/openapi/config/v1/queryUrls?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json 
 
## URL参数说明
名称|必填|类型|说明
---|:-:|:-:|:-:
token|是|string|token参数


## 返回参数说明
名称|类型|说明 
---|:-:|:-: 
code|int|返回码  
message| string|返回描述  
result|object|返回结果集  

## 返回结果集说明
名称| 类型|说明
---|:-:|:-:
url|string|url 地址
url_module|string|url 模块


## JSON返回示例
~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       [
           {
               "url": "https://www.xfyeta.com",
               "url_module": "receive"
           }
       ]
    }  
    
}  

# 十一、查询发音人列表
功能：查询url  
接口地址：https://www.xfyeta.com/openapi/config/v1/queryVoice?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json 
 
## URL参数说明
名称|必填|类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明
---|:-:|:-:|:-:
voiceId|否|long|发音人id
version|否|int|发音人版本 1:TTS 2:XTTS；默认2

## 请求示例
~~~
{
    "voiceId": 34，
    "version": 1
}
~~~

## 返回参数说明
名称|类型|说明 
---|:-:|:-: 
code|int|返回码  
message| string|返回描述  
result|object|返回结果集  

## 返回结果集说明
名称| 类型|说明
---|:-:|:-:
id|long|发音人id
name|string|发音人名称
code|string|音色编码


## JSON返回示例
~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       [
           {
                "id": 1,
                "name": "小柯",
                "code": "1"
           }
       ]
    }  
    
}
~~~

# 十二、创建外呼任务池
功能：创建外呼任务池    
接口地址：https://www.xfyeta.com/openapi/outbound/v1/task/createTaskPool?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## URL参数说明
名称|必填| 类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明|备注
---|:-:|:-:|:-:|:-:
task_name|是|string|任务名称|

## 请求示例
~~~
{
   "task_name": "测试外呼"
}
~~~

## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集
## 返回结果集说明
名称| 类型|说明
---|:-:|:-:
task_id|string|任务Id

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
       "task_id": "129"
    }     
}
~~~ 

# 十三、添加任务池数据
功能：添加任务池数据
接口地址：https://www.xfyeta.com/openapi/config/v1/addTaskPoolData?token=08236d0aeeee4d5b566db5f4adc41a63  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  
## URL参数说明
名称|必填| 类型|说明
---|:-:|:-:|:-:
token|是|string|token参数

## 请求参数说明
名称| 必填|类型|说明|备注
---|:-:|:-:|:-:|:-:
taskId|是|long|任务编码|
config|是|long|外呼配置|
taskData|是|map[]|外呼任务数据列表|数据map的key要和speechSkillId话术模版包含匹配

## 外呼配置参数说明
名称| 必填|类型|说明|备注
---|:-:|:-:|:-:|:-:
speechSkillId|是|long|话术ID|
speechCode|否|long|发音人编码|
speechSpeed|否|int|语速|
telNum|是|string|外呼使用号码|
mcp|否|string|MCP引擎组|


## 请求示例
~~~
{
  "taskId": 1,
  "config": {
    "telNum": "18756056557;18756056558",
    "mcp": "mcp01",
    "speechSkillId": 1,
    "speechCode": "60030",
    "speechSpeed": 400
  },
"taskData": [
    {
      "客户手机号码": "13000000001",
      "职业": "律师",
      "密码": "t3"
    },
    {
      "客户手机号码": "13000000002",
      "职业": "老师",
      "密码": "123456"
    }
  ]
}

~~~

## 返回参数说明
名称| 类型|说明
---|:-:|:-:
code|int|返回码
message| string|返回描述
result|object|返回结果集
## 返回结果集说明
名称| 类型|说明
---|:-:|:-:
total|int|外呼任务数据数量
task_data_ids|long[]|外呼任务数据编号列表

## JSON返回示例

~~~
{
    "code": 0,  
    "message": "ok",  
    "result": {
        "total": 2,
        "task_data_ids": [130,131]
    }     
}
~~~ 

# 附录：返回码参照
## 成功码参照
返回码|说明  
---|:-:
0 | ok（成功）
## 服务级错误码参照  
错误码|说明  
---|:-- 
200101|账号或密码错误

## 系统级错误码参照
错误码|说明  
---|:--  
10001|无效的token请求  
10002|该token无请求权限
10003|错误的app_id
10004|服务忙，请稍后再试
10005|输入参数不正确
10020|接口维护
10021|接口停用
200200|错误的数据关系
200201|错误的企业id
200202|错误的任务id
200203|错误的外呼号码
200204|错误的话术id
200205|错误的话术发音id
200206|错误的语速
200209|余额不足  
200210|错误的文件格式
200211|文件上传超过限定大小10m
200212|错误的数据，与话术动态信息不匹配
200213|上传的外呼数据量超过限制

## 错误码格式说明（示例：200201）
开头|中间| 尾部|
:--|:--|:--
2|002|01
服务级错误（1为系统级错误）|服务模块代码|具体错误代码


# Part2:数据推送

由yeta主动向企业预先配置的接口URL进行POST，向企业推送过程和结果数据。
强烈建议企业在接收推送数据时采用异步处理机制（接收到推送时，立即缓存数据并响应，然后使用异步服务对数据进行业务处理），避免超时造成推送数据丢失。

----------
# 一、话单推送
功能：推送话单信息  
接口地址：[企业预先配置url]  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：any  

## 请求参数说明
名称| 必填| 类型| 说明| 备注
---|:-:|:-:|:-:|:-:
business_id||string|企业id|
app_id||string|应用id|
robot_id||string|话术话术id|
call_uuid|是|string|通话UUID|录音和会话的唯一关联。
caller|是|string|主叫号码|
callee|是|string|被叫号码|
direction|是|int|呼叫方向|1:呼入 2 呼出
time_answer|是|long|接听时间戳|unix_time
time_hangup|是|long|挂断时间戳|unix_time
duration_ring|是|int|拨号振铃时长|秒
duration_call|是|int|通话时长|秒
call_relation_id|仅呼入|string|关联ID|业务侧的唯一标识 
task_id|仅呼出|string|外呼任务id|
task_data_id|仅呼出|string|外呼任务id|
time_dial|仅呼出|long|拨号时间戳|unix_time
time_ring|仅呼出|long|振铃时间戳|unix_time
task_result|仅呼出|string|外呼结果|
task_result_desc|仅呼出|string|外呼结果描述|
task_tries|仅呼出|int|外呼重试次数|
task_row_index|仅呼出|int|外呼数据行号|
task_row_head|仅呼出|array|外呼数据行头|
task_row_value|仅呼出|array|外呼数据行值|

#### 外呼结果说明
task_result| task_result_desc| 备注
---|:-:|:-:
-1 |超时|
26 |网络故障|
7  |号码错误|
0  |成功|
15 |客户接听后并主动挂机|
16 |客户掉线|
3  |无法接通|  无法接听,无法接通
9  |停机  |停机,暂停服务,欠费
6  |空号  |空号,改号
23 |呼入限制|
22 |呼叫转移  |呼叫转移失败
4  |关机|
20 |用户未接| 回铃音,彩铃,无人接听,用户拒接,来电提醒
5  |用户正忙|  短忙音,长忙音,静音
2  |正在通话中|
27 |呼出受限 | 拨号方式不正确
28 |线路故障 | 线路故障,网络忙


## 请求示例
~~~
{
    "business_id":"2745",
    "app_id":"test",
    "robot_id":"2745001",
    "call_uuid":"123425c-6483-11e8-b8fa-2769c24918cd",
    "call_relation_id":"test-123",
    "task_id":"0",
    "task_data_id":"262381",
    "caller":"69102939",
    "callee":"15556985227",
    "direction":1,
    "time_dial":1527737756729,
    "time_ring":1527737757449,
    "time_answer":1527737763429,
    "time_hangup":1527737784829,
    "duration_ring":7,
    "duration_call":28
}

~~~
## 响应结果说明
响应结果不限格式，包含success关键字即认为推送成功，否则将重试3次。

## 响应结果示例
~~~
success
~~~


# 二、录音推送

功能：推送录音信息  
接口地址：[企业预先配置url]  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：any  

## 请求参数说明
名称| 必填| 类型| 说明| 备注
---|:-:|:-:|:-:|:-:
business_id||string|企业id|
app_id||string|应用id|
robot_id||string|话术id|
task_data_id||string|外呼任务id|
call_relation_id||string|呼入关联id|
call_uuid|是|string|通话UUID|
url|是|string|url路径|http://voice.kxjlcc.com:9000/
size|是|long|文件大小|字节
duration|是|int|录音长度|秒


## 请求示例
~~~

{
    "call_uuid":"809c825c-6483-11e8-b8db-2769c24918cd",
    "url":"ant/4/ivr/0/2018/05/31/0/452263-7571100e-7afc-42aa-9d69-ac25805d45b6.wav",
    "size":1043244,
    "duration":32,
    "business_id":"10527",
    "app_id":"123",
    "robot_id":"10527"
}

~~~
## 响应结果说明
响应结果不限格式，包含success关键字即认为推送成功，否则将重试3次。

## 响应结果示例
~~~
success
~~~



# 三、会话推送

功能：推送会话交互信息  
接口地址：[企业预先配置url]  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：any  

## 请求参数说明
名称| 必填| 类型| 说明| 备注
---|:-:|:-:|:-:|:-:
business_id||string|企业id|
app_id||string|应用id|
robot_id||string|话术话术id|
task_data_id||string|外呼任务id|
call_relation_id||string|呼入关联id|
call_uuid|是|string|通话UUID|
tag_max||string|最高意向度|
tag_min||string|最低意向度|
dialog|是|array|交互记录|
seq||int|节点序号|
role||string|节点角色|robot话术customer客户
node_id||string|节点id|
node_name||string|节点名称|
node_type||string|节点类型|
tag_name||string|意向|
tag_desc||string|意向说明|
text_robot||string|话术输出内容|
text_man||string|用户输入内容|
question_id||String|问题uuid|
hits||array|命中|
hit||string|命中内容|
pick||boolean|是否选中|
answer_id||string|回答uuid|

#### node_type节点类型说明

node_type| 说明
---|:-:
AnyNode | 任意回答节点
CollectInputNode | 自定义输入节点
ConditionNode | 按键输入节点
EndNode | 结束节点
HookInfoNode | 动态引用节点
KeyNavigationNode | 按键导航节点
NoNeedAnswerNode | 无需回答节点
NormalNode | 普通交互节点



## 请求示例
~~~

{
    "business_id":"10527",
    "app_id":"123",
    "robot_id":"10527",
    "call_uuid":"f3b13ebc-6394-11e8-907d-ebcc4d560c04",
    "dialog":[
        {
            "seq":"3",
            "role":"robot",
            "node_id":"word_node_70601",
            "node_name":"开头语",
            "text_robot":"喂，您好！",
            "text_man":"",
            "question_id":""
        },
        {
            "seq":"4",
            "role":"customer",
            "node_id":"word_node_70601",
            "node_name":"开头语",
            "text_robot":"喂，您好！",
            "text_man":"",
            "question_id":"",
            "hits":[
                {
                    "hit":"任意回答",
                    "pick":true,
                    "answer_id":""
                }
            ]
        }
    ]
}


~~~
## 响应结果说明
响应结果不限格式，包含success关键字即认为推送成功，否则将重试3次。

## 响应结果示例
~~~
success
~~~

# Part3:话术话术上下文动态数据获取

电话呼入时，由yeta主动向企业预先配置的接口URL进行POST请求，获取话术话术上下文动态数据。
接口响应时间建议不超过300毫秒，避免影响客户电话体验。
如果机器人话术没有动态数据，可以不实现此接口。

----------

功能：获取话术话术上下文动态数据  
接口地址：[企业预先配置url]  
请求方式：http post  
请求header：content-type：application/json;charset=UTF-8  
返回格式：json  

## 请求参数说明
名称| 必填| 类型| 说明| 备注
---|:-:|:-:|:-:|:-:
business_id||string|企业id|sip头X-business-id
app_id||string|应用id|sip头X-app-id
robot_id||string|话术话术id|sip头X-robot-id
call_relation_id|是|string|业务侧关联id|sip头X-call-relation-id




## 请求示例
~~~

{
	"call_relation_id":"1111",
	"business_id":"167",
	"app_id":"3333",
	"robot_id":"4444"
}

~~~
## 响应结果说明
map<string,string> 当前通话话术话术要求的上下文动态数据

## 响应结果示例
~~~
{
"key1": "value1",
"性别": "男",
"姓名": "张三"
}
~~~