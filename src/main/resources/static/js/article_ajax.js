$("#article-page-block").on("click", "li a:not(#prev_page):not(#next_page)", nextArticlePage);

function nextArticlePage(e) {
    e.preventDefault();

    var pageNumber = $(this).text();
    var url = "/api/article?page=" + pageNumber;
    setPageNav(parseInt(pageNumber));

    $.ajax({
        type: 'get',
        url: url,
        dataType: 'json',
        error: function () {
            alert("error");
        },
        success: function (data, status) {
            console.log(data);
            $("#article-list").empty();
            data.forEach(function (item) {
                var answerTemplate = $("#article-list-template").html();
                var template = answerTemplate.format(item)
                    .format({
                        'author_id': item.author.id,
                        'author_name': item.author.name
                    });
                $("#article-list").append(template); // 추가
            })
        }
    });
}

function setPageNav(pageNumber){
    $("#p1").text(pageNumber);
    $("#p2").text(pageNumber + 1);
    $("#p3").text(pageNumber + 2);
    $("#p4").text(pageNumber + 3);
    $("#p5").text(pageNumber + 4);
}

function goToPrevPage(){
    var pageNumber = parseInt($("#p1").text());
    console.log(pageNumber);
    if(pageNumber === 1){
        return;
    }
    setPageNav(pageNumber-1);
}

function goToNextPage(){
    var pageNumber = parseInt($("#p5").text());
    console.log(pageNumber);
    setPageNav(pageNumber + 1);
}