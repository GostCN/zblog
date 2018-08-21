<#include "/admin/include/macros.ftl">
<@header>
    <link href="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.css" rel="stylesheet"/>
<style>
    #dropzone {
        margin-bottom: 3rem;
    }

    .dropzone {
        border: 2px dashed #0087F7;
        border-radius: 5px;
        background: white;
    }

    .dropzone .dz-message {
        font-weight: 400;
    }

    .dropzone .dz-message .note {
        font-size: 0.8em;
        font-weight: 200;
        display: block;
        margin-top: 1.4rem;
    }

    .attach-img {
        width: 100px;
        height: 100px;
        border-radius: 10px;
        box-shadow: 0px 0px 8px #333;
    }

    .attach-text {
        font-size: 12px;
        font-weight: 300;
    }

    .attach-img:hover {
        background-color: #f9f9f9;
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
                    <li class="active">文件管理</li>
                </ol>
            </div>
            <div class="row">
                <div class="col-md-12 portlets">
                    <!-- Your awesome content goes here -->
                    <div class="m-b-30">
                        <form action="#" class="dropzone" id="dropzone">
                            <div class="fallback">
                                <input name="file" type="file" multiple="multiple"/>
                            </div>
                            <div class="dz-message">
                                将文件拖至此处或点击上传.
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-12 attach">
                <#if attachs?? && (attachs.list?size > 0)>
                    <#list attachs.list as attach>
                        <div class="col-md-2 text-center m-t-10">
                            <a href="${attach.fkey}" target="_blank">
                                <img class="attach-img"
                                     src="<#if attach.ftype == 'image'>${attach.fkey} <#else>/assets/images/attach.png</#if>"
                                     title="${attach.fname}"/>
                            </a>
                            <div class="clearfix m-t-10">
                                <span class="attach-text" data-toggle="tooltip" data-placement="top"
                                      attr="data-original-title=${attach.fname}" text="${attach.fname}"></span>
                            </div>
                            <div class="clearfix">
                                <button url="${attach.fkey}" type="button"
                                        class="btn btn-warning btn-sm waves-effect waves-light m-t-5 copy">
                                    <i class="fa fa-copy"></i> <span>复制</span>
                                </button>
                                <button type="button"
                                        class="btn btn-danger btn-sm waves-effect waves-light m-t-5"
                                        onclick="delAttach('${attach.id}');">
                                    <i class="fa fa-trash-o"></i> <span>删除</span>
                                </button>
                            </div>
                        </div>
                    </#list>
                <#else>
                <div class="row">
                    <div class="col-md-4 text-center">
                        <div class="alert alert-warning">
                            目前还没有一个附件呢，你可以上传试试!
                        </div>
                    </div>
                </div>
                </#if>
            </div>
        </div>
            <#if attachs?? && (attachs.list?size > 0)>
                <div class="clearfix">
                    <ul class="pagination m-b-5 pull-right">
    <#if attachs.hasPreviousPage>
                            <li>
                                <a href="attachs?pageNumber=${attachs.prePage}" aria-label="Previous">
                                    <i class="fa fa-angle-left"></i>&nbsp;上一页
                                </a>
                            </li>
    </#if>
    <#list attachs.navigatepageNums as nav>
                    <li class="<#if nav==attachs.pageNum>active<#else></#if>">
                        <a href="attachs?pageNumber=${nav}">${nav}</a>
                    </li>
    </#list>
            <#if attachs.hasNextPage>
                <li>
                    <a href="attachs?pageNumber=${attachs.nextPage}" aria-label="Next">
                        下一页&nbsp;<i class="fa fa-angle-right"></i>
                    </a>
                </li>
            </#if>
                        <li><span text="'共'+${attachs.pages}+'页'"></span></li>
                    </ul>
                </div>
            </#if>
    </div>
</div>
<@footer>
<script src="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.js"></script>
<script src="//cdn.bootcss.com/clipboard.js/1.6.0/clipboard.min.js"></script>
<script type="text/javascript">
    Dropzone.autoDiscover = false;
    $("#dropzone").dropzone({
        paramName: "file",
        url: "/admin/attachs/upload",
        maxFilesize: 1,
        init: function () {
            this.on("success", function (file, result) {
                if (result.status !== 200) {
                    $.alert.info('上传文件大于1M');
                }
                setTimeout(function () {
                    window.location.reload();
                }, 200);
            });
        }
    });
    var clipboard = new Clipboard('button.copy', {
        text: function (trigger) {
            return $(trigger).attr('url');
        }
    });

    clipboard.on('success', function (e) {
        console.info('Action:', e.action);
        console.info('Text:', e.text);
        console.info('Trigger:', e.trigger);
        e.clearSelection();
    });

    function delAttach(id) {
        $.alert.confirm('确定删除该附件吗?', function () {
            $.ajax({
                type: 'post',
                url: '/admin/attachs/delete',
                data: {id: id},
                success: function (result) {
                    $.alert.ajaxSuccess(result);
                    window.location.reload();
                }
            });
        }, function () {
        }, 5000);
    }
</script>
</@footer>