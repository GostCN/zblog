<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        <a class="navbar-brand" href="/"><img src="${config.staticWebSite}/images/logo_178x42.png"></a>
    </div>
    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search">
                    <form role="form" method="post" action="/onlineTool/search">
                        <div class="input-group custom-search-form">
                            <input type="text" name="keywords" class="form-control" placeholder="Search..." value="">
                            <span class="input-group-btn"> <button class="btn btn-default" type="submit"> <i class="fa fa-search"></i> </button> </span>
                        </div>
                    </form> </li>
                <li> <a href="/onlineTool/"><i class="fa fa-dashboard fa-fw"></i> 首页</a> </li>
                <li> <a href="/onlineTool/json2Entity"><i class="fa fa-cubes fa-fw"></i> <strong>Json转实体</strong><i class="glyphicon glyphicon-fire badge-hot"></i></a> </li>
                <li> <a href="/onlineTool/highlighter"><i class="fa fa-code fa-fw"></i> <strong>代码高亮</strong><i class="glyphicon glyphicon-fire badge-hot"></i></a> </li>
                <li> <a href="/onlineTool/format/js"><i class="fa fa-coffee fa-fw"></i> Javascript格式化</a> </li>
                <li> <a href="/onlineTool/format/json"><i class="fa fa-cubes fa-fw"></i> Json格式化</a> </li>
                <li> <a href="/onlineTool/format/css"><i class="fa fa-css3 fa-fw"></i> CSS格式化</a> </li>
                <li> <a href="#"><i class="fa fa-users fa-fw"></i> 站长工具<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li><a href="/onlineTool/domain"><strong>域名注册查询</strong><i class="glyphicon glyphicon-fire badge-hot"></i></a></li>
                        <li><a href="/onlineTool/ua"><strong>UserAgent分析</strong><i class="glyphicon glyphicon-fire badge-hot"></i></a></li>
                    </ul> </li>
            </ul>
        </div>
    </div>
</nav>