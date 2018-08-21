<#-- 公共顶部 -->
<#macro header title="博客" keywords="默认文字" description="默认文字" canonical="">
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="baidu_union_verify" content="eeac97d6851630e4f4885c7b0dd212b6">
    <meta name="360-site-verification" content="66509beaf5456e21159e63553160d8ef"/>
    <meta name="baidu-site-verification" content="LMsuq9avNn" />
    <title>${title}</title>
    <meta name="author" content="${config.authorName}(${config.authorEmail})">
    <meta name="keywords" content="${keywords}"/>
    <meta name="description" content="${description}" id="meta_description">
    <link rel="canonical" href="${config.siteUrl}${canonical}" />
    <#include "/layout/quote.ftl">
    <#--黑白界面
    <style>
        html {
            filter: grayscale(100%);
            -webkit-filter: grayscale(100%);
            -moz-filter: grayscale(100%);
            -ms-filter: grayscale(100%);
            -o-filter: grayscale(100%);
            filter: url("data:image/svg+xml;utf8,<svg xmlns="\'http://www.w3.org/2000/svg\'"><filter id="\'grayscale\'"><feColorMatrix type="\'matrix\'" values="\'0.3333" 0.3333="" 0="" 1="" 0\'=""></fecolormatrix></filter></svg>#grayscale");filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);-webkit-filter:grayscale(1);}
    </style>-->
    <#nested>
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?276f351e7122f9161362e81c54f87f70";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
        (function(){
            var bp = document.createElement('script');
            var curProtocol = window.location.protocol.split(':')[0];
            if (curProtocol === 'https') {
                bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
            } else {
                bp.src = 'http://push.zhanzhang.baidu.com/push.js';
            }
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(bp, s);
        })();


    </script>
</head>
<body>
    <#include "/layout/header.ftl"/>
</#macro>

<#-- 公共底部 -->
<#macro footer>
    <#include "/layout/footer.ftl"/>

    <#nested>

    </body>
</html>
</#macro>

<#-- 分页组件 -->
<#macro pageBar>
    <#if page?exists && (page.pages > 1)>
    <nav>
        <ul class="pager page-btn" data-url="${config.siteUrl}/${url?if_exists}" data-search="${(model.keywords == null || model.keywords == '')?string('false', 'true')}">
            <#if page.hasPreviousPage>
            <li><a class="pointer" data-page="${page.prePage}"><i class="fa fa-angle-double-left"></i></a></li>
            </#if>
            <#list 1..page.pages as item>
            <li><a ${(page.pageNum == item)?string('class="pointer active"','class="pointer" data-page="${item?c}"')}>${item?c}</a></li>
            </#list>
            <#if page.hasNextPage>
            <li><a class="pointer" data-page="${page.nextPage}"><i class="fa fa-angle-double-right"></i></a></li>
            </#if>
        </ul>
    </nav>
    </#if>
</#macro>


<#-- blog-header -->
<#macro blogHeader title="Header">
    <div class="col-sm-12 blog-main">
        <div class="blog-header">
            <h1 class="blog-title">${title}</h1>
            <p class="blog-description" id="hitokoto"></p>
            <div class="info">
                <a href="javascript:void(0);" target="_blank" title="点击QQ联系我"onclick="window.open('tencent://message/?uin=1098308189&amp;Site=www.${config.domain}&amp;Menu=yes')" rel="external nofollow"><i class="fa fa fa-qq fa-fw"></i>QQ联系</a>
                |
                <a href="mailto:1098308189@qq.com" target="_blank" title="点击给我发邮件" rel="external nofollow"><i class="fa fa fa-envelope fa-fw"></i>邮箱联系</a>
                |
                <a href="http://weibo.com/5593964435" target="_blank" title="点击查看我的微博" rel="external nofollow"><i class="fa fa fa-weibo fa-fw"></i>@微博不存在</a>
            </div>
        </div>
    </div>
</#macro>