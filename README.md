# mini-program-aggregator

## 简介

> Java前后端分离模式的管理系统开发平台+微信小程序。

<br>

* 演示地址：[](暂无)
* 演示账号：  密码：

## 使用技术

### 后端
描述 | 框架 
:---|:---
核心框架 | Spring、Spring Boot、Spring MVC
持久层 | MyBatis、MyBatis-Plus、Druid
权限框架 | Spring Security、Security-OAuth2

### 前端
描述 | 框架 
:---|:---
核心框架 | [Layui](http://www.layui.com/)、[jQuery](http://jquery.cuishifeng.cn/)
路由框架 | [Q.js](https://github.com/itorr/q.js) (纯js轻量级路由框架)
mvvm框架 | [pandyle.js](https://gitee.com/pandarrr/pandyle) (专为jquery编写的mvvm)
主要特色 | 单页面 / 响应式 / 简约 / 极易上手

> 开发工具为IDEA，数据库文件存放在项目的`src/main/resources/sql`目录下。


## 导入项目

### 后台导入和部署
1. 开启redis(oauth2需要redis)、导入数据库
2. 确认application.properties配置信息是否正确
3. 在通过IDEA启动运行

### 前端导入和部署
#### 分离部署
1. 把前端页面放在`nginx`服务器的`html`文件夹下面
2. 修改`nginx/conf/nginx.conf`配置文件，设置代理以解决跨域问题
    ```
    http {
        server {
            # 加入以下配置，之前的配置全部不要动，这个location是新加入的
            location /api/ {
                proxy_pass  http://www.baidu.com/; # 这个是后台接口所在的地址
            }
        }
    }
    ```
3. 修改`module/config.js`里面的`base_url`为`http://localhost:80/api/`，80是ngix的端口，localhost是ngix所在服务器的ip

> 前后端分离应该采用分离部署的方式，后台支持跨域资源共享。

## 项目结构

### 后台接构
```text
|-mini-program-aggregators
   |
   |
   |-mini-program-commons     //通用包：引入redis、rabbitmq等
   |
   |
   |
   |-mini-program-model       //通用实体对象包
   |
   |
   |
   |
   |-mini-program-server      //服务包
      |-java
      |              
      |-resources
            |-mapper     // mapper文件
            |    |-system
            |
            |-application.properties  // 配置文件
```


### 前端结构
```text
|-assets
|     |-css                     // 样式
|     |-images                  // 图片
|     |-libs                    // 第三方库
|
|-components            // html组件
|     |-system                  // 系统管理页面
|     |-xxxxxx                  // 其他业务页面
|     |-tpl                     // 公用组件
|     |     |-message.html                 // 消息
|     |-console.html            // 主页一
|     |-header.html             // 头部
|     |-side.html               // 侧导航
|
|-module                // js模块 (使用layui的模块开发方式)
|     |-admin.js                // admin模块
|     |-config.js                // config模块
|     |-index.js                // index模块
|
|-index.html            // 主界面
|-login.html            // 登陆界面
```


### 快速上手
#### 后台快速上手

**如何添加自己的业务代码：**



#### 前端快速上手
&emsp;&emsp;前端页面详细开发文档：[https://whvse.gitee.io/easywebpage/docs/](https://whvse.gitee.io/easywebpage/docs/)


---

## 相关学习资料

- [Spring Boot 从入门到进阶系列教程](http://www.spring4all.com/article/246)

- [Spring Security 从入门到进阶系列教程](http://www.spring4all.com/article/428)
- [理解OAuth 2.0 - 阮一峰](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
- [基于Token的WEB后台认证机制](https://www.cnblogs.com/xiekeli/p/5607107.html)
- [Redis - 菜鸟教程](http://www.runoob.com/redis/redis-tutorial.html)
- [RESTful API 设计指南](http://www.ruanyifeng.com/blog/2014/05/restful_api.html)
- [使用Swagger2构建RESTful API](http://www.spring4all.com/article/251)
- [Swagger2 - 注解详细说明](http://www.spring4all.com/article/251)
- [SpringBoot - jar包 Linux后台启动](https://www.jianshu.com/p/5887e524de89)
- [IDEA SpringBoot 热部署+html修改自动刷新](https://my.oschina.net/yejunxi/blog/845752)
- [SpringBoot 多模块项目实践（附打包方法）](https://www.jianshu.com/p/59ceea4f029d)
- [markdown基本语法](https://www.jianshu.com/p/191d1e21f7ed)

 :smirk: 学如逆水行舟，不进则退~~~

---