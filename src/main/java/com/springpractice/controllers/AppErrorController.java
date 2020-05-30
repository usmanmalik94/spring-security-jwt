package com.springpractice.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppErrorController implements ErrorController {

	/**
	 * @param request
	 * @return error view name
	 **/
	@GetMapping("error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (null != status) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "errorpages/error-404";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				return "errorpages/error-400";
			} else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
				return "errorpages/error-401";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "errorpages/error-403";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "errorpages/error-500";
			} else if (statusCode == HttpStatus.NOT_IMPLEMENTED.value()) {
				return "errorpages/error-501";
			} else if (statusCode == HttpStatus.BAD_GATEWAY.value()) {
				return "errorpages/error-502";
			} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				return "errorpages/error-503";
			}
		}
		return "errorpages/error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
