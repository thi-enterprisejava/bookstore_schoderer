$("input[type=file].imagesOnlyMax6MB").attr("accept", "image/*").on("change", function () {
    var max = 6 * 1024 * 1024; //6MB image only!!!

    if (this.files && this.files[0].size > max) {
        alert("File too large."); // DIRTY!!!
        this.value = null;
    }
});