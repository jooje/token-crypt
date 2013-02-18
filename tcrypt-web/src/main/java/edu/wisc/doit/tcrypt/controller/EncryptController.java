package edu.wisc.doit.tcrypt.controller;

import edu.wisc.doit.tcrypt.BouncyCastleTokenEncrypter;
import edu.wisc.doit.tcrypt.TokenEncrypter;
import edu.wisc.doit.tcrypt.exception.ServiceErrorException;
import edu.wisc.doit.tcrypt.exception.ValidationException;
import edu.wisc.doit.tcrypt.services.TCryptServices;
import edu.wisc.doit.tcrypt.vo.ServiceKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@Controller
public class EncryptController extends BaseController {

	private TCryptServices tcryptServices;
	private HashMap<String,TokenEncrypter> tokenEncrypters;
	
	@Autowired
	public EncryptController(TCryptServices tcryptServices) {
		this.tcryptServices = tcryptServices;
		tokenEncrypters = new HashMap<String,TokenEncrypter>();
	}
	
	//Request actions
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.GET)
	public ModelAndView encryptTextInit() throws Exception {
		ModelAndView modelAndView = new ModelAndView("encryptTokenBefore");
		try {
			Set<String> serviceNames =  tcryptServices.getListOfServiceNames();
	
	        if (!serviceNames.isEmpty())
	        {
	            modelAndView.addObject("serviceNames", formatForJavaScript(serviceNames));
	        }
		} catch (Exception e) {
			logger.error("Issue populating list of service names, recoverable error.",e);
			throw new ValidationException("error.issuePopulatingServiceNames");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.POST)
	public ModelAndView encryptText(
			@RequestParam("serviceNames") String serviceName,
			@RequestParam("text") String text) throws Exception {

		ModelAndView modelAndView = new ModelAndView("encryptTokenResult");

		TokenEncrypter tokenEncrypter;
		if(tokenEncrypters.containsKey(serviceName)) {
			tokenEncrypter = tokenEncrypters.get(serviceName);
		} else {
			ServiceKey sk = tcryptServices.readServiceKeyFromFileSystem(serviceName);
			if(sk != null  && sk.getPublicKey() != null) {
				tokenEncrypter = new BouncyCastleTokenEncrypter(sk.getPublicKey());
				tokenEncrypters.put(sk.getServiceName(), tokenEncrypter);
			} else {
				throw new ServiceErrorException(serviceName,"error.serviceKeyNotFound");
			}
			
		}
			
        final String token = tokenEncrypter.encrypt(text);
        modelAndView.addObject("serviceName", serviceName);
		modelAndView.addObject("encryptedText", token);
			return modelAndView;
	}
	
	//Exception Handlers
	
	@ExceptionHandler(ValidationException.class)
	public ModelAndView handleException(ValidationException e) throws Exception {
		ModelAndView mav;
		try {
			mav = encryptTextInit();
		} catch (Exception e1) {
			logger.error("Error resetting view after error",e);
			throw new Exception(e);
		}
		
		mav.addObject(e.getErrorMessage());
		
		return mav;
	}
	
	@ExceptionHandler(ServiceErrorException.class)
	public ModelAndView handleException(ServiceErrorException e) throws Exception {
		ModelAndView mav;
		try {
			mav = encryptTextInit();
		} catch (Exception e1) {
			logger.error("Error resetting view after error",e);
			throw new Exception(e);
		}
		
		mav.addObject("errorMessage",e.getErrorMessage());
		mav.addObject("zero",e.getServiceName());
		
		return mav;
	}
	
	//Private Methods
	
	private String formatForJavaScript(Set<String> serviceNames) {
		Iterator<String> iterator = serviceNames.iterator();
		String commaSeparated = "[ '" + iterator.next() + "'";
		for (; iterator.hasNext();) 
			commaSeparated =  commaSeparated + ", '" + iterator.next() + "'";
		commaSeparated = commaSeparated+ "]";
		return commaSeparated;
	}

}