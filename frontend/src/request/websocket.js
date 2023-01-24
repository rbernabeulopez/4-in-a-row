import { over } from 'stompjs';
import SockJS from 'sockjs-client';
import { errorNotification } from "../../utils/notification"; let stompClient = null;


// EVENTS 

let eventHandlers = [];

let userData = undefined; export const sendEvent = (data) => {
    if (stompClient) {
        stompClient.send("/service/v1/scripture", {}, JSON.stringify({
            senderId: userData.id, ...data
        }));
    }
}
export const setEventHandler = (handler) => {
    eventHandlers.push(handler);
}
const handleEvent = (event) => {
    let data = JSON.parse(event.body);
    if (data.senderId === userData.id) return;
    eventHandlers.forEach((handler) => {
        if (handler.action === data.action) {
            handler.handler(data.payload);
        }
    })
}
const listenScriptureEvents = (gameId) => {
    if (stompClient) {
        stompClient.subscribe(`/game-notifications/${gameId}`, handleEvent);
        sendEvent({ payload: userData, action: JOIN, gameId });
    }
}

// CONNECTION
const handleConnection = listenScriptureEvents
const handleConnectionError = () => {
    errorNotification("Connection error", "Connection to server was lost. Check your internet connection and try again.")
}
export const connect = (gameId, playerInfo) => {
    userData = playerInfo;
    let Sock = new SockJS(`localhost:8080/api/v1`);
    stompClient = over(Sock);
    stompClient.debug = null
    stompClient.connect({}, () => handleConnection(gameId), handleConnectionError);
}