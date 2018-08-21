<#include "/admin/include/macros.ftl">
<@header>
<style>
    .markdown-textarea {
        width: 100%;
        font-family: "Arial";
        resize: none;
        background-color: #fafafa;
    }

    .md-prompt-dialog input, .markdown-textarea {
        background: #FFF;
        border: 1px solid #D9D9D6;
        padding: 10px;
        border-radius: 2px;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
</style>
</@header>

<div class="">
    <div class="clearfix"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12">
                <ol class="breadcrumb">
                    <li><a href="/admin/">首页</a></li>
                    <li class="active">爬虫管理</li>
                </ol>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <h4 class="page-title">
                    <#if spider??>
                        编辑爬虫
                    <#else>
                        添加网站
                    </#if>
                </h4>
            </div>
            <div class="col-md-12">
                <form id="spiderForm" role="form" novalidate="novalidate">
                    <input type="hidden" name="id"
                           value="<#if spider?? && spider.id??>${spider.id}</#if>" id="id"/>
                    <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                        <input type="text" class="form-control" placeholder="请输入网站中文名（必须）" name="netName"
                               required="required"
                               aria-required="true"
                               value="<#if spider?? && spider.netName??>${spider.netName}</#if>"/>
                    </div>
                    <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                        <input type="text" class="form-control" placeholder="请输入网站英文名（必须）" name="netEng"
                               required="required"
                               aria-required="true"
                               value="<#if spider?? && spider.netEng??>${spider.netEng}</#if>"/>
                    </div>
                    <div class="clearfix">
                        <h5 style="color: red">以下多个CSS路径用|隔开</h5>
                    </div>
                    <div class="form-group">
                        <h5 style="color: gray">解析标题CSS路径(必填)</h5>
                        <textarea style="height: 100px" autocomplete="off" id="parseTitle" name="parseTitle"
                                  class="markdown-textarea"
                                  utext="<#if spider?? && spider.parseTitle??>${spider.parseTitle}</#if>"><#if spider?? && spider.parseTitle??>${spider.parseTitle}</#if></textarea>
                    </div>
                    <div class="form-group">
                        <h5 style="color: gray">解析关键字CSS路径</h5>
                        <textarea style="height: 100px" autocomplete="off" id="parseKeywords" name="parseKeywords"
                                  class="markdown-textarea"
                                  utext="<#if spider?? && spider.parseKeywords??>${spider.parseKeywords}</#if>"><#if spider?? && spider.parseKeywords??>${spider.parseKeywords}</#if></textarea>
                    </div>
                    <div class="form-group">
                        <h5 style="color: gray">解析正文CSS路径(必填)</h5>
                        <textarea style="height: 100px" autocomplete="off" id="parseContent" name="parseContent"
                                  class="markdown-textarea"
                                  utext="<#if spider?? && spider.parseContent??>${spider.parseContent}</#if>"><#if spider?? && spider.parseContent??>${spider.parseContent}</#if></textarea>
                    </div>
                    <div class="clearfix"></div>
                    <div class="text-right">
                        <a class="btn btn-default waves-effect waves-light" href="/admin/spider">返回列表</a>
                        <button type="button" class="btn btn-primary waves-effect waves-light"
                                onclick="saveSpider('publish');">
                            保存
                        </button>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>
<@footer>
<script type="text/javascript">
    /**
     * 保存网站
     * @param status
     */
    function saveSpider(status) {
        var netName = $('#spiderForm input[name=netName]').val();
        var netEng = $('#spiderForm input[name=netEng]').val();
        if (netName == '') {
            $.alert.error('请输入网站中文名称');
            return;
        }
        if (netEng == '') {
            $.alert.error('请输入网站英文名称');
            return;
        }
        var title = $('#parseTitle').val();
        // var keywords = $('#keywords').val();
        var content = $('#parseContent').val();
        if (title == '') {
            $.alert.error('请输入解析标题标签路径');
            return;
        }
        if (content == '') {
            $.alert.error('请输入解析正文标签路径');
            return;
        }
        var params = $("#spiderForm").serialize();
        var url = '/admin/spider/save';
        $.ajax({
            type: "post",
            url: url,
            data: params,
            success: function (result) {
                $.alert.ajaxSuccess(result);
                if (result.status == 200) {
                    window.location.href = '/admin/spider';
                }
            }
        });
    }
</script>
</@footer>