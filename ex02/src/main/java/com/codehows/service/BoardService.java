package com.codehows.service;

import java.util.List;

import com.codehows.domain.BoardVO;



public interface BoardService {

	public void register(BoardVO board);  //글을 등록
		
	public BoardVO get(Long bno);		  //bno 기준으로 글을 가져오기
	
	public boolean modify(BoardVO board); //BoardVO 객체 안에 내용으로 글 수정
	
	public boolean remove(Long bno);	  //bno 기준으로 글 삭제
	
	public List<BoardVO> getList();		  //전체 내용 가져오기
} 
