<%
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>


<portlet:defineObjects />

<portlet:resourceURL var="d3Test" id="d3Test"></portlet:resourceURL>

<script type="text/javascript">
loadD3Data();

function loadD3Data(){
    var myInfoUrl = "<%=d3Test%>";
    $.ajax({
        url: myInfoUrl ,
        type: 'GET',
        datatype:'json',
        success: function(data){
            var obj = $.parseJSON(data);
            alert(obj);
        }
    });
}
</script>

