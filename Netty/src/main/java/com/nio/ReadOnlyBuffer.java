package com.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0;i < 64;i++){
            byteBuffer.put((byte)i);
        }
        byteBuffer.flip();
        //得到一个只读的buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        //读取
        while(readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }
}
