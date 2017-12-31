/* Copyright (C) 2017
 * This file is part of the FAQ System.
 *
 * filename : default
 * function :
 * action   :
 * version  : 7.1
 * author   : Marissa
 * date     : 2017-12-07
 * modify   : 此文件如需修改,请联系Marissa
 */

(function () {
    // 私有变量
    var TITLE = 'FAQ',
        PRECISION_QTY = 0,
        PRECISION_MONEY = 2,
        API_URL = 'https://127.0.0.1/FAQ',
        IMAGE_URL_PREFIX = 'https://127.0.0.1/FAQ',
        API_VERSION = 100,
        VERSION = 0.1;

    // 私有方法
    function setCookie(key, value) {
        var expires = new Date();
        expires.setTime(expires.getTime() + (7 * 24 * 60 * 60 * 1000));
        document.cookie = key + '=' + value + ';expires=' + expires.toUTCString();
    }

    function getCookie(key) {
        var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
        return keyValue ? keyValue[2] : null;
    }

    function removeCookie(key) {
        var expires = new Date();
        expires.setTime(expires.getTime() - (7 * 24 * 60 * 60 * 1000));
        var current = getCookie(key);
        if (current != null) {
            document.cookie = key + '= undefined;expires=' + expires.toUTCString();
        }
    }

    //
    var epm = {};

    // 变量
    epm.v = {
        get title() {
            return TITLE;
        },
        get imageURLPrefix() {
            return IMAGE_URL_PREFIX;
        },
        get version() {
            return VERSION;
        },
        get api_url() {
            return API_URL;
        }
    };

    // 配置项
    epm.c = {
        set userToken(value) {
            epm.setLocalItem(epm.k.USER_TOKEN, value);
        },
        get userToken() {
            return epm.getLocalItem(epm.k.USER_TOKEN);
        }
    };

    // 键
    epm.k = {
        SEARCH_KEYWORDS: 'SEARCH_KEYWORDS',
        USER_TOKEN: 'USER_TOKEN',
        VERSION: 'VERSION'
    };

    // 业务相关
    epm.b = {
        checkVersion: function () {
            var bool_version = false;
            var curr_version = epm.getLocalItem(epm.k.VERSION);
            if (!curr_version) {
                bool_version = true;
            } else if (curr_version != epm.v.version) {
                bool_version = true;
            }
            if (bool_version) {
                epm.removeLocalItem(epm.k.VERSION);
                epm.setLocalItem(epm.k.VERSION, epm.v.version);
            }
        },

        isLogin: function () {
            var user_token = epm.getLocalItem(epm.k.USER_TOKEN);
            return !epm.isEmpty(user_token);
        },
        logOut: function () {
            var params = {};
            params['action'] = 'clear_session';
            epm.ajax(params, function (data) {
                epm.removeLocalItem(epm.k.USER_TOKEN);
                window.location.href = 'index.html';
            });
        },

        addSearchKeywords: function (value, type) {
            var item;

            var keys = epm.getLocalItem(epm.k.SEARCH_KEYWORDS);
            if (!keys) {
                keys = [];
            } else {
                keys = JSON.parse(keys);
            }

            var isExist = false;
            $.each(keys, function (i, v) {
                if (v['text'] == value) {
                    isExist = true;
                    v['times'] = parseInt(v['times']) + 1;
                    return false;
                }
            });
            if (!isExist) {
                item = {};
                item['text'] = value;
                item['times'] = 1;
                item['type'] = type;
                keys.push(item);
            }

            epm.setLocalItem(epm.k.SEARCH_KEYWORDS, JSON.stringify(keys));
        },
        getSearchKeywords: function () {
            var value = epm.getLocalItem(epm.k.SEARCH_KEYWORDS);

            try {
                value = JSON.parse(value);
            } catch (err) {
                return null;
            }

            if (!value) {
                return null;
            }

            // 排序
            value.sort(function (a, b) {
                if (a['times'] > b['times']) {
                    return -1;
                }

                return 1;
            });

            // var ret = [];
            // var len = value.length > 5 ? 5 : value.length;
            // for (var i = 0; i < len; i++) {
            //     ret.push(value[i]['text']);
            // }
            return value;
        }
    };

    epm.setSessionItem = function (key, value) {
        if (window.sessionStorage) {
            sessionStorage.setItem(key, value);
        } else {
            //后备方案
            setCookie(key, value);
        }
    };

    epm.getSessionItem = function (key) {
        if (window.sessionStorage) {
            return sessionStorage.getItem(key);
        } else {
            //后备方案
            return getCookie(key);
        }
    };

    epm.removeSessionItem = function (key) {
        if (window.sessionStorage) {
            sessionStorage.removeItem(key);
        } else {
            //后备方案
            removeCookie(key);
        }
    };

    epm.setLocalItem = function (key, value) {
        if (window.localStorage) {
            localStorage.setItem(key, value);
        } else {
            //后备方案
            setCookie(key, value);
        }
    };

    epm.getLocalItem = function (key) {
        if (window.localStorage) {
            return localStorage.getItem(key);
        } else {
            //后备方案
            return getCookie(key);
        }
    };

    epm.removeLocalItem = function (key) {
        if (window.localStorage) {
            localStorage.removeItem(key);
        } else {
            //后备方案
            removeCookie(key);
        }
    };

    epm.getNumber = function (num) {
        if (isNaN(num) || num === null || num === '') {
            return num;
        }

        return num.replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
    };

    epm.getDateTime = function (o) {
        if (o == null || o == '') {
            return null;
        }

        var d = new Date(o);
        if (typeof  d === 'undefined') {
            o = o.substr(0, o.indexOf('.'));
            o = o.replace(/-/g, '/');
            d = new Date(o);
        }

        var month = d.getMonth() + 1;
        var day = d.getDate();
        var hour = d.getHours();
        var minute = d.getMinutes();
        var second = d.getSeconds();

        return d.getFullYear() + '-' + (('' + month).length < 2 ? '0' : '')
            + month + '-' + (('' + day).length < 2 ? '0' : '') + day + ' '
            + (('' + hour).length < 2 ? '0' : '') + hour + ':'
            + (('' + minute).length < 2 ? '0' : '') + minute + ':'
            + (('' + second).length < 2 ? '0' : '') + second;
    };

    epm.getDate = function (o) {
        if (o == null || o == '') {
            return null;
        }

        var d = new Date(o);
        if (typeof d === 'undefined') {
            o = o.substr(0, o.indexOf('.0'));
            o = o.replace(/-/g, '/');
            d = new Date(o);
        }

        var month = d.getMonth() + 1;
        var day = d.getDate();

        return d.getFullYear() + '-' + (('' + month).length < 2 ? '0' : '')
            + month + '-' + (('' + day).length < 2 ? '0' : '') + day;
    };

    epm.getFloat = function (o) {
        var ret = parseFloat(o);

        return isNaN(ret) ? 0 : ret;
    };

    epm.getQty = function (o) {
        var ret = this.getFloat(o);

        if (PRECISION_QTY == 0) {
            return Math.round(ret);
        } else if (PRECISION_QTY == 1) {
            return Math.round(ret * 10) / 10;
        } else if (PRECISION_QTY == 2) {
            return Math.round(ret * 100) / 100;
        } else {
            return Math.round(ret);
        }
    };

    epm.getMoney = function (o) {
        var ret = this.getFloat(o);

        if (PRECISION_MONEY == 2) {
            return Math.round(ret * 100) / 100;
        } else if (PRECISION_MONEY == 1) {
            return Math.round(ret * 10) / 10;
        } else if (PRECISION_MONEY == 2) {
            return Math.round(ret);
        } else {
            return Math.round(ret * 100) / 100;
        }
    };

    epm.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }

        return null;
    };

    epm.isEmpty = function (o) {
        if (o === undefined) {
            return true;
        }

        if (o == null) {
            return true;
        }

        return $.trim(o.toString()) == '';
    };

    epm.isArray = function (o) {
        return Object.prototype.toString.call(o) === '[object Array]';
    };

    epm.replaceIllegalStr = function (str) {
        var reg;
        var illegal_list = ["/", "\\",
            "[", "]",
            "{", "}",
            "<", ">",
            "＜", "＞",
            "「", "」",
            "：", "；",
            "、", "•",
            "^", "'", "\"",
            "\r", "\r\n", "\\n", "\n"];
        for (var i = 0; i < illegal_list.length; i++) {
            if (str.indexOf(illegal_list[i]) >= 0) {
                if (illegal_list[i] == '\\' || illegal_list[i] == '[') {
                    reg = new RegExp('\\' + illegal_list[i], "g");
                } else {
                    reg = new RegExp(illegal_list[i], "g");
                }
                str = str.replace(reg, '');
            }
        }
        return str.trim();
    };

    epm.getImageUrl = function (imgUrl, size) {
        var imgSize = size + 'x1';
        if (imgUrl.indexOf('.jpg') > -1) {
            return imgUrl.replace('org', imgSize);
        } else {
            return imgUrl;
        }
    };

    epm.ajax = function (url, params, success, successError, fail, always) {
        if (!epm.isEmpty(params)
            && !epm.isEmpty(epm.c.userToken)) {
            params['user_token'] = epm.c.userToken;
        }
        params['api_version'] = API_VERSION;

        $.ajax(url, {
            xhrFields: {
                withCredentials: true
            },
            type: 'POST',
            dataType: 'text',
            data: params,
            contentType: 'application/json',
            processData: false
        }).done(function (data, status, xhr) {
            if (data == undefined || data == null || data.length == 0) {
                alert('服务器繁忙');
                return;
            }

            if (typeof data !== 'object') {
                data = JSON.parse(data);
            }

            // if (data['ans'] != 'ok') {
            //
            //     if (data['ans'] == '用户不存在') {
            //         epm.b.logOut();
            //     } else {
            //         if ($.isFunction(successError)) {
            //             successError(data);
            //         } else {
            //             alert(data['ans']);
            //         }
            //     }
            //
            //     return;
            // }

            // if (!epm.isEmpty(data['new_user_token'])) {
            //     var newUserToken = data['new_user_token'];
            //     if (newUserToken !== epm.c.userToken) {
            //         epm.c.userToken = newUserToken;
            //     }
            // }

            if ($.isFunction(success)) {
                success(data);
            }

        }).fail(function (jqXHR, textStatus, errorThrown) {
            if ($.isFunction(fail)) {
                fail();
            } else {
                var msg = '\r\nstatus: ' + jqXHR.status + ', textStatus: ' + textStatus + ',errorThrown: ' + errorThrown;
                // alert('服务器繁忙，请稍候重试' + msg);
            }

        }).always(function () {
            if ($.isFunction(always)) {
                always();
            }
        });
    };

    epm.extendAjax = function (url, urlParams, success, fail, always) {
        /*$.post("test.php", { "func": "getNameAndTime" },
         function(data){
         alert(data.name); // John
         console.log(data.time); //  2pm
         }, "json");*/
        /*  $.ajax({
         type: 'post',
         crossDomain: true,
         url: 'http://your.url.com/admin/login',
         data: {
         UserName: $('#name:text', this.el).val(),
         PassWord: $('#Password:password', this.el).val()
         },
         dataType:'json',
         xhrFields: {
         'Access-Control-Allow-Origin': '*'
         },
         success: function(data, textStatus, jqXHR){
         console.log("getAllResponseHeaders:"+jqXHR.getAllResponseHeaders());
         console.dir(jqXHR);
         Backbone.history.navigate("#booklist",true);
         }
         });*/

        // $.ajax(API_URL, {
        //     xhrFields: {
        //         withCredentials: true
        //     },
        //     type: 'POST',
        //     dataType: 'text',
        //     data: params
        $.post(url,{
            data:urlParams,
            crossDomain: true,
            withCredential: true,
            xhrFields: {'Access-Control-Allow-Origin': '*'},
            dataType: 'jsonp',jsonpCallback:"success"}, "json").done(function (data) {
            console.log(url);
            console.log(urlParams);
            console.log(JSON.stringify(data));

            if (data == undefined || data == null || data.length == 0) {
                alert('服务器繁忙');
                return;
            }

            if (typeof data !== 'object') {
                data = JSON.parse(data);
            }

            if ($.isFunction(success)) {
                success(data);
            }

        }).fail(function () {
            if ($.isFunction(fail)) {
                fail();
            } else {
                alert('服务器繁忙，请稍候重试');
            }

        }).always(function () {
            if ($.isFunction(always)) {
                always();
            }
        });
    };

    var _ud = {};
    _ud.v = {
        'email': 'sp@spzjs.com',
        'password': 'spzjs1001',
        'token_url': 'https://3pzs.udesk.cn/open_api_v1/log_in',//获取token连接
        'prefix_params_url': 'https://3pzs.udesk.cn/open_api_v1/customers/custom_fields?',//获取用户自定义字段连接
        'im_user_key': '491ef3323f36b63e49a631e821c14b97',
        'ud_url': '//assets-cli.udesk.cn/im_client/js/udeskApi.js?1490946450362',
        'code': '1ckk9hji',
        'default_online_text': '客服在线',
        'default_offline_text': '客服离线'
    };

    //生成随机数
    _ud.createNonceStr = function () {
        return Math.random().toString(36).substr(2, 16);
    };
    //生成时间戳
    _ud.createTimestamp = function () {
        return parseInt(new Date().getTime()) + '';
    };
    //生成web token
    _ud.createWebToken = function () {
        var web_token;
        if (epm.c.buyer) {
            web_token = epm.c.buyer['buyer_iid'];
        } else {
            if (epm.getLocalItem('service_web_token')) {
                web_token = epm.getLocalItem('service_web_token');
            } else {
                web_token = _ud.createNonceStr().toUpperCase() + _ud.createTimestamp();
                epm.setLocalItem('service_web_token', web_token);
            }
        }
        return web_token;
    };

    /**
     * 获取open_api_auth_token
     * 生成签名
     */
    _ud.getOpenAipToekn = function (array) {
        var params = {};
        params.email = _ud.v.email;
        params.password = _ud.v.password;
        $.post(_ud.v.token_url, params, function (result) {
            var token = result['open_api_auth_token'];
            var times = _ud.createTimestamp();
            var sign = _ud.createTokenSign(token, times);
            _ud.getUserParams(sign, times, array);
        })
    };

    /**
     * 获取用户自定义字段
     * @param sign：签名参数
     * @param timestamp：时间戳
     */
    _ud.getUserParams = function (sign, timestamp, data_array) {
        var url = _ud.v.prefix_params_url
            + 'email=' + _ud.v.email
            + '&sign=' + sign
            + '&timestamp=' + timestamp;
        $.get(url, function (result) {
            console.log(JSON.stringify(result));
            var custom_fields = result.custom_fields;
            var customer = {};
            customer.c_name = _ud.createWebToken();
            var field_name, field_title;
            $.each(custom_fields, function (key, value) {
                field_title = value['title'];
                field_name = value['custom_field_name'];
                customer['c_cf_' + field_title] = data_array[field_title];
            });
            _ud.initUD('onlink', 'offlink', 'aaa', customer);
        })
    };

    /**
     * 生成token签名
     * @param token：签名所需token
     * @param timestamp：时间戳
     * @returns {*}
     */
    _ud.createTokenSign = function (token, times) {
        var sign_str = _ud.v.email + '&' + token + '&' + times;
        var sign = hex_sha1(sign_str);
        return sign;
    };

    /**
     * 生成UD签名
     * @param nonce
     * @param timestamp
     * @param web_token
     * @returns {string}
     */
    _ud.createUdSign = function (nonce, timestamp, web_token) {
        var sign = 'nonce=' + nonce + '&timestamp=' + timestamp + '&web_token=' + web_token + '&' + _ud.v.im_user_key;
        sign = hex_sha1(sign);
        sign = sign.toUpperCase();
        return sign;
    };

    /**
     * 加载ud客服
     * @param onlineText：客服在线文字
     * @param offlineText：客服离线文字
     * @param targetSelector：触发客服窗口元素class，需加'.'，不能为空
     * @param customer：客户自定义信息数组
     */
    _ud.initUD = function (onlineText, offlineText, targetSelector, customer) {
        //客服信息数组校验内容生成
        var nonce = _ud.createNonceStr();
        var timestamp = _ud.createTimestamp();
        var web_token = _ud.createWebToken();
        var sign = _ud.createUdSign(nonce, timestamp, web_token);

        customer['c_name'] = (epm.isEmpty(epm.c.buyer) ? '访客' : '用户') + web_token;
        if (!epm.isEmpty(epm.c.buyer)) {
            customer['c_cf_用户编号'] = web_token;
        }
        customer['nonce'] = nonce;
        customer['signature'] = sign;
        customer['timestamp'] = timestamp;
        customer['web_token'] = web_token;
        console.log(customer);
        (function (a, h, c, b, f, g) {
            a["UdeskApiObject"] = f;
            a[f] = a[f] || function () {
                    (a[f].d = a[f].d || []).push(arguments)
                };
            g = h.createElement(c);
            g.async = 1;
            g.src = b;
            c = h.getElementsByTagName(c)[0];
            c.parentNode.insertBefore(g, c)
        })(window, document, "script", _ud.v.ud_url, "ud");
        ud({
            "code": _ud.v.code,
            "link": "//3pzs.udesk.cn/im_client?web_plugin_id=23894",
            "mode": "blank",
            "onlineText": epm.isEmpty(onlineText) ? _ud.v.default_online_text : onlineText,
            "offlineText": epm.isEmpty(offlineText) ? _ud.v.default_offline_text : offlineText,
            "targetSelector": targetSelector,
            'selector': targetSelector,
            "customer": customer
        });
    };

    window.epm = epm;
    window._ud = _ud;
})();

