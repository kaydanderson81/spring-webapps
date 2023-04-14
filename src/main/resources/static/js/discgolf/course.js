$(' #deleteButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
       $('#deleteCourseModal #delCourse').attr('href', href);
       $('#deleteCourseModal').modal();

    });