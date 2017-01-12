/* 로그인 유효성 체크 검사 */
//login.jsp
function loginCheck(){
	if(document.frm.userid.value.length == 0 ){
		alert("아이디를 써주세요!");
		frm.userid.focus();
		return false;
	}
	if( document.frm.pwd.value == ""){
		alert("암호는 반드시 입력해야 합니다.");
		frm.pwd.focus();
		return false;
	}
	return true;
}

//중복 체크 페이지를 새로운 창으로 띄우기 위한 자바 스크립트 함수 
//join.jsp
function idCheck(){
	if(document.frm.userid.value == ""){
		alert("아이디를 입력하여 주십시오.");
		document.frm.userid.focus();
		return;
	}
	var url = "idCheck.do?userid=" + document.frm.userid.value;
	window.open(url, "_blank_1",
	"toolbar= no, menubar=no, scrollbars=yes, resizable=no, width=450, height=200");
}

//아이디 중복 체크 완료 처리를 위한 자바 스크립트 함수
//중복된 내용은 생략합니다.
//idCheck.jsp
function idok(){
	opener.frm.userid.value="${userid}";
	opener.frm.reid.value="${userid}";
	self.close();
}

//회원정보가 올바르게 입력되었는지 유효성을 체크하기 위한 자바 스크립트 함수 
//join.jsp
function joinCheck(){
	if(document.frm.name.value.length == 0){
		alert("이름을 써주세요.");
		frm.name.focus();
		return false;
	}
	if(document.frm.userid.value.length == 0){
		alert("아이디를 써주세요.");
		frm.userid.focus();
		return false;
	}
	if(document.frm.userid.value.length < 4){
		alert("아이디는 4글자 이상이어야 합니다!");
		frm.userid.focus();
		return false;
	}
	if(document.frm.pwd.value == ""){
		alert("암호는 반드시 입력해야 합니다.");
		frm.pwd.focus();
		return false;
	}
	if(document.frm.pwd.value != document.frm.pwd_check.value){
		alert("암호가 일치하지 않습니다.");
		frm.pwd.focus();
		return false;
	}
	if(document.frm.reid.value.length == 0){
		alert("중복 체크를 하지 않았습니다.");
		frm.userid.focus();
		return false;
	}
	return true;
}
