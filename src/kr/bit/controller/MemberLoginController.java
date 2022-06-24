package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.bit.model.MemberDAO;
import kr.bit.model.MemberVO;

public class MemberLoginController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String password = request.getParameter("password");
		
		String ctx=request.getContextPath();
		
		MemberVO vo = new MemberVO();
		vo.setId(user_id);
		vo.setPass(password);
		
		MemberDAO dao = new MemberDAO();
		String user_name = dao.memberLogin(vo);
		HttpSession session = request.getSession();
		if (user_name != null && !"".equals(user_name)) {
			
			session.setAttribute("userId", user_id);
			session.setAttribute("userName", user_name);
		}else {
			session.setAttribute("userId", "");
			session.setAttribute("userName", "");
			session.setAttribute("msg", "사용자 정보가 올바르지 않습니다.");
		}
		
		return "redirect:"+ctx+"/memberList.do";
	}
}
