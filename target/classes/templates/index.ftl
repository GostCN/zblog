<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0,user-scalable=0,shrink-to-fit=no">
    <title>Hey, baby！每天都会有不一样的精彩...</title>
    <meta name="description" content="${hitokoto!'在复杂的世界里，我只想做一个简单的人！生活本就是这样子,活的越简单不复杂就好。'}"/>
    <meta name="baidu_union_verify" content="eeac97d6851630e4f4885c7b0dd212b6">
    <meta name="baidu-site-verification" content="LMsuq9avNn" />
    <link href="${config.siteFavicon}" rel="shortcut icon" type="image/x-icon">
    <link rel="apple-touch-icon" href="${config.siteFavicon}">
    <style type="text/css">
        body, html {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            font: 400 16px/1.7 "Microsoft JhengHei", sans-serif;
            color: #444;
            min-width: 320px;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none
        }

        .welcome {
            text-align: center;
            height: 568px;
            padding: 0 20px;
            margin: 0 auto;
            position: relative;
            top: 50%;
            transform: translateY(-50%)
        }

        /*.welcome .map {*/
        /*background: url(/images/map.png)*/
        /*}*/

        .welcome .nickname {
            height: 46px;
            line-height: 46px;
            font-size: 26px;
            font-weight: 700
        }

        .welcome .avatar img {
            padding: 4px;
            border: 1px solid #eaeaea;
            display: inline-block;
            width: 110px;
            height: 110px;
            margin: 70px auto 0;
            border-radius: 50%
        }

        .welcome .address {
            height: 36px;
            line-height: 36px;
            font-size: 15px
        }

        .local {
            padding-right: 15px
            /*background: url(/images/local.png) no-repeat*/
        }

        .welcome .about {
            margin: 5px auto
        }

        .welcome .link a {
            color: #37474f;
            display: inline-block;
            margin: auto 8px;
            height: 36px;
            line-height: 36px
        }

        .welcome .link a:hover {
            color: #000
        }
    </style>
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "http://hm.baidu.com/hm.js?276f351e7122f9161362e81c54f87f70";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>
<body>
<div class="welcome">
    <div class="avatar">
        <a href="http://www.cchcz.com"><img
                src="${config.staticWebSite}/images/head.jpg" alt="${config.authorName}"
                title="${config.authorName}"></a>
    </div>
    <div class="nickname">${config.authorName}</div>
    <div class="address">
        <i class="local"></i> Beijing ,China
    </div>
    <div class="about">
    ${hitokoto_eng!'Do one thing at a time, and do well.'}
    </div>
    <div class="about">
    ${hitokoto!'专心做好事情。'}
    </div>
    <div class="link">
        <a href="//blog.cchcz.com">博客</a>
        <a href="//nav.cchcz.com/nav" target="_blank">导航</a>
        <a href="//book.cchcz.com/book" target="_blank">书籍</a>
        <a href="//tool.cchcz.com/tool" target="_blank">工具软件</a>
        <a href="//music.cchcz.com/music/" target="_blank">音乐</a>
        <a href="//www.cchcz.com/links" target="_blank">朋友</a>
        <br/>
        <br/>

        <p style="font-size: smaller">托管于<a href="https://www.aliyun.com/" target="_blank" title="阿里云-为了无法计算的价值"
                                            data-toggle="tooltip" data-placement="bottom"
                                            rel="external nofollow">阿里云</a> · <a
                href="http://www.miitbeian.gov.cn/publish/query/indexFirst.action" target="_blank" title="查看备案信息"
                data-toggle="tooltip" data-placement="bottom" rel="external nofollow">京ICP备18037250号</a></p>
        <p style="font-size: smaller">Copyright&copy;2016-${.now?string("yyyy")} ${config.siteName} · Powered by <a
                href="#" title="zblog是一款简洁美观、自适应的Java博客系统..." data-toggle="tooltip" data-placement="right"
                target="_blank"><strong>博客</strong></a> · <a href="http://www.cchcz.com/visitor/" target="_blank"
                                                             title="点击查看${config.siteName}网站统计详情" data-toggle="tooltip"
                                                             data-placement="right" rel="external nofollow"><i
                class="fa fa-bar-chart-o fa-fw fa-spin"></i>网站统计</a></p>
    </div>
</div>
<script type="text/javascript" src="${config.staticWebSite}/js/ribbon.min.js"></script>
</body>
</html>