package com.gaon.prj.member.svc;

import com.gaon.prj.member.vo.MemberVO;

public interface MemberSVC {

	public int newMember(MemberVO memberVO);
	public MemberVO memberCheck(MemberVO memberVO);
	public String pwCheck(String id);
}