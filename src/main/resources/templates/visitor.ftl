<#include "include/macros.ftl">
<@header title="访客城市分布 | ${config.siteName}"
keywords="访客城市分布,${config.siteName}"
description="访客城市分布"
canonical="/${url?if_exists}">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/es5-shim/4.1.13/es5-shim.min.js"></script>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.0/html5shiv-printshiv.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="${config.staticWebSite}/css/g-nav.css?t=1524624425729">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
</@header>
<div class="container custome-container">
    <!--[if lt IE 9]>
    <div class="alert alert-danger topframe" role="alert">Oh My God！你的浏览器实在<strong>太太太太太太旧了</strong>，赶紧升级浏览器 <a
            target="_blank" class="alert-link" href="http://browsehappy.com">立即升级</a></div>
    <![endif]-->
    <nav class="breadcrumb">
        <a class="crumbs" title="返回首页" href="http://www.cchcz.com" data-toggle="tooltip" data-placement="bottom"><i
                class="fa fa-home"></i>首页</a>
        <i class="fa fa-angle-right"></i>访客城市分布
    </nav>
    <div class="row">
        <div class="col-sm-12 blog-main">
            <div class="blog-header">
                <h1 class="blog-title">访客城市分布</h1>
                <p class="blog-description" id="hitokoto"></p>
            </div>
        </div>
        <div class="col-sm-12 blog-main">
            <div id="wrap">
                <div id="bdy">
                    <div class="w">
                        <div class="main no-side">
                            <div class="inner">
                                <!-- main content -->
                                <div id="js_visitor_map" style="height: 550px;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-12 blog-main">
            <table class="table table-striped" cellspacing="0" cellpadding="0" width="100%">
                <thead>
                <tr>
                    <th width="20%">#</th>
                    <th width="60%">城市</th>
                    <th width="20%">人数</th>
                </tr>
                </thead>
                <tbody>
                                        <#if topList?exists && (topList?size > 0)>
                                        <#list topList as topItem>
                                            <tr>
                                                <td>${topItem_index+1}</td>
                                                <td>${topItem.name?if_exists}</td>
                                                <td>${topItem.num?if_exists}</td>
                                            </tr>
                                        </#list>
                                        </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<@footer>
<link rel="stylesheet" href="https://cdn.bootcss.com/jvectormap/2.0.1/jquery-jvectormap.min.css">
<link rel="stylesheet" href="${config.staticWebSite}/css/visitor.css">
<script>
    var markers = ${visitorVoList!'[]'}
</script>
<script src="https://cdn.bootcss.com/howler/1.1.28/howler.min.js"></script>
<script src="${config.staticWebSite}/js/jquery-jvectormap.min.js"></script>
<script src="${config.staticWebSite}/js/jquery-jvectormap-cn-merc-cn.js"></script>
<script src="https://cdn.bootcss.com/jquery.transit/0.9.12/jquery.transit.min.js"></script>
<script src="${config.staticWebSite}/js/visitor.js"></script>
<#--<script type="text/javascript" defer="defer">
$.hitokoto.init("#hitokoto");
</script>-->
</@footer>
