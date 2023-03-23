package com.miaMesQ;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * wenhui.xiang
 * 2023/03/21 2:35 下午
 */
@Component
public class MesQConsumer {

    private Class<?> clazz;

    private final String L = "lock";
    private int retry;

    @Async
    public void operation() throws Exception {
        synchronized (L) {
            while (true) {
                if (MesQEntrance.getQueue().size() > 0) {
                    /*
                     * 移除链表头部元素并返回
                     */
                    Mes<?> mes = MesQEntrance.getQueue().removeFirst();
                    try {

                        clazz.getMethod("normalProcess",Object.class).invoke(null,mes.getValue());

                    } catch (Exception e) {
                        /*
                         * 发生异常的处理逻辑放在这里
                         */
                        e.printStackTrace();
                        if (mes.getRetryCount() > retry) {
                            /*
                             * retry次数上限，进一步处理
                             */
                            clazz.getMethod("exceptionalProcess",Object.class).invoke(null,mes.getValue());
                        } else {
                            mes.setRetryCount(mes.getRetryCount() + 1);
                            MesQEntrance.getQueue().addLast(mes);
                        }
                    }

                } else {
                    L.wait();
                }
            }
        }
    }

    /*
     *  num 用于唤醒线程且只唤醒一次
     */
    public int num = 0;
    @Async
    public void wakeup() {
        if (num != 0) {
            return;
        }
        num += 1;
        synchronized (L) {
            L.notify();
            num = 0;
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setRetry(int retry) {
        this.retry = retry - 1;
    }
}
