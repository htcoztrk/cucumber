package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;



public class Steps {

    static String baseUri;
    static HashMap<String, String> headers = new HashMap<>();
    static HashMap<String, String> body = new HashMap<>();
    static HashMap<String, String> params = new HashMap<>();
    static HashMap<String, String> saveValue = new HashMap<>();
    static JSONObject jsonObject = new JSONObject();
    static JSONObject jsonChild = new JSONObject();
    static JSONArray jsonArray = new JSONArray();

    static Response response;

    static String bodyy;




    @Given("{string} dosyasi body olarak eklenir")
    public void addBody(String dosyaIsmi){

        body.clear();

        try {
            Object object = new JSONParser().parse(new FileReader("data/body/"+dosyaIsmi+".json"));

            JSONObject jsonObject = (JSONObject) object;

            for (Object key : jsonObject.keySet()) {
                String keys = (String)key;
                String keyValue = jsonObject.get(keys).toString();
                body.put(keys,keyValue);
            }

            System.out.println("Body dosyasi eklendi : "+body);

        } catch (Exception e){
            System.out.println("Body dosyasi oluşturulurken hatayla karsilasildi");
        }

    }


    @Given("{string} dosyasina ait body {string} parametresine {string} eklenerek kaydedilir")
    public void addBodyy(String dosyaIsmi,String parametre,String value){

        body.clear();

        try {
            Object object = new JSONParser().parse(new FileReader("data/body/"+dosyaIsmi+".json"));

            JSONObject jsonObject = (JSONObject) object;

            System.out.println(jsonObject.get(parametre));
            System.out.println(jsonObject.get("CancelledItems[0].CancelReasonId"));
            //jsonObject.replace(jsonObject.get(parametre),value);
            System.out.println(jsonObject);

            for (Object key : jsonObject.keySet()) {
                String keys = (String)key;
                String keyValue = jsonObject.get(keys).toString();
                body.put(keys,keyValue);
            }

            System.out.println("Body dosyasi eklendi : "+body);

        } catch (Exception e){
            System.out.println("Body dosyasi oluşturulurken hatayla karsilasildi");
        }

    }

    @Given("{string} dosyasinda bulunan {string} degeri eklenir")
    public void addBodyWithDynamicValue(String dosyaIsmi, String replaceId) throws IOException {

        JSONObject jsonObject = new JSONObject();
        JSONArray cancelledItemsArray = new JSONArray();
        JSONObject cancelledItem = new JSONObject();
        jsonObject.put("OrderId",saveValue.get(dosyaIsmi));
        String deneme = saveValue.get(dosyaIsmi).replace("[","");
        String deneme2 = deneme.replace("]","");
        cancelledItem.put("OrderItemId", deneme2);
        cancelledItem.put("CancelReasonId","23");
        cancelledItemsArray.put(cancelledItem);
        jsonObject.put("CancelledItems", cancelledItemsArray);

        for (Object key : jsonObject.keySet()) {
            String keys = (String)key;
            String keyValue = jsonObject.get(keys).toString();
            body.put(keys,keyValue);
        }

        System.out.println("**********");
        System.out.println(jsonObject);
        System.out.println(body);
        System.out.println("**********");

    }


    @Given("{string} dosyasi header olarak eklenir")
    public void addHeaders(String dosyaIsmi){

        try {
            String line = "";
            String splitBy = ":";

            BufferedReader br = new BufferedReader(new FileReader("data/header/"+dosyaIsmi+".csv"));
            while ((line = br.readLine()) != null)

            {
                String[] keyValue = line.split(splitBy,2);
                headers.put(keyValue[0],keyValue[1]);
            }
            System.out.println("Headers dosyasi eklendi : "+headers);

        }catch (Exception e){
            System.out.println("Header dosyasi oluşturulurken hatayla karsilasildi");
            System.out.println(headers);
        }
    }

    @Given("{string} parametresini {string} value degeriyle headera ekle")
    public void addHeaderManuel(String key,String value){
        headers.put(key,value);
        System.out.println(headers);
    }

    @Given("{string} parametresini {string} value degeriyle body ekle")
    public void addBodyManuel(String key,String value){
        body.put(key,value);
        System.out.println(body);
    }

