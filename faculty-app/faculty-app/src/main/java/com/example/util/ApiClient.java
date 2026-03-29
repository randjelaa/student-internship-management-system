package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T post(String path, Object requestBody, Class<T> responseType, HttpServletRequest request) throws Exception {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        addCookie(conn, request);

        if (requestBody != null) {
            String json = mapper.writeValueAsString(requestBody);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }
        }

        int status = conn.getResponseCode();

        if (status == 200) {
            saveCookie(conn, request);
            return readResponse(conn, responseType);

        } else {
            throw new RuntimeException("POST failed: " + status);
        }
    }

    public static <T> T get(String path, Class<T> responseType, HttpServletRequest request) throws Exception {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        addCookie(conn, request);

        int status = conn.getResponseCode();

        if (status == 200) {
            return readResponse(conn, responseType);
        } else {
            throw new RuntimeException("GET failed: " + status);
        }
    }

    private static <T> T readResponse(HttpURLConnection conn, Class<T> type) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        return mapper.readValue(response.toString(), type);
    }

    private static void addCookie(HttpURLConnection conn, HttpServletRequest request) {
        Object cookie = request.getSession().getAttribute("JSESSIONID");

        if (cookie != null) {
            conn.setRequestProperty("Cookie", cookie.toString());
        }
    }

    private static void saveCookie(HttpURLConnection conn, HttpServletRequest request) {
        String cookie = conn.getHeaderField("Set-Cookie");

        if (cookie != null) {
            request.getSession().setAttribute("JSESSIONID", cookie);
        }
    }
}