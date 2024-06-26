package com.codehows.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codehows.domain.BoardAttachVO;
import com.codehows.domain.BoardVO;
import com.codehows.domain.Criteria;
import com.codehows.mapper.BoardAttachMapper;
import com.codehows.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

   @Setter(onMethod_= @Autowired)
   private BoardMapper mapper;

   @Setter(onMethod_= @Autowired)
   private BoardAttachMapper attachmapper;
   
   @Transactional
   @Override
   public void register(BoardVO board) {

      log.info("register......" + board);

      mapper.insertSelectKey(board);
      
      if(board.getAttachList()==null || board.getAttachList().size() <= 0) {
    	  return;
      }
      
      board.getAttachList().forEach(attach ->{
    	  attach.setBno(board.getBno());
    	  attachmapper.insert(attach);
      });
      
   }

   @Override
   public BoardVO get(Long bno) {

      log.info("get......" + bno);

      return mapper.read(bno);

   }

   @Transactional
   @Override
   public boolean modify(BoardVO board) {

      log.info("modify......" + board);
      
      attachmapper.deleteAll(board.getBno());
      boolean modifyResult = mapper.update(board) == 1;
      
      if(modifyResult && board.getAttachList() != null && board.getAttachList().size() > 0) {
    	  board.getAttachList().forEach(attach -> {
    		  attach.setBno(board.getBno());
    		  attachmapper.insert(attach);
    	  });
      }
      
      return modifyResult;
   }
   
   @Transactional
   @Override
   public boolean remove(Long bno) {

	   log.info("remove....."+bno);
	   
	   attachmapper.deleteAll(bno);
	   
	   return mapper.delete(bno) == 1;
   }

//   @Override
//   public List<BoardVO> getList() {
//
//      log.info("getList..........");
//
//      return mapper.getList();
//   }
   
   @Override
   public List<BoardVO> getList(Criteria cri) {

      log.info("get List With criteria "+cri);

      return mapper.getListWithPaging(cri);
   }
   
   @Override
   public int getTotal(Criteria cri) {
	   
	   log.info("get total count");
	   return mapper.getTotalCount(cri);
	   
   }
   
   @Override
   public List<BoardAttachVO> getAttachList(Long bno) {

      log.info("get Attach List by bno "+bno);

      return attachmapper.findByBno(bno);
   }

}

