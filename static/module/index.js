layui.define(['config', 'admin', 'layer'], function (exports) {
    var $ = layui.$;
    var config = layui.config;
    var admin = layui.admin;
    var layer = layui.layer;

    var index = {
        // 渲染左侧导航栏
        initLeftNav: function () {
            var authMenus = new Array();
            for (var i = 0; i < config.menus.length; i++) {
                var tempMenu = config.menus[i];
                var tempSubMenus = new Array();
                for (var j = 0; j < tempMenu.subMenus.length; j++) {
                    var tempSubMenu = tempMenu.subMenus[j];
                    if (!tempSubMenu.auth) {
                        tempSubMenus.push(tempSubMenu);
                    } else if (admin.hasPerm(tempSubMenu.auth)) {
                        tempSubMenus.push(tempSubMenu);
                    }
                }
                if (tempSubMenus.length > 0) {
                    tempMenu.subMenus = tempSubMenus;
                    authMenus.push(tempMenu);
                }
            }
            $('.layui-layout-admin .layui-side').vm({menus: authMenus});
            admin.activeNav(Q.lash);
        },
        // 路由注册
        initRouter: function () {
            index.regRouter(config.menus);
            Q.init({
                index: 'console'
            });
        },
        // 使用递归循环注册
        regRouter: function (menus) {
            $.each(menus, function (i, data) {
                if (data.url) {
                    Q.reg(data.url, function () {
                        admin.loadView('components/' + data.path);
                    });
                }
                if (data.subMenus) {
                    index.regRouter(data.subMenus);
                }
            });
        },
        // 从服务器获取登录用户的信息
        getUser: function (success) {
            layer.load(2);
            admin.req('userInfo', {}, function (data) {
                layer.closeAll('loading');
                if (200 == data.code) {
                    config.putUser(data.user);
                    success(data.user);
                } else {
                    layer.msg('获取用户失败', {icon: 2});
                }
            }, 'GET');
        },
        // 页面元素绑定事件监听
        bindEvent: function () {
            // 退出登录
            $('#btnLogout').click(function () {
                layer.confirm('确定退出登录？', function (i) {
                    layer.close(i);
                    config.removeToken();
                    location.replace('login.html');
                });
            });
            // 修改密码
            $('#setPsw').click(function () {
                admin.popupRight('components/tpl/password.html');
            });
            // 个人信息
            $('#setInfo').click(function () {

            });
            // 消息
            $('#btnMessage').click(function () {
                admin.popupRight('components/tpl/message.html');
            });
        }
    };

    exports('index', index);
});