    @Given("{string} parametresini {string} value degeriyle parametre ekle")
    public void addParametreManuel(String key,String value){
        params.put(key,value);
        System.out.println(params);
    }

    @Given("{string} base uri olarak eklenir")
    public void addBaseUri(String baseUriString){
        baseUri = baseUriString;
    }


    @Given("{string} servisine {string} metoduyla istek at")
    public void request(String servis,String metot){

        RequestSpecification httpRequest = RestAssured.given();

        if (metot.equals("post")){
            httpRequest.headers(headers);
            System.out.println("Body dosyasi oluşturulan Json body ile istek atildi");
            httpRequest.body(body);
            httpRequest.queryParams(params);
            httpRequest.baseUri(baseUri);
            response = httpRequest.post(servis);
            System.out.println("------ Post metoduyla istek atildi ------");
            System.out.println("Response status kodu : "+response.getStatusCode());
            System.out.println("****************"+" RESPONSE BODY BASLANGICI "+"****************");
            System.out.println("Response : "+response.getBody().asPrettyString());
            System.out.println("****************"+" RESPONSE BODY BITISI "+"****************");
        }
        if (metot.equals("get")){
            httpRequest.headers(headers);
            httpRequest.body(body);
            httpRequest.queryParams(params);
            httpRequest.baseUri(baseUri);
            response = httpRequest.get(servis);
            System.out.println("------ Post metoduyla istek atildi ------");
            System.out.println("Response status kodu : "+response.getStatusCode());
            System.out.println("****************"+" RESPONSE BODY BASLANGICI "+"****************");
            System.out.println("Response : "+response.getBody().asPrettyString());
            System.out.println("****************"+" RESPONSE BODY BITISI "+"****************");
        }
    }

    @Given("{string} servisine json dosyasiyla {string} metoduyla istek at")
    public void request2(String servis,String metot){

        RequestSpecification httpRequest = RestAssured.given();

        if (metot.equals("post")){
            httpRequest.headers(headers);
            System.out.println("Json dosyasi oluşturulan Json body ile istek atildi");
            httpRequest.body(jsonObject);
            httpRequest.queryParams(params);
            httpRequest.baseUri(baseUri);
            response = httpRequest.post(servis);
            System.out.println("------ Post metoduyla istek atildi ------");
            System.out.println("Response status kodu : "+response.getStatusCode());
            System.out.println("****************"+" RESPONSE BODY BASLANGICI "+"****************");
            System.out.println("Response : "+response.getBody().asPrettyString());
            System.out.println("****************"+" RESPONSE BODY BITISI "+"****************");
        }
    }

    @Given("Response status kodunun {int} degerine esit oldugu kontrol edilir")
    public void statusCodeControl(int statusCode){
        Assert.assertEquals("Status kodu "+statusCode+" degerine esit degil !!!",response.getStatusCode(),statusCode);
        System.out.println("Status kodu "+statusCode+" degerine esit");
    }

    @Given("Response bodyde bulunan {string} parametresinin {string} degerine esit oldugu kontrol edilir")
    public void responseBodyControlEquals(String parametre,String value){

        JsonPath jsonPathEvaluator = response.jsonPath();

        String responseValue = jsonPathEvaluator.get(parametre).toString();

        System.out.println("Response icinde "+parametre+" parametresine ait deger : "+responseValue);

        Assert.assertTrue("Degerler birbirine esit degil",responseValue.equals(value));
    }

    @Given("Response bodyde bulunan {string} parametresinin {string} degerini icerdigi kontrol edilir")
    public void responseBodyControlContains(String parametre,String value){

        JsonPath jsonPathEvaluator = response.jsonPath();

        String responseValue = jsonPathEvaluator.get(parametre).toString();

        System.out.println("Response icinde "+parametre+" parametresine ait deger : "+responseValue);

        Assert.assertTrue("Degerleri icermiyor ",responseValue.contains(value));
    }

    @Given("Response bodyde bulunan {string} parametresinin deger icerdigi kontrol edilir")
    public void responseBodyControlContains(String parametre){

        JsonPath jsonPathEvaluator = response.jsonPath();

        String responseValue = jsonPathEvaluator.get(parametre).toString();

        System.out.println("Response icinde "+parametre+" parametresine ait deger : "+responseValue);

        Assert.assertNotNull("Degerleri icermiyor ",responseValue);
    }

