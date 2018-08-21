<#include "include/macros.ftl">
<@header title="网址导航 | ${config.siteName?if_exists}"
keywords="${config.siteName},网址导航"
description="${config.siteName},网址导航"
canonical="/${url?if_exists}">
<link href="https://cdn.bootcss.com/simplemde/1.11.2/simplemde.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/github-markdown-css/2.10.0/github-markdown.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/highlight.js/9.12.0/styles/github.min.css" rel="stylesheet">
<style>
    .CodeMirror, .CodeMirror-scroll {
        min-height: 130px;
        max-height: 200px;
    }

    .CodeMirror .cm-spell-error:not(.cm-url):not(.cm-comment):not(.cm-tag):not(.cm-word) {
        background: none;
    }

    .editor-statusbar {
        display: none;
    }

    .editor-preview {
        overflow-y: initial !important;
    }

    ul {
        list-style: none;
        margin-left: 0;
        padding-left: 0;
    }
</style>
</@header>
<div class="container custome-container">
    <!--[if lt IE 9]>
    <div class="alert alert-danger topframe" role="alert">Oh My God！你的浏览器实在<strong>太太太太太太旧了</strong>，赶紧升级浏览器 <a
            target="_blank" class="alert-link" href="http://browsehappy.com">立即升级</a></div><![endif]-->
    <nav class="breadcrumb">
        <a class="crumbs" title="返回首页" href="http://www.cchcz.com" data-toggle="tooltip" data-placement="bottom"><i
                class="fa fa-home"></i>首页</a>
        <i class="fa fa-angle-right"></i>网址导航
    </nav>
    <div class="row">
        <div class="col-sm-12 blog-main">
            <div class="blog-header">
                <h1 class="blog-title">网址导航</h1>
                <p class="blog-description" id="hitokoto"></p>
                <div class="bdsharebuttonbox">
                    <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                    <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                    <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                    <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ"></a>
                    <a href="#" class="bds_more" data-cmd="more"></a>
                </div>
            </div>
        </div>
        <@customTag method="navTypes">
            <#if navTypes?? && navTypes?size gt 0>
                <#list navTypes as navType>
                    <@customTag method="navList" navTypeId="${navType.id}">
                        <#if navList?? && navList?size gt 0>
        <div class="col-sm-12 blogweb-container">
            <div class="blogweb-box blogweb-tool">
                <div class="category">
                    <div style="color: #e04620;"><i class="${navType.icon!''} fa-fw fa-2x"></i>${navType.name!''}
                    </div>
                </div>
                <div class="">
                    <#list navList as nav>
                        <a target="_blank" href="${nav.outLink}">
                            <div class="item" style="width: 18%;height: 90px">
                                <div class="logo"><img data-original="${nav.imageIcon}"
                                                       width="100%" class="lazy-img">${nav.name}
                                </div>
                                <div class="desc"> ${nav.description}</div>
                            </div>
                        </a>
                    </#list>
                </div>
            </div>
        </div>
                        </#if>
                    </@customTag>
                </#list>
            </#if>
        </@customTag>
        <div class="col-sm-12 blog-main">
            <div class="blog-body expansion">
                <div id="comment-box" data-id="-3"></div>
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
    $.hitokoto.init("#hitokoto");
</script>
</@footer>