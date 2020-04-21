<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色</title>
    <jsp:include page="/common/backend_common.jsp" />
    <link rel="stylesheet" href="/ztree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/assets/css/bootstrap-duallistbox.min.css" type="text/css">
    <script type="text/javascript" src="/ztree/jquery.ztree.all.min.js"></script>
    <script type="text/javascript" src="/assets/js/jquery.bootstrap-duallistbox.min.js"></script>
    <style type="text/css">
        .bootstrap-duallistbox-container .moveall, .bootstrap-duallistbox-container .removeall {
            width: 50%;
        }
        .bootstrap-duallistbox-container .move, .bootstrap-duallistbox-container .remove {
            width: 49%;
        }
    </style>



    <script src="https://cdn.fusioncharts.com/fusioncharts/fusioncharts.powercharts.js"></script>


    <script src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>


    <script src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.theme.fusion.js"></script>



    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<!-- 提示牌 -->
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        角色管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护角色与用户, 角色与权限关系
        </small>
    </h1>
</div>
<div class="main-content-inner">

    <div class="col-sm-9">
        <div class="tabbable" id="roleTab">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#roleAclTab">
                        角色与权限
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#roleUserTab">
                        角色与用户
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="roleAclTab" class="tab-pane fade in active">
                    <ul id="roleAclTree" class="ztree"></ul>

                    <div id="chart-container"></div>
                    <script type="text/javascript">
                        const dataSource = {
                            chart: {
                                caption: "Countries With Most Oil Reserves [2017-18]",
                                subcaption: "In MMbbl = One Million barrels",
                                xaxisname: "Country",
                                yaxisname: "Reserves (MMbbl)",
                                numbersuffix: "K",
                                theme: "fusion"
                            },
                            data: [
                                {
                                    label: "Venezuela",
                                    value: "290"
                                },
                                {
                                    label: "Saudi",
                                    value: "260"
                                },
                                {
                                    label: "Canada",
                                    value: "180"
                                },
                                {
                                    label: "Iran",
                                    value: "140"
                                },
                                {
                                    label: "Russia",
                                    value: "115"
                                },
                                {
                                    label: "UAE",
                                    value: "100"
                                },
                                {
                                    label: "US",
                                    value: "30"
                                },
                                {
                                    label: "China",
                                    value: "30"
                                }
                            ]
                        };

                        FusionCharts.ready(function() {
                            var myChart = new FusionCharts({
                                type: "column2d",
                                renderAt: "chart-container",
                                width: "100%",
                                height: "400",
                                dataFormat: "json",
                                dataSource
                            }).render();
                        });

                    </script>

                </div>

                <div id="roleUserTab" class="tab-pane fade" >
                    <div class="row">
                        <div class="box1 col-md-6">待选用户列表</div>
                        <div class="box1 col-md-6">已选用户列表</div>
                    </div>
                    <select multiple="multiple" size="10" name="roleUserList" id="roleUserList" >
                    </select>
                    <div class="hr hr-16 hr-dotted"></div>
                    <button class="btn btn-info saveRoleUser" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="dialog-role-form" style="display: none;">
    <form id="roleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td><label for="roleName">名称</label></td>
                <td>
                    <input type="text" name="name" id="roleName" value="" class="text ui-widget-content ui-corner-all">
                    <input type="hidden" name="id" id="roleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="roleStatus">状态</label></td>
                <td>
                    <select id="roleStatus" name="status" data-placeholder="状态" style="width: 150px;">
                        <option value="1">可用</option>
                        <option value="0">冻结</option>
                    </select>
                </td>
            </tr>
            <td><label for="roleRemark">备注</label></td>
            <td><textarea name="remark" id="roleRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>


<script id="roleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#roleList}}
        <li class="dd-item dd2-item role-name" id="role_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green role-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red role-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/roleList}}
</ol>
</script>

<script id="selectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}" selected="selected">{{username}}</option>
{{/userList}}
</script>

<script id="unSelectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}">{{username}}</option>
{{/userList}}
</script>

