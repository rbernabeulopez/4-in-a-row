import axios from "axios";
import {checkStatus} from "./statusChecker";

export async function createPlayer(name, password){
    await axios({
        url: "/api/v1/player",
        method: "POST",
        data: {name, password}
    }).then(checkStatus)
}

export async function loginPlayer(name, password){
    await axios({
        url: "/api/v1/login",
        method: "POST",
        data: {name, password}
    }).then(checkStatus)
        .then((res) => {
            localStorage.setItem("playerId", res.data.id);
            localStorage.setItem("playerName", res.data.name);
            localStorage.setItem("playerToken", res.headers.authorization);
        })
}