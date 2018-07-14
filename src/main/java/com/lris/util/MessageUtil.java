package com.lris.util;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lris.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_VIDEO="video";
    public static final String MESSAGE_LINK="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVENT="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
    public static final String MESSAGE_CLICK="CLICK";
    public static final String MESSAGE_VIEW="VIEW";



    public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception{


        Map<String, String> map=new HashMap<String, String>();
        SAXReader reader=new SAXReader();
        InputStream ins = request.getInputStream();

        Document doc = reader.read(ins);

        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;



    }

    public static String textMessageToXml(TextMessage textMessage){

        XStream stream=new XStream();
        stream.alias("xml", textMessage.getClass());

        return stream.toXML(textMessage);
    }


    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage  text=new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);

    }


    public static String menuText(){
        StringBuffer sb=new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示操作：");
        sb.append("1.学习内容简介\n");
        sb.append("2.104简介\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }

    public static String firstMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("本社团教学：java、iOS、安卓、微信开发、大数据、ps、前端页面等技术。\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }

    public static String secondMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("104是IT极客社的大本营。里面汇聚了全球四面八方的极客大神。\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }
    public static String othersMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("请回复正确的菜单选项。\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }
    
}
