var MaxInputs = 8; //maximum input boxes allowed
var $InputsWrapper = $("#InputsWrapper"); //Input boxes wrapper ID
var $AddButton = $("#AddMoreFileBox"); //Add button ID
var FieldCount = 1; //to keep track of text box added
var LinkGetList = 'list/doc';
var LinkDelete = 'del/doc';
var LinkUpload = 'api/upload/multi';
function pageInit() {
    eventTrigger();
    initDataList();
}
function initDataList() {
    var params = {};
    params['page'] = 1;
    params['size'] = 1;
    epm.ajax(LinkGetList,JSON.stringify(params), function (result) {
        console.log(JSON.stringify(result));
        // [
        //     {
        //         "id":1,
        //         "name":"0371e1de-0cce-11e4-985a-32de0f717c8f.jpg",
        //         "content":"",
        //         "tags":"tags",
        //         "collectCnt":0,
        //         "createTime":1513343304000,
        //         "updateTime":1513343304000,
        //         "userId":0,
        //         "deleted":0,
        //         "ext":null
        //     },
        // ]
        // if((typeof result=='string')&&result.constructor==String){
        //     alert(result);
        //     return;
        // }
        var $dataList = $('#dataList'),
            html = '';
        var path = "upload/";
        $.each(result, function(index, value) {
            html += '<li class="cm-doc-li">'
                + '<div class="detail-list-title">'
                + '<div title="' + value["id"] + '">' + value["id"] + '</div>'
                + '<div title="' + value["name"] + '">' + value["name"] + '</div>'
                + '<div title="' + value["name"] + '">' + value["name"] + '</div>'
                + '<div>' + epm.getDateTime(value["createTime"]) + '</div>'
                + '<div>' + value["tags"] + '</div>'
                + '<div>'
                + '<a class="cm-btn-sm cm-btn-active" href="detail?href='+escape(value['name'])+'">详情</a>'
                + '<a href="'+ path + value["name"] + '" class="cm-btn-sm cm-btn-active download" data-name="' + value["name"] + '">下载</a>'
                + '<a class="cm-btn-sm cm-btn-active">编辑</a>'
                + '<a class="cm-btn-sm cm-btn-active doc-delete" data-id="' + value['id']+ '">删除</a>'
                + '</div>'
                + '</div></li>';
        });
        $dataList.append(html);
        $('.cm-doc-li').find('.doc-delete').on({
            click:function () {
                var params = {};
                params['id'] = $(this).attr('data-id');
                epm.ajax(LinkDelete,JSON.stringify(params),function () {
                    console.log(LinkDelete +JSON.stringify(result));
                });
            }
        });
    });
}

function eventTrigger() {
    $AddButton.click(function (e) {
        var x = $InputsWrapper.find('li').length; //initlal text box count
        if (x <= MaxInputs){
            FieldCount++; //text box added increment
            $InputsWrapper.append('<li class="detail-list-title">'
                +'<div><a class="cm-btn-sm cm-btn-active removeclass">删除</a></div>'
                +'<div><input type="file" name="files" id="field_' + FieldCount + '" accept="application/pdf"/></div>'
                +'<div><input type="text" name="tags" id="tags_' + FieldCount + '"/></div>'
                +'<span id="result"></span>'
                +'</li>');
            x++; //text box increment
        }
        return false;
    });

    $("body").on("click", ".removeclass", function (e) { //user click on remove text
        var x = $InputsWrapper.find('li').length;
        if (x > 1) {
            $(this).parents('li').remove(); //remove text box
            x--; //decrement textbox
        }
        return false;
    });

    $("#btnSubmit").click(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
}
function fire_ajax_submit() {
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    $("#btnSubmit").prop("disabled", true);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: LinkUpload,
        data: data,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#result").text(data);
            console.log("SUCCESS : ", data);
            $("#btnSubmit").prop("disabled", false);
        },
        error: function (e) {
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);
        }
    });
}