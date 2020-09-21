package com.gaon.prj.member.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.gaon.prj.member.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public int newMember(MemberVO memberVO) {
		return sqlSession.insert("mappers.MemberDAO-mapper.newMember",memberVO);
	}
	@Override
	public MemberVO memberCheck(MemberVO memberVO) {
		return sqlSession.selectOne("mappers.MemberDAO-mapper.memberCheck",memberVO);
	}
	@Override
	public String pwCheck(String id) {
		return sqlSession.selectOne("mappers.MemberDAO-mapper.idCheck",id);
	}
}