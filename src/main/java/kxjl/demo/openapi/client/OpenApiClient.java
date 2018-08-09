package kxjl.demo.openapi.client;

import com.alibaba.fastjson.JSONObject;
import kxjl.demo.openapi.util.HttpUtil;

/**
 * OpenApi客户端
 *
 * @author jhjiang
 * @Description: OpenApi简易客户端示例
 * @date 2018/6/14 0014下午 9:52
 */
public class OpenApiClient {
    /**
     * OpenApi的URL
     */
    private String baseUrl = "https://www.xfyeta.com";


    public OpenApiClient() {
    }

    public OpenApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 获取访问令牌
     *
     * @param appKey    应用key
     * @param appSecret 应用secret
     * @return
     * @throws Exception
     */
    public JSONObject oauthToken(String appKey, String appSecret) throws Exception {
        JSONObject request = new JSONObject();
        request.put("app_key", appKey);
        request.put("app_secret", appSecret);
        String url = baseUrl + "/openapi/oauth/v1/token";
        return new HttpUtil().post(url, request);
    }

    /**
     * 获取企业配置
     *
     * @param token 令牌
     * @return
     * @throws Exception
     */
    public JSONObject configQuery(String token) throws Exception {
        String url = baseUrl + "/openapi/config/v1/query?token=" + token;
        return new HttpUtil().post(url, new JSONObject());
    }

    /**
     * 创建外呼任务
     *
     * @param token 令牌
     * @param args  任务参数
     * @return
     * @throws Exception
     */
    public JSONObject outboundTaskCreate(String token, JSONObject args) throws Exception {
        String url = baseUrl + "/openapi/outbound/v1/task/create?token=" + token;
        return new HttpUtil().post(url, args);
    }

    /**
     * 上传外呼数据
     *
     * @param token 令牌
     * @param args  数据对象
     * @return
     * @throws Exception
     */
    public JSONObject outboundTaskInsert(String token, JSONObject args) throws Exception {
        String url = baseUrl + "/openapi/outbound/v1/task/insert?token=" + token;
        return new HttpUtil().post(url, args);
    }

    /**
     * 启动外呼任务
     *
     * @param token  令牌
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    public JSONObject taskStart(String token, String taskId) throws Exception {
        String url = baseUrl + "/openapi/outbound/v1/task/start?token=" + token;

        JSONObject request = new JSONObject();
        request.put("task_id", taskId);
        return new HttpUtil().post(url, request);
    }
}
