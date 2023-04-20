$(' #deleteRoundButton').on('click', function(event) {
        event.preventDefault();
        var href = $(this).attr('href');
       $('#deleteRoundModal #delRound').attr('href', href);
       $('#deleteRoundModal').modal();

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
          if (!scoreData[score.name]) {
            scoreData[score.name] = {
              count: 0,
              color: score.color,
              score: score.score,
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
