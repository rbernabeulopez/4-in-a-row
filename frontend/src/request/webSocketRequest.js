import { errorNotification, infoNotification } from "../util/notification";
import { over } from 'stompjs';
import SockJS from 'sockjs-client';

let stompClient = null;

let eventHandlers = [];

export const sendEvent = (endPoint, data) => {
    if (stompClient) {
        console.log("Segundo paso")
        stompClient.send(`/service/v1/${endPoint}`, {}, JSON.stringify(
            data
        )
        );
    }
}

const handleNotification = (event) => {
    console.log("Received notification", JSON.parse(event.body));
};

const handlePersonalNotification = (event) => {
    console.log("Received personal notification", JSON.parse(event.body));
    const response = JSON.parse(event.body);
    if (response.httpCode < 400) {
        infoNotification("Notification", response.message, "topRight");
    } else {
        errorNotification("Something went wrong", response.message, "topRight");
    }
};

const listenEvents = (gameId, playerId) => {
    infoNotification("Connection established", "Connection to server was established.", "topRight")
    if (stompClient) {
        stompClient.subscribe(`/game-notifications/${gameId}`, handleNotification);
        stompClient.subscribe(`/personal-notifications/${playerId}`, handlePersonalNotification);
        
    }
}

const handleConnectionError = () => {
    errorNotification("Connection error", "Connection to server was lost. Check your internet connection and try again.", "topRight")
}

const handleConnection = listenEvents

export const makeSocketConnection = (gameId, playerId) => {
    let Sock = new SockJS(`http://localhost:8080/service/v1`);
    stompClient = over(Sock);
    stompClient.debug = null
    stompClient.connect({}, () => handleConnection(gameId, playerId), handleConnectionError);
}