<#include "/admin/include/macros.ftl">
<@header>
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
                <h4 class="page-title">爬虫spider</h4>
                <h6 style="color: gray">爬取的文章,需要手动调整后才能发布</h6>
            </div>
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <form id="cform" class="form-inline" role="form">
                            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                                <input type="text" class="form-control" style="width: 80%" id="targetUrl"
                                       placeholder="请输入目标网址"/>
                                <select id="netAddType" class="form-control clearfix"
                                        style="width: 15%" data-placeholder="请选择网址类型">
                                    <option value="010">文章地址</option>
                                    <option value="020">目录地址</option>
                                </select>
                            </div>
                            <button id="save-spider-btn" type="button"
                                    class="btn btn-success waves-effect waves-light m-l-10">开始爬取
                            </button>
                            <button id="add-spider-btn" type="button"
                                    class="btn btn-success waves-effect waves-light m-l-10">添加
                            </button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="20%">网站名称</th>
                        <th width="15%">网站英文</th>
                        <th width="15%">操作时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list spiders.list as spider>
                    <tr cid="${spider.id}">
                        <td>
                            <a href="/admin/spider/${spider.id}">${spider.netName}</a>
                        </td>
                        <td>
                            ${spider.netEng}
                        </td>
                        <td>
                            ${spider.updateTime?datetime}
                        </td>
                        <td>
                            <a href="/admin/spider/${spider.id}"
                               class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i
                                    class="fa fa-edit"></i> <span>编辑</span></a>
                            <a href="javascript:void(0)" onclick="'delSpider('${spider.id}');'"
                               class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i
                                    class="fa fa-trash-o"></i> <span>删除</span></a>
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<@footer>
<script type="text/javascript">
    $('#save-spider-btn').click(function () {
        var targetUrl = $('#cform #targetUrl').val();
        var netAddType = $('#cform #netAddType').val();
        if (targetUrl && targetUrl != '') {
            $.ajax({
                type: "post",
                url: '/admin/spider/publish',
                data: {targetUrl: targetUrl, netAddType: netAddType},
                success: function (result) {
                    $('#targetUrl #targetUrl').val('');
                    $.alert.ajaxSuccess(result);
                    if (result.status == 200) {
                        window.location.reload();
                    }
                }
            });
        }
    });
    $('#add-spider-btn').click(function () {
        window.location.href = "/admin/spider/add";
    });

    function delSpider(cid) {
        tale.alertConfirm({
            title: '确定删除该网站吗?',
            then: function () {
                tale.post({
                    url: '/admin/spider/delete',
                    data: {sid: cid},
                    success: function (result) {
                        $.alert.ajaxSuccess(result);
                        if (result.status == 200) {
                            window.location.reload();
                        }
                    }
                });
            }
        });
    }
</script>
</@footer>