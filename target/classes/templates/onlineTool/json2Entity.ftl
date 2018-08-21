<#include "tool_common/macros.ftl">
<@header title="在线Json字符串转实体类 - 在线工具 | ${config.siteName}"
keywords="在线工具,JSON字符串转实体类,JSON字符串转JavaBean,Json转JavaBean,Json生成JavaBean文件,${config.siteName}"
description="在线Json字符串转实体类是一款支持将任意复杂/简单格式的Json字符串转换成Java实体类的实用工具"
canonical="">
<link href="https://cdn.bootcss.com/iCheck/1.0.2/skins/square/green.css" rel="stylesheet">
</@header>
    <div class="row">
        <div class="col-xs-12">
            <div class="tool-body">
                <form role="form" method="post" action="/tool/json2Entity">
                    <div class="form-group">
                        <textarea placeholder="请输入要转换的Json字符串。请保证Json字符串的正确性，确保能正常生成对应的Java实体类。" id="json_input"
                                  rows="10" cols="10" class="form-control" style="resize: none;padding: 0px 5px;"
                                  name="content"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="mainClassName" class="col-sm-5 control-label" style="padding-left: 0px;">类名</label>
                        <input name="mainClassName" id="mainClassName" type="text" placeholder="请输入类名"
                               class="form-control" value="Json2Entity">
                    </div>
                    <div class="form-group">
                        <label for="packageName" class="col-sm-5 control-label" style="padding-left: 0px;">包名</label>
                        <input name="packageName" id="packageName" type="text" placeholder="请输入包名" class="form-control"
                               value="cc.cchcz.ztool.json2Entity">
                    </div>
                    <div class="separateline">
                        <span id="advancedBtn"><i class="fa fa-angle-double-down"></i>高级选项(可选)</span>
                    </div>
                    <div id="advanced" class="hide" style="display: none;">
                        <div class="form-group">
                            <label for="classNamePrefix" class="col-sm-5 control-label"
                                   style="padding-left: 0px;">类名前缀</label>
                            <input name="classNamePrefix" id="classNamePrefix" type="text" placeholder="请输入类名前缀"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="classNameSuffix" class="col-sm-5 control-label"
                                   style="padding-left: 0px;">类名后缀</label>
                            <input name="classNameSuffix" id="classNameSuffix" type="text" placeholder="请输入类名后缀"
                                   class="form-control">
                        </div>
                        <div class="separateline"></div>
                        <div class="form-group">
                            <label class="control-label col-sm-1" style="padding-left: 0px;">重写功能</label>
                            <input type="checkbox" id="hasGenerateType" name="hasGenerateType">
                            <p class="help-block"><i class="fa fa-exclamation-circle"></i>
                                选中则开启重写@equals、@hashCode、@toString的功能</p>
                        </div>
                        <div class="form-group" style="display: none" id="generateType">
                            <label class="col-sm-5 control-label" style="padding-left: 0px;">重写方式</label>
                            <select class="form-control" name="generateType">
                                <option value="ApacheBuilder">Apache Commons Lang</option>
                                <option value="Lombok">Lombok</option>
                            </select>
                            <p class="help-block"><i class="fa fa-exclamation-circle"></i> 默认采用<code>Apache
                                Commons-lang</code></p>
                        </div>
                        <a data-toggle="modal" data-target="#myModal" style="cursor: pointer;display: none"
                           id="alertModal"><span class="label label-success">Maven依赖</span></a>
                        <div class="separateline"></div>
                    </div>
                    <div class="btn-list">
                        <button class="btn btn-success" type="submit"><i class="fa fa-send"></i>生成实体类</button>
                        <button class="btn btn-primary" type="button" onclick="tempJson()"><i class="fa fa-files-o"></i>来个Json试试
                        </button>
                        <button class="btn btn-danger" type="reset"><i class="fa fa-refresh"></i>清空</button>
                    </div>
                </form>
            </div>
            <div class="tool-footer">
                <div class="separateline">
                    <span>工具到此结束</span>
                </div>
                <div class="underline">
                    <h4><i class="fa fa-lightbulb-o"></i>关于本工具 | About this tool</h4>
                </div>
                <ul class="tools_info">
                    <li>本工具支持将任意复杂/简单格式的Json字符串转换成Java实体类</li>
                    <li>在转换之前，请保证Json字符串的正确性，确保能正常生成对应的Java实体类。<a href="http://tool.cchcz.com/format/json"
                                                                  target="_blank"><i
                            class="fa fa-angle-double-right"></i>验证Json字符串</a></li>
                    <li style="color: red"><strong>有问题欢迎反馈！</strong></li>
                </ul>
                <br>
                <div class="underline">
                    <h4><i class="fa fa-lightbulb-o"></i>本工具优点 | Advantage</h4>
                </div>
                <ul class="tools_info">
                    <li style="color: red"><strong>特此声明：以下三种功能，目前已知的在线工具中<a href="http://www.tool.cchcz.com">ztool</a>是第一个提供该功能的<a
                            href="http://tool.cchcz.com">在线工具</a>网站</strong> ---- 2017-05-11 15:41
                    </li>
                    <li>
                        支持指定生成实体类的前后缀，不至于破坏项目命名规范。比如指定类名：Dog，指定前缀：animal，指定后缀：bean，则生成的文件为AnimalDogBean.java，并且关联的子类全部适用该规则
                    </li>
                    <li>支持指定是否需要重写@equals、@hashCode、@toString函数的功能，且提供
                        <mark>Apache Commons Lang</mark>
                        和
                        <mark>Lombok</mark>
                        两种方式
                    </li>
                    <li>支持自动识别类中是否需要import子类</li>
                </ul>
            </div>
        </div>
    </div>
