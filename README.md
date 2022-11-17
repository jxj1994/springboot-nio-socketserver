# 一个用来研究NIO模式下java socket的demo

[BIO模式参考代码项目](https://github.com/DavidDingXu/springboot-socket-demo)

## BIO Socket的基本使用
1. 服务实例创建

```java
ServerSocket server = new ServerSocket(port);
```

此时已开始监听

2. 接受消息

```java
Socket socket = server.accept();
BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()))
```

3. 发送消息
```java
PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
writer.println("message");
```
    存在的问题：
        1、连接时服务器线程与客户端数量比例为1：1，费资源
        2、所有监听线程需要持续堵塞
        3、如果不使用多线程服务器启动后其他进程将堵塞在Socket Server中，无
    法加载其他类。NIO模式也存在类似问题，但NIO只需要一个线程轮询Selector事件
    耗费资源较少（个人理解）


## NIO Socket的基本使用
#### 参考资料：
[一文彻底理解BIO、NIO、AIO ](https://juejin.cn/post/7121354128067919886)

[BIO,NIO,AIO 总结](https://www.lanqiao.cn/library/JavaGuide/docs/java/BIO-NIO-AIO)

1. 服务实例创建

```java
// 获取socket通道
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
serverSocketChannel.bind(new InetSocketAddress(this.properties.getPort()));
//设置为非堵塞
serverSocketChannel.configureBlocking(false);
//打开nio模式通信监听，并使用selector通信通道
selector = Selector.open();
//将通道注册到选择器上，并且指定监听接收事件
serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
// 持续监听,轮询获取事件
while (true){
    if(selector.select()>0){
        // 获取选择器中所有注册通道的就绪事件
        Set<SelectionKey>selectionKeys=selector.selectedKeys();
        for(SelectionKey key:selectionKeys){
          //处理事件
        }
    }
}
```

2. 接受消息

```java
ByteBuffer rBuffer = ByteBuffer.allocate(1024);
Charset cs = Constant.DefaultChart;
String requestMsg;
rBuffer.clear();
int count = socketChannel.read(rBuffer);
// 读取数据
if (count > 0) {
   rBuffer.flip();
   requestMsg = String.valueOf(cs.decode(rBuffer).array());
   log.info("客户端消息：{}", requestMsg);
}
rBuffer.clear();
```

3. 发送消息
```java
ByteBuffer wBuffer = ByteBuffer.allocate(1024);
wBuffer.put(message.getBytes(Constant.DefaultChart));
wBuffer.flip();
socketChannel.write(wBuffer);
wBuffer.clear();
```