package com.lris.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lris.util.CheckUtil;
import com.lris.util.MessageUtil;
import com.lris.util.SignUtil;

public class WeixinServlet extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public WeixinServlet() {
		System.out.println("hehe----进来了！");
		//logger.error("doget--接入微信校验------->:");
	}
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String  signature=request.getParameter("signature");
        String  timestamp=request.getParameter("timestamp");
        String  nonce=request.getParameter("nonce");
        String  echostr=request.getParameter("echostr");


        PrintWriter out=response.getWriter();
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
        	System.out.println("验签成功");
            out.write(echostr);
        }else {
        	System.out.println("验签失败");
        }

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	System.out.println("doPost----进来了！");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out=response.getWriter();   
        try {
            Map<String, String> map=MessageUtil.xmlToMap(request);
            String toUserName=map.get("ToUserName");
            String fromUserName= map.get("FromUserName");
            String msgType=map.get("MsgType");
            String content=map.get("Content");

            String message=null;

            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){

                if("1".equals(content)){
                    message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());

                }else if ("2".equals(content)) {
                    message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());

                }else if ("?".equals(content)||"？".equals(content)) {
                    message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else{

                    message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.othersMenu());

                }

            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){

                String eventType=map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());

                }


            }
            out.print(message);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }



    }
}
