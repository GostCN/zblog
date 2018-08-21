<#--公共顶部-->
<#macro header title="音乐馆" keywords="默认文字" description="默认文字">
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=600px,initial-scal=1,user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>${title}</title>
    <meta name="keywords" content="${keywords}"/>
    <meta name="description" content="${description}"/>
    <meta name="baidu-site-verification" content="LMsuq9avNn" />
    <link rel="shortcut icon" href="${config.siteFavicon}">
    <link rel="stylesheet" href="${config.staticWebSite}/css/blog-web.music.core.css">
    <#nested>
    <#--<link rel="stylesheet" href="${config.staticWebSite}/css/leaves.css" type="text/css" media="screen" charset="utf-8">-->
    <#--<script src="${config.staticWebSite}/js/leaves.js" type="text/javascript" charset="utf-8"></script>-->
</head>
<body>
<div id="leafContainer"></div>
</#macro>
<#--公共底部-->
<#macro footer>
<footer class="footer">
    <div class="clear">
        <p>托管于<a href="https://www.aliyun.com/" target="_blank" title="阿里云-为了无法计算的价值" data-toggle="tooltip" data-placement="bottom" rel="external nofollow">阿里云</a>  · <a href="http://www.miitbeian.gov.cn/publish/query/indexFirst.action" target="_blank" title="查看备案信息" data-toggle="tooltip" data-placement="bottom" rel="external nofollow">京ICP备18037250号</a></p>
        <p>Copyright&copy;2016-${.now?string("yyyy")} ${config.siteName} · Powered by <a href="#" title="zblog是一款简洁美观、自适应的Java博客系统..." data-toggle="tooltip" data-placement="right" target="_blank"><strong>博客</strong></a> · <a href="http://www.cchcz.com/visitor/" target="_blank" title="点击查看${config.siteName}网站统计详情" data-toggle="tooltip" data-placement="right" rel="external nofollow"><i class="fa fa-bar-chart-o fa-fw fa-spin"></i>网站统计</a></p>
    </div>
</footer>
    <a class="goTop" title="返回顶部"></a>
    <script type="text/javascript" src="${config.staticWebSite}/js/ribbon.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js" type="text/javascript"></script>
    <!--[if IE]>
<script src="http://apps.bdimg.com/libs/html5shiv/r29/html5.min.js"></script><![endif]-->
    <script src="https://cdn.bootcss.com/jqueryui/1.11.1/jquery-ui.js" type="text/javascript"></script>
    <script src="${config.staticWebSite}/js/jquery.toTop.min.js?v=1.0.1" type="text/javascript"></script>
    <script src="${config.staticWebSite}/js/star.js?v=1.0.1" type="text/javascript"></script>

    <#nested>

    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?276f351e7122f9161362e81c54f87f70";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
    <script>
        window._bd_share_config = {
            "common": {
                "bdSnsKey": {},
                "bdText": "",
                "bdMini": "2",
                "bdMiniList": ["qzone", "tsina", "weixin", "sqq", "mshare", "bdysc", "renren", "tqq", "bdxc", "kaixin001", "tqf", "tieba", "douban", "bdhome", "thx", "ibaidu", "meilishuo", "mogujie", "diandian", "huaban", "duitang", "hx", "fx", "youdao", "sdo", "qingbiji", "people", "xinhua", "mail", "isohu", "yaolan", "wealink", "ty", "iguba", "fbook", "twi", "linkedin", "h163", "evernotecn", "copy", "print"],
                "bdPic": "",
                "bdStyle": "0",
                "bdSize": "16"
            }, "slide": {"type": "slide", "bdImg": "3", "bdPos": "right", "bdTop": "203.5"}
        };
        with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion=' + ~(-new Date() / 36e5)];
    </script>
</body>
</html>
</#macro>