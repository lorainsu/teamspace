//刷新页面
function returnpage() {
	history.go(0);
}

// 查询数据
function search_row() {
	document.getElementById('project_tab').style.display = '';
	document.getElementById('detail_tab').style.display = 'none';
	$(".bootstrap-table").show();
}

// 导出数据
function export_tab() {
	var excelForm = document.createElement("form");
	excelForm.method = "post";
	excelForm.action = "project/export";
	excelForm.style.display = "none";
	var opt = document.createElement("input");
	opt.name = "industry";
	opt.value = $('#search_industry').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "ecosphere";
	opt.value = $('#search_ecosphere').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "product";
	opt.value = $('#search_product').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "start_time";
	opt.value = $('#search_start_time').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "end_time";
	opt.value = $('#search_end_time').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "proj_name";
	opt.value = $('#search_proj_name').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "isv";
	opt.value = $('#search_isv').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "esdk_liaison";
	opt.value = $('#search_esdk_liaison').val();
	excelForm.appendChild(opt);
	opt = document.createElement("input");
	opt.name = "key_proj";
	opt.value = $('#search_key_proj').val();
	excelForm.appendChild(opt);
	document.body.appendChild(excelForm);
	excelForm.submit();
	document.body.removeChild(excelForm);
}
// 显示添加数据表格
function to_add_tab() {
	document.getElementById('detail_tab').style.display = '';
	document.getElementById('project_tab').style.display = 'none';
	document.getElementById('edit_button').style.display = "none";
	document.getElementById('add_button').style.display = '';

	document.getElementById('project_name_update').innerHTML = "添加项目";
	document.getElementById("search_industry").value = "";
	document.getElementById("search_ecosphere").value = "";
	document.getElementById("search_product").value = "";
	document.getElementById("search_proj_name").value = "";
	document.getElementById("search_isv").value = "";
	document.getElementById("search_esdk_liaison").value = "";
	document.getElementById("search_start_time").value = "";
	document.getElementById("search_end_time").value = "";

	document.getElementById("industry").value = "";
	document.getElementById("ecosphere").value = "";
	document.getElementById("product").value = "";
	document.getElementById("key_proj").value = "";
	document.getElementById("proj_name").value = "";
	document.getElementById("isv").value = "";
	document.getElementById("spp_solution").value = "";
	document.getElementById("spp_status").value = "";
	document.getElementById("proj_background").value = "";
	document.getElementById("requirement").value = "";
	document.getElementById("amount").value = "";
	document.getElementById("delivery_time").value = "";
	document.getElementById("ability").value = "";
	document.getElementById("proj_status").value = "";
	document.getElementById("liaison").value = "";
	document.getElementById("liaison_tel").value = "";
	document.getElementById("liaison_email").value = "";
	document.getElementById("huawei_liaison").value = "";
	document.getElementById("huawei_liaison_dept").value = "";
	document.getElementById("esdk_liaison").value = "";
	document.getElementById("start_time").value = "";
	document.getElementById("end_time").value = "";
	document.getElementById("remark").value = "";
	// $(".bootstrap-table").hide();
	$("#top_search").hide();
	$("#top_progress").hide();
	$("#top_project_update").show();
}

