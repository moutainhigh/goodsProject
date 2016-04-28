function orderChart(isview) {
	
	$.ajax({
        type:'get',
        url:"/c/report/showOrderJson?isView="+isview,//请求数据的地址
        success:function(data){
            var json = eval(data);
            var categories =[];
        	var showdataTK = [];
        	var showdataFK = [];
        	var showdataWF = [];
            for(var key in json){
            	 if(categories.indexOf(json[key].name) == -1){
            		 categories.push(json[key].name);
            	 }
                if(json[key].orderType == 1){
                	showdataWF.push(json[key].value);
                }else if(json[key].orderType == 2){
                	showdataFK.push(json[key].value);
                }else if(json[key].orderType == 3){
                	showdataTK.push(json[key].value);
                }
            }
		  $('#orderClumn').highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : '订单'
							},
							xAxis : {
								categories :categories
							},
							yAxis : {
								min : 0,
								title : {
									text : '订单总量'
								},
								stackLabels : {
									enabled : true,
									style : {
										fontWeight : 'bold',
										color : (Highcharts.theme && Highcharts.theme.textColor)
												|| 'gray'
									}
								}
							},
							legend : {
								align : 'right',
								x : -70,
								verticalAlign : 'top',
								y : 20,
								floating : true,
								backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid)
										|| 'white',
								borderColor : '#CCC',
								borderWidth : 1,
								shadow : false
							},
							tooltip : {
								formatter : function() {
									return '<b>' + this.x + '</b><br>'
											+ this.series.name + ': ' + this.y
											+ '<br>' + '订单: '
											+ this.point.stackTotal;
								}
							},
							plotOptions : {
								column : {
									stacking : 'normal',
									dataLabels : {
										enabled : true,
										color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
												|| 'white'
									}
								}
							},
							series : [ {
								name : '退款中',
								data : showdataTK
							}, {
								name : '未付款',
								data : showdataWF
							}, {
								name : '已付款',
								data : showdataFK
							} ]
						});
        },
        error:function(e){
        } 
    });
}
