<#include "tool_common/macros.ftl">
<@header title="域名注册查询 - 在线工具 | ${config.siteName}"
keywords="在线工具,域名注册,域名查询,域名批量查询,域名状态查询,${config.siteName}"
description="在线查询域名注册情况是一款支持精确后缀查询和全后缀批量查询的实用工具"
canonical="">
</@header>
       <div class="row">
           <div class="col-md-12">
               <form role="form" method="post" action="/onlineTool/domain" class="loading">
                   <div style="padding: 20px 20px 10px 20px;margin: 0 auto;background: #fcfcfc;border: 1px solid #fdfdfd;">
                       <div class="form-group input-group" style="width: 60%;margin: 0 auto;">
                           <input type="text" class="form-control input-lg" name="domain" value="">
                           <span class="input-group-btn"> <select name="domainSuffix" id=""
                                                                  class="form-control input-lg" style="width: 125px;"> <option
                                   value="">全部</option> <option value=".com">.com</option> <option
                                   value=".cn">.cn</option> <option value=".com.cn">.com.cn</option> <option
                                   value=".net">.net</option> <option value=".cc">.cc</option> <option
                                   value=".me">.me</option> <option value=".vip">.vip</option> <option value=".wang">.wang</option> <option
                                   value=".top">.top</option> <option value=".xin">.xin</option> <option value=".win">.win</option> <option
                                   value=".club">.club</option> <option value=".org">.org</option> <option
                                   value=".org.cn">.org.cn</option> <option value=".news">.news</option> <option
                                   value=".site">.site</option> <option value=".online">.online</option> <option
                                   value=".wiki">.wiki</option> <option value=".biz">.biz</option> <option value=".red">.red</option> <option
                                   value=".kim">.kim</option> <option value=".pro">.pro</option> <option value=".pub">.pub</option> <option
                                   value=".tv">.tv</option> <option value=".mobi">.mobi</option> <option value=".asia">.asia</option> <option
                                   value=".info">.info</option> <option value=".name">.name</option> <option
                                   value=".tech">.tech</option> <option value=".press">.press</option> <option
                                   value=".space">.space</option> <option value=".website">.website</option> <option
                                   value=".net.cn">.net.cn</option> </select> </span>
                           <span class="input-group-btn"> <button class="btn btn-default btn-lg" type="submit"> <i
                                   class="fa fa-search"></i> </button> </span>
                       </div>
                       <div style="clear: both;width: 60%;margin: 0 auto;padding-top: 5px;">
                           <a href="https://wanwang.aliyun.com/help/price.html" target="_blank"><span
                                   class="label label-success">域名价格总览</span></a>
                           <a href="https://mi.aliyun.com/shop/19462" target="_blank"><span class="label label-success">精品域名购买</span></a>
                       </div>
                   </div>
                   <p style="height: 10px"></p>
                   <div class="table-responsive" style="background: #fdfdfd;border: 1px solid #fcfcfc;">
                       <table class="table">
                           <thead>
                           <tr>
                               <th style="width: 40%;">域名</th>
                               <th>查询结果</th>
                           </tr>
                           </thead>
                           <tbody>
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
                       <li>想注册域名但不知道是否可用？可以使用本工具一键查询！</li>
                       <li>本工具支持
                           <mark>精确后缀查询</mark>
                           和
                           <mark>全后缀批量查询</mark>
                       </li>
                       <li>
                           <mark>精确后缀查询</mark>
                           可以快速查询某个后缀的域名是否已经被注册
                       </li>
                       <li>
                           <mark>全后缀批量查询（全部）</mark>
                           可以快速查询全部后缀的注册情况
                       </li>
                       <li style="color: red"><strong>本工具目前支持33种常用的后缀</strong></li>
                       <li style="color: red"><strong>有问题欢迎反馈！</strong></li>
                   </ul>
               </div>
           </div>
       </div>
<@footer>
</@footer>