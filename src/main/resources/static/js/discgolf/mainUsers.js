//console.log(JSON.stringify(userInfoList));

    function getUserInfoById(userInfoList, userId) {
      return userInfoList.find((userInfo) => userInfo.userId === Number(userId));
    }

//    const user = getUserInfoById(userInfoList, 3);
//    console.log(user);

    const charts = document.querySelectorAll('[userId]');
          charts.forEach(chart => {
          console.log(chart);

            const getUser = chart.getAttribute('userId').split(',');
            const userData = getUserInfoById(userInfoList, getUser);

            console.log(JSON.stringify(userData));

            // Create an object to store the scores data
            const scoreData = {};

            // Iterate through each score in the user's data
            userData.scoreList.forEach(score => {
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
            });

            // Convert the scores data to an array of data
            const chartData = {
              labels: Object.keys(scoreData),
              datasets: [{
                data: Object.values(scoreData).map(data => data.count),
                backgroundColor: Object.values(scoreData).map(data => data.color)
              }]
            };

            // Create a pie chart using Chart.js
            const myChart = new Chart(chart, {
              type: 'pie',
              data: chartData,
              options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    },
                  title: {
                    display: false
                  }
                },
                elements: {
                    arc: {
                        borderWidth: 0
                    }
                }
              }
            });






          });

