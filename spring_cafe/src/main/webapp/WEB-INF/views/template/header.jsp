<%@ page contentType="text/html; charset=UTF-8" %> 
 
<% 
	String root = request.getContextPath(); 
    String id = (String)session.getAttribute("id");
    String grade = (String)session.getAttribute("grade");
%>
 
<html>
<head>
<style type="text/css">
ul#menu li{
display : inline;
}
ul#menu li a{
background-color: black;
color:white;
pading : 10px 20px;
text-decoration:none;
border-radius:4px 4px 0 0;
}
ul#menu li a:hover{
background-color: orange;
}

li#admin{
float:right;
padding-right: 30px
} 
</style>
</head>
<body>
<!-- 상단 메뉴 -->
<div style="background-color: #EEEEEE;">
<table style="width: 100%">
  <tr>
    <td>
        <img src="<%=root %>/images/main.jpg" width='100%' height='100%'>
    </td>
  </tr>
  
  <tr>
    <td>
    <ul id="menu">
     <li><a href="<%=root %>/">홈</a></li> 
<% if(id==null){ %>    
     <li><a href="<%=root %>/member/login">로그인</a></li>
     <li><a href="<%=root %>/member/agree">회원가입</a></li>
     <li><a href="<%=root %>">아이디찾기</a></li>   
     <li><a href="<%=root %>">비번찾기</a></li>
<% }else{ %>      
     <li><a href="<%=root %>/member/logout">로그아웃</a></li>   
     <li><a href="<%=root %>/member/read">회원정보</a></li>   
     <li><a href="<%=root %>/member/update">회원수정</a></li>   
     <li><a href="<%=root %>/member/delete">회원탈퇴</a></li>   
<% } %>
     <li><a href="<%=root %>/bbs/list">게시판목록</a></li>  
     <li><a href="<%=root %>/bbs/create">게시판생성</a></li>  
     <li><a href="<%=root %>/memo/list">메모목록</a></li>  
     <li><a href="<%=root %>/memo/create">메모생성</a></li> 
     <li><a href="<%=root %>/img/list">이미지목록</a></li>  
     <li><a href="<%=root %>/img/create">이미지생성</a></li> 
<% if(id!=null && grade.equals("A")) {%> 
     <li id="admin"><a href="<%=root %>/admin/list">회원목록</a></li> 
<% } %>
     </ul>
    </td> 
  </tr>
</table>
</div>
<!-- 상단 메뉴 끝 -->
 
 
 
<!-- 내용 시작 -->
<div style="width: 100%; padding-top: 10px;">