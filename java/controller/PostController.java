package controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import model.map.MapVO;
import model.post.Paging;
import model.post.PostService;
import model.post.PostVO;

@Controller
public class PostController {
	
	@Autowired
	private PostService ps;
	
	// C
	@RequestMapping("/insert.do")
	public String insertP(HttpSession session, PostVO vo, MapVO mvo, Model model) throws Exception {
		
		if(session.getAttribute("mid")==null) {
			return "redirect:login.jsp";
		}
		
		MultipartFile img=vo.getImg();
		System.out.println("upload한 img size: "+vo.getImg().getSize());
		if(!img.isEmpty()) {
			UUID uuid=UUID.randomUUID();
			String rand=uuid.toString().replace("-", "").substring(24);
			// System.out.println(rand);
			System.out.println("img og name: "+img.getOriginalFilename());
			String imgName=rand+"_"+img.getOriginalFilename();
			System.out.println("img rename: "+imgName);
			
			// 1. 학원  2. 집
			//img.transferTo(new File("D:\\JIN_0622\\test\\img\\"+imgName));
			img.transferTo(new File("C:\\code_0622\\test\\img\\"+imgName));
			vo.setImgname("\\img\\"+imgName);
			mvo.setImgname("\\img\\"+imgName);
		}
		//System.out.println("pvo 내에 이미지네임: "+vo.getImgname());
		ps.insertP(vo);
		//System.out.println(mvo.getImgname());
		//request.setAttribute("mvo", mvo);
		model.addAttribute("imgname", mvo.getImgname());
		//return "redirect:main.do";
		return "insertMap.do";
	}
	
	// R ONE for U
	@RequestMapping(value="/update.do",method=RequestMethod.GET)
	public String getInfo(HttpSession session, PostVO vo, Model model) throws Exception {
		System.out.println(vo.getMid()+session.getAttribute("mid"));
		if(session.getAttribute("mid")==null) {
			return "redirect:login.jsp";
		}
		
		PostVO data=ps.getP(vo);
		
		model.addAttribute("data", data);
		return "update.jsp";
	}
	
	// U
	@RequestMapping(value="/update.do",method=RequestMethod.POST)
	public String update(HttpSession session, PostVO vo, Model model) throws Exception {

		if(session.getAttribute("mid")==null) {
			return "redirect:login.jsp";
		}
		
		ps.updateP(vo);
		
		model.addAttribute("pno", vo.getPno());
		return "redirect:post.do";
	}
	
	// D
	@RequestMapping("/delete.do")
	public String deleteP(PostVO vo) throws Exception {
		ps.deleteP(vo);
		return "redirect:main.do";
		//return "deleteMap.do";
	}
	
	// R ONE
	@RequestMapping("/post.do")
	public String getP(HttpSession session, PostVO vo, Model model) throws Exception {
		PostVO data=ps.getP(vo);
		
		vo.setMid((String)session.getAttribute("mid"));
		
		// 이전 글 & 다음 글 처리
		int size=ps.getPlist(vo).size(); // 게시글 사이즈
		int startNum=ps.getPlist(vo).get(size-1).getPno(); // 처음 게시글 pno
		int lastNum=ps.getPlist(vo).get(0).getPno(); // 마지막 게시글 pno
		
		List<PostVO> test=ps.getPlist(vo);
		
		int indexNum=0; // 현재 pno의 인덱스
		for(int i=0;i<size;i++) {
			if(test.get(i).getPno()==vo.getPno()) {
				indexNum=i;
			}
		}
		
		int nextNum=0; // 현재 pnum기준 다음 pnum
		int prevNum=0; // 현재 pnum기준 이전 pnum
		if(indexNum==0) {
			nextNum=lastNum;
		}
		else {
			if(ps.getPlist(vo).get(indexNum-1).getPno()==lastNum) {
				nextNum=lastNum;
			}
			else {				
				nextNum=ps.getPlist(vo).get(indexNum-1).getPno(); // 현재 pnum기준 다음 pnum
			}				
		}
		
		if(indexNum==size-1) {
			prevNum=startNum;				
		}
		else {
			if(ps.getPlist(vo).get(indexNum+1).getPno()==startNum) {
				prevNum=startNum;
			}
			else {				
				prevNum=ps.getPlist(vo).get(indexNum+1).getPno(); // 현재 pnum기준 이전 pnum
			}
		}
		
		model.addAttribute("nextNum", nextNum);
		model.addAttribute("prevNum", prevNum);
		model.addAttribute("startNum", startNum);
		model.addAttribute("lastNum", lastNum);
		model.addAttribute("data", data);
		return "post.jsp";
	}
	
	// R ALL
	@RequestMapping("/main.do")
	public String getPlist(HttpServletRequest request, PostVO vo, Paging paging, Model model) throws Exception {
		int totalCount; // 테이블마다 전체 데이터 개수 
		int page = request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
		
		HttpSession session=request.getSession();
		String mid=(String) session.getAttribute("mid");
		
		System.out.println("PostController 내의 mid: "+mid);
		if(mid==null) {
			return "index.jsp";
		}
		vo.setMid(mid);
		
		totalCount = ps.getTotalCount(vo);
		paging.setPageNo(page);
		paging.setTotalCount(totalCount);
		
		page = ((page-1)*9);
		//paging.setPageSize(page+8);
		List<PostVO> datas = ps.getPList(vo, page, 9);
		
		//List<PostVO> datas=ps.getPList();
		
		model.addAttribute("datas", datas);
		model.addAttribute("paging", paging);
		return "main.jsp";
	}
	
}