<@footer>
  <div class="modal fade" tabindex="-1" role="dialog" id="myModal">
      <div class="modal-dialog" role="document">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                          aria-hidden="true">×</span></button>
                  <h4 class="modal-title">Maven 依赖</h4>
              </div>
              <div class="modal-body">
                  <div class="form-group">
                      <label class="control-label" style="padding-left: 0px;">1.Apache Commons Lang</label>
                      <textarea rows="6" cols="10" class="form-control" style="resize: none;padding: 0px 5px;"
                                name="content">
&lt;dependency&gt;
    &lt;groupId&gt;commons-lang&lt;/groupId&gt;
    &lt;artifactId&gt;commons-lang&lt;/artifactId&gt;
    &lt;version&gt;2.6&lt;/version&gt;
&lt;/dependency&gt;</textarea>
                  </div>
                  <div class="form-group">
                      <label class="control-label" style="padding-left: 0px;">1.Lombok</label>
                      <textarea rows="6" cols="10" class="form-control" style="resize: none;padding: 0px 5px;"
                                name="content">
&lt;dependency&gt;
    &lt;groupId&gt;org.projectlombok&lt;/groupId&gt;
    &lt;artifactId&gt;lombok&lt;/artifactId&gt;
    &lt;version&gt;1.16.8&lt;/version&gt;
    &lt;scope&gt;provided&lt;/scope&gt;
&lt;/dependency&gt;</textarea>
                  </div>
              </div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
          </div>
          <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
  </div>
<!-- /.modal -->
<script type="text/javascript" src="${config.staticWebSite}/js/jquery.format.js"></script>
<script src="${config.staticWebSite}/js/icheck.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
            increaseArea: '20%' // optional
        });
        $('input').on('ifChecked', function (event) {
            $("#hasGenerateType").prop("checked", "checked");
            $("#generateType").show();
            $("#alertModal").show();
        }).on('ifUnchecked', function (event) {
            $("#generateType").hide();
            $("#alertModal").hide();
            $("select[name=generateType]").val("ApacheBuilder");
        });
        $("#advancedBtn").on("click", function () {
            var $this = $(this);
            var $advanced = $("#advanced");
            if ($advanced.hasClass("show")) {
                $this.find("i").attr("class", "fa fa-angle-double-down");
                $advanced.addClass("hide").removeClass("show").hide();
            } else {
                $this.find("i").attr("class", "fa fa-angle-double-up");
                $advanced.addClass("show").removeClass("hide").show();
            }
        });
    });

    function tempJson() {
        $.get("/json/json2Entity.json", function (json) {
            $("#json_input").val(json);
        }, "html");
    }
</script>
</@footer>