$(document).ready(function () {
    if ($.isFunction(window.pageInit)) {
        pageInit();
    }
});

// 热词搜索
function getSearchKeyList(search_type) {
    var htmlSearch = '';
    var params = {};
    params['action'] = 'get_search_key_list';
    params['market_iid'] = epm.c.market['market_iid'];
    params['search_type'] = search_type;
    params['search_key'] = epm.replaceIllegalStr($('#search').val());

    epm.ajax(params, function (result) {
        if (result.ans != 'ok') {
            return;
        }

        $.each(result['data'], function (key, value) {
            if ($('#searchSelect').val() == '商品') {

                htmlSearch += '<li class="hd-search-item">'
                    + '<a href="goods_list.html?key=' + escape(value['goods_name']) + '&iskey=1">' + value['goods_name'] + '</a>'
                    + '</li>';
            } else {

                htmlSearch += '<li class="hd-search-item">'
                    + '<a href="shop_list.html?stall_key=' + escape(value['shop_name']) + '&iskey=1">' + value['shop_name'] + '</a>'
                    + '</li>';
            }
        });
        $('.hd-search-list').html(htmlSearch);

    });

}

// 初始化用户信息
function spInitUser() {

    var $loginIn = $('.hd-login-in');
    var $hdUser = $('.hd-user');

    var buyer = epm.c.buyer;
    if (buyer) {
        initFinish(buyer);

    } else {
        var params = {};
        params['action'] = 'get_buyer_info';
        if (epm.c.market != null) {
            params['market_iid'] = epm.c.market['market_iid'];
        }
        epm.ajax(params, function (data) {
            buyer = data['data'];
            epm.c.buyer = buyer;
            initFinish(buyer);
        });
    }

    function initFinish(data) {
        var $target = $hdUser.find('.hd-top-txt');
        $target.text('欢迎，' + data['buyer_name']);
        $target.attr('buyer_iid', data['buyer_iid']);

        $loginIn.hide();
        $hdUser.show();

        // 根据用户初始化地址列表
        spInitAddressList();
    }
}
function readAsDataURL() {
    $('#imgUpload').addClass('cm-btn-active');
    //检验是否为图像文件
    var file = $('#files').prop('files')[0];
    if (file.size >= 1000000) {
        alert('图片文件不得超过1M');
        return;
    }
    if (!/image\/\w+/.test(file.type)) {
        alert("目前只能上传jpg图片！");
        return false;
    }
    var reader = new FileReader();
    //将文件以Data URL形式读入页面
    reader.readAsDataURL(file);
    reader.onload = function () {
        //显示文件
        $('.user-img-upload').html('<img src="' + this.result + '" alt="" />');
    }
}