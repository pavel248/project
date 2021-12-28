package com.company;

import java.io.FileReader;
import java.util.HashMap;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.Fields;
import org.json.*;




public abstract class VkApi {

    public static HashMap<String, Integer> getVkIds() throws ClientException, ApiException {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        var result = new HashMap<String, Integer>();

        UserActor actor = new UserActor(8001148, "2dbb707cbbcbdf36041bc828ee93a8d7547c63a19160687e14708d8849743752e696c1d2697b314a0dd45");

        var exc = vk.groups().getMembers(actor).groupId("198188261").count(1000).fields(Fields.PERSONAL).executeAsString();
        var jsonArray = new JSONObject(exc).getJSONObject("response").getJSONArray("items");
        for (var i=0;i<jsonArray.length();i++){
            var name = jsonArray.getJSONObject(i).getString("first_name");
            var lName = jsonArray.getJSONObject(i).getString("last_name");
            var id = jsonArray.getJSONObject(i).getInt("id");
            result.put(lName + " " + name,id);
        }

        return result;
    }

    public static void setFieldsFromVk(Integer id, Student std) throws ClientException, ApiException{
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserActor actor = new UserActor(8001148, "2dbb707cbbcbdf36041bc828ee93a8d7547c63a19160687e14708d8849743752e696c1d2697b314a0dd45");

        var exc = vk.users().get(actor).userIds(String.valueOf(id)).fields(Fields.CITY,Fields.BDATE, Fields.SEX).executeAsString();
        try {
            var json = new JSONObject(exc).getJSONArray("response");
            try {
                var city = json.getJSONObject(0).getJSONObject("city").getString("title");
                std.setCity(city);
                try {
                    var bdate = json.getJSONObject(0).getString("bdate");
                    std.setDateOfBirth(bdate);
                    try {
                        var sex = json.getJSONObject(0).getInt("sex");
                        if (sex == 1){
                            std.setSex("Female");
                        } else {
                            std.setSex("Male");
                        }
                    }
                    catch (Exception e){

                    }
                }
                catch (Exception e){

                }

            }
            catch (Exception e){

            }
        }
        catch (Exception e){

        }

    }


}
