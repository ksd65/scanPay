<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>交易明细</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css">
<script type="application/javascript" src="../js/iscroll.js"></script>
<script type="application/javascript" src="../js/jquery-2.2.3.min.js"></script>
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
    //window.location.reload();
	 $(".tibtitul li.cur").click();
	 myScroll.refresh();
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();      // 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
    //setTimeout(function () {    // <-- Simulate network congestion, remove setTimeout from production!
        var el, li, i;
        el = document.getElementById('thelist');console.log(">>>>>>>>"+pageParam.pageType)
        if(pageParam.pageType == 2){
	        locadMoreData(pageParam.last.pageIndex+1,pageParam.last.pageSize,2,2);
        }else{
	        locadMoreData(pageParam.current.pageIndex+1,pageParam.current.pageSize,1,2);
        }
        /* for (i=0; i<3; i++) {
            li = document.createElement('li');
            li.innerHTML = "<a href='#'>"
            +"<dl><span class='red'>￥1000元</span><span class='blue'>微信（支付成功）</span><span class='smo'>2016-11-05<br/>17:18:43</span></dl>"
            +"</a>";
            el.appendChild(li, el.childNodes[0]);
        } */
        
        myScroll.refresh();     // 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
   // }, 0);   // <-- Simulate network congestion, remove setTimeout from production!
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
            if (pullUpEl.className.match('loading') || pullDownEl.className.match('loading')) {
                pullUpEl.className = '';
                pullDownEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
            }
        },
        onScrollMove: function () {
            /*
        	if (this.y > 5 && !pullDownEl.className.match('flip')) {
                pullDownEl.className = 'flip';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开刷新';
                this.minScrollY = 0;
            } else if (this.y < 5 && pullDownEl.className.match('flip')) {
                pullDownEl.className = '';
                pullDownEl.querySelector('.pullDownLabel').innerHTML = '';
                this.minScrollY = -pullDownOffset;
            } else if (this.y < (this.maxScrollY - 10) && !pullUpEl.className.match('flip')) {
                pullUpEl.className = 'flip';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '松开刷新';
                this.maxScrollY = this.maxScrollY;
            } else if (this.y > (this.maxScrollY + 10) && pullUpEl.className.match('flip')) {
                pullUpEl.className = '';
                pullUpEl.querySelector('.pullUpLabel').innerHTML = '';
                this.maxScrollY = pullUpOffset;
            }
            */
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
                pullUpAction(); // Execute custom function (ajax call?)
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
		url:"${ctx }/memberInfo/transactionLogData",
		data:dataParam,
		type:'post',
		cache:false, 
		dataType:'json',
		success:function(data) {
			console.log(data);
			if(data.returnCode=="0000"){//请求成功
				var transactionLogList = data.resData.transactionLogList;
				var html = "";
				for(var i=0;i<transactionLogList.length;i++){
					var status="";
					var transaction = transactionLogList[i];
					var remark = transaction.remarks;
					if(remark == null || "" == remark){
						remark = "无备注";
					}
					if(transaction.txnType=='1')status+="微信";
					else if(transaction.txnType=='2')status+="支付宝";
					else if(transaction.txnType=='3')status+="QQ钱包";
					else if(transaction.txnType=='4')status+="百度钱包";
					else if(transaction.txnType=='5')status+="京东钱包";
					else if(transaction.txnType=='6')status+="快捷有积分";
					else if(transaction.txnType=='7')status+="快捷无积分";
					//if(transaction.respType=='S')status+="(支付成功)";
					//else status+="(支付失败)";
					html = html + "<li><a href='#'><dl class='dl1'><i class='red'>￥"+ transaction.money.toFixed(2) +"</i><i class='blue'>"+status+"</i></dl>"
							+ "<dl class='dl2'><span>"+remark+"</span><span class='smo'>"+transaction.createDate +"</span></dl></a></li>";
				}
				if(oparateType == 1){
					$("#thelist").html(html);
					$("#pageCount .numCount").text(data.resData.transactionCounts);
					$("#pageCount .moneyCount").text(data.resData.moneyCount);
					if(data.resData.transactionCounts == 0){
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
                pageParam.pageType = $this.attr("data-type");
                pageParam.current.pageIndex = 1;
                locadMoreData(pageParam.current.pageIndex,pageParam.current.pageSize,1,1);
                	
            });
        }
    });
</script>
<body>

<ul class="tibtitul" id="tab">
    <li data-type="2"><a>历史交易<i></i></a></li>
    <li class="cur" data-type="1"><a>近7天交易</a></li>
</ul>

<div id="wrapper" class="jymxdiv">
    <div id="scroller">
        <div id="pullDown">
            <span class="pullDownLabel"></span>
        </div>
        <ul id="thelist" class="acmr">
	        <c:forEach var="transaction" items="${resData.transactionLogList }">
		        <li>
		        <a href="#">
					<dl class="dl1"><i class="red">￥<fmt:formatNumber value="${transaction.money }" pattern="0.00"/></i>
					<i class="blue">
						<c:if test="${transaction.txnType=='1' }">微信</c:if>
						<c:if test="${transaction.txnType=='2' }">支付宝</c:if>
						<c:if test="${transaction.txnType=='3' }">QQ钱包</c:if>
						<c:if test="${transaction.txnType=='4' }">百度钱包</c:if>
						<c:if test="${transaction.txnType=='5' }">京东钱包</c:if>
						<c:if test="${transaction.txnType=='6' }">快捷支付</c:if>
						<!-- 
						<c:if test="${transaction.respType=='S' }">(支付成功)</c:if>
						<c:if test="${transaction.respType=='E' }">(支付失败)</c:if>
						<c:if test="${transaction.respType=='R' }">(支付失败)</c:if>
						 -->
					</i>
					</dl>
					<dl class="dl2">
						<span>
							<c:choose>
							<c:when test="${!empty transaction.remarks }">${transaction.remarks }</c:when>
							<c:otherwise>无备注</c:otherwise>
						</c:choose>
						</span>
						<span class="smo">${transaction.createDate}</span>
					</dl>
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
<div id="datanull" style="display:${fn:length(resData.transactionLogList)>0?'none':'block'} " class="datanull">
	<img src="../images/datanull.png" />
	<span>暂无数据</span>
</div>

<div id="pageCount" class="jymxtop">
    <span class="left">交易笔数：<span class="numCount">${resData.transactionCounts }</span>笔</span>
    <span class="right">交易金额：<span class="moneyCount">${resData.moneyCount }</span>元</span>
</div>

</body>
</html>