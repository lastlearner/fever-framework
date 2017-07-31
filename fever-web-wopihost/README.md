# 安装
1. 安装windows server
```
2012 r2
Datacenter Product Key - JGXYY-7NMTC-MHKY3-QCC9B-VQRG7
Automatic Virtual machine Activation Key - XVNRV-9HTX4-TH2JD-HVJQD-QRQWG
```
2. 安装域控
3. 安装必需的角色和服务
4. 安装office web apps server
5. KB2760445(pdf支持)
6. 安装语言包
7. 修改默认支持大小10MB
```
GET-OfficeWebAppsFarm
Set-OfficeWebAppsFarm -ExcelWorkbookSizeMax 50
```

# 实现
+ token鉴权接口
+ WOPI协议getFileInfo接口
+ WOPI协议getFile接口
+ 编辑接口(待实现，需要.Net 4.5.2+)

# 调用
+ word文档预览
```
  http://[office]/wv/wordviewerframe.aspx?WOPISrc=http://[WopiHost]:8081/wopi/files/test.docx&access_token=accessToken
```

+ excel预览
```
  http://[office]/x/_layouts/xlviewerinternal.aspx?ui=zh-CN&rs=zh-CN&WOPISrc=http://[WopiHost.domain]:8080/wopi/files/test.xlsx&access_token=123
```

+ ppt预览
```
  http://[office]/p/PowerPointFrame.aspx?PowerPointView=ReadingView&WOPISrc=http://[WopiHost.domain]:8080/wopi/files/test.pptx&access_token=123
```

# 参考
[部署 Office Web Apps Server](https://technet.microsoft.com/zh-cn/library/jj219455.aspx)
[Office Web Apps安装部署](http://www.cnblogs.com/poissonnotes/p/3238238.html)
