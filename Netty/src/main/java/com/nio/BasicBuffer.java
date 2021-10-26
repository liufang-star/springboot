package com.nio;

import java.nio.IntBuffer;

/**
 * Buffer的使用
 *
 * NIO和BIO的比较
 * 1、BIO以流的方式处理数据，而NIO以块的方式处理数据，块I/O的效率比流I/O高很多
 * 2、BIO是阻塞的，而NIO是非阻塞的
 * 3、BIO基于字节流和字符流进行操作，而NIO基于Channel（通道）和Buffer（缓冲区）进行操作，数据总是从通道读取数据到缓冲区，或者从缓冲区写入到通道中。
 *    Selector（选择器）用于监听多个通道的事件（比如：连接请求，数据到达等），因此使用单个线程就可以监听多个客户端通道
 *
 * Buffer类定义了所有的缓冲区都具有的四个属性来提供关于其所包含的数据元素的信息
 *              Capacity：容量
 *              Limit：缓冲区的当前终点
 *              Mark：标记
 *              Position：位置
 */
public class BasicBuffer {
    public static void main(String[] args){
        //创建一个IntBuffer，大小为5，即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向Buffer中存放数据
        for (int i = 0;i < intBuffer.capacity();i++){
            intBuffer.put(i * 2);
        }

        //如何从Buffer中读取数据
        //将Buffer转换，读写切换
        intBuffer.flip();

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }


    }
}
