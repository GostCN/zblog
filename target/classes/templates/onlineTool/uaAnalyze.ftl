<#include "tool_common/macros.ftl">
<@header title="在线分析UserAgent信息 - 在线工具 | ${config.siteName}"
keywords="在线工具,userAgent解析,userAgent在线解析,检测浏览器User-Agent信息,监测UA信息,UserAgent,ua分析,ua查询,userAgent分析,userAgent查询,根据userAgent判断设备,${config.siteName}"
description="在线分析UserAgent信息是一款在线解析User-Agent信息的工具，可以根据UA一键分析所属的浏览器信息、浏览器版本信息以及对应的系统、设备信息。"
canonical="">
</@header>
    <div class="row">
        <div class="col-md-12">
            <form role="form" method="post" action="/onlineTool/ua" class="loading">
                <div style="padding: 20px 20px 10px 20px;margin: 0 auto;background: #fcfcfc;border: 1px solid #fdfdfd;">
                    <div class="form-group input-group" style="width: 90%;margin: 0 auto;">
                        <input type="text" class="form-control input-lg" name="userAgent"
                               value="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36">
                        <span class="input-group-btn"> <button class="btn btn-default btn-lg" type="submit"> <i
                                class="fa fa-search"></i>分析 </button> </span>
                    </div>
                    <div style="clear: both;width: 90%;margin: 0 auto;padding-top: 5px;">
                        <span class="label label-success" style="cursor: pointer" onclick="showModal('UserAgent')"><i
                                class="fa fa-list-ul"></i> 常用的User-Agent</span>
                    </div>
                </div>
                <p style="height: 10px"></p>
                <div class="table-responsive" style="background: #fdfdfd;border: 1px solid #fcfcfc;">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th colspan="2">Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML,
                                like Gecko) Chrome/53.0.2785.143 Safari/537.36
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td colspan="2" style="color: #5cb85c;font-weight: bold;">浏览器信息</td>
                        </tr>
                        <tr>
                            <td><strong>浏览器名称</strong></td>
                            <td><img src="${config.staticWebSite}/images/chrome.png" width="16" height="16" alt="Chrome浏览器图标" title="Chrome"
                                     style="margin-right: 5px;">Chrome
                            </td>
                        </tr>
                        <tr>
                            <td><strong>浏览器引擎</strong></td>
                            <td>WebKit</td>
                        </tr>
                        <tr>
                            <td style="width: 200px;">浏览器类型</td>
                            <td>Browser <i class="fa fa-question-circle" onclick="showModal('browser')"
                                           style="margin-left: 5px;"></i></td>
                        </tr>
                        <tr>
                            <td>浏览器厂商</td>
                            <td>Google Inc.</td>
                        </tr>
                        <tr>
                            <td>浏览器产品系列</td>
                            <td>Chrome</td>
                        </tr>
                        <tr>
                            <td colspan="2" style="color: #5cb85c;font-weight: bold;">操作系统信息</td>
                        </tr>
                        <tr>
                            <td><strong>系统名称</strong></td>
                            <td><img src="${config.staticWebSite}/images/mac.png" alt="Mac OS X系统图标" width="16" height="16" title="Mac OS X"
                                     style="margin-right: 5px;">Mac OS X
                            </td>
                        </tr>
                        <tr>
                            <td>设备类型</td>
                            <td>Computer<i class="fa fa-question-circle" onclick="showModal('os')"
                                           style="margin-left: 5px;"></i></td>
                        </tr>
                        <tr>
                            <td>系统产品系列</td>
                            <td>Mac OS X</td>
                        </tr>
                        <tr>
                            <td>系统生产厂商</td>
                            <td>Apple Inc.</td>
                        </tr>
                        <tr>
                            <td colspan="2" style="color: #5cb85c;font-weight: bold;">版本信息</td>
                        </tr>
                        <tr>
                            <td>主版本</td>
                            <td>53</td>
                        </tr>
                        <tr>
                            <td>小版本</td>
                            <td>0</td>
                        </tr>
                        <tr>
                            <td><strong>完整版本</strong></td>
                            <td>53.0.2785.143</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </form>
            <div class="tool-footer">
                <div class="separateline">
                    <span>工具到此结束</span>
                </div>
                <div class="underline">
                    <h4><i class="fa fa-lightbulb-o"></i>关于本工具 | About this tool</h4>
                </div>
                <ul class="tools_info">
                    <li>可以根据UA一键分析所属的浏览器信息、浏览器版本信息以及对应的系统、设备信息</li>
                    <li style="color: red"><strong>有问题欢迎反馈！</strong></li>
                </ul>
            </div>
        </div>
    </div> 
    <div class="modal fade" id="helpModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
         style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <h4 class="modal-title help-title"></h4>
                </div>
                <div class="modal-body help">
                    <div class="browser">
                        <table class="table table-striped table-bordered">
                            <tbody>
                            <tr>
                                <td>Browser</td>
                                <td>一般指Web端的浏览器</td>
                            </tr>
                            <tr>
                                <td>Browser (mobile)</td>
                                <td>指手机端的浏览器</td>
                            </tr>
                            <tr>
                                <td>Robot</td>
                                <td>指一些可识别的爬虫</td>
                            </tr>
                            <tr>
                                <td>Email Client</td>
                                <td>电子邮件客户端</td>
                            </tr>
                            <tr>
                                <td>Downloading tool</td>
                                <td>下载工具</td>
                            </tr>
                            <tr>
                                <td>Application</td>
                                <td>其他应用</td>
                            </tr>
                            <tr>
                                <td>unknown</td>
                                <td>未识别</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="os">
                        <table class="table table-striped table-bordered">
                            <tbody>
                            <tr>
                                <td>Computer</td>
                                <td>电脑设备</td>
                            </tr>
                            <tr>
                                <td>Mobile</td>
                                <td>移动设备</td>
                            </tr>
                            <tr>
                                <td>Game console</td>
                                <td>游戏机</td>
                            </tr>
                            <tr>
                                <td>Digital media receiver</td>
                                <td>数字媒体接收机</td>
                            </tr>
                            <tr>
                                <td>Wearable computer</td>
                                <td>可穿戴式电脑</td>
                            </tr>
                            <tr>
                                <td>Unknown</td>
                                <td>未识别</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="UserAgent" style="height: 450px;overflow-y: scroll;">
                        <table class="table table-striped table-bordered">
                            <tbody>
                            <tr>
                                <td>IE 9.0</td>
                                <td>Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;</td>
                            </tr>
                            <tr>
                                <td>IE 8.0</td>
                                <td>Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)</td>
                            </tr>
                            <tr>
                                <td>IE 7.0</td>
                                <td>Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)</td>
                            </tr>
                            <tr>
                                <td>Firefox – MAC</td>
                                <td>Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv,2.0.1) Gecko/20100101
                                    Firefox/4.0.1
                                </td>
                            </tr>
                            <tr>
                                <td>Firefox – Windows</td>
                                <td>Mozilla/5.0 (Windows NT 6.1; rv,2.0.1) Gecko/20100101 Firefox/4.0.1</td>
                            </tr>
                            <tr>
                                <td>Opera – MAC</td>
                                <td>Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11
                                </td>
                            </tr>
                            <tr>
                                <td>Opera – Windows</td>
                                <td>Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11</td>
                            </tr>
                            <tr>
                                <td>傲游</td>
                                <td>Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)</td>
                            </tr>
                            <tr>
                                <td>腾讯TT</td>
                                <td>Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)</td>
                            </tr>
                            <tr>
                                <td>世界之窗</td>
                                <td>Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)</td>
                            </tr>
                            <tr>
                                <td>搜狗浏览器</td>
                                <td>Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0;
                                    SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)
                                </td>
                            </tr>
                            <tr>
                                <td>360浏览器</td>
                                <td>Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)</td>
                            </tr>
                            <tr>
                                <td>QQ浏览器 For android</td>
                                <td>MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22;
                                    CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1
                                </td>
                            </tr>
                            <tr>
                                <td>safari iOS 4.33 – iPhone</td>
                                <td>Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us)
                                    AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5
                                </td>
                            </tr>
                            </tbody>
                        </table>
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
<@footer>
  <script>
      function showModal(type) {
          $(".help-title").html(type + " 类型介绍")
          $(".help div").css({"display": "none"});
          $("." + type).show();
          $("#helpModal").modal("show");
      }
  </script>
</@footer>