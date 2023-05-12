package com.marondal.marondalgram.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.marondalgram.common.FileManagerService;
import com.marondal.marondalgram.post.comment.bo.CommentBO;
import com.marondal.marondalgram.post.comment.model.CommentDetail;
import com.marondal.marondalgram.post.dao.PostDAO;
import com.marondal.marondalgram.post.like.bo.LikeBO;
import com.marondal.marondalgram.post.model.Post;
import com.marondal.marondalgram.post.model.PostDetail;
import com.marondal.marondalgram.user.bo.UserBO;
import com.marondal.marondalgram.user.model.User;

@Service
public class PostBO {
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private CommentBO commentBO;
	
	
	public int addPost(int userId, String content, MultipartFile file) {
		
		String imagePath = FileManagerService.saveFile(userId, file);
		
		return postDAO.insertPost(userId, content, imagePath);
		
	}
	
	public List<PostDetail> getPostList(int userId) {
		
		// 컨트롤러에서 원하는 (jsp에서 사용할) 데이터 형태를 만들어 준다. 
		List<Post> postList = postDAO.selectPostList();
		
		List<PostDetail> postDetailList = new ArrayList<>();
						
		for(Post post:postList) {
			
			User user = userBO.getUserById(post.getUserId());
			int likeCount = likeBO.getLikeCount(post.getId());
			boolean isLike = likeBO.isLike(userId, post.getId());
			List<CommentDetail> commentList = commentBO.getCommentList(post.getId());
			
			PostDetail postDetail = new PostDetail();
			
			postDetail.setId(post.getId());
			postDetail.setContent(post.getContent());
			postDetail.setImagePath(post.getImagePath());
			postDetail.setUserId(post.getUserId());
			postDetail.setLoginId(user.getLoginId());
			postDetail.setLikeCount(likeCount);
			postDetail.setLike(isLike);
			postDetail.setCommentList(commentList);
			
			postDetailList.add(postDetail);
			
		}
		
		
		return postDetailList;
		
	}
	
	public int deletePost(int userId, int postId) {
		
		Post post = postDAO.selectPostByUserId(userId, postId);
		
		if(post != null) {
			FileManagerService.removeFile(post.getImagePath());
			
			// 댓글
			commentBO.deleteCommentByPostId(postId);
			
			// 좋아요
			likeBO.deleteLikeByPostId(postId);
			return postDAO.deletePost(postId);
		} else {
			return 0;
		}
	}
	
	

}
