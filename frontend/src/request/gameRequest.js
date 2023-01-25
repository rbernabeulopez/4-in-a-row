import axios from "axios";

export function createGame(player1Id) {

    const response = axios({
    url: '/api/v1/game',
    method: "POST",
    data: {"player1Id": player1Id}
    })
    /*
    .then((res) => {
      res
    })
    .catch((err) => {
      console.error("Error: " + err);
      
    }); 
    */
    const dataPromise = response.then((response) => response.data)

    // return it
    return dataPromise
}

