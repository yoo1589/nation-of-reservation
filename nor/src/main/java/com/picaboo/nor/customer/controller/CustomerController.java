package com.picaboo.nor.customer.controller;
 
import java.util.*;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.picaboo.nor.customer.service.*;
import com.picaboo.nor.customer.vo.*;

@Controller
public class CustomerController {
	@Autowired private CustomerService customerService;
	
	//가맹점이 등록한 pc방 좌석을 고객이 확인을 하는 페이지로 get 요청
	@GetMapping("/selectCustomerSeat")
	public String customer(HttpSession session, Model model, @RequestParam("franchiseeNo")String franchiseeNo) {
		//세션값이 없으면 기본 인덱스로 이동
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		//세션에 저장된 memberName값을 가져와 모델에 저장함
		model.addAttribute("memberName",session.getAttribute("memberName"));
		//Franchisee타입에 parameter값으로 넘어온 franchiseeNo값에 일치하는 프렌차이즈정보를 저장함
		Franchisee franchisee = customerService.getFranchisee(franchiseeNo);
		
		//Seat타입의 리스트에 franchiseeNo값에 일치하는 좌석 정보를 저장함
		List<Seat> seat = customerService.getSeat(franchiseeNo);
		//System.out.println(seat);
		
		model.addAttribute("franchisee",franchisee);
		model.addAttribute("seat", seat);
		//System.out.println("cnt :"+seat);
		return "customer/selectCustomerSeat";
	}
	
	//기본 인덱스로 get요청
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		return "index";
	}
	
	//로그인후 고객이 보는 인덱스로 get요청
	@GetMapping("/customerIndex")
	public String customerindex(HttpSession session, Model model) {
		//세션값이 없으면 기본 인덱스로 이동
		if (session.getAttribute("memberNo") == null) {
			return "redirect:/";
		}
		//System.out.println("커스텀인덱스 세션"+session.getAttribute("memberName"));
		//세션에 저장된 memberName값을 가져와 모델에 저장함
		model.addAttribute("memberName",session.getAttribute("memberName"));
		
		return "customerIndex";
	}
}
