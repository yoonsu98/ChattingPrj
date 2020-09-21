package com.gaon.prj.member.controller;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaon.prj.member.svc.MemberSVC;
import com.gaon.prj.member.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Inject
	MemberSVC memberSVC;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	private Logger logger = LoggerFactory.getLogger("MemberController.class");
	/**
	 * �α��� ������ �̵�
	 * @return
	 */
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "member/loginForm";
	}
	
	/**
	 * ȸ������ ������ �̵�
	 * @return
	 */
	@RequestMapping("/joinForm")
	public String joinForm() {
		return "member/joinForm";
	}
	
	/**
	 * ȸ������ ó��
	 * @param memberInfo
	 * @param memberVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/newMember", method = RequestMethod.POST, produces = "application/json")
	public int newMember(@RequestBody HashMap<String, String> memberInfo, MemberVO memberVO) throws Exception {
		memberVO.setId(memberInfo.get("id"));
		String encrypw = memberInfo.get("pw");
		String pw = pwdEncoder.encode(encrypw);
		memberVO.setPw(pw);
		//memberVO.setPw(memberInfo.get("pw"));
		memberVO.setNickname(memberInfo.get("nickname"));
		memberVO.setEmail(memberInfo.get("email"));
		memberVO.setPhone(memberInfo.get("phone"));
		return memberSVC.newMember(memberVO);
	}
	/**
	 * �α��� ó��
	 * @param memberInfo
	 * @param memberVO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public int login(@RequestBody HashMap<String, String> memberInfo, MemberVO memberVO, HttpSession session) throws Exception{
		String id = memberInfo.get("id");
		String pw = memberInfo.get("pw");
		memberVO.setId(id);
		MemberVO result = memberSVC.memberCheck(memberVO);
		boolean pwMatch = pwdEncoder.matches(pw, result.getPw());
		logger.info("pwMatch : " + pwMatch);
		if(pwMatch == false) {
			return -1;
		}else {
			session.setAttribute("member",result);
			System.out.println("���� : " + session.getAttribute("member"));
			return 1;
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
}