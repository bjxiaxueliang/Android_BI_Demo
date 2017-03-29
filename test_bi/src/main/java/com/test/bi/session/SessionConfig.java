package com.test.bi.session;

import android.text.TextUtils;

/**
 * 1、sessionId的生成规则：
 * sessionId的生成规则： md5（设备ID+用户Id+时间戳）
 * 2、sessionId的生命周期：
 * 进入生成新的sessionId
 * 进入后台sessionId生命周期结束
 * 杀进程sessionId生命周期结束
 * 3、上传规则：
 * 进入上传
 * 退出上传
 * 日志积累到40条上传
 * 程序切换到后台上传
 * <p>
 */
public class SessionConfig {

    // sessionId
    private static String sessionId;
    // session内操作顺序(获取一个新的Session时，sequence归零)
    private static int sequence = 0;

    /**
     * 获取sessionId
     *
     * @return
     */
    public static String getSessionId() {
        if (TextUtils.isEmpty(sessionId)) {
            return updateSessionId("", "");
        }
        return sessionId;
    }

    /**
     * 更新sessionId
     *
     * @param cid
     * @param userId
     * @return
     */
    public static String updateSessionId(String cid, String userId) {
        sessionId = SessionUtil.createNewSession(cid, userId);
        sequence = 0;
        return sessionId;
    }

    /**
     * 每次获取Sequence时，Sequence的值均增加1
     *
     * @return
     */
    public static int getSequence() {
        return sequence++;
    }


}
