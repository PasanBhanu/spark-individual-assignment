package lk.spark.pasan.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Writer;

public class Http {
    public static HttpServletResponse setResponse(HttpServletResponse resp, int status) {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        return resp;
    }

    public static PrintWriter getWriter(PrintWriter writer, String status, String message, JsonObject data, JsonArray errors) {
        JsonObject json = new JsonObject();
        json.addProperty("status", status);
        json.addProperty("message", message);
        if (data != null) {
            json.add("data", data);
        }
        if (errors != null) {
            json.add("errors", errors);
        }
        writer.print(json.toString());
        return writer;
    }
}
