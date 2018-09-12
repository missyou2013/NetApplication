package net.com.mvp.ac.cache;

import com.lzy.okgo.request.base.ProgressRequestBody;
import net.com.mvp.ac.commons.UtilsLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/7/26.
 */

public class LogInterceptor implements Interceptor {
    public static String TAG = "tag";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        UtilsLog.e(TAG, "\n");
        UtilsLog.e(TAG, "----------Start----------------");
        UtilsLog.e(TAG, "| " + request.toString());
        String method = request.method();
        if ("POST".equalsIgnoreCase(method)) {
            UtilsLog.e(TAG, "| method:" + method);
            UtilsLog.e(TAG, "| request.body():" + request.body());
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                UtilsLog.e(TAG, "| RequestParams:{" + sb.toString() + "}");
            } else {
                ProgressRequestBody progressRequestBody = (ProgressRequestBody) request.body();

                UtilsLog.e(TAG, "|-----------:" + progressRequestBody.toString());
            }
        } else {
            UtilsLog.e(TAG, "| method:" + method);
        }
        UtilsLog.e(TAG, "| Response:" + content);
        UtilsLog.e(TAG, "----------End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }


}
