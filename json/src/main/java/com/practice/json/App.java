package com.practice.json;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class App {
    public static void main( String[] args ) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        String jsonStr = "\"value\":100,\"object\":{\"string\":\"test\",\"status\":200}";
        map.put("tmp", jsonStr);
        map.put("1", true);
        map.put("2", 200);

        String tmp = (String) map.remove("tmp");
        String msg = gson.toJson(map);
        msg = (String) msg.subSequence(0, msg.length()-1)
            + "," + tmp + "}";
        System.out.println(msg); // send this as a websocket message

        Gson gson2 = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(),new GenericMapDeserializer<Object>())
            .serializeNulls()
            .create();
        Map<String, Object> map2 = gson2.fromJson(msg, new TypeToken<Map<String, Object>>(){}.getType());
        System.out.println(map2);
        for(Map.Entry<String, Object> entry : map2.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println(map2.get("object"));
    }
}
