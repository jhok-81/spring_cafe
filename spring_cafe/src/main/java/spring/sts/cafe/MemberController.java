package spring.sts.cafe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import spring.model.member.MemberDAO;
import spring.model.member.MemberDAO_Mybatis;
import spring.model.member.MemberDTO;
import spring.model.member.ZipcodeDTO;
import spring.utility.cafe.Utility;
import spring.utility.cafe.Paging;

@Controller
public class MemberController {
	@Autowired
	private MemberDAO_Mybatis mdao;
	
	@RequestMapping("/admin/list")
	public String list(HttpServletRequest request){
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
	 
		if(col.equals("total"))word="";
	   
	   //paging 관련
	    int nowPage =1; //현재페이지
	    int recordPerPage = 5; //한페이지당 보여줄 레코드 갯수
	    if(request.getParameter("nowPage")!=null){
	  	  nowPage = Integer.parseInt(request.getParameter("nowPage"));
	    }
	   
	    int sno = ((nowPage -1) * recordPerPage) +1;    
	    int eno =  nowPage * recordPerPage;
	   
	    Map map = new HashMap();
	    map.put("col", col);
	    map.put("word", word);
	    map.put("sno", sno);
	    map.put("eno", eno);
	     
	    int total = mdao.total(map);
	    List<MemberDTO> list = mdao.list(map);
	   
	    String paging = new Paging().paging3(total, nowPage, recordPerPage, col, word);
		   		
	    request.setAttribute("list", list);
	    request.setAttribute("paging", paging);
	    request.setAttribute("col", col);
	    request.setAttribute("word", word);

		return "/member/list";
	}
	
	@RequestMapping(value="/member/delete",method=RequestMethod.POST)	
	public String delete(String id, String oldfile, HttpSession session,
			HttpServletRequest request){
		String upDir = request.getRealPath("/storage");
		int cnt = mdao.delete(id);
		if(cnt==1){
			if(!oldfile.equals("member.jpg"))Utility.deleteFile(upDir, oldfile);
			session.invalidate();
			return "redirect:/";
		}else{
			return "member/error";
		}
	}
	
