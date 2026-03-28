package com.example.service;

import com.example.model.dto.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {

    public LoginResponse login(String email, String password, HttpServletRequest request) throws Exception {

        URL url = new URL("http://localhost:8080/api/auth/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String json = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email, password
        );

        try(OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
        }

        int status = conn.getResponseCode();

        if (status == 200) {

            // 🔥 UZMI SESSION COOKIE
            String cookie = conn.getHeaderField("Set-Cookie");

            // sacuvaj cookie u JSP session
            request.getSession().setAttribute("JSESSIONID", cookie);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // parsiranje (mozes koristiti Jackson)
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.toString(), LoginResponse.class);

        } else {
            throw new RuntimeException("Login failed");
        }
    }
}
