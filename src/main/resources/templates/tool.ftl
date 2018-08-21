<#include "include/macros.ftl">
<@header title="推荐软件、开发工具 | ${config.siteName?if_exists}"
keywords="${config.siteName},推荐软件,常用的开发工具"
description="${config.siteName},推荐常用的开发工具"
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
        <i class="fa fa-angle-right"></i>推荐软件、开发工具
    </nav>
    <div class="row">
        <div class="col-sm-12 blog-main">
            <div class="blog-header">
                <h1 class="blog-title">推荐软件、开发工具</h1>
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
                <p class="blog-description">
                    <small>注意：所有软件排名不分先后。当前页面中的软件排序与互联网权威排名机构的软件热门程度的排序无关，仅作为参考。</small>
                </p>
                <div class="bdsharebuttonbox">
                    <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                    <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                    <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                    <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ"></a>
                    <a href="#" class="bds_more" data-cmd="more"></a>
                </div>
            </div>
        </div>
<@customTag method="toolTypes">
    <#if toolTypes?? && toolTypes?size gt 0>
        <#list toolTypes as toolType>
            <@customTag method="toolList" toolTypeId="${toolType.id}">
                <#if toolList?? && toolList?size gt 0>
        <div class="col-sm-12 blogweb-container">
            <div class="blogweb-box blogweb-tool">
                <div class="category">
                    <div style="color: #e04620;"><i class="${toolType.icon!''} fa-fw fa-2x"></i>${toolType.name!''}
                    </div>
                </div>
                <div class="">
                    <#list toolList as tool>
                        <a target="_blank" href="${tool.outLink}">
                            <div class="item">
                                <div class="logo"><img data-original="${tool.imageIcon}"
                                                       width="100%" class="lazy-img">${tool.name}
                                </div>
                                <div class="desc"> ${tool.description}</div>
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