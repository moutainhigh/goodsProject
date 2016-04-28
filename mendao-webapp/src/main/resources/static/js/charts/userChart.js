function userChart(isview) {
	$.ajax({
        type:'get',
        url:"/c/report/showMenberJson?isView="+isview,//请求数据的地址
        success:function(data){
            var json = eval(data);
            var categories =[];
        	var showdata = [];
            for(var key in json){
                categories.push(json[key].name);
                showdata.push(json[key].value);
            }
			    $('#userline').highcharts({
			        chart: {
			            type: 'line'
			        },
			        title: {
			            text: '用户'
			        },
			        subtitle: {
			            text: ' '
			        },
			        xAxis: {
			            categories: categories
			        },
			        yAxis: {
			            title: {
			                text: '人数'
			            }
			        },
			        tooltip: {
			            enabled: false,
			            formatter: function() {
			                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'';
			            }
			        },
			        plotOptions: {
			            line: {
			                dataLabels: {
			                    enabled: true
			                },
			                enableMouseTracking: false
			            }
			        },
			        series: [{
			            name: '新增用户',
			            data: showdata
			        }]
			    });
        },
        error:function(e){
        } 
    });
}		