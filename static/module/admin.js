layui.define(['config', 'layer', 'element', 'form'], function (exports) {
    var config = layui.config;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;
    var popupRightIndex, popupCenterIndex, popupCenterParam;

    var admin = {
        // 路由加载组件
        loadView: function (path) {
            admin.showLoading('.layui-layout-admin .layui-body');
            $('.layui-layout-admin .layui-body').load(path, function () {
                element.render('breadcrumb');
                form.render('select');
                admin.removeLoading('.layui-layout-admin .layui-body');
            });
            admin.activeNav(Q.lash);
            // 移动设备切换页面隐藏侧导航
            if (document.body.clientWidth <= 750) {
                admin.flexible(true);
            }
        },
        // 设置侧栏折叠
        flexible: function (expand) {
            var isExapnd = $('.layui-layout-admin').hasClass('admin-nav-mini');
            if (isExapnd == !expand) {
                return;
            }
            if (expand) {
                $('.layui-layout-admin').removeClass('admin-nav-mini');
            } else {
                $('.layui-layout-admin').addClass('admin-nav-mini');
            }
            admin.onResize();
        },
        // 设置导航栏选中
        activeNav: function (url) {
            $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item .layui-nav-child dd').removeClass('layui-this');
            if (url && url != '') {
                $('.layui-layout-admin .layui-side .layui-nav .layui-nav-item').removeClass('layui-nav-itemed');
                var $a = $('.layui-layout-admin .layui-side .layui-nav>.layui-nav-item>.layui-nav-child>dd>a[href="#!' + url + '"]');
                $a.parent('dd').addClass('layui-this');
                $a.parent('dd').parent('.layui-nav-child').parent('.layui-nav-item').addClass('layui-nav-itemed');
            }
        },
        // 右侧弹出
        popupRight: function (path) {
            popupRightIndex = layer.open({
                type: 1,
                id: 'adminPopupR',
                anim: 2,
                isOutAnim: false,
                title: false,
                closeBtn: false,
                offset: 'r',
                shade: .2,
                shadeClose: true,
                resize: false,
                area: '336px',
                skin: 'layui-layer-adminRight',
                success: function () {
                    //admin.showLoading('#adminPopupR');
                    $('#adminPopupR').load(path, function () {
                        //admin.removeLoading('#adminPopupR');
                    });
                },
                end: function () {
                    layer.closeAll('tips');
                }
            });
        },
        // 关闭右侧弹出
        closePopupRight: function () {
            layer.close(popupRightIndex);
        },
        // 中间弹出
        popupCenter: function (param) {
            popupCenterParam = param;
            popupCenterIndex = layer.open({
                type: 1,
                id: 'adminPopupC',
                title: param.title ? param.title : false,
                shade: .2,
                offset: '120px',
                area: param.area ? param.area : '450px',
                resize: false,
                skin: 'layui-layer-adminCenter',
                success: function () {
                    $('#adminPopupC').load(param.path, function () {
                        $('#adminPopupC .close').click(function () {
                            layer.close(popupCenterIndex);
                        });
                        param.success ? param.success() : '';
                    });
                },
                end: function () {
                    layer.closeAll('tips');
                    param.end ? param.end() : '';
                }
            });
        },
        // 关闭中间弹出并且触发finish回调
        finishPopupCenter: function () {
            layer.close(popupCenterIndex);
            popupCenterParam.finish ? popupCenterParam.finish() : '';
        },
        // 关闭中间弹出
        closePopupCenter: function () {
            layer.close(popupCenterIndex);
        },
        // 封装ajax请求
        req: function (url, data, success, method) {
            if ('put' == method.toLowerCase()) {
                method = 'POST';
                data._method = 'PUT';
            } else if ('delete' == method.toLowerCase()) {
                method = 'POST';
                data._method = 'DELETE';
            }
            var token = config.getToken();
            if (token) {
                data.access_token = token.access_token;
            }
            $.ajax({
                url: config.base_server + url,
                data: data,
                type: method,
                dataType: 'JSON',
                success: function (data) {
                    success(data);
                },
                error: function (xhr) {
                    console.log(xhr.status + ' - ' + xhr.statusText);
                    if (xhr.status == 401) {
                        config.removeToken();
                        layer.msg('登录过期', {icon: 2}, function () {
                            location.href = '/login.html';
                        });
                    } else {
                        success({code: xhr.status, msg: xhr.statusText});
                    }
                },
                beforeSend: function (xhr) {
                    var token = config.getToken();
                    if (token) {
                        xhr.setRequestHeader('Authorization', 'Basic ' + token.access_token);
                    }
                }
            });
        },
        // 判断是否有权限
        hasPerm: function (auth) {
            var user = config.getUser();
            if (user.authorities) {
                for (var i = 0; i < user.authorities.length; i++) {
                    if (auth == user.authorities[i].authority) {
                        return true;
                    }
                }
            }
            return false;
        },
        // 窗口大小改变监听
        onResize: function () {
            if (config.autoRender) {
                if ($('.layui-table-view').length > 0) {
                    setTimeout(function () {
                        admin.events.refresh();
                    }, 800);
                }
            }
        },
        // 显示加载动画
        showLoading: function (element) {
            $(element).append('<i class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop admin-loading"></i>');
        },
        // 移除加载动画
        removeLoading: function (element) {
            $(element + '>.admin-loading').remove();
        },
        // 缓存临时数据
        putTempData: function (key, value) {
            if (value) {
                layui.sessionData('tempData', {key: key, value: value});
            } else {
                layui.sessionData('tempData', {key: key, remove: true});
            }
        },
        // 获取缓存临时数据
        getTempData: function (key) {
            return layui.sessionData('tempData')[key];
        }
    };

    // ewAdmin提供的事件
    admin.events = {
        flexible: function (e) {  // 折叠侧导航
            var expand = $('.layui-layout-admin').hasClass('admin-nav-mini');
            admin.flexible(expand);
        },
        refresh: function () {  // 刷新主体部分
            Q.refresh();
        },
        back: function () {  //后退
            history.back();
        },
        theme: function () {  // 设置主题
            admin.popupRight('components/tpl/theme.html');
        },
        fullScreen: function (e) {  // 全屏
            var ac = 'layui-icon-screen-full', ic = 'layui-icon-screen-restore';
            var ti = $(this).find('i');

            var isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
            if (isFullscreen) {
                var efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
                if (efs) {
                    efs.call(document);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                ti.addClass(ac).removeClass(ic);
            } else {
                var el = document.documentElement;
                var rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
                if (rfs) {
                    rfs.call(el);
                } else if (window.ActiveXObject) {
                    var ws = new ActiveXObject('WScript.Shell');
                    ws && ws.SendKeys('{F11}');
                }
                ti.addClass(ic).removeClass(ac);
            }
        }
    };

    // 所有ew-event
    $('body').on('click', '*[ew-event]', function () {
        var event = $(this).attr('ew-event');
        var te = admin.events[event];
        te && te.call(this, $(this));
    });

    // 移动设备遮罩层点击事件
    $('.site-mobile-shade').click(function () {
        admin.flexible(true);
    });

    // 侧导航折叠状态下鼠标经过显示提示
    $('body').on('mouseenter', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        var tipText = $(this).find('cite').text();
        if (document.body.clientWidth > 750) {
            layer.tips(tipText, this);
        }
    }).on('mouseleave', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        layer.closeAll('tips');
    });

    // 侧导航折叠状态下点击展开
    $('body').on('click', '.layui-layout-admin.admin-nav-mini .layui-side .layui-nav .layui-nav-item>a', function () {
        if (document.body.clientWidth > 750) {
            layer.closeAll('tips');
            admin.flexible(true);
        }
    });

    // 所有lay-tips处理
    $('body').on('mouseenter', '*[lay-tips]', function () {
        var tipText = $(this).attr('lay-tips');
        var dt = $(this).attr('lay-direction');
        layer.tips(tipText, this, {tips: dt || 1, time: -1});
    }).on('mouseleave', '*[lay-tips]', function () {
        layer.closeAll('tips');
    });

    exports('admin', admin);
});
