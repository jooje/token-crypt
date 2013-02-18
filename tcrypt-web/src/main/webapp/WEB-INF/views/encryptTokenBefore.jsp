<%@ include file="/WEB-INF/views/taglibs.jsp"%>
<z:layout pageTitle="Token Encryption">
<script type="text/javascript">
	$(function() {
		var availableServiceNames = ${serviceNames} ;
		$( "#serviceNames" ).autocomplete({
	        source: availableServiceNames
	    });
	});
</script>

	<form name="encryptToken" action="${pageContext.request.contextPath}/apps/encrypt" method="post">
			<div>
				<span class="label" >Service Name : </span>
				<span>
				  <input type="text" name="serviceNames" id="serviceNames"/> 
				</span>
			</div>
			<div>
				<span class="label">Text : </span>
				<span style="padding-left: 70px"><textarea rows="5" cols="20" id="text" name="text"></textarea> </span>
			</div>
			<div>
				<span><input type="submit" name="encrypt" value="Encrypt"></span>
			</div>
	</form>
</z:layout>