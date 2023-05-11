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

    console.log(JSON.stringify(rounds));

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
