<#-- 公共顶部 -->
<#macro header title="博客" keywords="默认文字" description="默认文字" canonical="">
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="baidu_union_verify" content="eeac97d6851630e4f4885c7b0dd212b6">
    <meta name="360-site-verification" content="66509beaf5456e21159e63553160d8ef"/>
    <meta name="baidu-site-verification" content="LMsuq9avNn" />
    <title>${title}</title>
    <meta name="author" content="${config.authorName}(${config.authorEmail})">
    <meta name="keywords" content="${keywords}"/>
    <meta name="description" content="${description}" id="meta_description">
    <link rel="canonical" href="${config.siteUrl}${canonical}"/>
    <#include "/tool_common/quote.ftl">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="${config.staticWebSite}/css/shCoreDefault.css" rel="stylesheet" id="css">
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
        (function () {
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
 <div id="wrapper">
    <#include "/tool_common/header.ftl"/>
     <div id="page-wrapper">
       <div class="row">
           <div class="col-lg-12">
               <div class="alert alert-warning alert-dismissible tool-history" role="alert">
                   <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
                   </button>
                   <strong> 最近常用：</strong>
                   <ul></ul>
               </div>
               <h1 class="page-header"><i class="fa fa-code"></i>${title}
                   <div class="bdsharebuttonbox" style="float: right">
                       <a href="#" class="bds_more" data-cmd="more"></a>
                       <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                       <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                       <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
                       <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                       <a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                       <a href="#" class="bds_copy" data-cmd="copy" title="分享到复制网址"></a>
                   </div>
               </h1>
           </div>
       </div>
</#macro>

<#-- 公共底部 -->
<#macro footer>
         <div class="separateline">
             <span>正文到此结束</span>
         </div>
<#--<div class="underline">-->
<#--<h4><i class="fa fa-quote-left"></i>评论 | Comment</h4>-->
<#--</div>-->
<#--<div id="SOHUCS" sid="19"></div>-->
         <div id="tool_list">
             <a class="btn gotop js-tip" href="javascript:;" onclick="window.scrollTo('0','0')" original-title="返回顶部">返回顶部</a>
         </div>
    <#include "/tool_common/footer.ftl"/>
     </div>
 </div>
<div class="tool-cover"></div>
<script src="${config.staticWebSite}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
  <script src="${config.staticWebSite}/js/metisMenu.min.js"></script>
<script src="https://cdn.bootcss.com/toastr.js/2.0.3/js/toastr.min.js"></script>
<script src="//cdn.bootcss.com/clipboard.js/1.6.0/clipboard.min.js"></script>
  <script type="text/javascript">
      //    var title = document.title.indexOf(" - ") != -1 ? document.title.split(" - ")[0] : document.title;
      var menuPath = '${url}';
      var title = '代码高亮';
      var path = menuPath ? menuPath : "/other";
  </script>
  <script src="${config.staticWebSite}/js/tool.core.min.js"></script>
  <script src="${config.staticWebSite}/js/tool.utils.min.js"></script>
    <#nested>
    </body>
</html>
</#macro>




<#-- blog-header -->
<#macro blogHeader title="Header">
    <div class="col-sm-12 blog-main">
        <div class="blog-header">
            <h1 class="blog-title">${title}</h1>
            <p class="blog-description" id="hitokoto"></p>
            <div class="info">
                <a href="javascript:void(0);" target="_blank" title="点击QQ联系我"
                   onclick="window.open('tencent://message/?uin=1098308189&amp;Site=www.${config.domain}&amp;Menu=yes')"
                   rel="external nofollow"><i class="fa fa fa-qq fa-fw"></i>QQ联系</a>
                |
                <a href="mailto:1098308189@qq.com" target="_blank" title="点击给我发邮件" rel="external nofollow"><i
                        class="fa fa fa-envelope fa-fw"></i>邮箱联系</a>
                |
                <a href="http://weibo.com/5593964435" target="_blank" title="点击查看我的微博" rel="external nofollow"><i
                        class="fa fa fa-weibo fa-fw"></i>@微博不存在</a>
            </div>
        </div>
    </div>
</#macro>