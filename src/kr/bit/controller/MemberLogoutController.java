package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLogoutController implements Controller {

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx=request.getContextPath(); 
		//세션을 가져와서 세션을 제거
		//세션을 제거하는 방법  1.강제로
		request.getSession().invalidate();
		// 2. 브라우져 종료(JSESSION 브라우져쿠키게 저장)
		// 3. 세션이 종료될때까지 대기  (세션 타임아웃 1800초)  서버의 web.xml에 지정 618라인
		return "redirect:" +ctx+ "/memberList.do";
		
	}
}
