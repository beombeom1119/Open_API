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

            String jsonResult = "[{\n\"영화명(한글)\" : " + "\"" + movieInfo.get("movieNm") + "\",\n" + "\"개봉일\" : " + movieInfo.get("openDt") + ",\n" + "\"감독이름\": " + "\"" + directors_peopleNm.get("peopleNm") + "\"" + "\n}]";

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonResult);

            return obj;
//                System.out.println("[{");
//                System.out.println(" \"영화명(한글)\" : "+ "\"" +movieInfo.get("movieNm")+"\",");
//                System.out.println("\"개봉일\" : "+movieInfo.get("openDt")+",");
//                System.out.println("\"감독이름\": "+"\""+directors_peopleNm.get("peopleNm")+"\"");
//                System.out.println("}]");
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }

        }


        @GetMapping("/jsontest")
    public String json()
        {
            String json1= "[{\n" +
                    " \"영화명(한글)\" : \"타워\",\n" +
                    "\"개봉일\" : 20121225,\n" +
                    "\"감독이름\": \"김지훈\"\n" +
                    "}]";

            return json1;
        }

}








