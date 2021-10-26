package com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        //创建文件输入流
        File file = new File("file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过FileInputStream获取对应的FileChannel ->实际类型 FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer  byteBuffer = ByteBuffer.allocate( (int) file.length());

        //将管道中的数据读入到byteBuffer
        fileChannel.read(byteBuffer);

        //将byteBuffer的字节数据转成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
