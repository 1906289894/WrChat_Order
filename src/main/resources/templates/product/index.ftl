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
            <div class="col-md-12 column">
                <form role="form" method="post" action="/sell/seller/product/save">
                    <input type="hidden" name="productId" value="${(productInfo.productId)!''}">
                    <div class="form-group">
                        <label>名称</label>
                        <input type="text" class="form-control" name="productName" value="${(productInfo.productName)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>价格</label>
                        <input type="text" class="form-control" name="productPrice" value="${(productInfo.productPrice)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>库存</label>
                        <input type="number" class="form-control" name="productStock" value="${(productInfo.productStock)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>描述</label>
                        <input type="text" class="form-control" name="productDescription" value="${(productInfo.productDescription)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>图片</label>
                        <img src="${(productInfo.productIcon)!''}" height="100" width="100">
                        <input type="text" class="form-control" name="productIcon" value="${(productInfo.productIcon)!''}"/>
                    </div>
                    <div class="form-group">
                        <label>类目</label>
                        <select name="categoryType" class="form-control">
                            <#list categoryList as category>
                                <option value="${category.categoryType}"
                                        <#--如果是商品修改页面，则必须是特定类目处于选中状态
                                            判断条件：productInfo.categoryType存在，并且与category.categoryType相等才选中
                                        -->
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>
                                >${category.categoryName}</option>
                            </#list>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>