<#include "include/macros.ftl">
<@header title="技术资料 | ${config.siteName?if_exists}"
keywords="${config.siteName},技术资料"
description="${config.siteName},技术资料"
canonical="/${url?if_exists}">
    <style>
        .container .blogweb-container .blogweb-os .logo {
            height: 20px;
            position: relative;
            font-size: 14px;
            font-weight: 700;
            color: #3273dc;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            padding: 0 .1rem;
        }

        .container .blogweb-container .blogweb-os .item {
            border-radius: 6px;
            background: #fff;
            padding: 10px;
            width: 30%;
            min-width: 200px;
            margin: 22px 0 0 15px;
            float: left;
            overflow: hidden;
            border: 1px solid #e4ecf3;
        }

        .container .blogweb-container .blogweb-os .item .desc {
            color: gray;
            font-size: 12px;
            padding-top: 10px;
            height: 72px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            margin-bottom: 16px !important;
        }
    </style>
</@header>
<body>
<div class="container custome-container">
    <!--[if lt IE 9]>
    <div class="alert alert-danger topframe" role="alert">Oh My God！你的浏览器实在<strong>太太太太太太旧了</strong>，赶紧升级浏览器 <a
            target="_blank" class="alert-link" href="http://browsehappy.com">立即升级</a></div><![endif]-->
    <nav class="breadcrumb">
        <a class="crumbs" title="返回首页" href="http://www.cchcz.com" data-toggle="tooltip" data-placement="bottom"><i
                class="fa fa-home"></i>首页</a>
        <i class="fa fa-angle-right"></i>技术资料
    </nav>
    <div class="row">
        <div class="col-sm-12 blog-main">
            <div class="blog-header">
                <h1 class="blog-title">技术资料 </h1>
                <p class="blog-description" id="hitokoto"></p>
                <div class="info">
                    <a href="javascript:void(0);" target="_blank" title="点击QQ联系我"
                       onclick="window.open('tencent://message/?uin=${config.qq!'1098308189'}&amp;Site=www.cchcz.com&amp;Menu=yes')"
                       rel="external nofollow"><i class="fa fa fa-qq fa-fw"></i>QQ联系</a>
                    |
                    <a href="mailto:${config.authorEmail!'1098308189@qq.com'}" target="_blank" title="点击给我发邮件"
                       rel="external nofollow"><i
                            class="fa fa fa-envelope fa-fw"></i>邮箱联系</a>
                    |
                    <a href="http://weibo.com/5593964435" target="_blank" title="点击查看我的微博" rel="external nofollow"><i
                            class="fa fa fa-weibo fa-fw"></i>@博客</a>
                </div>
                <div class="bdsharebuttonbox">
                    <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                    <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                    <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                    <a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
                    <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                    <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ"></a>
                    <a href="#" class="bds_more" data-cmd="more"></a>
                </div>
            </div>
        </div>
        <div class="col-sm-12 blogweb-container">
            <div class="blogweb-box blogweb-os">
                <div class="category">
                    <div style="color: #e04620;"><i class="fa fa-fire fa-fw fa-2x"></i>技术资料</div>
                </div>
                <div class="">
                <@customTag method="books">
                    <#if books?? && books?size gt 0>
                        <#list books as book>
                    <div class="item">
                        <div class="logo"><a target="_blank" href="/book/${book.id}">${book.title}</a>
                        </div>
                        <div class="desc">
                            ${book.description!''}
                        </div>
                        <div class="other">
                            <div style="float: right">
                                <a>
                                    <i class="fa fa-eye"></i> ${book.watchCount!'0'}
                                </a>
                                <a href="/bookAdmire/${book.id}">
                                    <i class="fa fa-thumbs-up"></i> ${book.starCount!'0'}
                                </a>
                            </div>
                        </div>
                    </div>
                        </#list>
                    </#if>
                </@customTag>
                </div>
            </div>
        </div>
    </div>
</div>
<@footer>
<script src="https://v1.hitokoto.cn/?encode=js&c=d&select=%23hitokoto" defer></script>
<script type="text/javascript" src="https://cdn.bootcss.com/highlight.js/9.12.0/highlight.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/simplemde/1.11.2/simplemde.min.js"></script>
<script type="text/javascript">
    setTimeout(function () {
        /* 百度分享 */
        var bdText = $("#meta_description").attr("content") + " - by cchcz.com";
        var coverImg = $("#cover-img").attr("src") || "/images/default_article_cover.jpg";
        window._bd_share_config = {
            "common": {
                "bdSnsKey": {},
                "bdText": bdText,
                "bdMini": "2",
                "bdMiniList": ["mshare", "qzone", "tsina", "bdysc", "weixin", "renren", "tqq", "kaixin001", "tqf", "tieba", "douban", "bdhome", "sqq", "youdao", "sdo", "qingbiji", "mail", "isohu", "ty", "fbook", "twi", "linkedin", "h163", "evernotecn", "copy", "print"],
                "bdPic": coverImg,
                "bdStyle": "1",
                "bdSize": "24"
            }, "share": {}
        };
        with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion=' + ~(-new Date() / 36e5)];
        /* 百度分享 */
    }, 1000);
</script>
<script type="text/javascript" defer="defer">
    // $.hitokoto.init("#hitokoto");
    $("#loading").hide();
</script>
</@footer>
