import axios from "axios";

export function createGame(player1Id) {
  const response = axios({
    url: "/api/v1/game",
    method: "POST",
    data: { player1Id: player1Id },
  });
  return response.then((res) => res.data.id);
}

export function getGameById(gameId){
  const response = axios(
    {
      url: `/api/v1/game/${gameId}`,
      method: "GET",
    }
  );
  return response.then((res) => res.data);
}
export function joinGame(gameId) {
  const response = axios({
    url: `/api/v1/game/join`,
    method: "PATCH",
    data: { gameId },
    headers: {
      "Authorization": `Bearer ${localStorage.getItem("playerToken")}`,
    },
  });
  return response.then((res) => res.data);
}
