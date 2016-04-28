		function changeToMobile(){
			
			var email = document.getElementById("find-by-emial");
			var mobile = document.getElementById("find-by-mobile");
			
			if(mobile.style.display == "none"){
				$("#js_find_by_email").removeClass("active");
				$("#js_find_by_mobile").addClass("active");
				email.style.display = "none";
				mobile.style.display = "block";
			}
		};
		function changeToEmail(){
			var email = document.getElementById("find-by-emial");
			var mobile = document.getElementById("find-by-mobile");
			
			if(email.style.display == "none"){
				$("#js_find_by_email").addClass("active");
				$("#js_find_by_mobile").removeClass("active");
				email.style.display = "block";
				mobile.style.display = "none";
			}
			
		};
		
		$(function(){
			/*$("#js_find_by_mobile").addClass("active");*/
			
			$(".listmenu li").mouseenter(function(){
				$(".panellist >div").hide();
				$(".panellist >div").eq($(this).index()).show();
				$(this).siblings().removeClass("active");//siblings() 选择同级的所有元素
				$(this).addClass("active");
			});
			
		});
		
		