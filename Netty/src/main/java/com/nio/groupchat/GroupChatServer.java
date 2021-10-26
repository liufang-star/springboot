package com.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * NIO网络编程应用实例 - 群聊系统
 *       实例要求：
 *             1）编写一个NIO群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 *             2)实现多人聊天系统
 *             3)服务器端：可以监测用户上线，离线，并实现消息转发功能
 *             4)客户端：通过channel可以无阻塞发送消息给其它所有用户，同时可以接收其它用户发送的消息（由服务器转发得到）
 *             5）目的：进一步理解NIO非阻塞网络编程机制
 *
 */

public class GroupChatServer {
    //定义属性
    private Selector selector;    //选择器
    private ServerSocketChannel listenChannel;   //用于监听
    private static  final int PORT = 6666;   //监听的端口号

    //构造器
    //初始化工作
    public GroupChatServer(){
        try{
            //得到选择器
            selector = Selector.open();
            //得到ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try{
            //循环处理
            while(true){
                int count = selector.select();
                if (count > 0){   //有事件处理
                    //遍历得到selector集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        //取出selectionKey
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将该sc注册到selector
                            sc.register(selector,SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress() + "上线.");
                        }
                        if (key.isReadable()){//通道发生read事件，即通道是可读的状态
                            //处理读（专门写方法..）
                            readData(key);
                        }
                        //当前的key删除，防止重复处理
                        iterator.remove();
                    }
                }else{
                    System.out.println("等待......");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //发生处理异常
        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) throws Exception {
        //定义一个SocketChannel
        SocketChannel channel = null;
        try{
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0){
                //把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("form 客户端 ： "+msg);
                //向其他的客户端转发消息，专门写一个方法来处理
                sendInfoToOtherClients(msg,channel);
            }
        }catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress() + "离线了...");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }

    //转发消息给其他客户（通道）
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException{
        System.out.println("服务器转发消息中...");
        //遍历 所有到注册selector上的SocketChannel，并排除self
        for(SelectionKey key: selector.keys()){
            //通过key，取出对应的SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self){
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer写入到通道
                dest.write(buffer);
            }
        }

    }

    public static void main(String[] args) {
        //创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