// 显示编辑数据表格
function to_edit_tab(id, proj_name) {
	document.getElementById('detail_tab').style.display = "";
	document.getElementById('edit_button').style.display = "";
	document.getElementById('project_tab').style.display = 'none';
	document.getElementById('add_button').style.display = "none";

	document.getElementById('project_name_update').innerHTML = proj_name;
	document.getElementById("search_industry").value = "";
	document.getElementById("search_ecosphere").value = "";
	document.getElementById("search_product").value = "";
	document.getElementById("search_proj_name").value = "";
	document.getElementById("search_isv").value = "";
	document.getElementById("search_esdk_liaison").value = "";
	document.getElementById("search_start_time").value = "";
	document.getElementById("search_end_time").value = "";
	document.getElementById("search_key_proj").value = "";
	// $(".bootstrap-table").hide();

	$("#top_search").hide();
	$("#top_progress").hide();
	$("#top_project_update").show();
	$
			.ajax({
				type : "get",// 方法类型
				dataType : "json",// 预期服务器返回的数据类型
				url : "project?id=" + id + "&timestamp=" + new Date().getTime(),// url
				async : false,
				success : function(data) {
					var project = eval(data);
					document.getElementById("id").value = project.id;
					document.getElementById("industry").value = project.industry;
					document.getElementById("ecosphere").value = project.ecosphere;
					document.getElementById("product").value = project.product;
					document.getElementById("key_proj").value = project.key_proj;
					document.getElementById("proj_name").value = project.proj_name;
					document.getElementById("isv").value = project.isv;
					document.getElementById("spp_solution").value = project.spp_solution;
					document.getElementById("amount").value = project.amount;
					document.getElementById("delivery_time").value = project.delivery_time;
					document.getElementById("proj_status").value = project.proj_status;
					document.getElementById("liaison").value = project.liaison;
					document.getElementById("liaison_tel").value = project.liaison_tel;
					document.getElementById("liaison_email").value = project.liaison_email;
					document.getElementById("huawei_liaison").value = project.huawei_liaison;
					document.getElementById("huawei_liaison_dept").value = project.huawei_liaison_dept;
					document.getElementById("esdk_liaison").value = project.esdk_liaison;
					document.getElementById("start_time").value = project.start_time;
					document.getElementById("end_time").value = project.end_time;
					document.getElementById("spp_status").value = project.spp_status;

					document.getElementById("ability").value = "";
					if (project.ability == null) {
						document.getElementById("ability").value = "";
					} else {
						document.getElementById("ability").value = project.ability;
					}

					if (project.proj_background == null) {
						document.getElementById("proj_background").value = "";
					} else {
						document.getElementById("proj_background").value = project.proj_background;
					}

					if (project.remark == null) {
						document.getElementById("remark").value = "";
					} else {
						document.getElementById("remark").value = project.remark;
					}

					if (project.requirement == null) {
						document.getElementById("requirement").value = "";
					} else {
						document.getElementById("requirement").value = project.requirement;
					}

					if (project.start_time == null) {
						start_time_is_null = "";
					} else {
						start_time_is_null = "exist";
					}
					if (project.end_time == null) {
						end_time_is_null = "";
					} else {
						end_time_is_null = "exist";
					}
				},
				error : function(XMLHttpRequest) {

					if (XMLHttpRequest.readyState == 4
							&& XMLHttpRequest.status == 403) {
						alert("页面超时！");
						window.location = "/teamspace";
					} else {
						alert("查询异常:" + XMLHttpRequest.responseText);
					}
				}
			})
}

// 删除选中项目数据
function delete_row() {
	var rows = $('#project_tab').bootstrapTable('getSelections');
	if (rows.length == 0) {
		alert("请选择要删除的数据");
		return;
	}
	var r = confirm("确认要删除选中的'" + rows.length + "'条数据吗?")
	if (r == false) // 点击取消，直接返回
		return;
	else {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		var url = "project?ids=" + ids;
		// 定义ajax请求参数
		$.ajax({
			type : "delete",
			url : url,
			datatype : "json",
			success : function(data) {
				alert("删除成功");
				history.go(0);
			},
			error : function(XMLHttpRequest) {
				if (XMLHttpRequest.readyState == 4
						&& XMLHttpRequest.status == 403) {
					alert("页面超时！");
					window.location = "/teamspace";
				} else {
					alert("删除异常:" + XMLHttpRequest.responseText);
				}
			}
		});
	}
}

// 删除选中进度数据
function delete_progress() {
	var rows = $('#progress_tab').bootstrapTable('getSelections');
	if (rows.length == 0) {
		alert("请选择要删除的数据");
		return;
	}
	var r = confirm("确认要删除选中的'" + rows.length + "'条数据吗?")
	if (r == false) // 点击取消，直接返回
		return;
	else {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		var url = "progress?ids=" + ids;
		var id = document.getElementById('proj_id').value;
		$.ajax({
			type : "delete",
			url : url,
			datatype : "json",
			success : function(data) {
				alert("删除成功！");
				$("#progress_tab").bootstrapTable('refresh', {
					url : "progress/query?proj_id=" + id
				});
			},
			error : function(XMLHttpRequest) {
				if (XMLHttpRequest.readyState == 4
						&& XMLHttpRequest.status == 403) {
					alert("页面超时！");
					window.location = "/teamspace";
				} else {
					alert("删除异常:" + XMLHttpRequest.responseText);
				}
			}
		});
	}
}
// 校验必选框数据
function check_data() {
	var industry = document.getElementById("industry").value;
	var ecosphere = document.getElementById("ecosphere").value;
	var product = document.getElementById("product").value;
	var proj_name = document.getElementById("proj_name").value;
	var isv = document.getElementById("isv").value;
	var esdk_liaison = document.getElementById("esdk_liaison").value;
	var start_time = document.getElementById("start_time").value;
	var end_time = document.getElementById("end_time").value;
	if (industry == "") {
		alert("请选择行业！");
		document.getElementById("industry").focus();
		return false;
	} else if (ecosphere == "") {
		alert("请选择生态圈！");
		document.getElementById("ecosphere").focus();
		return false;
	} else if (product == "") {
		alert("请选择所属产品！");
		document.getElementById("product").focus();
		return false;
	} else if (proj_name == "") {
		alert("请输入项目名称！");
		document.getElementById("proj_name").focus();
		return false;
	} else if (isv == "") {
		alert("请输入ISV！");
		document.getElementById("isv").focus();
		return false;
	} else if (esdk_liaison == "") {
		alert("请输入esdk支持工程师！");
		document.getElementById("esdk_liaison").focus();
		return false;
	} else if (Date.parse(start_time) > Date.parse(end_time)) {
		alert("开始时间不能早于结束时间！");
		document.getElementById("start_time").focus();
		return false;
	}

	return true;
}

