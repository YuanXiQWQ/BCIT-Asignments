package UploadServer;

import java.net.*;
import java.io.*;
import java.util.concurrent.Semaphore;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Clock;

public class UploadServerThread extends Thread {
    private Socket socket = null;

    public UploadServerThread(Socket socket)
    {
        super("DirServerThread");
        this.socket = socket;
    }

    private static Semaphore sem = new Semaphore(3);

    public void run()
    {
        try
        {
            sem.acquire();
            // 将输入流转换为字符缓冲流，便于读取 HTTP 请求首行
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // 读取请求的第一行，例如："GET / HTTP/1.1"
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            // 按空格分割获取方法名和路径
            String[] parts = requestLine.split("\\s+");
            String method = parts.length > 0 ? parts[0] : "";
            String path   = parts.length > 1 ? parts[1] : "/";

            // 构造请求和响应对象给封装好的类
            HttpServletRequest req = new HttpServletRequest(socket, method, path, in);
            HttpServletResponse resp = new HttpServletResponse(socket.getOutputStream());

            // 根据请求方法进行分发
            if ("GET".equals(method) && "/".equals(path)) {
                // 如果是 GET 请求并且路径为 "/"，调用 doGet() 生成上传表单页面
                httpServlet.doGet(req, resp);

            } else if ("POST".equals(method)) {
                // 如果是 POST 请求，调用 doPost() 处理上传逻辑
                httpServlet.doPost(req, resp);

            } else {
                // 其它情况返回 404 或 405
                resp.setStatus(404);
                resp.setHeader("Content-Type", "text/plain; charset=UTF-8");
                resp.writeBody("404 Not Found");
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            socket.close();
            UploadServer.sem.release();
        }
    }
}
