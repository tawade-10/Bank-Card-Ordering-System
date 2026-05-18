import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectWebSocket = (userId, onNotification) => {
    const socketUrl = "http://localhost:8080/ws";

    stompClient = new Client({
        webSocketFactory: () => new SockJS(socketUrl),
        reconnectDelay: 3000,

        onConnect: () => {
            console.log("Connected to WebSocket");

            stompClient.subscribe(`/topic/notifications/${userId}`, (msg) => {
                if (!msg.body) return;
                const notification = JSON.parse(msg.body);
                onNotification(notification);
            });
        },

        onStompError: (frame) => {
            console.error("Broker error:", frame.headers["message"]);
        },

        onWebSocketClose: () => {
            console.warn("WebSocket closed, attempting reconnect...");
        }
    });

    stompClient.activate();
};

export const disconnectWebSocket = () => {
    if (stompClient && stompClient.active) {
        stompClient.deactivate();
    }
};