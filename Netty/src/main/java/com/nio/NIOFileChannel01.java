package com.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "Hello Liu Fang";

        //创建一个输出流 ->channel
        FileOutputStream fileOutputStream = new FileOutputStream("file01.txt");

        //通过FileOutputStream 获取对应的 FileChannel
        //这个FileChannel 真实类型是  FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入byteBuffer
        byteBuffer.put(str.getBytes());

        //对byteBuffer进行flip   读写切换
        byteBuffer.flip();

        //将byteBuffer写入到fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
