package com.mendao.application;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.common.util.StringUtil;

@Controller
public class SiteErrorController implements ErrorController {

	private static final String ERROR_KEY = "error";
	 
    //private final Log logger = LogFactory.getLog(BasicErrorController.class);
 
 
    @Override
    public String getErrorPath() {
        return "error_page";
    }
 
//    @RequestMapping(value = "/error", produces = "text/html")
//    public ModelAndView errorHtml(HttpServletRequest request) {
//        Map<String, Object> map = extract(new ServletRequestAttributes(request), false,
//                false);
//        return new ModelAndView(ERROR_KEY, map);
//    }
 
//    @RequestMapping(value = "/error")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
//        String trace = request.getParameter("trace");
//        Map<String, Object> extracted = extract(attributes,
//                trace != null && !"false".equals(trace.toLowerCase()), true);
//        HttpStatus statusCode = getStatus((Integer) extracted.get("status"));
//        return new ResponseEntity<Map<String, Object>>(extracted, statusCode);
//    }
    @RequestMapping(value = "/error")
    public String error(HttpServletRequest request, ModelMap modelMap, RedirectAttributes attr) {
    	String requestType = request.getHeader("X-Requested-With");
    	
    	if(StringUtil.equalsIgnoreCase("XMLHttpRequest", requestType)){
    		return "redirect:/ajaxError";
    	}else{
    		ServletRequestAttributes attributes = new ServletRequestAttributes(request);
            String trace = request.getParameter("trace");
            Map<String, Object> extracted = extract(attributes,
                    trace != null && !"false".equals(trace.toLowerCase()), true);
            HttpStatus statusCode = getStatus((Integer) extracted.get("status"));
            
    		//modelMap.addAllAttributes(extracted);
    		String[] atts = attributes.getAttributeNames(RequestAttributes.SCOPE_REQUEST);
//    		for(String a : atts){
//    			System.out.println(attributes.getAttribute(a, RequestAttributes.SCOPE_REQUEST));
//    		}
    		
    		Throwable error = (Throwable) attributes.getAttribute(
                    "javax.servlet.error.exception", RequestAttributes.SCOPE_REQUEST);
//            Object obj = attributes.getAttribute("javax.servlet.error.status_code",
//                    RequestAttributes.SCOPE_REQUEST);
            if(null != error){
            	attr.addFlashAttribute("message", error.getMessage() );
            }
    		
    		return getErrorPath();
    	}
    }
    
    @RequestMapping(value = "/showError")
    public String showError(HttpServletRequest request, ModelMap modelMap, @ModelAttribute("message") String message) {
    	modelMap.addAttribute("message", message);
    	return "error_page";
    }
    
    
    /**
     * ajax error
     * @param request
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/ajaxError")
    public ResponseEntity<Map<String, Object>> ajaxError(HttpServletRequest request, ModelMap modelMap) {
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        String trace = request.getParameter("trace");
        Map<String, Object> extracted = extract(attributes,
                trace != null && !"false".equals(trace.toLowerCase()), true);
        HttpStatus statusCode = getStatus((Integer) extracted.get("status"));
        
        return new ResponseEntity<Map<String, Object>>(extracted, statusCode);
    }
 
    private HttpStatus getStatus(Integer value) {
        try {
            return HttpStatus.valueOf(value);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
 
    public Map<String, Object> extract(RequestAttributes attributes, boolean trace, boolean log) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("timestamp", new Date());
        try {
            Throwable error = (Throwable) attributes.getAttribute(
                    "javax.servlet.error.exception", RequestAttributes.SCOPE_REQUEST);
            Object obj = attributes.getAttribute("javax.servlet.error.status_code",
                    RequestAttributes.SCOPE_REQUEST);
            int status = 999;
            if (obj != null) {
                status = (Integer) obj;
                map.put(ERROR_KEY, HttpStatus.valueOf(status).getReasonPhrase());
            }
            else {
                map.put(ERROR_KEY, "None");
            }
            map.put("status", status);
            if (error != null) {
                while (error instanceof ServletException && error.getCause() != null) {
                    error = ((ServletException) error).getCause();
                }
                map.put("exception", error.getClass().getName());
                map.put("message", error.getMessage());
                if (trace) {
                    StringWriter stackTrace = new StringWriter();
                    error.printStackTrace(new PrintWriter(stackTrace));
                    stackTrace.flush();
                    //map.put("trace", stackTrace.toString());
                }
                if (log) {
                    //this.logger.error(error);
                }
            }
            else {
                Object message = attributes.getAttribute("javax.servlet.error.message",
                        RequestAttributes.SCOPE_REQUEST);
                map.put("message", message == null ? "No message available" : message);
            }
            return map;
        }
        catch (Exception ex) {
            map.put(ERROR_KEY, ex.getClass().getName());
            map.put("message", ex.getMessage());
            if (log) {
                //this.logger.error(ex);
            }
            return map;
        }
    }

}
