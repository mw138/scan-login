package com.yuorfei.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.yuorfei.bean.User;
import com.yuorfei.util.QRCodeUtil;

/** 
 * 得到二维码
 * 这里得到二维码的图片是传递的base64编码，便于在传递图片的同时也可以将参数传递过去。
 * 前端解析base64很简单，谷歌会告诉你
 * 
 */
@WebServlet("/get_qrcode")
public class GetQRCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public GetQRCode() {
        System.out.println("as1Sd23213434534456436456456456");
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//生成uuid，传给客户端，同时二维码里的内容也是这个uuid
		String uuid = UUID.randomUUID().toString();
		String base64 = QRCodeUtil.toBase64(uuid, 500, 300);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("uuid", uuid);
		hashMap.put("base64", base64);
		//转json格式，这里需要用到fastjson包
		String result = JSONObject.toJSONString(hashMap);
		//目前UUID对应的用户是空的，手机扫描后往这个uuid对应的user放入相应的cookie或者登录信息，这样轮询的时候就能拿到这些登录信息
		//如果不能使用application等会话，可以使用缓存，uuid做key，user为value。
		User user = null;
		this.getServletContext().setAttribute(uuid, user); 
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
	}

}
