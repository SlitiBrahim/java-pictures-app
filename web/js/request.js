window.onload = function () {
    getAllPosts();

};

function getAllPosts() {

    var query = "api/posts";
    $.ajax({
        url: query,
        type: 'GET',
        success: function (apiAnswer, status){
            updatePictures(JSON.stringify(apiAnswer));
        },

        error : function (resulat, status, erreur) {
            alert(erreur);
        }
    });
}

function searchPosts(keywords, category) {

    var query = "api/search?";
    if (keywords != null && keywords !== "") {
        query += "q=" + keywords;
        if (category != null && category !== "") {
            query += "&categoryId=" + category;
        }
        console.log("query to api/search : " + query);
        $.ajax({
            url: query,
            type: 'GET',
            success: function (apiAnswer, status) {
                updatePictures(JSON.stringify(apiAnswer));
            },
            error: function (resulat, status, erreur) {
                alert(erreur);
            }
        });
    }
    else {
        console.log("'Keywords' is empty. No query sent. ")
    }
}





