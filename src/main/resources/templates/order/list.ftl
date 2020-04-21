<html>
<head>
    <#include "../common/header.ftl">
</head>
<body>
<div id="wrapper" class="toggled">
    <#--侧边栏-->
    <#include "../common/nav.ftl">
    <#--主要内容区域-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <!--表格-->
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单ID</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.buyerName}</td>
                                <td>${orderDTO.buyerPhone}</td>
                                <td>${orderDTO.buyerAddress}</td>
                                <td>${orderDTO.orderAmount}</td>
                                <td>${orderDTO.getOrderStatusEnum().msg}</td>
                                <td>${orderDTO.getPayStatusEnum().msg}</td>
                                <td>${orderDTO.createTime}</td>
                                <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                                <td>
                                    <#if orderDTO.getOrderStatusEnum().msg == "新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <!--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                        <#--如果当前页为第一页-->
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                        </#if>
                        <#list 1..orderDTOPage.getTotalPages() as index>
                        <#--当前页无法点击-->
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li class="disabled"><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var webSocket = null;
    if("WebSocket" in window){
        //浏览器支持WebSocket
        webSocket = new WebSocket("ws://3ymscs.natappfree.cc/sell/websocket");
    }else {
        alert("该浏览器不支持WebSocket");
    }

    //webSocket建立连接的事件
    webSocket.onopen = function () {
        console.log("建立连接")
    }

    webSocket.onclose = function () {
        console.log("关闭连接")
    }

    //收到信息的事件
    webSocket.onmessage = function (event) {
        console.log("收到消息"+event.data);
        //弹窗提醒

        //播放音乐
    }

    webSocket.onerror = function () {
        console.log("WebSocket通信发生错误")
    }

    //关闭页面之前要关闭WebSocket
    window.onbeforeunload = function (ev) {
        webSocket.close();
    }
</script>
</body>
</html>