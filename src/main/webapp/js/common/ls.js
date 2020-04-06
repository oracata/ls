//全局 变量
var lsWebRoot = lsWebRoot || {};
lsWebRoot.appRoot = lsWebRoot.appRoot
    || (function() {
        var wlocation = window.location;
        var path = (wlocation.pathname.charAt(0) == "/" ? wlocation.pathname.substring(1): wlocation.pathname);
        var vport = ((wlocation.port == '' || wlocation.port == '80') ? '': ':' + wlocation.port);
        var AppUrl = "";
        if (wlocation.hostname === "pubhealth.gdhktech.com") {
            AppUrl = window.location.protocol + '//'
                + window.location.hostname + '/';
        } else {
            AppUrl = [ wlocation.protocol, '//', wlocation.hostname, vport,
                '/' ]
                .join('');
        }
        return AppUrl;
    })();