    @Given("Response icerisinden {string} pathine ait degeri {string} ismiyle kaydet")
    public void saveParametre(String parametre,String Isim) throws IOException {

        JsonPath jsonPathEvaluator = response.jsonPath();

        String responseValue = jsonPathEvaluator.get(parametre).toString();

        saveValue.put(Isim,responseValue);

        System.out.println(responseValue+" degeri "+Isim+" adiyla kaydedildi");

    }

    @Given("Body, Header verileri silinir")
    public void clearData() {
        body.clear();
        headers.clear();

        System.out.println("Header ve body verileri silindi");
    }

    @Given("Json objesine {string} key, {string} value eklenir")
    public void jsonAddKeyValue(String key,String value){

        jsonObject.put(key,value);

        System.out.println("Json icerisine Key: "+key+" Value: -"+value+"- eklendi");

        System.out.println(jsonObject);
    }

    @Given("Json objesine {string} key ve {string} ismiyle kayitli value eklenir")
    public void jsonAddSavedKeyValue(String key,String value){

        jsonObject.put(key,saveValue.get(value));

        System.out.println("Json icerisine Key: "+key+" ve -"+value+"- ismiyle kayitli Value: -"+saveValue.get(value)+"- eklendi");

        System.out.println(jsonObject);
    }

    @Given("Json objesinde array icerisine eklenecek olan {string} key, {string} value eklenir")
    public void jsonAddNode(String key,String value){

        jsonChild.put(key, value);

        System.out.println("Json child icerisine Key: -"+key+"- Value: -"+value+"- eklendi");

        System.out.println(jsonChild);

    }

    @Given("Json objesinde array icerisine eklenecek olan {string} key ve {string} ismiyle kayitli value eklenir")
    public void addJsonSavedValue(String key,String value){

        String deneme = saveValue.get(value).replace("[","");

        String deneme2 = deneme.replace("]","");

        System.out.println(deneme);

        jsonChild.put(key, deneme2);

        System.out.println("Json icerisine Key: -"+key+"- ve "+value+" ismiyle kayitli Value: -"+saveValue.get(value)+"- eklendi");

        System.out.println(jsonChild);

    }


    @Given("{int} saniye beklenir")
    public void waitSecond(int second) throws IOException, InterruptedException {
        Thread.sleep(second*1000);
        System.out.println(second+" saniye beklendi");
    }

    @Given("{string} isimli Array olusuturulur")
    public void createJsonArray(String nodeName){
        jsonArray.put(jsonChild);
        jsonObject.put(nodeName, jsonArray);
        System.out.println(jsonObject);
    }


    @Given("bbbb")
    public void bbbb(){
        JSONObject jsonObject = new JSONObject();
        JSONArray cancelledItemsArray = new JSONArray();
        JSONObject cancelledItem = new JSONObject();
        jsonObject.put("OrderId","aaaaaa");
        System.out.println(jsonObject);

        cancelledItem.put("OrderItemId", "Deneme");
        cancelledItem.put("CancelReasonId","23");
        cancelledItemsArray.put(cancelledItem);
        jsonObject.put("CancelledItems", cancelledItemsArray);

        for (Object key : jsonObject.keySet()) {
            String keys = (String)key;
            String keyValue = jsonObject.get(keys).toString();
            body.put(keys,keyValue);
        }

        System.out.println("**********");
        System.out.println(jsonObject);
        System.out.println(body);
        System.out.println("**********");
    }

