package com.bookstore.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String getHomePage() {
		return "homePage";
	}
	
	@GetMapping("/welcome")
	public String getWelcomePage() {
		return "welcomePage";
	}
	
	@GetMapping("/admin")
	public String getAdminPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        
        String fullName = userDetails.getUsername();
        
        
        model.addAttribute("fullName", fullName);
		return "adminPage";
	}
	
	@GetMapping("/emp")
	public String getEmployeePage() {
		return "empPage";
	}
	
	@GetMapping("/mgr")
	public String getManagerPage() {
		return "mgrPage";
	}
	
	@GetMapping("/hr")
	public String getHrPage() {
		return "hrPage";
	}
	
	@GetMapping("/common")
	public String getCommonPage() {
		return "commonPage";
	}
	
	@GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
		return "accessDeniedPage";
	}
}
