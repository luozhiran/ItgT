# TCP/IP协议族分成管理

应用层

传输层

网络层

数据链路层


为什么分层？ 

分成好处：
     1.假设不分层，用一个协议，假如某个地方需要改变设计，则必须把整个部分全部换掉。
     2.分层设计后，每个层拥有自己独有任务，改变设计也变的简单
     
     
应用层
    
   作用：向用户提供应用服务时通信的活动。实现多个系统应用进程相互通信。主要是提供网络任意端上应用程序之间的接口

   服务：TCP/ID协议族提供的通用服务，FTP,DNS，HTTP

传输层

   作用： 为应用层，提供处于网络连接中的两台计算机之间的数据传输。
   
   服务：TCP,UDP
 
 
 网络层
   
   作用：处理在网络上流动的数据包（数据包网络传输最小数据单位）。规定通过怎样的路径到达对方计算机，并把数据包传给该计算机。
        在复杂局域网内选择一条传输路线。
        
 数据链路层
 
   作用：用来处理连接网络的硬件部分
   
   
 请求报文：
   
        请求头：     : get /from/entry HTTP/1.1
        请求首部字段 : Host : hackr.jp
                       Connection:keep-alive
                       Content-Type:application/x-www-form-urlencoded
         必须空行    :  空行             
         内容实体    :name=ueno&age=37 
  
  
  
  响应报文:
  
       响应头       :HTTP/1.1 200 OK
       响应首部字段 :Date:Tue,10 Jul 2012 06:50:15 GMT
                     Content-Length:362
                     Content-Type:text/html
       必须空行     :  空行             
       主体         : <html>...</html>
       
       
       
       
 #   HTTP是无状态协议
   
   不对请求和响应的通信状态进行保存，客服端请求了什么或者服务器响应了什么都不会有任何记录
   
   
   
 # HTTP 协议使用 URI 定位互联网上的资源。
 
 完整请求URI                               : get http://www.baidu.com/index.html HTTP/1.1
 
 在首部字段Host中写明网络域名或IP地址      : get index.html HTTP/1.1
                                             Host:www.baidu.com  
                                          
 不访问资源，指访问服务器,可以用*代替URI   :get * HTTP/1.1
 
 # GET :获取资源
    GET : 1. 方法用来请求访问已被 URI 识别的资源。
          2. 经服务端解析后返回内容。(如果请求的资源是文本，那就保持原样返回；如果是像CGI那样的程序，则返回经过执行后的输出结果)
          

例子：

     请求：GET /index.html HTTP/1.1
           Host:www.baidu.com
     响应：返回index.html的页面资源
      
     请求： GET /inxex.html HTTP/1.1
            Host:www.baidu.com
            if-Modifid-Since:Thu,12 Jul2012 07:30:00 GMT
     响应：仅返回2012年7月12日7点30分以后更新过的index.html页面资源。
           如果未 有内容更新，则以状态码304 Not Modified作为响应返回
           
           
 # POST：传输实体主体
 
    虽然用 GET 方法也可以传输实体的主体，但一般不用 GET 方法进行 传输，
    而是用 POST 方法。虽说 POST 的功能与 GET 很相似，但 POST 的主要目的并不是获取响应的主体内容
 
 例子：
      
      请求：POST /submit.cgi HTTP/1.1
            Host:www.hackr.jp
            Content-Length : 1560
          
      响应:返回 submit.cgi 接收数据的处理结果
      
      
      
 # PUT：传输文件    
 
     和FTP协议的文件上传一样，在请求报文的主体包含文件内容，然后保存到请求 URI 指定的位置。
     PUT 方法自身不带验证机制，任何人都可以 上传文件，存在安全性问题
  
 例子：
 
     请求：PUT /example.html HTTP/1.1
           Host: www.hackr.jp 
           Content-Type: text/html 
           Content-Length: 1560（1560 字节的数据）
     响应：响应返回状态码 204 No Content（比如 ：该 html 已存在于服务器上）
          
     响应的意思其实是请求执行成功了，但无数据返回。       
    
             
 # HEAD：获得报文首部
 
    HEAD 方法和 GET 方法一样，只是不返回报文主体部分。
    用于确认 URI 的有效性及资源更新的日期时间等。
    
 例子：
     
    请求： HEAD /index.html HTTP/1.1
           Host: www.hackr.jp
    响应： 返回index.html有关的响应首部
    
    
 # DELETE：删除文件
 
    DELETE 方法用来删除文件，是与PUT相反的方法。
    DELETE 方法按请求URI删除指定的资源。
    HTTP/1.1 的 DELETE 方法本身和 PUT 方法一样不带验证机制，所以一般的 Web 网站也不使用 DELETE 方法
    
 例子：
          
    请求： DELETE /index.html HTTP/1.1
           Host: www.hackr.jp
    响应： 响应返回状态码 204 No Content（比如 ：该 html 已从该服务器上删除）
    
    
