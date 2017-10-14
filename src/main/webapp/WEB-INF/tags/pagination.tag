<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="body" type="java.lang.String" required="true"%>
<script type='text/javascript'>
	$(document).ready(function(){
		ajaxPagination("<%=url%>?${searchParams}", "<%=body%>", "<%=id%>");
	});
	
</script>
<div class='pagination'>
	<ul><li class='disabled'><a id="<%=id%>Count"></a></li></ul>
</div>
<div id="<%=id%>" class="pagination-right">
</div>