package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    private static final String BASE_URL = PropertiesUtil.get("api.base.url");
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
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        return mapper.readValue(response.toString(), type);
    }

    public static void postMultipart(String path, Part filePart, HttpServletRequest request) throws Exception {
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();

        URL url = new URL(BASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        addCookie(conn, request);

        OutputStream os = conn.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8), true);

        writer.append("--").append(boundary).append("\r\n");
        writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"file.csv\"\r\n");
        writer.append("Content-Type: text/csv\r\n\r\n").flush();

        InputStream input = filePart.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = input.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        os.flush();

        writer.append("\r\n").flush();
        writer.append("--").append(boundary).append("--").append("\r\n").flush();
        writer.close();

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Multipart POST failed: " + status);
        }
    }

    public static void delete(String path, HttpServletRequest request) throws Exception {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        addCookie(conn, request);

        int status = conn.getResponseCode();
        if (status != 200 && status != 204) {
            throw new RuntimeException("DELETE failed: " + status);
        }
    }

    public static <T> void put(String path, Object body, Class<T> responseType, HttpServletRequest request) throws Exception {
        URL url = new URL(BASE_URL + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        addCookie(conn, request);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(body);

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes());
        os.flush();

        int status = conn.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("PUT failed: " + status);
        }

        InputStream is = conn.getInputStream();
        mapper.readValue(is, responseType);
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