# OPTIONS：询问支持的方法

    OPTIONS 方法用来查询针对请求，URI指定的资源支持的方法。
             
例子：
  
    请求 ： OPTIONS * HTTP/1.1
            Host: www.hackr.jp
    响应：HTTP/1.1 200 OK
          Allow: GET, POST, HEAD, OPTIONS
 
 （返回服务器支持的方法）
 
 
 # CONNECT：要求用隧道协议连接代理
 
    CONNECT 方法要求在与代理服务器通信时建立隧道,实现用隧道协议进行 CP通信。
    主要使用 SSL和TLS,协议把通信内容加密后经网络隧道传输
    
 格式：CONNECT 代理服务器名:端口号 HTTP版本
 
    请求： CONNECT proxy.hackr.jp:8080 HTTP/1.1
           Host: proxy.hackr.jp
    响应：HTTP/1.1 200 OK（之后进入网络隧道）
    
    
#为什么要用 GET,PUT,POST,DELETE等方法？
   方法的作用在于，可以指定请求资源,按期望产生某种行为。方法中有 GET、POST 和 HEAD 等
   
    翻译：GET:请给我那个资源
          PUT:我发文件过来啦
          HEAD:告诉我通信状态
          
# 持久连接节省通信量
 
 HTTP协议的初始版本中，每进行一次HTTP通信就要断开一次TCP连接。

 建立tcp连接
       client                           SERVICE
                     
        发送-----------SYN--------------》收到
        
        接收《----------ACK,SYN----------返回    
        
        发送------------ACK-------------》接收
        --------建立连接，可以通信------ 
        
        发送------HTTP请求-------------》处理请求  
        
        接收《--------HTTP响应---------处理结果 
        
        接收《--------FIN--------------处理完成
        
        发送---------ACK--------------》接收
        
        发送---------FIN-------------》接收
        
        接收《-------ACK--------------验证
        
        ----------断开TCP连接---------
        
        
  # Cookie
  
  为什么要有Cookie?
  
    HTTP 是无状态协议，它不对之前发生过的请求和响应的状态进行管理。
    也就是说，无法根据之前的状态进行本次的请求处理。
  
  总结： 上一次请求对下一次请求有影响，HTTP无法记录上一次的状态，所以出现Cookie。
  
  Cookie 技术通过在请求和响应报文中写入Cookie信息来控制客户端的状态。
  
  Cookie：服务器端发送的响应报文，其中有一个Set-Cookie的首部字段，
          通知客户端保存 Cookie。当下次客户端再往该服务器 发送请求时，
          客户端会自动在请求报文中加入 Cookie 值后发送出去。
   
 Cookie由服务器生成，保存在浏览器上，下次请求在携带给服务器。
 
 
 例子：
   1.请求报文（没有 Cookie 信息的状态）
     
     GET /reader/ HTTP/1.1
     Host:hack.jp
     
  2. 响应报文（服务器端生成 Cookie 信息）
    
    HTTP/1.1 200 OK
    Date: Thu, 12 Jul 2012 07:12:20 GMT
    Server: Apache
    ＜Set-Cookie: sid=1342077140226724; path=/; expires=Wed, 10-Oct-12 07:12:20 GMT＞
    Content-Type: text/plain; charset=UTF-8
    
  3. 请求报文（自动发送保存着的 Cookie 信息）
     
    GET /image/ HTTP/1.1
    Host: hackr.jp
    Cookie: sid=1342077140226724
    
    
    
# HTTP 报文

请求端（客户端）的 HTTP 报文叫做请求报文。

响应端（服务器端）的叫做响应报文。

HTTP报文本身是由多行（用 CR+LF 作换行符）数据构成的字符串文本。

       CR :回车符
       LF:换行符


HTTP报文：由首部和主体构成，首部和主体通过空行隔开（CR+LF）

         报文首部
         CRLF
         报文主体
         
