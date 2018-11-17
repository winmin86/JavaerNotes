package xyz.up123.spring.server.impl;

import xyz.up123.spring.server.MessageService;

/**
 * @ClassName: MessageServiceImpl
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/15 19:27
 **/
public class MessageServiceImpl implements MessageService {
    public String getMessage() {
        return "hello world";
    }
}
