import axios from "axios";

export function createGame(player1Id) {
  const response = axios({
    url: "/api/v1/game",
    method: "POST",
    data: { player1Id: player1Id },
  });
  /*
    .then((res) => {
      res
    })
    .catch((err) => {
      console.error("Error: " + err);
      
    }); 
    */

  // return it
  return response.then((res) => res.data.id);
}
