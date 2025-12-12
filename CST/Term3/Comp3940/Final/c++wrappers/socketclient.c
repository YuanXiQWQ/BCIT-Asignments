#include <sys/socket.h>
#include <sys/types.h>
#include <resolv.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>

int main() {
  int sock;
  struct sockaddr_in server;
  int msgsock;
  char buf[1024];
  struct hostent *hp;
  char *host = "127.0.0.1";
  int rval;

  // ====== 客户端初始化流程 Step 1: 创建 socket ======
  // 和服务器一样，创建一个 IPv4 + TCP 的套接字
  sock = socket (AF_INET, SOCK_STREAM, 0);
  if (sock < 0) {
    perror("opening stream socket");
  }

  // ====== 客户端初始化流程 Step 2: 准备服务器地址结构体 ======
  // 先把 server 结构清零，避免有脏数据
  bzero(&server, sizeof(server));

  // 通过主机名（这里是 "localhost"）查询 IP 地址
  // 结果存在 hostent 结构里
  hp = gethostbyname("localhost");

  // 把查询到的 IP 地址拷贝到 server.sin_addr 中
  bcopy((char*)hp->h_addr, (char*)&server.sin_addr, hp->h_length);

  // 设置地址族为 IPv4
  server.sin_family = AF_INET;

  // 设置要连接的服务器端口号
  // 服务器那边 bind 了 8888，这里也要连 8888
  // 严格写法也应该使用 htons(8888)
  server.sin_port = 8888;

  // ====== 客户端初始化流程 Step 3: connect 主动发起连接 ======
  // 客户端用 connect() 向 server 指定的 IP:port 发起 TCP 连接
  // 成功后，sock 就代表一条已经建立好的 TCP 连接
  if (connect(sock, (struct sockaddr*)&server, sizeof(server))<0){
    perror("connecting");
  }

  // ====== 建立连接后的通信：先向服务器发送数据 ======
  // 这里把字符串 "/usr/include" 拷贝到 buf 中
  strcpy(buf,"/usr/include");

  // 把 buf 的内容通过 socket 写到服务器
  // strlen(buf)+1 包含结尾 '\0'
  if ((rval = write(sock, buf, strlen(buf)+1)) < 0){
    perror("writing socket");
  }

  // ====== 从服务器读回数据并打印 ======
  // 不断从 sock 里读取数据到 buf
  // read 返回 0 表示对方关闭连接，循环结束
  while (read(sock, buf, sizeof(buf)) > 0){
    printf("%s", buf);
  }

  // ====== 通信结束后关闭 socket ======
  close (sock);
}
