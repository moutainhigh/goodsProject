$.ajaxSetup({cache:false});
function isSelectData(checkName){
	var checkValue = getCheckValue(checkName);
	 if(checkValue==""||checkValue=="undefine"||checkValue==null){
	 	alert("没有选择任何数据!");
	 	return false;
	 }
	 return true;
}

function isMultiSelectData(checkName){
	 var checkList = document.getElementsByName(checkName);
	 var totalChecked = 0;
	 for(var i=0;i<checkList.length;i++){
	 	if(checkList[i].checked){
	 		totalChecked++;
	 	}
	 }
	 if(totalChecked>1){
		 alert("不能选择多个进行操作！");
		 return false;
	 }
	 if(totalChecked == 0){
		 alert("没有选择任何数据!");
		 return false;
	 }
	 return true;
}
//查看活动参与者列表js方法
function showActivityUser(actionUrl,checkName){
	var checkValue = getCheckValue(checkName);
	if(!checkValue){
		window.location.href = actionUrl;
	}else{
		window.location.href = actionUrl+"/"+checkValue;
	}
}
function editData(actionUrl,checkName){
	var checkValue = getCheckValue(checkName);
	if(!checkValue){
		window.location.href = actionUrl;
	}else{
		window.location.href = actionUrl+(actionUrl.indexOf("?")>0?"&":"?")+"queryId="+checkValue;
	}
}
function getCheckValue(checkName){
	 var checkValue=[];
	 var checkList = document.getElementsByName(checkName);
	 for(var i=0;i<checkList.length;i++){
	 	if(checkList[i].checked){
	 		checkValue.push(checkList[i].value);
	 	}
	 }
	 return checkValue;
	}

function click_delete(actionUrl,checkName,param){
	 var checkValue = getCheckValue(checkName);
	 if(checkValue==""||checkValue=="undefine"||checkValue==null){
	 	alert("请选择要删除的数据!");
	 	return ;
	 }else if(confirm('确定要删除所选的数据吗?')){
	 	var targetUrl = actionUrl+(actionUrl.indexOf("?")>0?"&":"?")+checkName+'='+checkValue;
	 	if(param!=null&&param!='undefine'&&param.length>0){
	 		targetUrl= targetUrl+'&'+param;
	 	}
	 	window.location.href=targetUrl;
	 }
	 return;
	}
function checkAll(selectAllBoxObj){
	checkName = selectAllBoxObj.name;
	checkName = checkName.substring(0,checkName.indexOf('_'));
	var checkList = document.getElementsByName(checkName);
	for(var i=0;i<checkList.length;i++){
		checkList[i].checked = selectAllBoxObj.checked;
	}
}
function savedataTree(){
	if(confirm('确认要保存吗？')){
		var actionStr = [];
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes(true);
		for(var i=0;i<nodes.length;i++){
			if(!nodes[i].isParent){
				 var flag = $(nodes[i]).attr('flag');
				 if(flag==2){
				 	actionStr.push(nodes[i].id);
				 }
			}
		}
		$("#actionId").val(actionStr);
	}
}
function deleteData(targetUrl){
	layer.confirm('是否删除该数据?', {icon: 3, title:'提示'}, function(index){
			layer.close(index);
			window.location.href=targetUrl;
		});
}