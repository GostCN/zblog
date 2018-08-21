<#include "tool_common/macros.ftl">
<@header title="在线代码着色高亮 - 在线工具 | ${config.siteName}"
keywords="在线工具,在线代码着色高亮,${config.siteName}"
description="在线代码着色高亮"
canonical="">
</@header>
       <h1>代码着色
           <small>(采用<a id="syntaxhh" href="http://www.oschina.net/p/syntaxhighlighter">SyntaxHighlighter</a>实现)</small>
       </h1>
       <div class="rows">
           <div class="col-md-12">
               <div class="h3">
                   输入源代码
               </div>
               <textarea id="code_source" class="form-control" rows="8" cols="15">
package com.cchcz.blog.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: yadong.zhang
 * @date: 2017/12/15 17:03
 */
public class IpUtil {

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        return checkIp(ip) ? ip : (
                    checkIp(ip = request.getHeader("Proxy-Client-IP")) ? ip : (
                        checkIp(ip = request.getHeader("WL-Proxy-Client-IP")) ? ip :
                            request.getRemoteAddr()));
    }

    private static boolean checkIp(String ip) {
        return !StringUtils.isEmpty(ip) &amp;&amp; !"unknown".equalsIgnoreCase(ip);
    }
}

			</textarea>
           </div>
           <div class="col-md-12">
               <div class="h3">
                   设置代码样式规则
               </div>
               <form class="form-inline">
                   <select id="code_type" class="form-control">
                       <option value="java" selected>Java</option>
                       <option value="js">Javascript</option>
                       <option value="xml">HTML/XML</option>
                       <option value="css">CSS</option>
                       <option value="sql">SQL</option>
                       <option value="powershell">PowerShell</option>
                       <option value="groovy">Groovy</option>
                       <option value="php">PHP</option>
                       <option value="python">Python</option>
                       <option value="scala">Scala</option>
                       <!-- <option value="c">C/C++/Objectiv-C</option> -->
                       <!-- <option value="ruby">Ruby</option> -->
                       <!-- <option value="csharp">C#</option> -->
                       <!-- <option value="delphi">Delphi</option> -->
                       <!-- <option value="erlang">Erlang</option> -->
                       <!-- <option value="javafx">JavaFX</option> -->
                       <!-- <option value="perl">Perl</option> -->
                       <!-- <option value="vb">VB</option> -->
                       <!-- <option value="as3">AS3</option> -->
                       <!-- <option value="bash">Bash</option> -->
                       <!-- <option value="coldfusion">ColdFusion</option> -->
                       <!-- <option value="diff">Diff</option> -->
                       <!-- <option value="plain">Plain</option> -->
                       <!-- <option value="sass">Sass</option> --> </select>
                   <select id="style_type" class="form-control">
                       <option value="shCoreDefault">默认样式</option>
                       <option value="shCoreEclipse">Eclipse样式</option>
                       <option value="shCoreEmacs">Emacs样式</option>
                       <option value="shCoreDjango">Django样式</option>
                       <option value="shCoreMDUltra">MDUltra样式</option>
                       <option value="shCoreMidnight">Midnight样式</option>
                       <option value="shCoreRDark">RDark样式</option>
                       <!-- <option value="shCoreFadetogrey">FadeToGrey样式</option> --> </select>
                   <label class="checkbox"><input type="checkbox" id="gutter" checked>显示行号</label>
                   <label class="checkbox"><input type="checkbox" id="to_html" checked>生成HTML</label>
                   <label class="checkbox"><input type="checkbox" id="trim" checked>去掉首尾空格</label>
                   <button type="button" class="btn btn-primary" onclick="render();">着色</button>
                   <br>
               </form>
           </div>
           <div class="col-md-12" id="html_box">
               <div class="h3">
                   HTML 代码
               </div>
               <textarea id="html" onclick="this.focus();this.select();" class="form-control" rows="8"
                         cols="15"></textarea>
           </div>
           <div class="col-md-12">
               <div class="h3">
                   HTML 代码预览
               </div>
               <div id="html_preview_box"></div>
           </div>
           <div class="col-md-12">
               <div class="tool-footer">
                   <div class="separateline">
                       <span>工具到此结束</span>
                   </div>
                   <div class="underline">
                       <h4><i class="fa fa-lightbulb-o"></i>关于本工具 | About this tool</h4>
                   </div>
                   <ul class="tools_info">
                       <li>代码着色高亮</li>
                       <li style="color: red"><strong>有问题欢迎反馈！</strong></li>
                   </ul>
               </div>
           </div>
       </div>
<@footer>
  <script src="${config.staticWebSite}/js/shCore.min.js"></script>
  <script src="${config.staticWebSite}/js/shBrushAll.js"></script>
  <script>
      var REGX_HTML_ENCODE = /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g;
      function encodeHtml(s) {
          return (typeof s != "string") ? s :
                  s.replace(REGX_HTML_ENCODE,
                          function ($0) {
                              var c = $0.charCodeAt(0), r = ["&#"];
                              c = (c == 0x20) ? 0xA0 : c;
                              r.push(c);
                              r.push(";");
                              return r.join("");
                          });
      }

      function change(type) {
          if (type) {
              //更改样式
              document.getElementById("css").setAttribute("href", "https://cdn.bootcss.com/SyntaxHighlighter/3.0.83/styles/" + type + ".min.css");
          }
          render();
      }

      $(document).ready(function () {
          render();
          SyntaxHighlighter.all();
          $("#style_type").change(function () {
              change($(this).val());
          });
          $("#code_type, #to_html, #gutter, #trim").click(function () {
              render();
          });
          $("#syntaxhh").popover({
              title: "SyntaxHighLighter",
              content: "SyntaxHighlighter是一套在浏览器上对各种代码进行语法着色的独立 JavaScript库。",
              placement: "bottom"
          });
      });

      function render(trim) {
          var codeSource = $("#code_source").val();
          if ($("#trim").prop("checked")) {
              codeSource = $.trim(codeSource);
          }
          $("#html_preview_box").empty();
          $("#html_preview_box").html("<pre>" + encodeHtml(codeSource) + "</pre>");
          var class_v = "brush :" + $("#code_type").val() + ";";
          if (!$("#gutter").prop("checked")) {
              class_v = class_v + "gutter: false;";
          }
          $("#html_preview_box pre").addClass(class_v);
          SyntaxHighlighter.highlight();
          toHTML();
      }

      function toHTML() {
          var html = "<link rel='stylesheet' type='text/css' href='";
          html = html + $("#css").attr("href");
          html = html + "'/>";
          html = html + $(".syntaxhighlighter").parent().html();
          $("#html_box textarea").val(html);
          if ($("#to_html").prop("checked")) {
              $("#html_box").show();
          }
          else {
              $("#html_box").hide();
          }
      }
  </script>
</@footer>