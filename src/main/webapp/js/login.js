}
n to_login_page() {
	document.getElementById('login_page').style.display = '';
	document.getElementById('edit_page').style.display = "none";
}

function to_edit_page()
{
	document.getElementById('login_page').style.display = 'none';
	document.getElementById('edit_page').style.display = '';
}

function login() {
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	if (username == "") {
		alert("请输入用户名！");
		document.getElementById("username").focus();
		return false;
	} else if (password == "") {
		alert("请输入密码！");
		document.getElementById("password").focus();
		return false;
	}
	
	$.ajax({
		//几个参数需要注意一下
		type : "POST",//方法类型
		dataType : "html",//预期服务器返回的数据类型
		url : "project",//url
		data : $('#login_form').serialize(),
		success : function(result) {
			window.location.assign("project.html")
		},
		error : function() {
			alert("用户名或密码错误！");
			document.getElementById("password").focus();
		}
	});
}

//编辑项目
function edit() {
	var edit_username = document.getElementById("edit_username").value;
	var edit_password = document.getElementById("edit_password").value;
	var new_password = document.getElementById("new_password").value;
	var new_password_again = document.getElementById("new_password_again").value;
	if (edit_username == "") {
		alert("请输入用户名！");
		document.getElementById("edit_username").focus();
		return false;
	} else if (old_password == "") {
		alert("请输入旧密码！");
		document.getElementById("old_password").focus();
		return false;
	} else if (new_password == ""){
		alert("请输入新密码！");
		document.getElementById("new_password").focus();
	} else if(new_password != new_password_again) {
		alert("两次密码输入不一致！");
		document.getElementById("new_password_again").focus();
	}
	
	$.ajax({
		//几个参数需要注意一下
		type : "POST",//方法类型
		dataType : "html",//预期服务器返回的数据类型
		url : "project",//url
		data : $('#edit_form').serialize(),
		success : function(result) {
			alert("修改成功！");
			to_login_page();
		},
		error : function() {
			alert("修改异常！");
		}
	});
}
