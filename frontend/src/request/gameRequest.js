import axios from "axios";

export function createGame(player1Id) {
  axios({
    url: "api/v1/game",
    method: "POST",
    data: { player1Id },
  })
    .then((res) => {
      console.log(res);
    })
    .catch((err) => {
      console.error("Error: " + err);
    });
}
