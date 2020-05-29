package com.crud.planner_frontend.service;

import com.crud.planner_frontend.weatherbit.WeatherBitForecast;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WBServiceTest {
    @Test
    public void testParsingJson() throws JSONException {
        //ResponseEntity<String> response = "<200,{\"data\":[{\"moonrise_ts\":1590557211,\"wind_cdir\":\"WNW\",\"rh\":70,\"pres\":1002.02,\"high_temp\":18.4,\"sunset_ts\":1590604545,\"ozone\":359.499,\"moon_phase\":0.26733,\"wind_gust_spd\":10.3101,\"snow_depth\":0,\"clouds\":78,\"ts\":1590530460,\"sunrise_ts\":1590547221,\"app_min_temp\":8.7,\"wind_spd\":2.26414,\"pop\":40,\"wind_cdir_full\":\"west-northwest\",\"slp\":1029.27,\"moon_phase_lunation\":0.15,\"valid_date\":\"2020-05-27\",\"app_max_temp\":18.3,\"vis\":0,\"dewpt\":8.4,\"snow\":0,\"uv\":5.01379,\"weather\":{\"icon\":\"c04d\",\"code\":804,\"description\":\"Overcast clouds\"},\"wind_dir\":296,\"max_dhi\":null,\"clouds_hi\":26,\"precip\":0.640625,\"low_temp\":9.4,\"max_temp\":18.4,\"moonset_ts\":1590533754,\"datetime\":\"2020-05-27\",\"temp\":14.1,\"min_temp\":8.7,\"clouds_mid\":46,\"clouds_low\":50},{\"moonrise_ts\":1590647426,\"wind_cdir\":\"WNW\",\"rh\":80,\"pres\":996.477,\"high_temp\":16.2,\"sunset_ts\":1590691013,\"ozone\":345.708,\"moon_phase\":0.372043,\"wind_gust_spd\":7.59129,\"snow_depth\":0,\"clouds\":92,\"ts\":1590616860,\"sunrise_ts\":1590633564,\"app_min_temp\":9.4,\"wind_spd\":1.95434,\"pop\":70,\"wind_cdir_full\":\"west-northwest\",\"slp\":1023.7,\"moon_phase_lunation\":0.18,\"valid_date\":\"2020-05-28\",\"app_max_temp\":16.2,\"vis\":0,\"dewpt\":9.3,\"snow\":0,\"uv\":3.35786,\"weather\":{\"icon\":\"c04d\",\"code\":804,\"description\":\"Overcast clouds\"},\"wind_dir\":282,\"max_dhi\":null,\"clouds_hi\":40,\"precip\":2.53711,\"low_temp\":9.8,\"max_temp\":16.2,\"moonset_ts\":1590622143,\"datetime\":\"2020-05-28\",\"temp\":12.7,\"min_temp\":9.4,\"clouds_mid\":71,\"clouds_low\":38}],\"city_name\":\"Kraków\",\"lon\":\"19.93658\",\"timezone\":\"Europe\/Warsaw\",\"lat\":\"50.06143\",\"country_code\":\"PL\",\"state_code\":\"77\"},[Server:\"openresty/1.11.2.2\", Date:\"Wed, 27 May 2020 06:26:34 GMT\", Content-Type:\"application/json; charset=utf-8\", Content-Length:\"11544\", X-RateLimit-Limit:\"1000\", X-RateLimit-Remaining:\"999\", X-RateLimit-Reset:\"1590623999\", X-Proxy-Cache:\"MISS\", Access-Control-Allow-Origin:\"*\", Access-Control-Allow-Methods:\"GET, OPTIONS, POST\", Access-Control-Allow-Headers:\"DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range\", Access-Control-Expose-Headers:\"DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range\"]>";
        RestTemplate rest = new RestTemplate();
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?";
        String city = "Kraków";
        ResponseEntity<String> exchange = rest.exchange(
                url + "city=" + city + "&key=0b3f7173fdd545a1aa36e3f27e22d9bb",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);
        String json = exchange.getBody();
        System.out.println(exchange);
        System.out.println(json);
        List<WeatherBitForecast> wBForecast = new ArrayList<>();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        JsonArray arr = jsonObject.getAsJsonArray("data");
        for (int i = 0; i < arr.size(); i++) {

            System.out.println(arr.get(i).getAsJsonObject().get("datetime").getAsString());
            JsonElement weather = arr.get(i).getAsJsonObject().get("weather");
            System.out.println(weather.getAsJsonObject().get("description"));
            wBForecast.add(new WeatherBitForecast(arr.get(i).getAsJsonObject().get("datetime").getAsString(),
                    weather.getAsJsonObject().get("icon").getAsString(),
                    weather.getAsJsonObject().get("description").getAsString(),
                    arr.get(i).getAsJsonObject().get("temp").getAsString()));
        }
System.out.println(wBForecast);



        /*JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject.getString("datetime"));
            System.out.println(jsonObject.getString("temp"));
        }*/
    }
}
