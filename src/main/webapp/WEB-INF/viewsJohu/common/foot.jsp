<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- 底部 -->

<style type="text/css">
.shopnum3{
  	position:absolute !important;
	top:5% !important;
	left:45% !important;
    z-index: 1010 !important;
    border-radius: 100% !important;
    background-color: red !important;
    color: #fff !important;
    padding: 0.1rem !important;
    text-align: center !important;
    line-height: normal !important;
    font-size: 0.5rem !important;
    font-style:normal !important;
    min-width: 20% !important;
}
</style>
<script type="text/javascript">
$(function(){
	
	 if($('#shopnum3').text()=='0'||$('#shopnum3').text()==''){
		 $('#shopnum3').hide();
	 }; 
})
</script>

<div class="pt-f z5 lb0 menu bor-top-c3c3c3 bg-fff">
  <ul class="li-fl">
    <!--<li> <i class="icon iconfont icon-home"></i>-->
    <li> <a href="${ctx}" class="cr-696969" id="homeMenu"> <i class="icon iconfont icon-homeon"></i>
      <p class="f6m">首页</p>
      </a> </li>
    
    <!-- <li> <i class="icon iconfont icon-classifyon cr-2383c4"></i>-->
    <li> <a href="${ctx}/category" class="cr-696969" id="categoryMenu"> <i class="icon iconfont icon-classify"></i>
      <p class="f6m">分类</p>
      </a> </li>
    <li> <a href="${ctx}/shop/shoppingCart" class="cr-696969" id="shopCartMenu" style="position:relative;"> <i class="icon iconfont icon-cart"></i>
      <i class="shopnum3" id="shopnum3">${sessionScope.TOTAL_SHOPCART}</i>
      <p class="f6m">购物车</p>
      </a> </li>
    <li> <a href="${ctx}/user/person" class="cr-696969" id="personMenu"> <i class="icon iconfont icon-person"></i>
      <p class="f6m">个人中心</p>
      </a> </li>
  </ul>
  <div class="clear"> </div>
</div>