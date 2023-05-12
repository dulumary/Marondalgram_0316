package com.marondal.marondalgram.post.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marondal.marondalgram.post.like.dao.LikeDAO;

@Service
public class LikeBO {
	
	@Autowired
	private LikeDAO likeDAO;
	
	public int addLike(int userId, int postId) {
		
		return likeDAO.insertLike(userId, postId);
	}
	
	public int getLikeCount(int postId) {
		return likeDAO.selectCountLike(postId);
	}
	
	public boolean isLike(int userId, int postId) {
		
		int count = likeDAO.selectCountLikeByUserId(userId, postId);
		
		// 조회된 개수가 0개면 좋아요 안함
		if(count == 0) {
			return false;
		} else {		// 조회된 개수가 1이상이면 좋아요 
		
			return true;
		}
		
	}
	
	public int unLike(int userId, int postId) {
		return likeDAO.deleteLike(userId, postId);
	}
	
	public int deleteLikeByPostId(int postId) {
		return likeDAO.deleteLikeByPostId(postId);
	}

}
