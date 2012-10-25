package edu.wisc.doit.tcrypt;

import java.io.File;
import java.io.FileInputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadController {
	
	private final TcryptHelper tcryptHelper;
	private AuthenticationState authenticationState;
	
	public DownloadController() {
		tcryptHelper = new TcryptHelper();
		authenticationState = new AuthenticationState();
	}
	
	@RequestMapping("/download")
	public void downloadKey(@RequestParam("serviceName") String serviceName,@RequestParam("keyType") String keyType, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileLocationOnServer = tcryptHelper.getFileLocationToDownloadFromServer(serviceName, authenticationState.getCurrentUserName(), keyType);
		File file = new File(fileLocationOnServer);
	    response.setContentType(new MimetypesFileTypeMap().getContentType(file));
	    response.setContentLength((int)file.length());
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
	    FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
	}
}