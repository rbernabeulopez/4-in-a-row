import axios from "axios";

export function createPlayer(name, password){
    axios({
        url: "/api/v1/player",
        method: "POST",
        data: {name, password}
    })
    .then((res) => {
        localStorage.setItem("playerId", res.data.id);
        localStorage.setItem("playerName", res.data.name);
        
    })
    .catch((err) => {
        console.log(err)
    })
};