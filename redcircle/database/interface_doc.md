#红圏客户端接口文档


##概述

**红圏**，是一款专注于真实性社交APP，其最大的特点在于用户的**可靠性**、**动态性**。只需要一步注册，后面的事全由我们来完成，并且可以精准的给于用户分类。我们没有发明一个东西，只是将最真实的生活体现在**红圈**中。我们宗旨的是：***让您的真实生活更精彩，而不是让你沉迷于虚似世界中***，***让您真正的驾叹互联网，而不是被互联主宰***

[接口整体说明](#summary)
[用户注册接口](#register)  
[用户登录接口](#login)  
[获取朋友接口](#getFriends)  
[修改用户接口](#modify)


<a name="summary">接口整体说明</a>
* 数据库名: redcircle  用户名: root  密码:chenyunzhan
* 数据库脚本参见同目录sql文件
* 启动服务: java -jar redcircle-0.0.1-SNAPSHOT.war
* 接口基础地址: "http://localhost:8080"
* 无论是get还是post传递的参数都是一个map类型的数据


<a name="register"> 用户注册接口</a>：

**请求url:**　　/register  
**请求方式:** 　post


参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
friendArrayMap | map数组  | 该用户的朋友的数组
meInfo | map对象  | 该用户对象，对象只包含一个me_phone属性
**示例:**
```
{meInfo={me_phone=18706734109}, friendArrayMap=[{verify_code_text=, phone_text=11111111}, {verify_code_text=, phone_text=22222222}]}
```

<a name="login"> 用户登录接口</a>：

**请求url:**　　/login  
**请求方式:** 　post

参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
mePhone | 字符串  | 该用户对象，对象只包含一个mePhone属性
**示例**
```
{mePhone=18706734109}
```
<a name="getFriends"> 获取朋友接口</a>：

**请求url:**　　/getFriends  
**请求方式:** 　get

参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
mePhone | 字符串  | 该用户对象，对象只包含一个mePhone属性
**示例**
```json
[
    {
        "ffriend": [],
        "friend": {
            "mePhone": "18706734109",
            "friendPhone": "111",
            "sex": "",
            "name": "陈云展"
        }
    },
    {
        "ffriend": [
            {
                "mePhone": "18706734109",
                "friendPhone": null,
                "sex": null,
                "name": "陈云展"
            },
            {
                "mePhone": "111",
                "friendPhone": null,
                "sex": null,
                "name": ""
            },
            {
                "mePhone": "222",
                "friendPhone": null,
                "sex": null,
                "name": ""
            }
        ],
        "friend": {
            "mePhone": "18706734109",
            "friendPhone": "18706734109",
            "sex": "",
            "name": "陈云展"
        }
    },
    {
        "ffriend": [],
        "friend": {
            "mePhone": "18706734109",
            "friendPhone": "222",
            "sex": "",
            "name": "陈云展"
        }
    }
]
```

<a name="modify"> 修改用户接口</a>：

**请求url:**　　/modify  
**请求方式:** 　post

参数名 | 参数类型 | 参数说明
------------ | ------------- | ------------
mePhone | 字符串  | 必传，用户手机号
sex | 字符串  | 可选参数，修改性别时传
name | 字符串  | 可选参数，修改姓名时传
**示例**
```
{name=New name, mePhone=18706734109}
```
