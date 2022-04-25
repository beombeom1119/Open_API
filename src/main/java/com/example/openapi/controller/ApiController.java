package com.example.openapi.controller;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

@RestController
public class ApiController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/cylinder")
    public Object callAPI() throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlSTR = "https://api.covid19api.com/total/dayone/country/kr";
        URL url = new URL(urlSTR);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n\r");
        }

        urlConnection.disconnect();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(result.toString());
        return obj;
    }


    @GetMapping("/real")
    public Object real() {

        // 인증키 (개인이 받아와야함)
        String key = "2911a3c9703528caac2b24c313aec593";

        // 파싱한 데이터를 저장할 변수
        String result = "";

        try {


            URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key="
                    + key + "&movieCd=20124039");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject movieInfoResult = (JSONObject) jsonObject.get("movieInfoResult");
            JSONObject movieInfo = (JSONObject) movieInfoResult.get("movieInfo");
            JSONArray directors = (JSONArray) movieInfo.get("directors");
            JSONObject directors_peopleNm = (JSONObject) directors.get(0);
            String jsonResult = "[{\n\"영화명(한글)\" : " + "\"" + movieInfo.get("movieNm") + "\",\n" + "\"개봉일\" : "
                    + movieInfo.get("openDt") + ",\n" + "\"감독이름\": " + "\"" + directors_peopleNm.get("peopleNm") + "\"" + "\n}]";

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonResult);

            return obj;


        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }

    }


    @GetMapping("/json")
    public Object json() {

        // 인증키 (개인이 받아와야함)
        String key = "2911a3c9703528caac2b24c313aec593";

        // 파싱한 데이터를 저장할 변수
        String result = "";

        try {


            URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key="
                    + key + "&movieCd=20124039");

            BufferedReader bf;

            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject movieInfoResult = (JSONObject) jsonObject.get("movieInfoResult");
            JSONObject movieInfo = (JSONObject) movieInfoResult.get("movieInfo");
            JSONArray actors = (JSONArray) movieInfo.get("actors");
            return actors;

        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }


    }


    @GetMapping("/example")
    public Object example() {

        // 파싱한 데이터를 저장할 변수
        String result = "";

        try {
            URL url = new URL("https://api.covid19api.com/total/dayone/country/kr");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }


    }


    @GetMapping("/map")
    public Object map() {

        // 인증키 (개인이 받아와야함)
        String key = "2911a3c9703528caac2b24c313aec593";

        // 파싱한 데이터를 저장할 변수
        String result = "";

        try {

            URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key="
                    + key + "&movieCd=20124039");

            BufferedReader bf;

            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject movieInfoResult = (JSONObject) jsonObject.get("movieInfoResult");
            JSONObject movieInfo = (JSONObject) movieInfoResult.get("movieInfo");
            JSONArray actors = (JSONArray) movieInfo.get("actors");

            JSONArray jsonArray = new JSONArray();


            for (int i = 0; i < actors.size(); i++) {
                HashMap<String, String> hashMap = new HashMap();
                JSONObject target = (JSONObject) actors.get(i);
                hashMap.put("peopleNm", (String) target.get("peopleNm"));
                hashMap.put("peopleNmEn", (String) target.get("peopleNmEn"));
                jsonArray.add(hashMap);
            }

            return jsonArray;

//


        } catch (Exception e) {
            e.printStackTrace();
            return "[뭔가 오류]";
        }


    }


    @GetMapping("/map1")
    public Object corona() {


        String result = "";

        try {

            URL url = new URL("https://api.covid19api.com/total/dayone/country/kr");

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONArray coronaArray = (JSONArray) jsonParser.parse(result);
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < coronaArray.size(); i++) {
                HashMap hashMap = new HashMap();                     // <,> 없이 HASH MAP 생성
                JSONObject target = (JSONObject) coronaArray.get(i);
                hashMap.put("Country", target.get("Country"));      //Country 뽑아내기
                hashMap.put("Active", target.get("Active"));        //Active 뽑아내기
                hashMap.put("Confirmed", target.get("Confirmed"));  //Confirmed 뽑아내기
                jsonArray.add(hashMap);
            }
            return jsonArray;

        } catch (Exception e) {
            e.printStackTrace();
            return "[뭔가 오류]";
        }
    }
}








