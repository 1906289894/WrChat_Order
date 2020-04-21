<html>
<head>
    <#include "../common/header.ftl">
</head>
<body>
<div id="wrapper" class="toggled">
    <#--侧边栏-->
    <#include "../common/nav.ftl">
    <#--主要内容区域-->
    <div class="container-fluid">
        <div class="row clearfix">
            <#--订单表格-->
            <div class="col-md-4 column">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>订单ID</th>
                        <th>订单总金额</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.orderAmount}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!--订单详情表格-->
            <div class="col-md-12 column">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>商品ID</th>
                        <th>商品名称</th>
                        <th>价格</th>
                        <th>数量</th>
                        <th>总额</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orderDTO.orderDetailList as orderDetail>
                        <tr>
                            <td>${orderDetail.productId}</td>
                            <td>${orderDetail.productName}</td>
                            <td>${orderDetail.productPrice}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <#--按钮-->
            <#if orderDTO.getOrderStatusEnum().msg == "新订单">
                <div class="col-md-12 column">
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" class="btn btn-default btn-primary">完结订单</a>
                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" class="btn btn-default btn-danger">取消订单</a>
                </div>
            </#if>
        </div>
    </div>
</div>
</body>
</html>