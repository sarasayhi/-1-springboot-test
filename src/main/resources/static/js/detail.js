function pageInit() {
    initDataList();
}
function initDataList() {
    var href = 'upload/'+epm.getUrlParam('href');
    $('iframe').attr('src',href);
}