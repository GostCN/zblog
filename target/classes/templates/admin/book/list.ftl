<#include "/admin/include/macros.ftl">
<@header></@header>
<div class="">
    <div class="clearfix"></div>
    <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <ol class="breadcrumb">
                <li><a href="/admin/">首页</a></li>
                <li class="active">技术资料</li>
            </ol>
            <div class="x_panel">
                <div class="x_content">
                    <div class="<#--table-responsive-->">
                        <div class="btn-group hidden-xs" id="toolbar">
                            <button id="btn_add" type="button" class="btn btn-default" title="新增书籍">
                                <i class="fa fa-plus"></i> 新增书籍
                            </button>
                            <button id="btn_delete_ids" type="button" class="btn btn-default" title="删除选中">
                                <i class="fa fa-trash-o"></i> 批量删除
                            </button>
                        </div>
                        <table id="tablelist">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--添加弹框-->
<div class="modal fade" id="addOrUpdateModal" tabindex="-1" role="dialog" aria-labelledby="addroleLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="addroleLabel">添加书籍</h4>
            </div>
            <div class="modal-body">
                <form id="addOrUpdateForm" class="form-horizontal form-label-left" novalidate>
                    <input type="hidden" name="id">
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="title">书名: <span
                                class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" name="title" id="title"
                                   required="required" placeholder="请输入书名"/>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="description">描述: <span
                                class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <textarea class="form-control col-md-7 col-xs-12" id="description"
                                      name="description"
                                      placeholder="请输入描述">
                            </textarea>
                        </div>
                    </div>
                    <div class="item form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="outLink">外链: <span
                                class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <input type="text" class="form-control col-md-7 col-xs-12" id="outLink" name="outLink"
                                   placeholder="请输入外链"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-close"> 关闭</i>
                </button>
                <button type="button" class="btn btn-success addOrUpdateBtn"><i class="fa fa-save"> 保存</i></button>
            </div>
        </div>
    </div>
</div>
<!--/添加弹框-->
<@footer>
    <script>
        /**
         * 操作按钮
         * @param code
         * @param row
         * @param index
         * @returns {string}
         */
        function operateFormatter(code, row, index) {
            var trId = row.id;
            var operateBtn = [
                '<a class="btn btn-xs btn-success btn-upshelf" data-id="' + trId + '"><i class="fa fa-thumbs-o-up"></i>上架</a>',
                '<a class="btn btn-xs btn-primary btn-update" data-id="' + trId + '"><i class="fa fa-edit"></i>编辑</a>',
                '<a class="btn btn-xs btn-danger btn-remove" data-id="' + trId + '"><i class="fa fa-trash-o"></i>删除</a>'
            ];
            return operateBtn.join('');
        }

        $(function () {
            var options = {
                modalName: "书籍",
                url: "/admin/book/list",
                getInfoUrl: "/admin/book/get/{id}",
                updateUrl: "/admin/book/edit",
                removeUrl: "/admin/book/remove",
                createUrl: "/admin/book/add",
                columns: [
                    {
                        checkbox: true
                    }, {
                        field: 'id',
                        title: 'ID',
                        width: '60px',
                        editable: false
                    }, {
                        field: 'title',
                        title: '书名',
                        width: '150px',
                        editable: false,
                        formatter: function (code, row, index) {
                            var id = row.id;
                            return '<a href="' + appConfig.wwwPath + '/book/' + id + '" target="_blank">' + row.title + '</a>';
                        }
                    }, {
                        field: 'description',
                        title: '描述',
                        width: '150px',
                        editable: false,
                    }, {
                        field: 'status',
                        title: '状态',
                        width: '150px',
                        editable: false,
                        formatter: function (code) {
                            return code == '1' ? '<span style="color: #26B99A;font-weight: 700">已上架</span>' : '已下架';
                        }
                    }, {
                        field: 'outLink',
                        title: '外链',
                        editable: false
                    }, {
                        field: 'watchCount',
                        title: '浏览',
                        editable: false,
                        width: '50px'
                    }, {
                        field: 'starCount',
                        title: '点赞',
                        editable: false,
                        width: '50px'
                    }, {
                        field: 'createTime',
                        title: '发布时间',
                        editable: false,
                        width: '100px',
                        formatter: function (code) {
                            return new Date(code).format("yyyy-MM-dd hh:mm:ss")
                        }
                    }, {
                        field: 'operate',
                        title: '操作',
                        width: '150px',
                        formatter: operateFormatter //自定义方法，添加操作按钮
                    }
                ]
            };
            //1.初始化Table
            $.tableUtil.init(options);
            //2.初始化Button的点击事件
            $.buttonUtil.init(options);

            /**
             * 上架
             */
            $('#tablelist').on('click', '.btn-upshelf', function () {
                var $this = $(this);
                var id = $this.attr("data-id");
                update("upshelf", id);
            });

            function update(type, id) {
                $.ajax({
                    type: "post",
                    url: "/admin/book/update/" + type,
                    traditional: true,
                    data: {'id': id},
                    success: function (json) {
                        $.alert.ajaxSuccess(json);
                        window.location.reload();
                    },
                    error: $.alert.ajaxError
                });
            }
        });
    </script>
</@footer>