package com.miaMesQ;

import java.util.LinkedList;

/**
 * wenhui.xiang
 * 2023/03/21 2:35 下午
 */

public class First {

    private boolean flag = true;
    public static LinkedList<Mes> queue = new LinkedList<>();
    private Second sencond;

    public void pushMes(Mes s) throws Exception {
        /*
         * 将接收参数存入 queue
         */
        queue.addLast(s);
        /*
         * 异步调用处理方法
         *      仅会在项目启动后第一个请求时才会调用
         */
        if (flag) {
            flag = false;
            sencond.operation();
        } else {
            /*
             * 唤醒处理方法的线程
             *         告诉其链表中已经存入新数据
             */
            sencond.wakeup();
        }
    }

    public static LinkedList<Mes> getList() {
        return queue;
    }

    public First(Second sencond) {
        this.sencond = sencond;
    }
}
