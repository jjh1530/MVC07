package kr.bit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.bit.model.MemberDAO;
import kr.bit.model.MemberVO;

public class MemberAjaxListController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = dao.memberList();
		//GSon API : LIST를 Json으로
		Gson g = new Gson();
		String json = g.toJson(list);

		// $.ajax()  -> json
		response.setContentType("text/json;charset=euc-kr");
		response.getWriter().print(json);
		return null;  //Ajax라 다른페이지로 넘기지 않기위해 null
	}
}