<script type="text/javascript">
    $(function () {
        var roleMap = {};
        var lastRoleId = -1;
        var selectFirstTab = true;
        var hasMultiSelect = false;

        var roleListTemplate = $("#roleListTemplate").html();
        Mustache.parse(roleListTemplate);
        var selectedUsersTemplate = $("#selectedUsersTemplate").html();
        Mustache.parse(selectedUsersTemplate);
        var unSelectedUsersTemplate = $("#unSelectedUsersTemplate").html();
        Mustache.parse(unSelectedUsersTemplate);

        loadRoleList();
        // zTree
        <!-- 树结构相关 开始 -->
        var zTreeObj = [];
        var modulePrefix = 'm_';
        var aclPrefix = 'a_';
        var nodeMap = {};

        var setting = {
            check: {
                enable: true,
                chkDisabledInherit: true,
                chkboxType: {"Y": "ps", "N": "ps"}, //auto check 父节点 子节点
                autoCheckTrigger: true
            },
            data: {
                simpleData: {
                    enable: true,
                    rootPId: 0
                }
            },
            callback: {
                onClick: onClickTreeNode
            }
        };

        function onClickTreeNode(e, treeId, treeNode) { // 绑定单击事件
            var zTree = $.fn.zTree.getZTreeObj("roleAclTree");
            zTree.expandNode(treeNode);
        }

        function loadRoleList() {
            $.ajax({
                url:[lsWebRoot.appRoot,"sys/role/list.php"].join(""),
                success:function (result) {
                    if(result.ret){
                        var rendered = Mustache.render(roleListTemplate,{roleList: result.data});
                        $("#roleList").html(rendered);
                        bindRoleClick();
                        $.each(result.data, function (i, role) {
                            roleMap[role.id] = role;
                        })
                    }else {
                        showMessage("加载角色列表失败", result.msg, false);
                    }
                }
            })
        }

        //绑定点击事件
        function bindRoleClick() {
            $(".role-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var roleId = $(this).attr("data-id");
                $("#dialog-role-form").dialog({
                    model: true,
                    title: "修改角色",
                    open: function(event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        $("#roleForm")[0].reset();
                        var targetRole = roleMap[roleId];
                        if(targetRole){
                            $("#roleId").val(roleId);
                            $("#roleName").val(targetRole.name);
                            $("#roleStatus").val(targetRole.status);
                            $("#roleRemark").val(targetRole.remark);
                        }
                    },
                    buttons : {
                        "修改": function(e) {
                            e.preventDefault();
                            updateRole(false, function (data) {
                                $("#dialog-role-form").dialog("close");
                                showMessage("修改角色", "修改角色成功", true);
                                loadRoleList();
                            }, function (data) {
                                showMessage("修改角色", data.msg, false);
                            })
                        },
                        "取消": function () {
                            $("#dialog-role-form").dialog("close");
                        }
                    }
                });
            })

            //点击名称刷新
            $(".role-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var roleId = $(this).attr("data-id");
                handleRoleSelected(roleId);
            });
        }

        function handleRoleSelected(roleId) {
            if (lastRoleId != -1) {
                var lastRole = $("#role_" + lastRoleId + " .dd2-content:first");
                lastRole.removeClass("btn-yellow");
                lastRole.removeClass("no-hover");
            }
            var currentRole = $("#role_" + roleId + " .dd2-content:first");
            currentRole.addClass("btn-yellow");
            currentRole.addClass("no-hover");
            lastRoleId = roleId;

            $('#roleTab a:first').trigger('click');
            if (selectFirstTab) {
                loadRoleAcl(roleId);
            }
        }

        function loadRoleAcl(selectedRoleId) {
            if (selectedRoleId == -1) {
                return;
            }
            $.ajax({
                url: "/sys/role/roleTree.php",
                data : {
                    roleId: selectedRoleId
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        renderRoleTree(result.data);
                    } else {
                        showMessage("加载角色权限数据", result.msg, false);
                    }
                }
            });
        }

        function getTreeSelectedId() {
            var treeObj = $.fn.zTree.getZTreeObj("roleAclTree");
            var nodes = treeObj.getCheckedNodes(true);
            var v = "";
            for(var i = 0; i < nodes.length; i++) {
                if(nodes[i].id.startsWith(aclPrefix)) {
                    v += "," + nodes[i].dataId;
                }
            }
            return v.length > 0 ? v.substring(1): v;
        }
        function renderRoleTree(aclModuleList) {
            zTreeObj = [];
            recursivePrepareTreeData(aclModuleList);
            for(var key in nodeMap) {
                zTreeObj.push(nodeMap[key]);
            }
            $.fn.zTree.init($("#roleAclTree"), setting, zTreeObj);
        }

        /**
         *  渲染Tree
         * @param aclModuleList
         */
        function recursivePrepareTreeData(aclModuleList) {
            // prepare nodeMap
            if (aclModuleList && aclModuleList.length > 0) {
                $(aclModuleList).each(function(i, aclModule) {
                    var hasChecked = false;
                    if (aclModule.aclList && aclModule.aclList.length > 0) {
                        $(aclModule.aclList).each(function(i, acl) {
                            zTreeObj.push({
                                id: aclPrefix + acl.id,
                                pId: modulePrefix + acl.aclModuleId,
                                name: acl.name + ((acl.type == 1) ? '(菜单)' : ''),
                                chkDisabled: !acl.hasAcl,
                                checked: acl.checked,
                                dataId: acl.id
                            });
                            if(acl.checked) {
                                hasChecked = true;
                            }
                        });
                    }
                    if ((aclModule.aclModuleList && aclModule.aclModuleList.length > 0) ||
                        (aclModule.aclList && aclModule.aclList.length > 0)) {
                        nodeMap[modulePrefix + aclModule.id] = {
                            id : modulePrefix + aclModule.id,
                            pId: modulePrefix + aclModule.parentId,
                            name: aclModule.name,
                            open: hasChecked
                        };
                        var tempAclModule = nodeMap[modulePrefix + aclModule.id];
                        while(hasChecked && tempAclModule) {
                            if(tempAclModule) {
                                nodeMap[tempAclModule.id] = {
                                    id: tempAclModule.id,
                                    pId: tempAclModule.pId,
                                    name: tempAclModule.name,
                                    open: true
                                }
                            }
                            tempAclModule = nodeMap[tempAclModule.pId];
                        }
                    }
                    recursivePrepareTreeData(aclModule.aclModuleList);
                });
            }
        }

        //增加
        $(".role-add").click(function () {
            $("#dialog-role-form").dialog({
                model: true,
                title: "新增角色",
                open: function(event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    $("#roleForm")[0].reset();
                },
                buttons : {
                    "添加": function(e) {
                        e.preventDefault();
                        updateRole(true, function (data) {
                            $("#dialog-role-form").dialog("close");
                            showMessage("新增角色", "新增角色成功", true);
                            loadRoleList();
                        }, function (data) {
                            showMessage("新增角色", data.msg, false);
                        })
                    },
                    "取消": function () {
                        $("#dialog-role-form").dialog("close");
                    }
                }
            });
        })

        //递归生成Select中的option列表
        function recursiveRenderRoleSelect(roleList, level) {
            level = level || 0;
            if(roleList && roleList.length > 0){
                $(roleList).each (function(i, role){
                    //存储map格式的单位信息--缓存作用
                    roleMap[role.id] = role;
                    var blank = "";
                    if(level > 1){
                        for(var j = 3; j <= level;j++ ){
                            blank += "..";
                        }
                        blank +=  "∟";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {id: role.id, name: blank + role.name});
                    if (role.deptList && role.deptList.length > 0) {
                        recursiveRenderRoleSelect(role.deptList, level + 1);
                    }
                })
            }
        }

        $(".saveRoleAcl").click(function (e) {
            e.preventDefault();
            if (lastRoleId == -1) {
                showMessage("保存角色与权限点的关系", "请现在左侧选择需要操作的角色", false);
                return;
            }
            $.ajax({
                url: "/sys/role/changeAcls.php",
                data: {
                    roleId: lastRoleId,
                    aclIds: getTreeSelectedId()
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        showMessage("保存角色与权限点的关系", "操作成功", false);
                    } else {
                        showMessage("保存角色与权限点的关系", result.msg, false);
                    }
                }
            });
        });

        //更新操作
        function updateRole(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate == true ?  [lsWebRoot.appRoot,"sys/role/save.php"].join('') : [lsWebRoot.appRoot,"sys/role/update.php"].join(''),
                data: $("#roleForm").serializeArray(),
                type: 'POST',
                success: function (result) {
                    if(result.ret){
                        //加载角色列表
                        loadRoleList();
                        if(successCallBack){
                            successCallBack(result);
                        }
                    }else {
                        if(failCallBack){
                            failCallBack(result);
                        }
                    }
                }
            })
        }

        $("#roleTab a[data-toggle='tab']").on("shown.bs.tab", function (e) {
            if(lastRoleId == -1){
                showMessage("加载角色关系", "请先在左侧选择操作的角色", false);
                return;
            }
            if(e.target.getAttribute("href") == '#roleAclTab'){
                selectFirstTab =true;
                loadRoleAcl(lastRoleId);
                console.log("loadRoleAcl:"+lastRoleId);
            }else{
                selectFirstTab = false;
                loadRoleUser(lastRoleId);
                console.log("loadRoleUser:"+lastRoleId);
            }
        });
