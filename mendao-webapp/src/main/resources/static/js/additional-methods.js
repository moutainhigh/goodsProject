// 电话号码验证   
jQuery.validator.addMethod("isPhone", function(value, element) {   
    var tel = /^1[3|4|5|7|8]\d{9}$/; 
    return this.optional(element) || (tel.test(value));
}, "请输入正确的手机号码");
// 检测是否输入空格
jQuery.validator.addMethod("isNull", function(value, element) {
	value=value.replace(/(^\s*)|(\s*$)/g, "");
	var parten = /[ ]/;
	var flag=true;
	if(value.length>0){
		flag=true;
	}else{
		flag=false;
	}
	return this.optional(element) || flag;
}, "请勿输入空格");









 