请求报文首部：
        
        请求行：GET index.html HTTP/1.1
        请求首部字段：
        通用首部字段：
        实体首部字段：
        其他        ：
        
        
响应报文首部：
        
        状态行 ：HTTP/1.1 200 OK
        响应首部字段：
        通用首部字段：
        实体首部字段：
        其他        ：
        
        
 例子：
     
     GET / HTTP/1.1
     Host:www.baid.com
     User-Agent:Mozilla/5.0 (Windows NT 6.1;WOW64;rv:13.0 Gecko/20100101 Firefox/13.0.1)
     Accept;text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
     Accept-Language:ja,en-us;q=0.7;en;q=0.3
     Accept-Encoding:gzip,deflate
     DNT:1
     Connection:keep-alive
     Pragma:no-cache
     Cache-Control:no-cache
     CRLF
     
     
     
    HTTP/1.1 200 OK
    Date:Fri,13 Jul 2012 02:45:26 GMT
    Server:Apache
    Last-Modified:Fri,31 Aug 2007 02:02:20 GMT
    ETag:"45bae1-16a-46d776ac"
    Accept-Ranges:bytes
    Content-length:362
    Connection:close
    Content-Type:text/html
    CRLF
    <html>
      ...
    </html>
   
   
   
 # 发送多种数据的多部分对象集合
 
 发送邮件时，为什么我们可以在邮件里写入文字并添加多份附件？
   
    因为采用了MIME（多用途因特网邮件扩展）机制，它允许邮件处理文本、图片、视频等多个不同类型的数据。
    例如，图片等二进制数据以 ASCII 码字符串编码的方式指明，就是利用MIME来描述标记数据类型。
    
    在 MIME 扩展中会使用一种称为多部分对象集合（Multipart）的方法，来容纳多份不同类型的 数据。
    
    HTTP 协议中也采纳了多部分对象集合，发送的一份报文主体内可含有多类型实体。通常是在图片或文本文件等上传时使用。
    
    
 多部分对象集合包含的对象如下。
 
   * multipart/form-data ：在Web表单文件上传时使用。
   * multipart/byteranges：状态码 206（Partial Content，部分内容）响应报文包含了多个范围的内容时使用。
   
例子：
    multipart/form-data:
    
    Content-Type:multipart/form-data;boundary=AaB03x
    
    --AaB03x
    Content-Disposition:form-data;name="field1"
    CRLF
    Joe Blow
    --AaB03x
    Content-Disposition:form-data;name="pics";fileName="file1.txt"
    Content-Type:text/plain
    CRLF
    (file1.txt数据)
    --AaB03x--
    
    
   multipart/byteranges
   
    HTTP/1.1 206 Partial Content
    Date: Fri, 13 Jul 2012 02:45:26 GMT
    Last-Modified: Fri, 31 Aug 2007 02:02:20 GMT
    Content-Type: multipart/byteranges; boundary=THIS_STRING_SEPARATES
    CRLF
    --THIS_STRING_SEPARATES
    Content-Type: application/pdf
    Content-Range: bytes 500-999/8000
    ...（范围指定的数据）...
    --THIS_STRING_SEPARATES
    Content-Type: application/pdf 
    Content-Range: bytes 7000-7999/8000    
     CRLF
     ...（范围指定的数据）...
     --THIS_STRING_SEPARATES--
 
在 HTTP 报文中使用多部分对象集合时，需要在首部字段里加上 Content-type。
        
        
# 获取部分内容的范围请求 

对一份10 000字节大小的资源，如果使用范围请求，可以只请求 5001~10 000 字节内的资源。  

例子：
    
     GET /t.jpg HTTP/1.1
     Host:www.baidu.com
     Range:bytes=5000-10000
     
     HTTP/1.1 200 OK
     Date: Fri, 13 Jul 2012 02:45:26 GMT
     Content-Range:bytes 5001-10000/10000
     Content-length:5000
     Content-Type:image/jpg
     
     
     
 #状态码告知从服务器端返回的请求结果 
 
   2XX 成功
   
   2XX 的响应结果表明请求被正常处理了。
    
    200 OK
    
    204 No Content  
    请求被服务器处理，但是返回的报文中不含实体部分。
    一种情况，当浏览器发送请求给服务器后，服务器返回204，那么浏览器显示的页面不需要更新
    
    206 Partial Content 
    
    表示请求携带了，Range:bytes = xx-xx,服务器会返回部分内容
    
     304 Not Modified
     请求资源已经找到，但是不符合条件请求
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
                        