package com.mydreamplus.smartdevice.domain.condition;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/25
 * Time: 下午9:54
 * To change this template use File | Settings | File Templates.
 */
public class APICondition extends BaseCondition{

    /**
     * 条件拦截URL,设备执行过程中通过这个地址来判断是否可以执行,一般配置设备管理器的API地址
     * (例如,开门事件:开门要通过URL API请求询问开门)
     */
    private String uri = "";

    /**
     * 请求URL返回的结果,成功:true  失败:false
     */
    private boolean result;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
