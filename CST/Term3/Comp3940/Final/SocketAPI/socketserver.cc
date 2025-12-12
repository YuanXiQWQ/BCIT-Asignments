#include <sys/socket.h>
#include <sys/types.h>
#include <resolv.h>
#include <unistd.h>
#include <stdio.h>
// 一般还会包含 <netinet/in.h> 用于 sockaddr_in 结构体
// #include <netinet/in.h>

int main() {
  int sock, length;
  struct sockaddr_in server;
  int msgsock;
  char buf[1024];
  int rval;
  int i;

  // ====== 服务器初始化流程 Step 1: 创建 socket ======
  // AF_INET      表示使用 IPv4
  // SOCK_STREAM  表示使用 TCP（面向连接的字节流）
  // 第三个参数 0 让系统自动选择协议（对 AF_INET + SOCK_STREAM 一般就是 TCP）
  sock = socket (AF_INET, SOCK_STREAM, 0);
  if (sock < 0) {
    perror("opening stream socket");
  }

  // ====== 服务器初始化流程 Step 2: 填写本地地址信息（server sockaddr_in） ======
  // 设置地址族为 IPv4
  server.sin_family = AF_INET;
  // INADDR_ANY 表示绑定到本机所有网卡的地址（0.0.0.0）
  server.sin_addr.s_addr = INADDR_ANY;
  // 这里直接写 8888，严格来说应该用 htons(8888) 转成网络字节序
  server.sin_port = 8888;

  // ====== 服务器初始化流程 Step 3: bind 把 socket 和 IP:port 绑定起来 ======
  // 把 sock 这个套接字绑定到 server 指定的本地地址和端口
  if (bind (sock, (struct sockaddr *)&server, sizeof server) < 0) {
    perror ("binding stream socket");
  }

  // ====== 服务器初始化流程 Step 4: listen 把 socket 变成“监听套接字” ======
  // 第二个参数 5 表示“等待连接队列”的最大长度
  listen (sock, 5);

  // ====== 服务器初始化流程 Step 5: accept 等待客户端连接（阻塞） ======
  // accept 会阻塞，直到有客户端来连
  // 返回的新 socket（msgsock）专门用于和该客户端通信
  msgsock = accept(sock, (struct sockaddr *)0, (socklen_t *)0);
  if (msgsock == -1) {
    perror("accept");
  }

  // ====== 建立连接后的通信：从客户端读数据 ======
  // 从 msgsock 中读取最多 1024 字节数据到 buf
  // read 也可能阻塞，直到客户端发送了数据或者关闭连接
  if ((rval = read(msgsock, buf, 1024)) < 0){
    perror("reading socket");
  } else  {
    printf("%s\n",buf);
  }

  // ====== 通信结束后关闭和客户端对应的 socket ======
  close (msgsock);
}
