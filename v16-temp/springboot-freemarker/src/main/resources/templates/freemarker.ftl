<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
hello,${username}
id:${product.id}
name:${product.name}
price:${product.price}

<#--id:${product.creatDate}-->
creatDate:${product.creatDate?date}
creatDate:${product.creatDate?time}
creatDate:${product.creatDate?datetime}

<#--集合-->
<#list productList as product>
    index:${product_index}
    id:${product.id}
    name:${product.name}
    price:${product.price}
    creatDate:${product.creatDate?datetime}
</#list>

<#if (age>50)>
    中年
    <#elseif (age>30)>
    青年
    <#else>
    少年
</#if>

===>${nullObject!}
</body>
</html>