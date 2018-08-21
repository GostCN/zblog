<#include "tool_common/macros.ftl">
<@header title="在线格式化CSS工具 - 在线工具 | ${config.siteName}"
keywords="在线工具,CSS简单压缩,CSS高级压缩,CSS格式化,${config.siteName}"
description="在线格式化CSS工具是一款对CSS进行简单压缩、高级压缩和格式化的实用工具"
canonical="">
</@header>
       <div class="row">
           <div class="col-xs-12">
               <div class="tool-body">
                   <div class="btn-list">
                       <button class="btn btn-success" id="min" type="button"><i class="fa fa-database"></i>CSS简单压缩
                       </button>
                       <button class="btn btn-warning" id="fullMin" type="button"><i class="fa fa-database"></i>CSS高级压缩
                       </button>
                       <button class="btn btn-primary" id="format" type="button"><i class="fa fa-th"></i>CSS格式化</button>
                       <button class="btn btn-danger" onclick="clearContent()" type="button"><i
                               class="fa fa-refresh"></i>清空
                       </button>
                       <button class="btn btn-default" id="copy" type="button" data-clipboard-action="copy"
                               data-clipboard-target="#content"><i class="fa fa-copy"></i>复制
                       </button>
                   </div>
                   <div id="" class="scroll">
                       <textarea name="text" id="content" rows="14" cols="55" class="form-control"
                                 style="resize: none;padding: 0px 5px;"></textarea>
                   </div>
               </div>
               <div class="tool-footer">
                   <div class="separateline">
                       <span>工具到此结束</span>
                   </div>
                   <div class="underline">
                       <h4><i class="fa fa-lightbulb-o"></i>关于本工具 | About this tool</h4>
                   </div>
                   <ul class="tools_info">
                       <li>简单压缩：将代码压缩为每条样式占一行，更小体积，便于传输</li>
                       <li>高级压缩：将代码全部压缩为一行，进一步缩小体积</li>
                       <li>格式化：格式化代码，使之容易阅读</li>
                   </ul>
               </div>
           </div>
       </div>
<@footer>
  <script type="text/javascript" src="${config.staticWebSite}/js/jquery.format.js"></script>
  <script type="text/javascript">
      var lCSSCoder = {
          format: function (s) {
              s = s.replace(/\s*([\{\}\:\;\,])\s*/g, "$1");
              s = s.replace(/;\s*;/g, ";");
              s = s.replace(/\,[\s\.\#\d]*{/g, "{");
              s = s.replace(/([^\s])\{([^\s])/g, "$1 {\n\t$2");
              s = s.replace(/([^\s])\}([^\n]*)/g, "$1\n}\n$2");
              s = s.replace(/([^\s]);([^\s\}])/g, "$1;\n\t$2");
              return s;
          }, packAdv: function (s) {
              s = s.replace(/\/\*(.|\n)*?\*\//g, "");
              s = s.replace(/\s*([\{\}\:\;\,])\s*/g, "$1");
              s = s.replace(/\,[\s\.\#\d]*\{/g, "{");
              s = s.replace(/;\s*;/g, ";");
              s = s.match(/^\s*(\S+(\s+\S+)*)\s*$/);
              return (s == null) ? "" : s[1];
          }, pack: function (s) {
              s = s.replace(/\/\*(.|\n)*?\*\//g, "");
              s = s.replace(/\s*([\{\}\:\;\,])\s*/g, "$1");
              s = s.replace(/\,[\s\.\#\d]*\{/g, "{");
              s = s.replace(/;\s*;/g, ";");
              s = s.replace(/;\s*}/g, "}");
              s = s.replace(/([^\s])\{([^\s])/g, "$1{$2");
              s = s.replace(/([^\s])\}([^\n]s*)/g, "$1}\n$2");
              return s;
          }
      };

      function CSS(s) {
          document.getElementById("content").value = lCSSCoder[s](document.getElementById("content").value);
      }

      function clearContent() {
          document.getElementById('content').value = '';
          document.getElementById('content').select();
      }

      $("#format").click(function () {
          mainFun('format');
      })
      $("#fullMin").click(function () {
          mainFun('packAdv');
      })
      $("#min").click(function () {
          mainFun('pack');
      })

      function mainFun(type) {
          var content = $('#content').val();
          if ($.ztool.isEmpty(content)) {
              $.ztool.showAlert("请先输入需要格式化的内容", "warning", 1);
          } else {
              CSS(type);
              $.ztool.showAlert("格式化成功", "success", 1);
          }
      }
  </script>
</@footer>