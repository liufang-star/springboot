package com.nio;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        //创建一个buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        //类型化方式存入数据
        byteBuffer.putInt(12);
        byteBuffer.putLong(9L);
        byteBuffer.putChar('刘');
        byteBuffer.putShort((short) 4);

        //取出
        byteBuffer.flip();

        System.out.println();

        //按顺序取出，存入的顺序和打印取出的顺序要一致，不然可能会导致抛出异常
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }
}
