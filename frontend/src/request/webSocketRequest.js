import {errorNotification, infoNotification} from "../util/notification";
import {over} from 'stompjs';
import SockJS from 'sockjs-client';

let stompClient = null;

let eventHandlers =  [];
let userData = undefined;

const handleNotification = (event) => {
    console.log("Received notification", JSON.parse(event.body));
};

const listenEvents = (gameId) => {
    infoNotification("Connection established", "Connection to server was established.", "topRight")
    if (stompClient) {
        stompClient.subscribe(`/game-notifications`, handleNotification);
        stompClient.send("/service/v1/join-game", {}, JSON.stringify({playerId: 1, gameId: gameId}));
    }
}

const handleConnectionError = () => {
    errorNotification("Connection error", "Connection to server was lost. Check your internet connection and try again.", "topRight")
}

const handleConnection = listenEvents
export const makeSocketConnection =(gameId, userInfo)=>{
    userData = userInfo;
    let Sock = new SockJS(`http://localhost:8080/service/v1`);
    stompClient = over(Sock);
    stompClient.debug = null
    stompClient.connect({}, () => handleConnection(gameId), handleConnectionError);
}