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


    @GetMapping("/papa")
    public String hel() {
        String clientId = "asd";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "asd";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode("안녕하세요. 오늘 기분은 어떻습니까?", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=en&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            return "";
        }

    }


    @GetMapping("real")
    public Object real() throws Exception
    {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(readUrl());
            JSONObject json = (JSONObject)jsonObject.get("SchulInfoHgschl");
            JSONArray array = (JSONArray)json.get("row");
            for(int i=0; i<array.size(); i++){
                JSONObject row = (JSONObject)array.get(i);
                String school = (String)row.get("SCHUL_NM");
                System.out.println(school);
            }
        }

        private static String readUrl() throws Exception{
            BufferedInputStream reader = null;

            try {
                URL url = new URL("http://openapi.seoul.go.kr:8088/"
                        + key+"/json/SchulInfoHgschl/1/20/");

                reader = new BufferedInputStream(url.openStream());
                StringBuffer buffer = new StringBuffer();
                int i = 0;
                byte[] b = new byte[4096];
                while((i = reader.read(b)) != -1){
                    buffer.append(new String(b, 0, i));
                }
                return buffer.toString();

            } finally{
                if(reader != null) reader.close();

            }

        }
    }

    }

}

