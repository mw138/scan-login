package com.yuorfei.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuorfei.bean.User;

/**
 * 浏览器翔服务端的轮询操作，判断是否有手机扫描了这个二维码
 * 每次都向application里面查找uuid对应的value，不为空的时候取出用户信息处理就登录成功
 * 为空的话就说明没有扫描过或者没有登录，继续轮询。
 * @author hxy
 *
 */
@WebServlet("/roll_poling")
public class RollPoling extends HttpServlet{

	private static final long serialVersionUID = 892705919885885704L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			Thread.sleep(2000); //轮询操作不要太快，休息两秒钟
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		//浏览器传来uuid
		String uuid = (String) request.getParameter("uuid");
		User user = (User) this.getServletContext().getAttribute(uuid);
		String message = "faild";
		if( user != null ){
			//user不为空说明扫描登录成功，做相应的处理即可
			message = "success";
		}
		PrintWriter pw = response.getWriter();
		pw.print(message);
		pw.flush();
		pw.close();
	}
	

}
