import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectWebSocket = (userId, callback) => {
    const socket = new SockJS("http://localhost:8080/ws");

    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,

        onConnect: () => {
            console.log("WebSocket Connected");

            stompClient.subscribe(`/topic/notifications/${userId}`, (msg) => {
                const notification = JSON.parse(msg.body);

                callback(notification);
            });
        }
    });

    stompClient.activate();
};

export const disconnectWebSocket = () => {
    if (stompClient) stompClient.deactivate();
};