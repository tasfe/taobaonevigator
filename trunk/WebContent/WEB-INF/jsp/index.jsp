<%-- 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="promotion" items="${promotions}">
	<tr>
		<td>${promotion.name}</td>
		<td>${promotion.picture}</td>
		<td>${promotion.enabled}</td>
		<td>${promotion.id}</td>
		<td>${promotion.catagory.name}</td>
	</tr><br/>
</c:forEach>
--%>
<%@page import="com.payment.taobaoNavigator.entity.CatagoryEntity"%>
<%@page import="java.util.List"%>
<%@page import="com.payment.taobaoNavigator.util.NavigatorCache"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="static/style.css" />
<link rel="stylesheet" type="text/css" href="static/main.css" />
<title>淘宝购物导航首页</title>
<script type="text/javascript" src="static/jquery.js"></script>
<script type="text/javascript" src="static/js/jquery-easing-1.3.pack.js"></script>
<script type="text/javascript" src="static/js/jquery-easing-compatibility.1.2.pack.js"></script>
<script type="text/javascript" src="static/js/coda-slider.1.1.1.pack.js"></script>
<script style="text/javascript">
$(function(){
	$(".catalog").each(function(){
		$(this).hover(function(){
			$(this).find(".detail").css("display","block");
			$(this).addClass("catalog_current");
		},function(){
			$(this).find(".detail").css("display","none");
			$(this).removeClass("catalog_current");
		});
	});
});
var titles= [1,2,3]
$(function(){
	$("#hotTitle ul li").each(function(){
		$(this).hover(function(item){
			var id = $(this).attr("id");
			var dom = $(this);
			removeClass();
			addBottom();
			$(this).css("border-bottom","none");
			$.each(titles, function(i,item){
				if(item==id){
					dom.find("div").addClass("hotCurrent");
				}
			});	
		},function(item){
			addBottom();
			var id = $(this).attr("id");
			var dom = $(this);
			$(this).css("border-bottom","none");		
		});
	});
	$("#1").css("border-bottom","none");
});

function removeClass(){
	$("#hotTitle ul li").each(function(){
		$(this).find("div").removeClass("hotCurrent");
	});
}
function addBottom(){
	$("#hotTitle ul li").each(function(){
		$(this).css("border-bottom","#acd1eb 1px solid");	
	});
}

</script>
<script type="text/javascript">
		var theInt = null;
		var $crosslink, $navthumb;
		var curclicked = 0;
		
		theInterval = function(cur){
			clearInterval(theInt);
			
			if( typeof cur != 'undefined' )
				curclicked = cur;
			
			$crosslink.removeClass("active-thumb");
			$navthumb.eq(curclicked).parent().addClass("active-thumb");
				$(".stripNav ul li a").eq(curclicked).trigger('click');
			
			theInt = setInterval(function(){
				$crosslink.removeClass("active-thumb");
				$navthumb.eq(curclicked).parent().addClass("active-thumb");
				$(".stripNav ul li a").eq(curclicked).trigger('click');
				curclicked++;
				if( 6 == curclicked )
					curclicked = 0;
				
			}, 5000);
		};
		
		$(function(){
			
			$("#main-photo-slider").codaSlider();
			
			$navthumb = $(".nav-thumb");
			$crosslink = $(".cross-link");
			
			$navthumb
			.click(function() {
				var $this = $(this);
				theInterval($this.parent().attr('href').slice(1) - 1);
				return false;
			});
			
			theInterval();
		});
	</script>
