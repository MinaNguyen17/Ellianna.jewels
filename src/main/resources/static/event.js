function func1() {
  // Get the current src of the image
  var currentSrc = $("#main-pic").attr("src");

  // Define the target filename
  var targetFilename = "/img/aaa.jpg";
  var targetLength = targetFilename.length;

  // Check if the src ends with the target filename
  if (currentSrc.slice(-targetLength) === targetFilename) {
    // Fade out the image, change src, and then fade in
    $("#main-pic").fadeOut("slow", function () {
      $(this).attr("src", "/img/bbb.jpg").fadeIn("slow");
    });
  } else {
    $("#main-pic").fadeOut("slow", function () {
      $(this).attr("src", "/img/aaa.jpg").fadeIn("slow");
    });
  }
}
