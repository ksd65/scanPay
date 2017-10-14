<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>提现明细</title>
<link href="${ctx }/johu/css/main_style.css" rel="stylesheet" type="text/css">
<script type="application/javascript" src="${ctx }/js/iscroll.js"></script>
<script type="text/javascript">

var myScroll,
    pullDownEl, pullDownOffset,
    pullUpEl, pullUpOffset,
    generatedCount = 0;
    
var pageParam = {
		current:{pageIndex : 1,pageSize : 10},   //pageType=1
		last: {pageIndex : 1,	pageSize : 10},	//pageType=2
		pageType : 1  ,
		isAbleFlag :true
};
/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();      // 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	pageParam.current.pageIndex = 1;
    locadMoreData(pageParam.current.pageIndex,pageParam.current.pageSize,1,1);
    myScroll.refresh();     // 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();      // 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {  
    //var el, li, i;
    //el = document.getElementById('thelist');
    locadMoreData(pageParam.current.pageIndex+1,pageParam.current.pageSize,1,2);
    /**for (i=0; i<3; i++) {
        li = document.createElement('li');
        li.innerHTML = "<a href='#'>"
        +"<dl><span class='red'>￥18900元</span><span class='blue'>提现成功</span><span class='smo'>2016-11-05<br/>17:18:43</span></dl>"
        +"</a>";
        el.appendChild(li, el.childNodes[0]);
    }**/
    
    myScroll.refresh();     // 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
}

/**
 * 初始化iScroll控件
 */
function loaded() {
    pullDownEl = document.getElementById('pullDown');
    pullDownOffset = pullDownEl.offsetHeight;
    pullUpEl = document.getElementById('pullUp');   
    pullUpOffset = pullUpEl.offsetHeight;
    
    myScroll = new iScroll('wrapper', {
        scrollbarClass: 'myScrollbar', /* 重要样式 */
        useTransition: false, /* 此属性不知用意，本人从true改为false */
        topOffset: pullDownOffset,
        onRefresh: function () {
            if (pullUpEl.className.match('loading') || pullDownEl.className.match('loading') ) {
                pullUpEl.className = '';
                pullDownEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
            }
        },
        onScrollMove: function () {
            if (this.y > -pullUpOffset) {
                pullDownEl.className = 'flip';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开刷新';
                this.minScrollY = 0;
            } else if (this.y >0 ) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
                this.minScrollY = -pullDownOffset;
            } else if (this.y < (this.minScrollY - pullUpOffset)) {
                pullUpEl.className = 'flip';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '松开加载';
                this.maxScrollY = this.maxScrollY;
            } else if (this.y > (this.minScrollY - pullUpOffset)) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '';
                this.maxScrollY = pullUpOffset;
            }
        },
        onScrollEnd: function () {
            if (pullDownEl.className.match('flip')) {
                pullDownEl.className = 'loading';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';                
                pullDownAction();   // Execute custom function (ajax call?)
            } else if (pullUpEl.className.match('flip')) {
                pullUpEl.className = 'loading';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';                
                pullUpAction(); 
            }
        }
    });
    
    setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 0);
}

//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 


function locadMoreData(pageIndex,pageSize,pageType,oparateType){
	var dataParam = {
			currentPage:pageIndex,
			pageSize:pageSize,
			dataType:pageParam.pageType};
	$.ajax({
		url:"${ctx }/memberInfo/drawListData",
		data:dataParam,
		type:'post',
		cache:false, 
		dataType:'json',
		success:function(data) {
			console.log(data);
			if(data.returnCode=="0000"){//请求成功
				var draws = data.resData.draws;
				var html = "";
				for(var i=0;i<draws.length;i++){
					var draw = draws[i];
					html = html + "<li><a href='#'><dl><span>￥"+(draw.respType=='S'?draw.drawamount:0) +"元</span><span class='blue'>"+(draw.respType=='S'?"提现成功":"提现失败") +"</span>"
							+ "<span class='smo'>"+draw.createDate.replace(' ','<br/>') +"</span></dl></a></li>";
				}
				if(oparateType == 1){
					$("#thelist").html(html);
					$("#pageCount .numCount").text(data.resData.drawCnt);
					$("#pageCount .moneyCount").text(data.resData.moneyCount);
					if(data.resData.draws == 0){
						$("#datanull").show()
					}else{
						$("#datanull").hide()
					}
				}else{
					$("#thelist").append(html);
					if(pageType == 1){
						pageParam.current.pageIndex = pageIndex;
					}else{
						pageParam.last.pageIndex = pageIndex;
					}
				}
			}else{
				alert(data.returnMsg);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {    
	        alert("请求出错");
	    }
	});
}
</script>

<script type="application/javascript" src="${ctx }/js/jquery-2.2.3.min.js"></script>
<script>
    $(function(){
        window.onload = function()
        {
            var $li = $('#tab li');
            var $ul = $('#scroller .acmr');
                        
            $li.click(function(){
                var $this = $(this);
                var $t = $this.index();
                $li.removeClass();
                $this.addClass('cur');
                $ul.css('display','none');
                $ul.removeAttr('id');
                $ul.eq($t).css('display','block');
                $ul.eq($t).attr('id','thelist');
            });
        }
    });
</script>
<body>
<div id="wrapper" class="jymxdiv txmxb">
    <div id="scroller">
        <div id="pullDown">
            <span class="pullDownLabel"></span>
        </div>
        <ul id="thelist" class="acmr">
        <c:forEach var="draw" items="${resData.draws }">
	        <li>
	        <a href="${ctx }/memberInfo/drawDetail?drawId=${draw.id}">
				<dl><span>￥
					<c:if test="${draw.respType=='S' }">${draw.drawamount }</c:if>
					<c:if test="${draw.respType=='E' }">0</c:if>
					<c:if test="${draw.respType=='R' }">0</c:if>
				元</span>
				<span class="blue">
					<c:if test="${draw.respType=='S' }">提现成功</c:if>
					<c:if test="${draw.respType=='E' }">提现失败</c:if>
					<c:if test="${draw.respType=='R' }">提现失败</c:if>
				</span>
				<span class="smo">${fn:replace(draw.createDate," ","<br/>") }</span></dl>
            </a>
	        </li>
        </c:forEach>
	    </ul>
        <div id="pullUp">
            <span class="pullUpLabel"></span>
        </div>
    </div>
</div>

<!--数据为空显示-->
<div id="datanull" style="display:${fn:length(resData.draws)>0?'none':'block'} " class="datanull">
	<img src="${ctx }/johu/images/datanull.png" />
	<span>暂无数据</span>
</div>

<div  id="pageCount" class="jymxtop">
    <span class="left">提现笔数：<span class="numCount">${resData.drawCnt }</span>笔</span>
    <span class="right">提现总额：<span class="moneyCount">${resData.moneyCount }</span>元</span>
</div>

</body>
</html>