//        $("#roleTab a[data-toggle='tab']").on("shown.bs.tab", function(e) {
//            if(lastRoleId == -1) {
//                showMessage("加载角色关系","请先在左侧选择操作的角色", false);
//                return;
//            }
//            if (e.target.getAttribute("href") == '#roleAclTab') {
//                console.log("loadRoleAcl:"+lastRoleId);
//                selectFirstTab = true;
//                loadRoleAcl(lastRoleId);
//            } else {
//                console.log("loadRoleUser:"+lastRoleId);
//                selectFirstTab = false;
//                loadRoleUser(lastRoleId);
//            }
//        });



        function loadRoleUser(selectedRoleId) {
            if (selectedRoleId == -1) {
                return;
            }
            $.ajax({
                url: "/sys/role/users.php",
                data : {
                    roleId: selectedRoleId
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        console.log("loadRoleUser success!");
                        var renderedSelect = Mustache.render(selectedUsersTemplate, {userList: result.data.selected});
                        console.log("renderedSelect success!");
                        var renderedUnSelect = Mustache.render(unSelectedUsersTemplate, {userList: result.data.unSeclected});
                        console.log("renderedUnSelect success!");
                        console.log(renderedSelect + renderedUnSelect);

                        $("#roleUserList").html(renderedSelect + renderedUnSelect);

                        //只执行一次
                        if(!hasMultiSelect) {
                            $('select[name="roleUserList"]').bootstrapDualListbox({
                                showFilterInputs: false,
                                moveOnSelect: false,
                                infoText: false
                            });
                            hasMultiSelect = true;
                        } else {
                            $('select[name="roleUserList"]').bootstrapDualListbox('refresh', true);
                        }
                    } else {
                        showMessage("加载用户权限数据", result.msg, false);
                    }
                }
            });
        }

        function renderUsersTree(UsersList) {
            zTreeObj = [];
            recursivePrepareTreeData(UsersList);
            for(var key in nodeMap) {
                zTreeObj.push(nodeMap[key]);
            }
            $.fn.zTree.init($("#roleUserTree"), setting, zTreeObj);
        }


        $(".saveRoleUser").click(function (e) {
            e.preventDefault();
            if (lastRoleId == -1) {
                showMessage("保存角色与用户的关系", "请现在左侧选择需要操作的角色", false);
                return;
            }
            $.ajax({
                url: "/sys/role/changeUsers.php",
                data: {
                    roleId: lastRoleId,
                    userIds: $("#roleUserList").val() ? $("#roleUserList").val().join(",") : ''
                },
                type: 'POST',
                success: function (result) {
                    if (result.ret) {
                        showMessage("保存角色与用户的关系", "操作成功", false);
                    } else {
                        showMessage("保存角色与用户的关系", result.msg, false);
                    }
                }
            });
        });


    })
</script>


</body>
</html>
