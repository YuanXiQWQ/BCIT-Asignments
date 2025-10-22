package UploadServer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

// 作业III-第1题 仅服务器端示例：GET表单 + POST解析保存 + HTML目录页
@MultipartConfig // 允许解析 multipart/form-data（文件上传）
public class UploadServlet extends HttpServlet {

    /*公共工具*/

    // 统一的上传目录（示例：${catalina.base}/webapps/upload/images）
    private File imagesDir() {
        String base = System.getProperty("catalina.base");          // 课程示例里一直用这个
        File dir = new File(base + File.separator + "webapps"
                + File.separator + "upload" + File.separator + "images");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    // 仅保留安全字符，避免路径穿越/XSS（示例足够用）
    private String safe(String s) {
        if (s == null) return "";
        return s.replaceAll("[^\\w\\-\\.]", "_");
    }

    // HTML 转义，目录页输出时用
    private String esc(String s) {
        return s.replace("&","&amp;").replace("<","&lt;")
                .replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
    }

    /*第1小问：GET / 返回上传表单*/
    // 题意：当浏览器访问 http://localhost:8082/ 时，返回含 file/caption/date 的 HTML 表单
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 【困惑说明】为什么是 text/html？
        // 因为题目要求“发送网页给浏览器”，必须设置 HTML 类型，不能再用 text/plain。
        String html =
                "<!doctype html>\r\n" +
                        "<html><head><meta charset='utf-8'><title>上传示例</title></head><body>\r\n" +
                        "<h2>作业III-第1题（第1小问）：上传表单</h2>\r\n" +
                        // 必须使用 multipart/form-data 才能上传文件
                        "<form action='/upload' method='POST' enctype='multipart/form-data'>\r\n" +
                        "  <p><label>标题 caption：<input name='caption' required></label></p>\r\n" +
                        "  <p><label>日期 date：<input type='date' name='date' required></label></p>\r\n" +
                        // 【困惑说明】字段名大小写必须与后台一致；这里用小写 'file'，后台也用 'file'
                        "  <p><label>文件 file：<input type='file' name='file' required></label></p>\r\n" +
                        "  <p><button type='submit'>提交</button></p>\r\n" +
                        "</form>\r\n" +
                        "</body></html>";
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html; charset=UTF-8");
        resp.setContentLength(html.getBytes("UTF-8").length);
        resp.getWriter().print(html);
    }

    /*第2小问：POST /upload 解析并重命名保存*/
    // 题意：解析 multipart，提取 caption/date/filename/文件，保存到目录，文件名需包含 caption 与 date
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 【困惑说明】在 Servlet 3.0+ 且有 @MultipartConfig 的情况下：
        // - 文本字段通常可用 req.getParameter("caption") 直接取到（容器会帮忙）
        // - 也可用 req.getPart("caption") 读取文本；这里优先用 getParameter，失败再回退到 Part
        String caption = req.getParameter("caption");
        String date    = req.getParameter("date");
        if (caption == null) {
            Part p = req.getPart("caption");
            caption = (p != null) ? new String(p.getInputStream().readAllBytes(), "UTF-8") : "";
        }
        if (date == null) {
            Part p = req.getPart("date");
            date = (p != null) ? new String(p.getInputStream().readAllBytes(), "UTF-8") : "";
        }
        // 文件 part；注意字段名与表单一致（这里使用 'file'）
        Part filePart = req.getPart("file");
        if (filePart == null || filePart.getSize() == 0) {
            // 简单处理：缺少文件即 400（示例足够）
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "缺少文件");
            return;
        }
        // 原始文件名（某些容器需从 header 中解析；Servlet 4 常可用 getSubmittedFileName）
        String submitted = filePart.getSubmittedFileName();
        if (submitted == null || submitted.isEmpty()) {
            // 退化处理：给个默认后缀
            submitted = "upload.bin";
        }
        // 取扩展名（保留）
        String ext = "";
        int dot = submitted.lastIndexOf('.');
        if (dot >= 0) ext = submitted.substring(dot);
        // 作业要求：保存时把 caption 与 date 拼进文件名
        String safeCaption = safe(caption);
        String safeDate    = safe(date);
        String ts          = String.valueOf(System.currentTimeMillis()); // 防重名，示例足够
        String finalName   = (safeDate.isEmpty() ? "nodate" : safeDate) + "_"
                + (safeCaption.isEmpty() ? "nocaption" : safeCaption) + "_"
                + ts + ext;
        // 保存到 images 目录
        File dir = imagesDir();
        File dest = new File(dir, finalName);
        try (InputStream in = filePart.getInputStream();
             OutputStream out = new FileOutputStream(dest)) {
            in.transferTo(out); // 二进制安全：适用于文本与图片（满足“二进制/文本均可”）
        }
        // 保存完成后直接进入“第3小问”的目录页输出（下面合并实现）
        listAsHtml(resp, dir);
    }

    /*第3小问：返回按字母序排序的 HTML 目录页*/
    // 题意：之前 doPost 返回纯文本，这里需要 HTML 页面且按字母序
    private void listAsHtml(HttpServletResponse resp, File dir) throws IOException {
        String[] names = dir.list((d, name) -> new File(d, name).isFile());
        if (names == null) names = new String[0];

        // 按字母序（忽略大小写）排序
        Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);

        StringBuilder html = new StringBuilder();
        html.append("<!doctype html>\r\n")
                .append("<html><head><meta charset='utf-8'><title>目录列表</title></head><body>\r\n")
                .append("<h2>作业III-第1题（第3小问）：images 目录（按字母序）</h2>\r\n")
                .append("<ul>\r\n");
        for (String n : names) {
            html.append("<li>").append(esc(n)).append("</li>\r\n");
        }
        html.append("</ul>\r\n")
                .append("<p><a href='/'>返回上传表单</a></p>\r\n")
                .append("</body></html>");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html; charset=UTF-8");
        resp.setContentLength(html.toString().getBytes("UTF-8").length);
        resp.getWriter().print(html.toString());
    }
}
