/**
 * Created by jojoldu@gmail.com on 2018. 2. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */


$('#btn-send').on('click', function () {
    const request = {
        letterId: $('#letter-id').val()
    };

    $.ajax({
        type: 'POST',
        url: '/admin/letter/send',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(request)
    }).done(function(data) {
        alert(data+' 명에게 전송되었습니다.');
    }).fail(function (error) {
        alert(error);
    });
});

$('#btn-target-send').on('click', function () {
    const request = {
        letterId: $('#target-letter-id').val(),
        email: $('#target-email').val()
    };

    $.ajax({
        type: 'POST',
        url: '/admin/letter/send',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(request)
    }).done(function(email) {
        alert(email+' 에게 전송되었습니다.');
    }).fail(function (error) {
        alert(error);
    });
});

$('#btn-test-send').on('click', function () {
    const request = {
        letterId: $('#letter-id').val()
    };

    $.ajax({
        type: 'POST',
        url: '/admin/letter/send/test',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(request)
    }).done(function(data) {
        alert(data+' 글이 테스트 유저에게 전송되었습니다.');
    }).fail(function (error) {
        alert(error);
    });
});

$('#btn-save').on('click', function () {
    const subject = $('#subject').val();
    const contentIds = getCheckedValue('.check-content', 'id');

    const request = {
        subject: subject,
        contentIds: contentIds
    };

    $.ajax({
        type: 'POST',
        url: '/admin/letter/save',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(request)
    }).done(function(data) {
        alert('ID: '+data+' 뉴스레터가 저장 되었습니다.');
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
});

function getCheckedValue(key, dataKey) {
    const checkedValues = [];

    $(key+':checked').each(function () {
        checkedValues.push($(this).data(dataKey));
    });

    return checkedValues;
}
