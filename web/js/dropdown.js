function searchEngine() {
    var keywords = document.getElementById("keywords").value;
    var category = document.getElementById("category").value;
    console.log("keywords: " + keywords);
    console.log("category " + category);
    searchPosts(keywords, category);
}