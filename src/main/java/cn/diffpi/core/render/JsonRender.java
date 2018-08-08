package cn.diffpi.core.render;

import cn.diffpi.response.errresp.ErrorResponse;
import cn.diffpi.response.succmodel.MainSuccess;
import cn.diffpi.response.succmodel.SimpleMainSuccess;
import cn.diffpi.response.succresp.SuccessResponse;
import cn.dreampie.common.Render;
import cn.dreampie.common.http.ContentType;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.common.util.json.Jsoner;

/***
 *
 * Created by one__l on 2016年4月24日
 */
public class JsonRender extends Render {

    public void render(HttpRequest request, HttpResponse response, Object out) {
        if (out != null) {
            response.setContentType(ContentType.JSON.value());
            if (out instanceof String) {
                if (((String) out).startsWith("\"") || ((String) out).startsWith("{") || ((String) out).startsWith("[")) {
                    write(request, response, (String) out);
                } else {
                    write(request, response, "\"" + out + "\"");
                }
            } else if (out instanceof ErrorResponse) {
                String json = Jsoner.toJSON(out);
                write(request, response, json);
            } else {
                MainSuccess mainSuccess = new SimpleMainSuccess("0", out, "success");
                SuccessResponse successResponse = new SuccessResponse(mainSuccess);

                String json = Jsoner.toJSON(successResponse);
                write(request, response, json);
            }
        } else {
            MainSuccess mainSuccess = new SimpleMainSuccess("0", "success");
            SuccessResponse successResponse = new SuccessResponse(mainSuccess);

            String json = Jsoner.toJSON(successResponse);
            write(request, response, json);
        }
    }
}
