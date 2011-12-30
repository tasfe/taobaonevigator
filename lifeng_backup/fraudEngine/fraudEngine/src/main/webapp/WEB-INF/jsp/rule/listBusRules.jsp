<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title><spring:message code="rule.listBus.title"/></title>



	<script>
		$(function() {

			$.extend($.jgrid.defaults, {
				datatype : 'xml',
				prmNames : {
					rows : 'max',
					search : null
				},
				height : 300,
				viewrecords : true,
				rowList : [ 10, 20, 50, 100 ],
				altRows : true,
				loadError : function(xhr, status, error) {
					alert(error);
				}
			});

			$.extend($.jgrid.edit, {
				closeAfterEdit : true,
				closeAfterAdd : true,
				ajaxEditOptions : {
					contentType : "application/json"
				},
				mtype : 'PUT',
				serializeEditData : function(data) {
					delete data.oper;
					return JSON.stringify(data);
				}
			});
			$.extend($.jgrid.del, {
				mtype : 'DELETE',
				serializeDelData : function() {
					alert('delete');
					return "";
				}
			});

			var editOptions = {
				onclickSubmit : function(params, postdata) {
					params.url = URL + '/' + postdata.id;
				}
			};
			var addOptions = {
				mtype : "POST"
			};
			var delOptions = {
				onclickSubmit : function(params, postdata) {
					params.url = URL + '/' + postdata;
				}
			};

			var URL = 'listBusRule.html';
			var options = {
				url : URL,
				editurl : URL,
				colModel : [ {
					name : 'busRulePk',
					label : 'Rule No.',
					width : 50,
					align : 'center',
					formatter: editcursor,
					unformat:unformatCursor,
					editable : true,
					editoptions : {
						disabled : true,
						size : 5
					}
				}, {
					name : 'ruleDescription',
					label : 'Rule Description',
					width : 400,
					editable : true,
					editrules : {
						required : true
					}
				}, {
					name : 'ruleFileName',
					label : 'Rule File Name',
					width : 200,
					hidden : true,
					editable : true,
					editrules : {
						required : true
					}
				}, {
					name : 'priority',
					label : 'Priority',
					width : 80,
					align : 'center',
					hidden : false,
					editable : true

				}, {
					name : 'startDate',
					label : 'Start Date',
					formatter : 'text',
					width : 150,
					align : 'center',
					editable : true,
					edittype : 'text',
					editoptions : {
						value : "true:false"
					}
				}, {
					name : 'endDate',
					label : 'End Date',
					width : 150,
					align : 'center',
					editable : true,
					edittype : 'text',
					editrules : {
						edithidden : true
					}
				} ,{
					name : 'hit',
					label : 'Rule Hit',
					width : 80,
					align : 'center',
					hidden : false,
					editable : true

				}],
				sortname : 'busRulePk',
				sortable: true,
				caption : "Business Rules",
				pager : '#pager',
				sortable : true,
				sortorder : 'asc',
				height : 300,
				rowList : [ 1, 3, 5, 10, 20 ],
				rowNum : 10,
				multiselect : true,
				viewrecords : true,
				subGrid : true,
				subGridRowExpanded : function(subgrid_id, row_id) {
					var rowData = jQuery("#grid").getRowData(row_id);
					var rulePk = unformatCursor(rowData.busRulePk,null);
					var subgrid_table_id;
					subgrid_table_id = subgrid_id + "_t";
					var subgrid_pager_id;
					subgrid_pager_id = subgrid_id + "_pgr";
					$("#" + subgrid_id)
							.html(
									"<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+subgrid_pager_id+"' class='scroll'></div>");
					/*var proData = [
					                {id:"1",property:"Order Threshold"},
					                {id:"2",property:"Order Threshold2"}
					              ];
					for(var i=0;i<=proData.length;i++)
					jQuery("#" + subgrid_table_id).jqGrid('addRowData',i+1,proData[i]);*/
					$("#" + subgrid_table_id).jqGrid(
							{
								url : "fetchProperty.html?id=" + rulePk,
								datatype : "xml",
								colNames : [ 'id', 'property' ],
								colModel : [ {
									name : "id",
									index : "id",
									width : 80,
									key : true,
									editrules : {
										edithidden : true
									},
									editoptions : {
										value : "true:false"
									}
								}, {
									name : "property",
									index : "property",
									width : 130,
									editable : true
								}, ],
								xmlReader : {
									root : "rows",
									row : "row",
									records : "records",
									total : "total",
									cell : "cell"
								},
								prmNames : {
									search : "search"
								},
								//pager: subgrid_pager_id,   
								viewrecords : true,
								ondblClickRow : function(id) {
									jQuery(this).jqGrid('editGridRow', id,
											editOptions);
								},
								width : "100%",
								height:'auto'
							//height: "100%",   
							//rowNum: 10   
							});
				}
			};
			$("#d1,#d2").click(
					function() {
						var sNos = jQuery("#grid").getGridParam('selarrrow');
						var warn = document.getElementById("warn").value;						
						if (sNos == null || sNos.length == 0) {
							alert(warn);
							return;
						}
						var confirm = document.getElementById("confirm").value;
						alert(confirm);
						if(!confirm(confirm))return;
						var rulePk = "";
						for ( var i = 0; i < sNos.length; i++) {
							var rowData = jQuery("#grid").getRowData(sNos[i]);
							rulePk += unformatCursor(rowData.busRulePk,null) + ",";
						}
						deleteActionToSpring("deleteBusRules.html", "?rulePKs=",
								rulePk + '');
					});

			$("#grid").jqGrid(options).navGrid('#pager', {
				edit : true,
				add : false,
				del : false,
				search : false
			});
			$("input:button").button();
		});
function deleteActionToSpring(url,param,data){
            location.href = url+param+data;
}


function initMsg(){
	    var a = "<%=request.getAttribute("message") == null ? "" : request
				.getAttribute("message")%>";
		var initMsg = document.getElementById("initMsg").value;		
		if(a=="")return;
		document.getElementById('message').innerHTML = initMsg;
		setTimeout("cleanMsg()", 5000);
}

function cleanMsg(){
        document.getElementById('message').innerHTML = "";
}

function edit(rulePk){
	var ret = window.showModalDialog(
				"updateBusRule.html?rulePKs="+rulePk,
				"Edit Rule",
				"dialogHeight: 650px; dialogWidth: 550px;"+ 
				"help: No; resizable: Yes; status: Yes;");
	if(ret=='true')document.location.reload();
}

function editcursor(cellvalue, options, rowObject){
	var new_formatted_cellvalue = '<a onclick="edit('+cellvalue+');"><span style="cursor: pointer; text-decoration: underline;">' + cellvalue + '</span></a>'; 
	return new_formatted_cellvalue; 
}

function unformatCursor(cellvalue, options){
	return cellvalue.substring(cellvalue.indexOf("(")+1,cellvalue.indexOf(")"));
}

</script>
<style>
          h1{
                          font-family:"arial",Georgia,Serif;
                          color: #CCCCCC;
          }
          #d1,#d2 {
                          font-family:"arial",Georgia,Serif;
                          font-size:65%;
          }
</style>
</head>
<body onload="initMsg();">
<h1><spring:message code="rule.listBus.theme"/></h1>
<input type="hidden"  id="warn" value="<spring:message code="rule.listBus.warn"/>"  />
<input type="hidden"  id="confirm" value="<spring:message code="rule.listBus.confirm"/>"  />
<input type="hidden"  id="initMsg" value="<spring:message code="rule.listBus.initMsg"/>"  />
	<table>
		<tr>
			<td>
				<input type="button" name="DELETE" id="d1" value="<spring:message code="rule.listBus.delete"/>" />
			</td>
			<td>
				<div id='message' style="color:green;font-size:16px;" align="right"></div>
			</td>
		</tr>
	</table>
	<table id="grid"></table>
	<div id="pager"></div>
	<table>
		<tr>
			<td>
				<input type="button" name="DELETE" id="d2" value="<spring:message code="rule.listBus.delete"/>" />
			</td>
			<td>
			</td>
		</tr>
	</table>

</body>
</html>
