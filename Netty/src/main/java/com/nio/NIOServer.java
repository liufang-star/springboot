package com.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO非阻塞网络编程
 * 案例：
 *       编写一个NIO入门案例，实现服务器端和客户端之间的数据简单通讯(非阻塞)
 * 目的：
 *       理解NIO非阻塞网络编程机制
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{

        //创建ServerSocketChannel ->ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个Selector对象
        Selector selector = Selector.open();

        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel 注册到 selector 关心事件 为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            //等待一秒，如果没有事件发生，返回
            if (selector.select(1000) == 0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            //如果返回的 > 0，就读取到相关的selectionKey集合
            //1、如果返回的 > 0，表示已经获取到关注的事件
            //2、selector.selectionKeys()返回关注事件的集合
            //通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //Set<SelectionKey>，使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()){
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key对应的通道发生的事件做相应处理
                if (key.isAcceptable()){//如果是OP_ACCEPT，有新的客户端连接
                    //该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个socketChannel "+socketChannel.hashCode());
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector，关注事件为 OP_READ，同时给socketChannel
                    //关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后，注册到selectionKey的数量 = "+selector.keys().size());
                }
                if (key.isReadable()){  //发生OP_READ
                    //通过key反向获取对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端  " + new String(buffer.array()));
                }
                //手动从集合中移动当前的selectionKey，防止重复操作
                keyIterator.remove();
                }
        }
    }
}
