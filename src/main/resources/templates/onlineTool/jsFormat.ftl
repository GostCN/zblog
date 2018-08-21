<#include "tool_common/macros.ftl">
<@header title="在线格式化Javascript工具 - 在线工具 | ${config.siteName}"
keywords="在线工具,JavaScript简单压缩,JavaScript高级压缩,JavaScript格式化,JavaScript混淆,JavaScript解混淆,${config.siteName}"
description="在线格式化Javascript工具是一款对JavaScript进行简单压缩、高级压缩、格式化、混淆和解混淆的实用工具"
canonical="">
</@header>
       <div class="row">
           <div class="col-xs-12">
               <div class="tool-body">
       <textarea placeholder="贴入要格式化或压缩的JS代码" id="js_input" rows="10" cols="10" class="form-control"
                 style="resize: none;padding: 0px 5px;">
/*   简单压缩：去掉代码中多余的注释、换行、空格等			*/
/*   高级压缩：将代码压缩为更小体积，便于传输	*/
/*   格式化：格式化代码，使之容易阅读		*/
/*   混淆：将代码的中变量名简短化以减小体积，但可读性差	*/
/*   解混淆：将混淆后的代码恢复混淆前的内容	*/
/*   复制：将下方文本域内的内容复制到粘贴板	*/

var ztool = {
isEmpty : function(value){
    if (value == null || this.trim(value) == "") {
        return true;
    }
    return false;
},
isInteger:function(){
    return (new RegExp(/^\d+$/).test(this));
},
isNumber: function(value, element) {
    return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
},
trim:function(value){
    if(this.isEmpty(value)){
        return "";
    }
    return value.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
},
html2Txt : function (value) {
    value = this.trim(value);
    value = value.replace(/(\n)/g, "");
    value = value.replace(/(\t)/g, "");
    value = value.replace(/(\r)/g, "");
    value = value.replace(/&lt;\/?[^&gt;]*&gt;/g, "");
    value = value.replace(/\s*/g, "");
    return value;
}
};
                    </textarea>
                   <div class="btn-list">
                       <button class="btn btn-success" onclick="javascript:js_compress(false);"><i
                               class="fa fa-database"></i>JS简单压缩
                       </button>
                       <button class="btn btn-warning" onclick="javascript:js_compress(true);"><i
                               class="fa fa-database"></i>JS高级压缩
                       </button>
                       <button class="btn btn-primary" onclick="javascript:js_format();"><i class="fa fa-th"></i>JS格式化
                       </button>
                       <button class="btn btn-danger" onclick="javascript:js_encode();"><i class="fa fa-random"></i>JS混淆
                       </button>
                       <button class="btn btn-info" onclick="javascript:js_decode();"><i class="fa fa-magic"></i>JS解混淆
                       </button>
                       <button class="btn btn-default" id="copy" type="button" data-clipboard-action="copy"
                               data-clipboard-target="#js_output"><i class="fa fa-copy"></i>复制
                       </button>
                   </div>
                   <strong class="explainer">操作提示：</strong>
                   <span id="output_rate" style="margin-left: 10px;color: #f54343;" class="green"></span>
                   <textarea id="js_output" rows="10" cols="10" class="form-control"
                             style="resize: none;padding: 0px 5px;"></textarea>
               </div>
               <div class="tool-footer">
                   <div class="separateline">
                       <span>工具到此结束</span>
                   </div>
                   <div class="underline">
                       <h4><i class="fa fa-lightbulb-o"></i>关于本工具 | About this tool</h4>
                   </div>
                   <ul class="tools_info">
                       <li>压缩：将代码压缩为更小体积，便于传输</li>
                       <li>简单压缩：一个function压缩成一行代码，压缩强度很大，压缩之后的文件更小</li>
                       <li>高级压缩：可以将function中的参数压缩，压缩率更大</li>
                       <li>格式化：格式化代码，使之容易阅读</li>
                       <li>混淆：完全混淆内容，但可读性差，注释全部丢失，请做好备份工作，<em class="em-warning">混淆之后的代码可能比压缩之后大</em></li>
                   </ul>
               </div>
           </div>
       </div>
<@footetr>
  <script src="${config.staticWebSite}/js/my.js" type="text/javascript"></script>
  <script src="${config.staticWebSite}/js/Words.js" type="text/javascript"></script>
  <script src="${config.staticWebSite}/js/beautify.js" type="text/javascript"></script>
  <script src="${config.staticWebSite}/js/Packer.js" type="text/javascript"></script>
  <script type="text/javascript">
      function reset_result() {
          $('#js_output').val('');
          $('#output_rate').text('');
      }

      function check_input() {
          var c = $("#js_input").val();
          if (c.length <= 0 || c === "请输入Javascript代码") {
              $("#js_input").val("请输入Javascript代码");
              return false;
          }
          return true;
      }

      function js_format() {
          reset_result();
          if (check_input()) {
              $('#js_output').val(js_beautify($('#js_input').val(), {
                  "indent_with_tabs": true
              }));
          }
      }

      function js_compress(a) {
          reset_result();
          var c = $("#js_input").val();
          if (check_input()) {
              var b = new Packer;
              var v = b.pack(c, false, a);
              var d = c.length;
              if (!/\r/.test(c)) {
                  d += match(c, /\n/g).length
              }
              $("#js_output").val(v);
              $('#output_rate').text(
                      "原来大小: " + d + "B, 压缩后大小: " + v.length + "B, 压缩率为: "
                      + (Math.round(v.length / d * 1000) / 10) + "%")
          }
      }

      function js_encode() {
          reset_result();
          var packer = new Packer;
          var c = $("#js_input").val();
          if (check_input()) {
              var v = packer.pack(c, true, true);
              $("#js_output").val(v);
          }
      }

      function js_decode() {
          reset_result();
          var c = $("#js_input").val();
          if (check_input()) {
              var r = eval(c.slice(4));
              r = r.replace(/^\s+/, '');
              if (r && r.charAt(0) === '<') {
                  r = style_html(r, 4, ' ', 80);
              } else {
                  r = js_beautify(r, 4, ' ');
              }
              $("#js_output").val(r);
          }
      }
  </script>
</@footetr>