	@RequestMapping(value="/member/delete",method=RequestMethod.GET)
	public String delete(String id, String oldfile, HttpSession session
			,Model model){
		if(id==null){
			id = (String)session.getAttribute("id");
		}
		if(oldfile==null){
			oldfile = mdao.getFname(id);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("oldfile", oldfile);
		
		return "/member/delete";
	}
	
	@RequestMapping(value="/member/updatePasswd",method=RequestMethod.POST)
	public String updatePasswd(String id, String passwd){
		
		int cnt = mdao.updatePasswd(id, passwd);
		
		if(cnt==1) {
			return "redirect:/";
		}else{
			return "member/error";
		}
		
	}
	@RequestMapping(value="/member/updatePasswd",method=RequestMethod.GET)
	public String updatePasswd(){
		
		return "/member/updatePasswd";
	}
	
	@RequestMapping(value="/member/updateFile",method=RequestMethod.POST)
	public String updateFile(String id, String oldfile,
			MultipartFile fnameMF,HttpServletRequest request){
		
		String basePath=request.getRealPath("/storage");
		int size = (int)fnameMF.getSize();
		String filename = null;
		if(size>0){
			filename = Utility.saveFileSpring30(fnameMF, basePath);
		}
		
		int cnt = mdao.updateFile(id, filename);
		
		if(cnt==1 && !oldfile.equals("member.jpg")){
			Utility.deleteFile(basePath, oldfile);
		}
		
		if(cnt==1){
			return "redirect:/";
		}else{
			return "member/error";
		}
		
	}
	
	@RequestMapping(value="/member/updateFile",method=RequestMethod.GET)
	public String updateFile(){
		
		return "/member/updateFile";
	}
	
	@RequestMapping(value="/member/update",method=RequestMethod.POST)
	public String update(MemberDTO dto){
		
		int cnt = mdao.update(dto);
		
		if(cnt==1){
			return "redirect:/";
		}else{
			return "member/error";
		}
	}
	
	@RequestMapping(value="/member/update",method=RequestMethod.GET)
	public String update(String id,HttpSession session,Model model){
		
		if(id==null){
			id =(String)session.getAttribute("id");
		}
		
		MemberDTO dto = mdao.read(id);
		
		model.addAttribute("dto", dto);
		
		return "/member/update";
	}
	
	@RequestMapping("/member/read")
	public String read(String id,HttpSession session,Model model){
		
	    if(id==null){
	        id = (String)session.getAttribute("id");
	    }
	    
	    MemberDTO dto = mdao.read(id);
	    
	    model.addAttribute("dto", dto);
		
		return "/member/read";
	}
	
	@RequestMapping("/member/logout")
	public String logout(HttpSession session){
		session.invalidate();
		
		return "redirect:/";
	}
	@RequestMapping(value="/member/login",method=RequestMethod.POST)
	public String login(String id,String passwd,HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			int bbsno,
			int nowPage, 
			int nPage,
			String col, 
			String word,
			String flag,
			Model model){
		int cnt = mdao.loginCheck(id, passwd);
		 
		String grade = null;//회원등급 
		String url = "/member/passwdError";
		if(cnt==1){
			url = "redirect:/"; 
			if(flag!=""){
			model.addAttribute("bbsno", bbsno);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
			url = "redirect:"+flag;
			}
		    grade = mdao.getGrade(id); 
		    session.setAttribute("id", id);
		    session.setAttribute("grade", grade);	   
		    // ---------------------------------------------- 
		    // Cookie 저장, Checkbox는 선택하지 않으면 null 임 
		    // ---------------------------------------------- 
		    Cookie cookie = null; 
		       
		    String c_id = request.getParameter("c_id"); // Y, 아이디 저장 여부 
		       
		    if (c_id != null){  // 처음에는 값이 없음으로 null 체크로 처리
		      cookie = new Cookie("c_id", "Y");    // 아이디 저장 여부 쿠키 
		      cookie.setMaxAge(120);               // 2 분 유지 
		      response.addCookie(cookie);          // 쿠키 기록 
		   
		      cookie = new Cookie("c_id_val", id); // 아이디 값 저장 쿠키  
		      cookie.setMaxAge(120);               // 2 분 유지 
		      response.addCookie(cookie);          // 쿠키 기록  
		         
		    }else{ 
		      cookie = new Cookie("c_id", "");     // 쿠키 삭제 
		      cookie.setMaxAge(0); 
		      response.addCookie(cookie); 
		         
		      cookie = new Cookie("c_id_val", ""); // 쿠키 삭제 
		      cookie.setMaxAge(0); 
		      response.addCookie(cookie); 
		    } 

		    return url;
		}else{
			return url;
		}

	}
	@RequestMapping(value="/member/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request,
			@RequestParam(value="bbsno",defaultValue="0")int bbsno,
			@RequestParam(value="nowPage",defaultValue="0")int nowPage, 
			@RequestParam(value="nPage",defaultValue="0")int nPage){
		/*----쿠키설정 내용시작----------------------------*/
		String c_id = "";     // ID 저장 여부를 저장하는 변수, Y
		String c_id_val = ""; // ID 값
		 
		Cookie[] cookies = request.getCookies(); 
		Cookie cookie=null; 
		 
		if (cookies != null){ 
		 for (int i = 0; i < cookies.length; i++) { 
		   cookie = cookies[i]; 
		 
		   if (cookie.getName().equals("c_id")){ 
		     c_id = cookie.getValue();     // Y 
		   }else if(cookie.getName().equals("c_id_val")){ 
		     c_id_val = cookie.getValue(); // user1... 
		   } 
		 } 
		} 
	  /*----쿠키설정 내용 끝----------------------------*/
		
		request.setAttribute("c_id", c_id);
		request.setAttribute("c_id_val", c_id_val);
		
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("nPage", nPage);
		request.setAttribute("bbsno", bbsno);
		
		return "/member/login";
	}
	
	@RequestMapping("/member/agree")
	public String agreement(){
		
		return "/member/agree";
	}
	
	@RequestMapping("/member/createForm")
	public String createForm(){
		
		return "/member/create";
	}
	
	@RequestMapping("/member/create")
	public String create(MemberDTO dto,HttpServletRequest request){
		String basePath = request.getRealPath("/storage");
		String fname = null;
		int size = (int)dto.getFnameMF().getSize();
			
		if(size>0){
		    fname = Utility.saveFileSpring30(dto.getFnameMF(), basePath);
		
		}else{
			fname = "member.jpg";
		}
		
		dto.setFname(fname);
		
		int cnt = mdao.create(dto);
		
		if(cnt==1){
			return "redirect:/";
		}else{
			return "/member/error";
		}
		
	}
	
	@RequestMapping("/member/emailForm")
	public String emailForm(){
		return "/popup/email_form";
	}
	
	@RequestMapping("/member/emailCheck")
	public String emailCheck(String email,Model model){
			
		int cnt = mdao.duplicateEmail(email);
		
		model.addAttribute("cnt", cnt);
		
		return "/popup/email_proc";
	}

	@RequestMapping("/member/idForm")
	public String idForm(){
		return "/popup/id_form";
	}
	
	@RequestMapping("/member/idCheck")
	public String idCheck(String id,Model model){
			
		int cnt = mdao.duplicateId(id);
		
		model.addAttribute("cnt", cnt);
		
		return "/popup/id_proc";
	}
	@RequestMapping(value="/member/zipCheck",method=RequestMethod.GET)
	public String zipcode(){
		
		return "/popup/zip_form";
	}
	@RequestMapping(value="/member/zipCheck",method=RequestMethod.POST)
	public String zipcode(String dongli,Model model){
		
		List<ZipcodeDTO> list = mdao.zipcodeList(dongli);
		
		model.addAttribute("list", list);
		
		
		return "/popup/zip_proc";
	}
	
}
