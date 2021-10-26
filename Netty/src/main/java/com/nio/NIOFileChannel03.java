package com.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{

        //创建文件输入输出以及通过FileInputStream获取FileChannel
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while(true){   //循环读取
            //清空buffer，如果没有这一步操作，程序存在bug.
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read = " +read);
            if (read == -1){ //表示读完
                break;
            }
            byteBuffer.flip();
            //将buffer中的数据写入到fileChannel02中 -->2.txt
            fileChannel02.write(byteBuffer);
        }
        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
