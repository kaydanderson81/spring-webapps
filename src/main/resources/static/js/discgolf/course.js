$(' #deleteButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
       $('#deleteCourseModal #delCourse').attr('href', href);
       $('#deleteCourseModal').modal();

    });



document.addEventListener('DOMContentLoaded', function() {
  const urlParams = new URLSearchParams(window.location.search);
  const updatedCourseId = urlParams.get('updatedCourseId');

  const courseId = 'course-' + updatedCourseId;
    console.log('CourseId: ' + courseId)

    if (updatedCourseId) {
      const updatedCourseElement = document.getElementById(courseId);
      if (updatedCourseElement) {
        // Scroll to the element with smooth behavior
        updatedCourseElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
});