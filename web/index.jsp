<%-- 
    Document   : index
    Created on : Oct 5, 2017, 10:32:24 AM
    Author     : Diep
--%>

<%@page import="java.lang.reflect.Array"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hierarchical Clustering</title>
    </head>
    <body>
        
        <% request.setCharacterEncoding("UTF-8"); %>
        <div style="text-align: center; margin-top: 50px">
            <h5 style="color: red">${mes1}</h5>
            <h5 style="color: red">${mes2}</h5>
            <form action="HierarchicalClustering" method="get">
                Slice: <input type="text" name="slice" style="width: 80px">
                <button type="submit" style="width: 100px;height: 40px; margin-left: 10px">Clustering</button>
            </form>
        </div>
        <div style="text-align: center; margin-top: 50px;width: 1000px;
             margin-left: 200px; overflow-y: auto;height: 500px">
            
            <%int t=0;
                ArrayList<HashMap> list = null;
                ArrayList<String> title = null;
                HashMap article = null;
                article = (HashMap)request.getAttribute("article");
                title = (ArrayList<String>)request.getAttribute("title");
                list = (ArrayList<HashMap>)request.getAttribute("list");
                if(list==null) list = new ArrayList<HashMap>();
                if(title==null) title = new ArrayList<String>();
                if(article==null) article = new HashMap();
            %>
            <%for(int i=0;i<list.size();i++){%>
                <h3 style="text-align: left;margin-left: 20px"><%=title.get(i)%></h3>
                
                <%for(int j=1;j<=list.get(i).size();j++){%>
                <a href="<%=article.get(list.get(i).get(j))%>" target="_blank"
                   style="text-decoration: none"><%=article.get(list.get(i).get(j))%></a>
                    <br>
                
           <%}}%>
        </div>
    </body>
</html>
