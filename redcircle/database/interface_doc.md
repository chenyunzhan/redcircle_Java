#红圏客户端接口文档


##概述

**红圏**，是一款专注于真实性社交APP，其最大的特点在于用户的**可靠性**、**动态性**。只需要一步注册，后面的事全由我们来完成，并且可以精准的给于用户分类。我们没有发明一个东西，只是将最真实的生活体现在**红圈**中。我们宗旨的是：***让您的真实生活更精彩，而不是让你沉迷于虚似世界中***

[用户注册接口](#register)  
[用户登录接口](#login)  
[获取朋友接口](#getFriends)  
[修改用户接口](#modify)


<a name="register"> 用户注册接口</a>：

**请求url:**　　/register  
**请求方式:** 　post


参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
friendArrayMap | Content Cell  | Content Cell
meInfo | Content Cell  | Content Cell
**示例:**
```
{meInfo={me_phone=18706734109}, friendArrayMap=[{verify_code_text=, phone_text=11111111}, {verify_code_text=, phone_text=22222222}]}
```

<a name="login"> 用户登录接口</a>：

**请求url:**　　/login  
**请求方式:** 　post

参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
Content Cell | Content Cell  | Content Cell
Content Cell | Content Cell  | Content Cell

<a name="getFriends"> 获取朋友接口</a>：

**请求url:**　　/getFriends  
**请求方式:** 　get

参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
Content Cell | Content Cell  | Content Cell
Content Cell | Content Cell  | Content Cell

<a name="modify"> 修改用户接口</a>：

**请求url:**　　/modify  
**请求方式:** 　post

参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
Content Cell | Content Cell  | Content Cell
Content Cell | Content Cell  | Content Cell