// 添加新的项目
function add_project() {
	var result = check_data()
	if (!result) {
		return false;
	}

	$
			.ajax({
				// 几个参数需要注意一下
				type : "POST",// 方法类型
				dataType : "html",// 预期服务器返回的数据类型
				url : "project",// url
				data : $('#project_form').serialize(),
				success : function(result) {
					alert("添加成功！");
					history.go(0);
				},
				error : function(XMLHttpRequest) {

					if (XMLHttpRequest.readyState == 4
							&& XMLHttpRequest.status == 403) {
						alert("页面超时！");
						window.location = "/teamspace";
					} else {
						alert("添加异常:" + XMLHttpRequest.responseText);
					}
				}
			});
}

// 编辑项目
function edit_project() {
	var result = check_data()
	if (!result) {
		return false;
	}

	if (start_time_is_null == "exist"
			&& document.getElementById("start_time").value == "") {
		alert("开始时间不能为空！")
		document.getElementById("start_time").focus();
		return false;
	} else if (end_time_is_null == "exist"
			&& document.getElementById("end_time").value == "") {
		alert("结束时间不能为空！")
		document.getElementById("end_time").focus();
		return false;
	}
	$
			.ajax({
				// 几个参数需要注意一下
				type : "put",// 方法类型
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",
				dataType : "html",// 预期服务器返回的数据类型
				url : "project",// url
				data : $('#project_form').serialize(),
				success : function(result) {
					alert("修改成功！");
					history.go(0);
				},
				error : function(XMLHttpRequest) {

					if (XMLHttpRequest.readyState == 4
							&& XMLHttpRequest.status == 403) {
						alert("页面超时！");
						window.location = "/teamspace";
					} else {
						alert("修改异常:" + XMLHttpRequest.responseText);
					}
				}
			});
}
// 增加新的进度
function add_progress() {
	var content = document.getElementById("content").value;
	if (content == "") {
		alert("请输入项目最新进度！");
		document.getElementById("content").focus();
		return false;
	}

	var id = document.getElementById('proj_id').value;
	$
			.ajax({
				type : "POST",// 方法类型
				dataType : "html",// 预期服务器返回的数据类型
				url : "progress",// url
				data : $('#add_progress_form').serialize(),
				success : function(result) {
					alert("添加成功！");
					document.getElementById("content").value = "";
					$("#progress_tab").bootstrapTable('refresh', {
						url : "progress/query?proj_id=" + id
					});
				},
				error : function(XMLHttpRequest) {

					if (XMLHttpRequest.readyState == 4
							&& XMLHttpRequest.status == 403) {
						alert("页面超时！");
						window.location = "/teamspace";
					} else {
						alert("添加异常:" + XMLHttpRequest.responseText);
					}
				}
			});
}

// 初始化进度数据
function show_progress(id, proj_name) {
	$('#progress_tab').bootstrapTable({
		method : 'post',
		contentType : "application/x-www-form-urlencoded",
		url : "progress/query?proj_id=" + id,
		dataType : 'json',
		striped : true, // 是否显示行间隔色
		pagination : true,// 是否分页
		sidePagination : "server",
		sortable : true, // 是否启用排序
		sortName : 'id',
		sortOrder : 'asc', // 排序方式
		queryParamsType : '',// 查询参数组织方式
		queryParams : queryParams,// 请求服务器时所传的参数
		sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
		pageNumber : 1, // 初始化加载第一页，默认第一页
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 20, 30 ], // 可供选择的每页的行数（*）
		clickToSelect : true,// 是否启用点击选中行

		buttonsAlign : 'right',// 按钮对齐方式

		columns : [ {
			field : 'id',
			title : '序号',
			visible : false,
			width : 50,
			align : 'center'
		}, {
			field : 'update_time',
			title : '更新时间',
			width : 400,
			align : 'center'
		}, {
			field : 'content',
			title : '项目进展',
			align : 'center'
		} ],
		onLoadSuccess : function() {
		},
		onLoadError : function() {
			showTips("数据加载失败！");
		},
		locale : 'zh-CN',// 中文支持
	})

	document.getElementById('project_tab').style.display = 'none';
	document.getElementById('progress_tab').style.display = '';
	document.getElementById('top_progress').style.display = '';
	document.getElementById('proj_id').value = id;
	document.getElementById('progress_proj_name').innerHTML = proj_name;
	$("#top_search").hide();
	$("#top_project_update").hide();

	// 请求服务数据时所传参数
	function queryParams(params) {
		return {
			// 每页多少条数据
			pageSize : params.pageSize,
			// 请求第几页
			pageNo : params.pageNumber,
			sortOrder : this.sortName,// 排序
			sortName : this.sortOrder
		// 排序字段
		}
	}
}
