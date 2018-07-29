package com.hzdongcheng.bll.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketUtils<T> {
    private static Log4jUtils log = Log4jUtils.createInstanse(PacketUtils.class);

    public static JsonPacket CreateRequestPacket(Object request, String terminalNo, String MessageId) {
        JsonPacket packet = new JsonPacket();
        packet._CmdType = JsonPacket.MSG_SENT_BY_CLIENT;
        // packet._TerminalUid = terminalNo;
        packet._ServiceName = request.getClass().getSimpleName();
        if (StringUtils.isEmpty(MessageId)) {
            packet._MessageId = StringUtils.createUUID();
        } else {
            packet._MessageId = MessageId;
        }

        return packet;
    }

    /**
     * 打包数据
     *
     * @param pack
     * @return
     */
    public static String pack(JsonPacket pack) {
        return serializeObject(pack);
    }

    /**
     * @param json
     * @return
     */
    public static JsonPacket unPack(String json) {
        JsonPacket jsonPacket = null;

        if (StringUtils.isNotEmpty(json)) {
            try {
                jsonPacket = (JsonPacket) deserializeObject(json, JsonPacket.class);
            } catch (Exception ex) {
                log.error(
                        "[Network Error]:<wrong packet type>" + ex.getCause() + ":" + ex.getMessage() + ",msg=" + json);
            }

        }

        return jsonPacket;
    }

    public static JsonResult deserializeJsonResult(String json) {
        JsonResult result = null;

        if (StringUtils.isNotEmpty(json)) {
            try {
                result = (JsonResult) deserializeObject(json, JsonResult.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    /**
     * 生成服务器返回结果
     *
     * @param result
     * @return
     */
    public static JsonResult buildLocalJsonResult(Object result) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.success = true;
        jsonResult.msg = "";

        jsonResult.result = serializeObject(result);

        return jsonResult;
    }

    /**
     * @param pack
     * @param success
     * @param errmsg
     * @return
     */
    public static String buildRemoteJsonResult(JsonPacket pack, boolean success, String errmsg) {

        JsonResult jsonResult = new JsonResult();
        jsonResult.success = success;
        jsonResult.msg = errmsg;

        pack.body = serializeObject(jsonResult);

        return serializeObject(pack);
    }

    public static String serializeObject(Object target) {

        String result = "";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        result = gson.toJson(target);
        return result;
    }

    /**
     * Deserializes the json string to object.
     *
     * @param json
     * @param type
     * @return
     */
    public static Object deserializeObject(String json, Type type) {
        GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return gb.create().fromJson(json, type);
        } catch (Exception e) {
            gb.registerTypeAdapter(Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
            Gson gson = gb.create();
            return gson.fromJson(json, type);
        }
    }

    /**
     * 把业务输出的结果字符串转为相应的业务输出DTO
     */
    @SuppressWarnings("unchecked")
    public static <T> T Eval(String json, Type type) {
        T result = null;

        if (StringUtils.isNotEmpty(json)) {
            try {
                result = (T) PacketUtils.deserializeObject(json, type);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}

class DateSerializer implements JsonDeserializer<Date> {
    // public JsonElement serialize(Date src, Type typeOfSrc,
    // JsonSerializationContext context) {
    // return new JsonPrimitive(src.getTime());
    // }

    @Override
    public Date deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        if (je != null) {
            String JSONDateToMilliseconds = "\\/(Date\\((.*?)(\\+.*)?\\))\\/";
            Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
            Matcher matcher = pattern.matcher(je.getAsJsonPrimitive().getAsString());
            String result = matcher.replaceAll("$2");
            return new Date(new Long(result));
        }
        return new Date();
    }
}
