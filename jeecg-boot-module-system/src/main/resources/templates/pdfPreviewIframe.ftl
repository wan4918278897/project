<#assign base=springMacroRequestContext.getContextUrl("")>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>PDF预览</title>
</head>
<body>
<script type="text/javascript">

function openScanFile(title,token,path,archivesNo){
    //http://localhost:8080/jeecg-boot/sys/common/download/files/20190527/yzx_1558937731767.pdf
 	 //var pdfUrl ="http://127.0.0.1:8080/jeecg-boot/generic/web/viewer.html?file="+encodeURIComponent("http://127.0.0.1:8080/jeecg-boot/jeecg-boot-module-system/jeecg-boot-mysql.sql);
 	 var pdfUrl ="${base}/generic/web/viewer.html?file="+encodeURIComponent("http://localhost:8080/jeecg-boot/sys/file/download/files/"+path);

	 var vm=window.open(pdfUrl);
}

 window.addEventListener('message', function(event) {
     var data = event.data;
     var title = data.title;
	 var token = data.token;
     var path = data.path;
     openScanFile(title,token,path);
 }, false);
 
</script>
</body>
</html>
