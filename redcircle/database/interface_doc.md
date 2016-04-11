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
{
	&quot;id&quot;: &quot;ID&quot;,
	&quot;icon&quot;: &quot;图标，FontAwesome图标编号或Webplat内图标ID&quot;,
	&quot;iconclor&quot;: &quot;图标颜色，不是预制颜色值的话就是HTML RGB颜色值&quot;,
	&quot;title&quot;: &quot;名称&quot;,
	&quot;desc&quot;: &quot;描述&quot;,
	&quot;type&quot;: &quot;tap/one&quot;,
	&quot;badgtype&quot;: &quot;badge标示类型：badge表示红圈数字，需要被求和显示在上层菜单和App图标上；num表示在标题后面显示数字；red表示小红点；new表示加new斜幅；hot表示加hot斜幅；none表示无&quot;,
	&quot;badge&quot;: &quot;badge标示数字，小红点时1/0代表是否显示&quot;,
	&quot;url&quot;: &quot;功能链接，内置功能或服务器功能接口URL&quot;
	&quot;method&quot;: &quot;url的请求方法：get/post/delete/put等HTTP动词&quot;,
	&quot;params&quot;: {&quot;传入参数名1&quot;:&quot;取值字段1&quot;, &quot;传入参数名2&quot;:&quot;取值字段2&quot;},
	&quot;present&quot;: &quot;界面视图出现方式：1当前Navigator栈 2新建Navigator栈&quot;
}
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
