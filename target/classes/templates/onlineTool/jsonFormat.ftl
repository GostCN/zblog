<#include "tool_common/macros.ftl">
<@header title="在线格式化Json工具 - 在线工具 | ${config.siteName}"
keywords="在线工具,JSON压缩,JSON转XML,${config.siteName}"
description="在线格式化Json工具是一款在线JSON压缩、JSON转XML实用工具"
canonical="">
</@header>
        <div class="row">
            <div class="col-xs-12">
                <div class="tool-body">
                    <main class="row-fluid">
                        <div class="col-md-5" style="padding:0;">
                            <textarea id="json-src" placeholder="在此输入json字符串或XML字符串..."
                                      class="form-control adapter_height json-textarea"></textarea>
                        </div>
                        <div class="col-md-7" style="padding:0;">
                            <div class="navi json-menu">
                                <button class="btn btn-success tip zip"><i class="fa fa-database"></i>JSON压缩</button>
                                <button class="btn btn-warning tip xml"><i class="fa fa-file-excel-o"></i>JSON转XML
                                </button>
                                <button class="btn btn-danger tip clear"><i class="fa fa-trash"></i>清空</button>
                                <button class="btn btn-default" id="copy" type="button" data-clipboard-action="copy"
                                        data-clipboard-target="#json-target"><i class="fa fa-copy"></i>复制
                                </button>
                            </div>
                            <div id="json-target" class="adapter_height json-result">
                            </div>
                            <form id="form-save" method="POST">
                                <input type="hidden" value="" id="txt-content" name="content">
                            </form>
                        </div>
                        <br style="clear:both;">
                    </main>
                </div>
                <div class="tool-footer">
                    <div class="separateline">
                        <span>工具到此结束</span>
                    </div>
                    <div class="underline">
                        <h4><i class="fa fa-lightbulb-o"></i>关于本工具 | About this tool</h4>
                    </div>
                    <ul class="tools_info">
                        <li>JSON压缩：将代码压缩，以缩小体积</li>
                        <li>JSON转XML：将JSON格式的数据转换成XML格式</li>
                    </ul>
                </div>
            </div>
        </div>
<@footer>
<script type="text/javascript" src="${config.staticWebSite}/js/jquery.message.js"></script>
<script type="text/javascript" src="${config.staticWebSite}/js/jquery.json.js"></script>
<script type="text/javascript" src="${config.staticWebSite}/js/jquery.xml2json.js"></script>
<script type="text/javascript" src="${config.staticWebSite}/js/jquery.json2xml.js"></script>
<script type="text/javascript" src="${config.staticWebSite}/js/json2.min.js"></script>
<script type="text/javascript" src="${config.staticWebSite}/js/jsonlint.min.js"></script>
<script type="text/javascript">
    var current_json = '';
    var current_json_str = '';
    var xml_flag = false;
    var zip_flag = false;
    $('.tip').tooltip();

    function init() {
        xml_flag = false;
        zip_flag = false;
    }

    $('#json-src').keyup(function () {
        init();
        var content = $.trim($(this).val());
        var result = '';
        if (content != '') {
            //如果是xml,那么转换为json
            if (content.substr(0, 1) === '<' && content.substr(-1, 1) === '>') {
                try {
                    var json_obj = $.xml2json(content);
                    content = JSON.stringify(json_obj);
                } catch (e) {
                    result = '解析错误：<span style="color: #f1592a;font-weight:bold;">' + e.message + '</span>';
                    current_json_str = result;
                    $('#json-target').html(result);
                    return false;
                }

            }
            try {
                current_json = jsonlint.parse(content);
                current_json_str = JSON.stringify(current_json);
                //current_json = JSON.parse(content);
                result = new JSONFormat(content, 4).toString();
            } catch (e) {
                result = '<span style="color: #f1592a;font-weight:bold;">' + e + '</span>';
                current_json_str = result;
            }

            $('#json-target').html(result);
        } else {
            $('#json-target').html('');
        }

    });
    $('.xml').click(function () {
        var content = $.trim($('#json-src').val());
        if (content == null || content == "") {
            $.ztool.showAlert("还没输入内容...", "error", 1);
            return;
        }
        if (xml_flag) {
            $(this).text("JSON转XML");
            $('#json-src').keyup();
        } else {
            $(this).text("XML转JSON");
            var result = $.json2xml(current_json);
            $('#json-target').html('<textarea style="width:100%;height:100%;border:0;resize:none;">' + result + '</textarea>');
            xml_flag = true;
        }

    });
    $('.zip').click(function () {
        var content = $.trim($('#json-src').val());
        if (content == null || content == "") {
            $.ztool.showAlert("还没输入内容...", "error", 1);
            return;
        }
        if (zip_flag) {
            $(this).text("JSON压缩");
            $('#json-src').keyup();
        } else {
            $(this).text("JSON格式化");
            $('#json-target').html(current_json_str);
            zip_flag = true;
        }

    });
    $('.clear').click(function () {
        current_json_str = '';
        current_json = '';
        $('#json-src').val('');
        $('#json-target').html('');
    });
    $('.save').click(function () {
        var content = JSON.stringify(current_json);
        $('#txt-content').val(content);
        $("#form-save").submit();
    });
    $('#json-src').keyup();

    function getWinHeight() {
        var winHeight = 0;
        if (window.innerHeight)
            winHeight = window.innerHeight;
        else if ((document.body) && (document.body.clientHeight))
            winHeight = document.body.clientHeight;
        return winHeight;
    }

    $(window).resize(function () {
        ///getWinHeight() - 117
        $('#json-src').css('height', "463px");
        $('#json-target').css('height', "428px");
    });
    $(window).resize();
</script>
</@footer>