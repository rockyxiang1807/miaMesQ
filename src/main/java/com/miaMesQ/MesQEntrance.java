package com.miaMesQ;

import java.util.LinkedList;

/**
 * wenhui.xiang
 * 2023/03/21 2:35 下午
 */

public class MesQEntrance {

    public static LinkedList<Mes> queue = new LinkedList<>();
    private MesQConsumer mesQConsumer;

    public void pushMes(Mes s){
        /*
         * 将接收参数存入 queue
         */
        queue.addLast(s);
        /*
         * 唤醒处理方法的线程
         *         告诉其链表中已经存入新数据
         */
        mesQConsumer.wakeup();
    }

    public static LinkedList<Mes> getQueue() {
        return queue;
    }

    public MesQEntrance(MesQConsumer mesQConsumer){
        this.mesQConsumer = mesQConsumer;
        try {
            this.mesQConsumer.operation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
