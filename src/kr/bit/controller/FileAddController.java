package kr.bit.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileAddController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String UPLOAD_DIR = "file_repo";
		String uploadPath = request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;
		File currentDirPath = new File(uploadPath); //업로드할 경로를 File 객체로 만들기
		if (!currentDirPath.exists()) {
			currentDirPath.mkdir();
		}
		//파일을 업로드 할 때 먼저 저장될 임시 저장경로 설정
		//파일 업로드시 필요한 API - commonse-fileupload, commons-io
		org.apache.commons.fileupload.disk.DiskFileItemFactory factory = new org.apache.commons.fileupload.disk.DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024*1024); // 1MB
		
		String fileName = null;
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem)items.get(i);
				if (fileItem.isFormField()) { // 폼필드(파라메터)
					System.out.println(fileItem.getFieldName() + "=" + fileItem.getString("utf-8"));
				}else { // 파일
					if (fileItem.getSize() > 0 ) {
						int idx = fileItem.getName().lastIndexOf("\\");  //   \\(윈도우)  /(Linux)
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");  //   \\(윈도우)  /(Linux)
						}
						fileName =fileItem.getName().substring(idx+1);
						File uploadFile = new File(currentDirPath + "\\" + fileName);
						//파일 중복체크
						if (uploadFile.exists()) {
							fileName = System.currentTimeMillis() + "_" + fileName;
							uploadFile = new File(currentDirPath + "\\" + fileName);
						}
						fileItem.write(uploadFile);  // 임시경로 -> 새로운경로에 파일 쓰기
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// $.ajax() 쪽으로 업로된 최종 파일이름을 전송시켜준다.
		response.setContentType("text/html;charset=euc-kr");
		response.getWriter().print(fileName);
		
		return null;
	}
}
