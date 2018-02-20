/**
 * Created by jojoldu@gmail.com on 2018. 2. 20.
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

import Editor from 'tui-editor';
import 'bootstrap';
import 'codemirror/lib/codemirror.css'; // codemirror
import 'tui-editor/dist/tui-editor.css'; // editor ui
import 'tui-editor/dist/tui-editor-contents.css'; // editor content
import 'highlight.js/styles/github.css'; // code block highlight
import 'bootstrap/dist/css/bootstrap.min.css';

const editor = new Editor({
    el: document.querySelector('#editSection'),
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    height: '300px'
});


$('#btn-save').on('click', function () {
    const imgFile = $('#img')[0].files[0];

    uploadImage(imgFile, function (img) {
        const title = encodeURIComponent($('#title').val());
        const link = encodeURIComponent($('#link').val());
        const content = encodeURIComponent($('.tui-editor-contents')[0].innerHTML);
        const contentMarkdown = encodeURIComponent(editor.getMarkdown());

        const request = {
            title: title,
            link: link,
            img: img,
            content: content,
            contentMarkdown: contentMarkdown
        };

        $.ajax({
            type: 'POST',
            url: '/letter-content/save',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(request)
        }).done(function(data) {
            alert(data+' 글이 등록되었습니다.');
        }).fail(function (error) {
            alert(error);
        })
    });
});

function uploadImage(file, callback) {
    const formData = new FormData();
    formData.append('data', file);

    $.ajax({
        type: 'POST',
        url: '/image/upload',
        data: formData,
        processData: false,
        contentType: false
    }).done(function(data) {
        callback(data);
    }).fail(function (error) {
        callback(error);
    })
}