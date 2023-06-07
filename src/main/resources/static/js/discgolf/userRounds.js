$(' #deleteRoundButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
       $('#deleteRoundModal #delRound').attr('href', href);
       $('#deleteRoundModal').modal();

    });

    document.addEventListener('DOMContentLoaded', function() {
      const urlParams = new URLSearchParams(window.location.search);
      const updatedCourseId = urlParams.get('updatedCourseId');

      const courseId = 'course-' + updatedCourseId;
      if (updatedCourseId) {
        const accordionButton = document.getElementById(`course-${updatedCourseId}`);
        if (accordionButton) {
          // Add the "active" class to the button
          accordionButton.classList.add('active');

          // Expand the corresponding accordion panel
          const accordionPanel = accordionButton.nextElementSibling;
          accordionPanel.style.maxHeight = accordionPanel.scrollHeight + "px";

          // Scroll to the accordion
          setTimeout(() => {
            accordionButton.scrollIntoView({ behavior: 'smooth', block: 'start' });
          }, 500);
        }
      }

    });

    console.log(JSON.stringify(courses));

    function getRoundById(rounds, roundId) {
      return rounds.find((round) => round.roundId === Number(roundId));
    }

    var acc = document.getElementsByClassName("accordion");
    var i;

    for (i = 0; i < acc.length; i++) {
      acc[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var panel = this.nextElementSibling;
        if (panel.style.maxHeight) {
          panel.style.maxHeight = null;
        } else {
          panel.style.maxHeight = panel.scrollHeight + "px";
        }
      });
    }

    const charts = document.querySelectorAll('[roundId]');
      charts.forEach(chart => {
        const getRound = chart.getAttribute('roundId').split(',');
        const roundData = getRoundById(rounds, getRound);

        const scoreCount = roundData.scores.length;
        const scoreData = {};

        for (let i = 0; i < scoreCount; i++) {
          const score = roundData.scores[i];
          let scoreValue;
          if (score.score === score.holePar - 3) {
            scoreValue = -3;
          } else if (score.score === score.holePar - 2) {
            scoreValue = -2;
          } else if (score.score === score.holePar - 1) {
            scoreValue = -1;
          } else if (score.score === score.holePar) {
            scoreValue = 0;
          } else if (score.score === score.holePar + 1) {
            scoreValue = 1;
          } else if (score.score === score.holePar + 2) {
            scoreValue = 2;
          } else if (score.score === score.holePar + 3) {
            scoreValue = 3;
          } else {
            scoreValue = 4;
          }
          if (!scoreData[score.name]) {
            scoreData[score.name] = {
              count: 0,
              color: score.color,
              score: scoreValue,
            };
          }
          scoreData[score.name].count += 1;
        }

        const datasets = [];
        Object.entries(scoreData).forEach(([name, data]) => {
          datasets.push({
            label: name,
            backgroundColor: data.color,
            data: [data.count],
            score: data.score, // add score value to dataset object
            borderWidth: 0,
          });
        });

        datasets.sort((a, b) => a.score - b.score); // sort datasets by score value

        const myChart = new Chart(chart, {
          type: 'bar',
          options: {
            responsive: true,
            maintainAspectRatio: false,
            indexAxis: 'y',
            scales: {
              x: {
                stacked: true,
                display: false,
              },
              y: {
                stacked: true,
                display: false,
              },
            },
            plugins: {
              legend: {
                display: false,
              },
            },
          },
          data: {
            labels: [''],
            datasets,
          },
        });
      });


    // Line Chart

    $(document).ready(function() {

      const courseCharts = document.querySelectorAll('[courseId]');
      courseCharts.forEach(courseChart => {
        const courseId = courseChart.getAttribute('courseId');
        var datasets = [];

        courses.forEach(function(course) {
          if (course.courseId === parseInt(courseId)) {
            var dataPoints = [];
            var monthLabels = []; // Store the month labels

            course.rounds.reverse().forEach(function(round, index) {
              var month = moment(round.roundDate).format('MMM');

              // Calculate the x-position for the data point
              var xPos = index;

              dataPoints.push({
                x: xPos,
                y: round.total - course.coursePar
              });

              monthLabels.push(month); // Add the month label
            });

            datasets.push({
              label: course.label,
              data: dataPoints,
              borderColor: getRandomColor(),
              lineTension: 0.3,
              fill: false
            });

            renderLineChart(courseChart, datasets, monthLabels); // Pass the month labels to the renderLineChart function
          }
        });
      });

      function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
          color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
      }

      function renderLineChart(element, datasets, labels) {
        var ctx = element.getContext('2d');

        // Calculate the minimum and maximum y-values from the datasets
        var minY = Number.MAX_VALUE;
        var maxY = Number.MIN_VALUE;

        datasets.forEach(function(dataset) {
          dataset.data.forEach(function(dataPoint) {
            minY = Math.min(minY, dataPoint.y);
            maxY = Math.max(maxY, dataPoint.y);
          });
        });

        // Adjust the y-axis range by subtracting 5 from the minimum and adding 5 to the maximum
        var yMin = Math.floor(minY) - 5;
        var yMax = Math.ceil(maxY) + 5;

        var chart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: labels,
            datasets: datasets,
          },
          options: {
            scales: {
              x: {
                beginAtZero: true,
                grid: {
                  display: false
                }
              },
              y: {
                beginAtZero: true,
                min: yMin, // Set the minimum value of the y-axis
                max: yMax // Set the maximum value of the y-axis
              }
            },
            plugins: {
              legend: {
                display: false
              }
            }
          }
        });
      }
    });



