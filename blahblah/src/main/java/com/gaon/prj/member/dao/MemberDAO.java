package com.gaon.prj.member.dao;

import org.springframework.context.annotation.Configuration;

import com.gaon.prj.member.vo.MemberVO;
@Configuration
public interface MemberDAO {
	public int newMember(MemberVO memberVO);
	public MemberVO memberCheck(MemberVO memberVO);
	public String pwCheck(String id);
}