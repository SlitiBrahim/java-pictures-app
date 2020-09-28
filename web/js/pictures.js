
function updatePictures(JSON_STRING) {
    var obj = JSON.parse(JSON_STRING); //Converti la variable "JSON_STRING" de String à JSON
    var items = obj.items;
    console.log(obj);
    var pictures = document.getElementById("pictures"); // Récupère l'élément avec l'ID "pictures" --> Un simple div utilisé comme pointer
    pictures.innerHTML = ""; // On vide les anciennes images.
    var i =0;
    while (items[i] != null) {
        var html = '<a href="picture?id='+ items[i].id +'" class="gallery-item">' +
            '<div class="title">' +
            '<label> ' + items[i].title + ' </label>' +
            '</div> ' +
            '<img style="height: 100px" src="' + items[i].path + '" class="gallery-image" alt="'+ items[i].description + '">' +
            '<div class="gallery-item-info"> </div> ' +
            '<div class="description"> ' +
            '<label> ' + items[i].description + ' </label> ' +
            '</div> ' +
            '</a>';
        pictures.innerHTML += html;
        i++;
    }
};



