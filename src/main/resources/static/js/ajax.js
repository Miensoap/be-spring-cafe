$(".submit-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $(".submit-write").serialize();
    var url = $(".submit-write").attr("action");


    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: function () {
            alert("error");
        },
        success: function (data, status) {
            console.log(data);
            var answerTemplate = $("#comment_template").html();
            var template = answerTemplate.format(data)

            $(".qna-comment-slipp-articles").prepend(template);
            $("textarea[name=contents]").val("");
        }
    });

}

$("#delete-answer-form").on("click", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = $("#delete-answer-form").attr("href");

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
            }
            alert(data.message);
        }
    });
}