    @Given("a {int} {int}")
    public void denemeSonn(int nodeName,int aaaa) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"OrderId\":\""+nodeName+"\",\"CancelledItems\":[{\"OrderItemId\":"+aaaa+",\"CancelReasonId\":23}]}");
        Request request = new Request.Builder()
                .url("https://www.network.com.tr/Customerv2/RequestToCancelOrder")
                .method("POST", body)
                .addHeader("authority", "www.network.com.tr")
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("content-type", "application/json")
                .addHeader("cookie", "_gcl_au=1.1.570912208.1681285497; _fbp=fb.2.1681285498019.776968785; _tt_enable_cookie=1; _ttp=H9sOBWxTejsOEZOfENE43ofxPM6; _sgf_exp=; _ym_uid=1681285506388425761; _ym_d=1681285506; wis_l_750=1; _hjSessionUser_1660719=eyJpZCI6ImNhZTQxNDA0LTUxN2MtNTBjMS1hY2NiLTJjYmE4YWVhMzNmZSIsImNyZWF0ZWQiOjE2ODEyODU1MDUzNDcsImV4aXN0aW5nIjp0cnVlfQ==; wis_l_892=1; wis_l_879=1; wis_l_1471=1; wis_l_748=1; wis_l_1421=1; visid_incap_2817660=F9PBVllsQ0i+ZRfcXxTDw20FOGQAAAAAQUIPAAAAAAD3y7Df2v/RKRuDgMg9CZH5; firstVisitTime=1681393013097; enh_visitor_session=DfjRYvY81G; enh_source=https://www.google.com/; enh_token=6135f4bb9e1814394e86bc0f; g_conv_id=; OptanonAlertBoxClosed=2023-04-13T13:37:13.294Z; UserID=2005937438; LastClick7Days=direct; __rtbh.lid=%7B%22eventType%22%3A%22lid%22%2C%22id%22%3A%22pf86dd7YfIV1TWIvZlGr%22%7D; wis_l_1472=1; Entegral.CookieKey.Customer=07e1092e-0152-4020-8aaf-a623b4cfa3d3; wis_s_71499=233093; transactionID=AYM-20230419-2303567; _gid=GA1.3.1621977601.1682315822; _sgf_session_id=-4013987472498696192; _ym_isad=2; wis_i_71958=234848; _hjSession_1660719=eyJpZCI6Ijk4MTZlODdmLTA5NGYtNDEyOC04Njg1LWMyMDJiYWM0NDJjYyIsImNyZWF0ZWQiOjE2ODIzMzY5NTExNjEsImluU2FtcGxlIjpmYWxzZX0=; _hjAbsoluteSessionInProgress=1; wis_i_72072=235210; wis_i_71935=234762; ASP.NET_SessionId=cu21ankmbkij5l0hcogsu4jk; Entegral.CookieKey.LanguageID=0; _deviceType=d; nlbi_2817660=9ADTGAUD6DbAz+LSLFGQCwAAAADXKBXh5WxKwXXn4tKwlW0l; incap_ses_1193_2817660=Kr3xWo5hoAUIAdHO82OOEF+IRmQAAAAArMk4cu0E/ubr0zuNM0NHaw==; FirstClick7Days=direct; LastClick5Days=direct; FirstClick5Days=direct; LastClick1Day=direct; Entegral.CookieKey.CustomerBasketGuid=MDdlMTA5MmUtMDE1Mi00MDIwLThhYWYtYTYyM2I0Y2ZhM2Qz; __rtbh.uid=%7B%22eventType%22%3A%22uid%22%2C%22id%22%3A%222005937438%22%7D; _dn_sid=bec495a2-85ad-4918-9150-405c76546a0c; sessionCount=10; sessionCount_converter=10; ShouldResetCookie=False; Entegral.SessionKey.RecentlyViewedProduct=[156237]; Entegral.SessionKey.FavoritedProductIds=%5b%7b%22ProductListID%22%3a154904%2c%22ProductListName%22%3a%22Favorilerim%22%2c%22IsAdded%22%3afalse%2c%22ProductItemList%22%3a%5b126999%2c168394%2c166319%2c141555%2c147509%2c141423%2c168801%2c168633%2c165638%2c143646%2c168376%2c143634%2c168965%2c169043%2c156237%5d%7d%5d; _hjIncludedInSessionSample_1660719=0; wis_u=1907a0b9-8a8e-72b4-84ce-1362937f92e7_0_28_2005937438__79; _sgf_user_id=07e1092e-0152-4020-8aaf-a623b4cfa3d3; _ga=GA1.3.1844093850.1681285497; _ga_MN9S0Z4WEB=GS1.1.1682336948.30.1.1682345827.54.0.0; wis_v=1682336949238_71_home_1; OptanonConsent=isIABGlobal=false&datestamp=Mon+Apr+24+2023+17%3A17%3A08+GMT%2B0300+(GMT%2B03%3A00)&version=6.29.0&hosts=&genVendors=&landingPath=NotLandingPage&groups=C0001%3A1%2CC0004%3A1%2CC0002%3A1%2CC0003%3A1&AwaitingReconsent=false&geolocation=%3B; cto_bundle=XfnQ_V9veno4ZENFdU1GMWJpYlA4dm02SFV3eG1Wc2R5eXZFT21ubm5iY2JSdDRFMDJYZVV0ckUydENnN2JtcE5rTVp4c09KMkxzUVVMcHVyUzhoOEdkOGJxZzglMkZUY1NBWDdCbW5qTUJMJTJCZEElMkZCaVB5b0NTcHNMT2l1JTJCJTJCOGJjNGlyOXBpUXE4RVlzejEyVVJib3JLYXpGeDNBJTNEJTNE; _dd_s=rum=0&expire=1682346841527; incap_ses_1193_2817660=TZBcJX6goSO4KG0e9mOOEMzBVGQAAAAAfKnpXULkvDMBR0dV0gr33g==; nlbi_2817660=+lR3QxSS3BInX0SULFGQCwAAAACKDr5dk5Al4nnSAg1LrTWt; visid_incap_2817660=+B+j34yQSauVLMCq4GCMuMzBVGQAAAAAQUIPAAAAAACXOE7ZTVGRPZhfrjQvrHfZ; ARRAffinity=52f8a4371de16c7afc44f037d30946a21b847265bb64e90f074cad5f16452d0c; ARRAffinitySameSite=52f8a4371de16c7afc44f037d30946a21b847265bb64e90f074cad5f16452d0c; ASP.NET_SessionId=gqdpaa352mrfanfscqeerpem; Entegral.CookieKey.LanguageID=0")
                .addHeader("origin", "https://www.network.com.tr")
                .addHeader("referer", "https://www.network.com.tr/customer/order-detail/2306616")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"112\", \"Google Chrome\";v=\"112\", \"Not:A-Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
    }

    @Given("Siparis iptal icin kayitli {string} ve {string} degeriyle istek atilir")
    public void denemee(String a, String b) throws IOException {

        System.out.println(a+" degeri hafizada :"+saveValue.get(a));
        System.out.println(b+" degeri hafizada :"+saveValue.get(b));

        String orderIdString = saveValue.get(a);
        String deneme = saveValue.get(b).replace("[","");
        String orderItemIdString = deneme.replace("]","");

        int orderId = Integer.parseInt(orderIdString);
        int orderItemId = Integer.parseInt(orderItemIdString);

        System.out.println(orderId);
        System.out.println(orderItemId);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"OrderId\":"+orderId+",\"CancelledItems\":[{\"OrderItemId\":"+orderItemId+",\"CancelReasonId\":23}]}");
        Request request = new Request.Builder()
                .url("https://www.network.com.tr/Customerv2/RequestToCancelOrder")
                .method("POST", body)
                .addHeader("authority", "www.network.com.tr")
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("content-type", "application/json")
                .addHeader("cookie", "_gcl_au=1.1.570912208.1681285497; _fbp=fb.2.1681285498019.776968785; _tt_enable_cookie=1; _ttp=H9sOBWxTejsOEZOfENE43ofxPM6; _ym_uid=1681285506388425761; _ym_d=1681285506; _hjSessionUser_1660719=eyJpZCI6ImNhZTQxNDA0LTUxN2MtNTBjMS1hY2NiLTJjYmE4YWVhMzNmZSIsImNyZWF0ZWQiOjE2ODEyODU1MDUzNDcsImV4aXN0aW5nIjp0cnVlfQ==; wis_l_879=1; wis_l_1471=1; wis_l_748=1; wis_l_1421=1; firstVisitTime=1681393013097; enh_visitor_session=DfjRYvY81G; enh_source=https://www.google.com/; enh_token=6135f4bb9e1814394e86bc0f; g_conv_id=; OptanonAlertBoxClosed=2023-04-13T13:37:13.294Z; __rtbh.lid=%7B%22eventType%22%3A%22lid%22%2C%22id%22%3A%22pf86dd7YfIV1TWIvZlGr%22%7D; wis_s_71499=233093; visid_incap_2817660=IRE6tquaRvS+JYsURZcHehZ4U2QAAAAAQUIPAAAAAACf4ZyAF8Maoq4sPU75MBQS; wis_s_71501=233096; wis_r_220275=1; wis_r_167388=1; wis_r_236413=1; wis_r_167380=1; wis_r_236412=1; wis_r_167379=1; wis_r_236411=1; wis_l_750=1; _sgf_exp=; wis_l_892=1; LastClick7Days=direct; LastClick5Days=direct; UserID=2005937438; wis_l_749=1; wis_s_71265=232286; _gid=GA1.3.1863725084.1685949835; wis_l_1472=1; _deviceType=d; FirstClick5Days=direct; LastClick1Day=direct; transactionID=AYM-20230605-2343620; __rtbh.uid=%7B%22eventType%22%3A%22uid%22%2C%22id%22%3A%222005937438%22%7D; ASP.NET_SessionId=bvktfeg4egtdqtm5p0ycdm2z; Entegral.CookieKey.LanguageID=0; nlbi_2817660=X7PlEKMt532sIy8QLFGQCwAAAADX5NR5TehfD1WwIHb8nl2A; incap_ses_1196_2817660=6x6fVCXZSSv7nxilkgyZELjjfmQAAAAAEiLPT8J0AL5sk60ccC6P6A==; FirstClick7Days=direct; sessionCount=64; sessionCount_converter=64; wis_u=1907a0b9-8a8e-72b4-84ce-1362937f92e7_0_44_2005937438__60; _dn_sid=50dec637-183b-4e8c-8c36-861b32d254b3; _sgf_session_id=3173788020230275072; _ym_isad=2; _sgf_user_id=2fd5327a-18a7-48d2-a102-dc55391a6faa; _hjIncludedInSessionSample_1660719=0; _hjSession_1660719=eyJpZCI6ImVjZDEyNGZmLTNlNzgtNDU5Ny04YjIxLTNmOGI1NjNiYTU2NSIsImNyZWF0ZWQiOjE2ODYwMzc0MzkwMzEsImluU2FtcGxlIjpmYWxzZX0=; _hjAbsoluteSessionInProgress=1; wis_i_72164=235591; __RequestVerificationToken=Hwe6qkjtyC3SXfc0Ju5T-ZOEAXzEkzb8xqEyNeboBvhsWUBa-7_rT4kSjyHiOu9omDOZoMLIQ9EZwFTRkraJNYyVZhXqscVoq_v1a918Pog1; wis_i_72694=237491; Entegral.CookieKey.CustomerBasketGuid=MmZkNTMyN2EtMThhNy00OGQyLWExMDItZGM1NTM5MWE2ZmFh; Entegral.CookieKey.Customer=2fd5327a-18a7-48d2-a102-dc55391a6faa; Entegral.SessionKey.FavoritedProductIds=%5b%5d; lastVisit=0; ShouldResetCookie=False; _ga=GA1.3.309098414.1683715501; _ga_MN9S0Z4WEB=GS1.1.1686037433.51.1.1686037451.42.0.0; wis_v=1686037433454_6_home_1; OptanonConsent=isIABGlobal=false&datestamp=Tue+Jun+06+2023+10%3A44%3A11+GMT%2B0300+(GMT%2B03%3A00)&version=6.29.0&hosts=&genVendors=&landingPath=NotLandingPage&groups=C0001%3A1%2CC0004%3A1%2CC0002%3A1%2CC0003%3A1&AwaitingReconsent=false&geolocation=%3B; wis_i_72072=235210; cto_bundle=RQ-PtV9ZVzNwMkdxNk41bVNyaGExNWVmSzNHM1N4anE0U0czelhvSzRTbzdGdzlWOW9sd1lKYm8lMkJiS1F1cCUyRkR2QlZUUVhOblJ0YlE2bHR0eFQ4RjJkajklMkJQZFMzQ1hIaUNuNXZtZTFvTGZ2JTJGN2g5ZkZSWTdmUVpRUFhjU0lWNlclMkJIb3BoZ1lRNlJXZTFQNGhYT2V2NTVZeHp3JTNEJTNE; _dd_s=rum=0&expire=1686038621765")
                .addHeader("origin", "https://www.network.com.tr")
                .addHeader("referer", "https://www.network.com.tr/checkout")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"112\", \"Google Chrome\";v=\"112\", \"Not:A-Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("****************************************************************************");
        System.out.println(response.toString());
        Assert.assertTrue(response.toString().contains("200"));
        System.out.println("Siparis iptal isleminin basarili oldugu kontrol edildi");
    }
}
