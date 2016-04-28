
function phoneChart(isview) {
	$.ajax({
        type:'get',
        url:"/c/report/showPhoneJson?isView="+isview,//请求数据的地址
        success:function(data){
            var json = eval(data);
            var categories =[];
        	var showdata = [];
            for(var key in json){
                categories.push(json[key].name);
                showdata.push(json[key].value);
            }
            $('#phoneline').highcharts({
                chart: {
                    type: 'line'
                },
                title: {
                    text: '双向回呼记录'
                },
                subtitle: {
                    text: ' '
                },
                xAxis: {
                    categories: categories
                },
                yAxis: {
                    title: {
                        text: '呼叫次数'
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
                    name: '呼叫次数',
                    data: showdata
                }]
            });
        },
        error:function(e){
        } 
    });
}		