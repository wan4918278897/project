package org.jeecg.modules.common.controller;


import org.jeecg.modules.common.utils.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yzx
 */
@Controller
@RequestMapping("/webSocket/yzx")
public class WebSocketController {
    @Autowired
    private WebSocket webSocket;

    @RequestMapping("/sendAllWebSocket")
    public String test() {
        webSocket.sendAllMessage("清晨起来打开窗，心情美美哒~");
        String s="ssssssss";
        return s;
    }

    @GetMapping("/sendOneWebSocket")
    @ResponseBody
    public String sendOneWebSocket(HttpServletRequest request) {
        String shopId=request.getParameter("shopId");
        webSocket.sendOneMessage(shopId, "xx1111x");
        String s="ssssssss";
        return "ssssssss";
    }
}