</head>
<body>
<div id="body">
	<div id="header">
		<div class="header_title">淘宝导航系统首页</div>
		<div id="navigator">
			<ul>
				<li id="first"><a href="#" class="current">图书首页</a>
				</li>
				<li><a href="#">分类列表</a>
					<div class="list">
						<%
							List<CatagoryEntity> catagoryEntitys = NavigatorCache.getByParentId(1);
							for(CatagoryEntity entity : catagoryEntitys){
								%>
						<div class="list_content">
							<span id="list_title">『<%=entity.getName() %>』</span>
							<%
								List<CatagoryEntity> children = NavigatorCache.getByParentId(entity.getId());
								for(int i = 0 ; i < children.size() ; i++){
									%>
							<span id="list_detail">
								<%=children.get(i).getName() %>
							</span>
									<%
								}
							%>
						</div>
								<%
							}
						%>
						<div class="list_content">
							<span id="list_title">『少儿读物』</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
						</div>
						<div class="list_content">
							<span id="list_title">『少儿读物』</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
							<span id="list_detail">
								少儿读物
							</span>
						</div>
					</div>
				</li>
				<li><a href="#">特卖专区</a>
					<div class="list">
						这里显示特卖专区
					</div>
				</li>
			</ul>
		</div>
	</div>
	<div id="content">
		<div id="leftBar">
			<div class="catalog">
				<span class="catalog_title"><a href="#">少儿读物<a></span><br>
				<span>
					<a href="#">幼儿教育</a> 
					<a href="#">小学一年级</a>
					>>
				</span>
				<div class="detail">
					<div>
						<a href="#">小学一年级</a>
						<a href="#">小学一年级</a>
						<a href="#">小学一年级</a>
						<a href="#">小学一年级</a>
						<a href="#">小学一年级</a>
					</div>
				</div>
			</div>
			<div class="catalog">
				<span class="catalog_title"><a href="#">少儿读物<a></span><br>
				<span>
					<a href="#">幼儿教育</a> 
					<a href="#">小学一年级</a>
					>>
				</span>
				<div class="detail">这里是详细信息</div>
			</div>
			<div class="catalog">
				<span class="catalog_title"><a href="#">特卖专区<a></span><br>
				<span>
					<a href="#">幼儿教育</a> 
					<a href="#">小学一年级</a>
					>>
				</span>
				<div class="detail">这里是详细信息</div>
			</div>
			<!-- 这里开始是展示侧栏的导航 -->
			<%
				for(CatagoryEntity entity : catagoryEntitys){
					%>
			<div class="catalog">
				<span class="catalog_title"><a href="#"><%=entity.getName() %><a></span><br>
				<span>
					<%
						List<CatagoryEntity> children = NavigatorCache.getByParentId(entity.getId());
						for(int i = 0 ; i < 2 ; i++){
							%>
							<a href="#"><%=children.get(i).getName() %></a> 
							<%
						}
					%>
				</span>
				<div class="detail">
					<div>
					<%
					for(int i = 2 ; i < children.size() ; i++){
						%>
						<a href="#"><%=children.get(i).getName() %></a> 						
						<%
						if((i+4)%5==0){
							out.print("<br><br>");
						}
					}
					%>
					</div>
				</div>
			</div>
					<%
				}
			%>
			
		</div>
		
		
		<div id="pov">
			
			
			
			<div class="slider-wrap">
				<div id="main-photo-slider" class="csw">
					<div class="panelContainer">

						<div class="panel" title="Panel 1">
							<div class="wrapper">
								<img src="static/images/tempphoto-1.jpg" alt="temp" />
								<div class="photo-meta-data">
									<a href="#">点击查看详细</a><br />
									<span>这里显示几个简单的说明</span>
								</div>
							</div>
						</div>
						<div class="panel" title="Panel 2">
							<div class="wrapper">
								<img src="static/images/tempphoto-2.jpg" alt="temp" />
								<div class="photo-meta-data">
									<a href="#">点击查看详细</a><br />
									<span>这里显示几个简单的说明</span>
								</div>
							</div>
						</div>		
						<div class="panel" title="Panel 3">
							<div class="wrapper">
								<img src="static/images/scotch-egg.jpg" alt="scotch egg" class="floatLeft"/>
								<div class="photo-meta-data">
									<a href="#">点击查看详细</a><br />
									<span>这里显示几个简单的说明</span>
								</div>
							</div>
						</div>
						<div class="panel" title="Panel 4">
							<div class="wrapper">
								<img src="static/images/tempphoto-4.jpg" alt="temp" />
								<div class="photo-meta-data">
									<a href="#">点击查看详细</a><br />
									<span>这里显示几个简单的说明</span>
								</div>
							</div>
						</div>
						<div class="panel" title="Panel 5">
							<div class="wrapper">
								<img src="static/images/tempphoto-5.jpg" alt="temp" />
								<div class="photo-meta-data">
									<a href="#">点击查看详细</a><br />
									<span>这里显示几个简单的说明</span>
								</div>
							</div>
						</div>
						<div class="panel" title="Panel 6">
							<div class="wrapper">
								<img src="static/images/6.jpg" alt="temp" />
								<div class="photo-meta-data">
									<a href="#">点击查看详细</a><br />
									<span>这里显示几个简单的说明</span>
								</div>			
							</div>
						</div>

					</div>
				</div>

				<a href="#1" class="cross-link active-thumb"><img src="static/images/tempphoto-1thumb.jpg" class="nav-thumb" alt="temp-thumb" /></a>
				<div id="movers-row">
					<div><a href="#2" class="cross-link"><img src="static/images/tempphoto-2thumb.jpg" class="nav-thumb" alt="temp-thumb" /></a></div>
					<div><a href="#3" class="cross-link"><img src="static/images/tempphoto-3thumb.jpg" class="nav-thumb" alt="temp-thumb" /></a></div>
					<div><a href="#4" class="cross-link"><img src="static/images/tempphoto-4thumb.jpg" class="nav-thumb" alt="temp-thumb" /></a></div>
					<div><a href="#5" class="cross-link"><img src="static/images/tempphoto-5thumb.jpg" class="nav-thumb" alt="temp-thumb" /></a></div>
					<div><a href="#6" class="cross-link"><img src="static/images/tempphoto-6thumb.jpg" class="nav-thumb" alt="temp-thumb" /></a></div>
				</div>
			</div>
			
			
			
		</div>
		<div id="hotSale">
			<span>新品推荐</span>
			<div id="hotTitle">
				<ul>
					<li id="1" class="hotCurrent">最新
						<div class="hotContent hotCurrent">
							这里展示的是最新的产品信息
							<c:forEach var="latestProduct" items="${latestProducts}">
									<div class="latest_title">『<a href="#">${latestProduct.name}</a></div> 
									<div class="latest_content">』${latestProduct.description} </div><br>
							</c:forEach>
						</div>
					</li>
					<li id="2">热卖
						<div class="hotContent" style="margin-left:-70px;">
							这里展示的是最热卖的产品信息
							<c:forEach var="hottestProduct" items="${hottestProducts}">
									<div class="latest_title">『<a href="#">${hottestProduct.name}</a></div> 
									<div class="latest_content">』${hottestProduct.description} </div><br>
							</c:forEach>
						</div>
					</li>
					<li id="3">排行榜
						<div class="hotContent" style="margin-left:-140px;">
							这里是排行榜
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div id="categoryContainer">
			<div id="category">
				<span class="category_title">『少儿读物』</span>
				<span class="category_title_detail">热搜关键词：排球、教案、谐音笑话、歇后语、大全运动会、加油稿、六年级上册语文第四单元</span>
				<div id="category_detail">
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>
					<div style="background:inherit">
						本处罗列出了少儿读物中的一些细节
					</div>
				</div>
			</div>
			<div id="category">
				<span class="category_title">『少儿读物』</span>
				<span class="category_title_detail">热搜关键词：排球、教案、谐音笑话、歇后语、大全运动会、加油稿、六年级上册语文第四单元</span>
				<div id="category_detail">
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>
					<div style="background:inherit">
						本处罗列出了少儿读物中的一些细节
					</div>
				</div>
			</div>
			<div id="category">
				<span class="category_title">『少儿读物』</span>
				<span class="category_title_detail">热搜关键词：排球、教案、谐音笑话、歇后语、大全运动会、加油稿、六年级上册语文第四单元</span>
				<div id="category_detail">
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>	
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>
					<div>
						<img src="static/images/76094b36843076a9a2cc2b77.jpg"/><br>
						<span ><a href="#" style="display:block;color:black;padding-top:3px;">小学教材</a></span>
					</div>
					<div style="background:inherit">
						本处罗列出了少儿读物中的一些细节
					</div>
				</div>
			</div>
	</div>
	<div id="foot">
		Copyright : Grom@gmail.com
	</div>
</div>
</body>
</html>