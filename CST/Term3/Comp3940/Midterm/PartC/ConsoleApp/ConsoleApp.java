package ConsoleApp;
import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.lang.reflect.*;
// 作业III-第1题-第4&5小问：ConsoleApp
public class ConsoleApp {
    public static void main(String[] args) {
        try {
            // 第5小问：Singleton 配置（创建型）
            HttpConfig cfg = HttpConfig.get();
            // 第4小问：准备要发送的表单字段
            String caption = (args.length > 0 ? args[0] : "demo");
            String date    = (args.length > 1 ? args[1] : LocalDate.now().toString());
            Path   file    = Paths.get(args.length > 2 ? args[2] : "sample.bin"); // 可替换为实际文件
            // 【困惑说明】为什么不依赖第三方库？
            // 题目希望“原生控制台程序”，这里用 Socket 手写 HTTP 报文，明确 boundary/CRLF/Content-Length。
            // 第5小问：行为型——线程池异步提交
            ExecutorService es = Executors.newSingleThreadExecutor();
            Uploader real = new MultipartUploader(cfg);
            // 第5小问：AOP（最小动态代理） + 日志切面
            Uploader proxy = (Uploader) Proxy.newProxyInstance(
                    Uploader.class.getClassLoader(),
                    new Class[]{Uploader.class},
                    new LoggingAspect(real));  // 在调用前后打印日志（横切关注点）
            Future<Integer> f = es.submit(() -> proxy.upload(caption, date, file));
            System.out.println("已提交上传任务（线程池异步）...");
            int status = f.get();
            System.out.println("上传结束，HTTP 状态码 = " + status);
            es.shutdown();
        } catch (UploadException ue) {
            // 第5小问：自定义异常
            System.err.println("上传失败（UploadException）: " + ue.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* 第5小问：创建型（Singleton 配置） */
    static final class HttpConfig {
        private static final HttpConfig INSTANCE = new HttpConfig();
        public final String host = "127.0.0.1";  // 或 localhost
        public final int    port = 8082;
        public final String path = "/upload";    // 与服务器 doPost 的路径一致


        private HttpConfig() {}
        public static HttpConfig get() { return INSTANCE; }
    }
    /* 第4小问：上传接口（用于AOP代理） */
    interface Uploader {
        int upload(String caption, String date, Path file) throws Exception;
    }
    /* 第5小问：AOP-风格的最小日志切面（动态代理） */
    static final class LoggingAspect implements InvocationHandler {
        private final Object target;
        LoggingAspect(Object t) { this.target = t; }
        @Override public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            long t0 = System.currentTimeMillis();
            System.out.println("[LOG] 调用 " + m.getName() + " 开始");
            try {
                return m.invoke(target, args);
            } finally {
                System.out.println("[LOG] 调用 " + m.getName() + " 结束，耗时 " + (System.currentTimeMillis() - t0) + "ms");
            }
        }
    }

    /* 第4&5小问：具体实现——手写 multipart/form-data 上传 */
    static final class MultipartUploader implements Uploader {
        private final HttpConfig cfg;
        MultipartUploader(HttpConfig cfg) { this.cfg = cfg; }
        @Override
        public int upload(String caption, String date, Path file) throws Exception {
            if (!Files.exists(file) || !Files.isRegularFile(file))
                throw new UploadException("待上传文件不存在: " + file.toAbsolutePath());
            // 第5小问：结构型（简单工厂）构造各类 Part
            String boundary = "----Boundary" + System.currentTimeMillis(); // 简单唯一
            BodyPart textCaption = PartFactory.text("caption", caption);
            BodyPart textDate    = PartFactory.text("date", date);
            byte[] fileBytes     = Files.readAllBytes(file); // 函数式：直接一次性读取
            String filename      = file.getFileName().toString();
            String contentType   = probeContentType(filename);

            BodyPart filePart    = PartFactory.file("file", filename, contentType, fileBytes);
            // 第4小问：拼接 multipart 原始字节（注意 CRLF）
            byte[] body = buildMultipart(boundary, Arrays.asList(textCaption, textDate, filePart));
            // 第4小问：构造 HTTP 请求报文（必须准确 Content-Length 与 CRLF）
            String requestLine = "POST " + cfg.path + " HTTP/1.1\r\n";
            String headers =
                    "Host: " + cfg.host + ":" + cfg.port + "\r\n" +
                            "Content-Type: multipart/form-data; boundary=" + boundary + "\r\n" +
                            "Content-Length: " + body.length + "\r\n" +
                            "Connection: close\r\n" +   // 便于用“读到对端关闭”判断响应结束
                            "\r\n";                      // 空行分隔 header 与 body

            try (Socket sock = new Socket(cfg.host, cfg.port);
                 OutputStream out = sock.getOutputStream();
                 InputStream in = sock.getInputStream()) {
                // 发出请求行 + 头 + 实体
                out.write(requestLine.getBytes("UTF-8"));
                out.write(headers.getBytes("UTF-8"));
                out.write(body);
                out.flush();
                // 读取响应（简化：读到对端关闭为止）
                ByteArrayOutputStream respBuf = new ByteArrayOutputStream();
                byte[] buf = new byte[8192];
                int n;
                while ((n = in.read(buf)) != -1) respBuf.write(buf, 0, n);


                String respRaw = respBuf.toString("UTF-8");
                System.out.println("服务器响应：\n" + respRaw);
                // 解析状态码（简单解析第一行）
                String firstLine = respRaw.split("\r\n", 2)[0];
                int code = 0;
                if (firstLine.startsWith("HTTP/")) {
                    String[] p = firstLine.split("\\s+");
                    if (p.length >= 2) code = Integer.parseInt(p[1]);
                }
                return code;
            }
        }
        // 【困惑说明】为什么 boundary 两边还有“--”？
        // 在实体里，每个分隔行以 "--" + boundary 开头；最终结束用 "--" + boundary + "--"
        private byte[] buildMultipart(String boundary, List<BodyPart> parts) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (BodyPart p : parts) {
                bos.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
                bos.write(p.headers().getBytes("UTF-8"));
                bos.write("\r\n".getBytes("UTF-8"));  // 空行后接内容
                bos.write(p.content());
                bos.write("\r\n".getBytes("UTF-8"));  // 每个 part 结束 CRLF
            }
            bos.write(("--" + boundary + "--\r\n").getBytes("UTF-8")); // 最终结束边界
            return bos.toByteArray();
        }
        // 简单推断 Content-Type；不准确也没关系（服务器端不强依赖）
        private String probeContentType(String filename) {
            String lower = filename.toLowerCase(Locale.ROOT);
            if (lower.endsWith(".png"))  return "image/png";
            if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
            if (lower.endsWith(".gif"))  return "image/gif";
            if (lower.endsWith(".txt"))  return "text/plain; charset=UTF-8";
            return "application/octet-stream";
        }
    }
    /* 第5小问：结构型（简单工厂） */
    static final class PartFactory {
        static BodyPart text(String name, String value) {
            // Content-Disposition 仅 name，无 filename
            String headers = "Content-Disposition: form-data; name=\"" + name + "\"\r\n" +
                    "Content-Type: text/plain; charset=UTF-8\r\n";
            return new BodyPart(headers, value.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        static BodyPart file(String field, String filename, String contentType, byte[] bytes) {
            String headers = "Content-Disposition: form-data; name=\"" + field + "\"; filename=\"" + filename + "\"\r\n" +
                    "Content-Type: " + contentType + "\r\n";
            return new BodyPart(headers, bytes);
        }
    }
    static final class BodyPart {
        private final String headers; // 每个 part 的头部（不含空行）
        private final byte[] content; // 每个 part 的内容字节
        BodyPart(String headers, byte[] content) { this.headers = headers; this.content = content; }
        String headers() { return headers; }
        byte[] content() { return content; }
    }
    /* 第5小问：自定义异常 */
    static final class UploadException extends RuntimeException {
        public UploadException(String msg) { super(msg); }
    }
}
