package kxjl.demo.openapi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kxjl.demo.openapi.client.OpenApiClient;

import java.util.Comparator;

/**
 * Yeta OpenApi 示例
 */
public class OpenApiDemo {
    /**
     * app_key必填
     */
    private static String APP_KEY = "ODM1ZTk4ODAtYTMyZC00ZjBiLTkzMDQtY2VjNWU0ZDUyZWQ5";
    /**
     * app_secret必填
     */
    private static String APP_SECRET = "NDk0NjM3MDI0NzVFNDcwMkMzQTI4NzVBMjZCMERDQTA=";
    /**
     * 服务URL
     */
    private static String BASE_URL = "https://www.xfyeta.com/";


    public static void main(String[] args) throws Exception {
        OpenApiClient client = new OpenApiClient(BASE_URL);

        //1. 获取token
        JSONObject oauthTokenResp = client.oauthToken(APP_KEY, APP_SECRET);
        System.out.println(oauthTokenResp);
        if (!isRespOk(oauthTokenResp)) {
            return;
        }
        String token = oauthTokenResp.getJSONObject("result").getString("token");//解析token值

        //2. 查询企业配置信息
        JSONObject configQueryResp = client.configQuery(token);
        System.out.println(configQueryResp);
        if (!isRespOk(configQueryResp)) {
            return;
        }
        JSONArray lines = configQueryResp.getJSONObject("result").getJSONArray("lines");//线路列表
        JSONArray robots = configQueryResp.getJSONObject("result").getJSONArray("robots");//机器人话术列表
        if (lines == null || lines.isEmpty() || robots == null || robots.isEmpty()) {
            System.out.println("对不起，没有可用的线路或机器人话术！建议您先在Web上进行外呼体验，确认基础准备工作已就绪。");
            return;
        }
        lines.sort(Comparator.comparing(o -> ((JSONObject) o).getString("line_num")));
        String line = lines.getJSONObject(0).getString("line_num");//默认使用第1个线路
        robots.sort(Comparator.comparing(o -> ((JSONObject) o).getString("robot_id")));
        String robot = robots.getJSONObject(0).getString("robot_id");//默认使用第1个机器人话术


        //3. 创建外呼任务
        JSONObject taskCreateReq = new JSONObject();
        taskCreateReq.put("task_name", "OpenApiDemo外呼测试");//任务名称
        taskCreateReq.put("line_num", line);//线路
        taskCreateReq.put("robot_id", robot); //机器人话术
        taskCreateReq.put("time_begin", System.currentTimeMillis());//任务开始时间
        System.out.println(">>> " + taskCreateReq.toJSONString());
        JSONObject taskCreateResp = client.outboundTaskCreate(token, taskCreateReq);
        System.out.println(taskCreateResp);
        if (!isRespOk(taskCreateResp)) {
            return;
        }
        String taskId = taskCreateResp.getJSONObject("result").getString("task_id");//任务id

        //4. 上传外呼号码数据
        JSONObject taskInsertReq = new JSONObject();
        taskInsertReq.put("task_id", taskId);//任务id
        taskInsertReq.put("call_column", new String[]{"客户手机号码", "姓名"});//数据列映射	第一列必须是客户手机号码，其他列是话术动态信息（建议在Web界面中确认当前话术对应的动态数据模板）。
        taskInsertReq.put("call_list", new String[][]{{"15855163023", "张三"}});//数据行	最多50条数据
        System.out.println(">>> " + taskInsertReq.toJSONString());
        JSONObject taskInsertResp = client.outboundTaskInsert(token, taskInsertReq);
        System.out.println(taskInsertResp);
        if (!isRespOk(taskInsertResp)) {
            return;
        }

        //5. 启动外呼任务
        JSONObject taskStartResp = client.taskStart(token, taskId);
        System.out.println(taskStartResp);
        if (!isRespOk(taskStartResp)) {
            return;
        }


        System.out.println("恭喜您成功对接Yeta OpenApi。请等待手机响铃，接听体验Yeta电话机器人吧！");
    }


    /**
     * 判断响应是成功
     *
     * @param resp
     * @return
     */
    private static boolean isRespOk(JSONObject resp) {
        if (resp == null || !"0".equals(resp.getString("code")) || !resp.containsKey("result")) {
            System.out.println("响应异常。" + resp.getIntValue("code") + "," + resp.getString("message"));
            return false;
        }
        return true;
    }
}
