import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectWebSocket = (userId, onNotification) => {
    const socketUrl = "http://localhost:8080/ws";

    stompClient = new Client({
        webSocketFactory: () => new SockJS(socketUrl),
        reconnectDelay: 3000,

        debug: (str) => console.log(str),

        onConnect: (frame) => {
            console.log("🔥 STOMP Connected:", frame);

            // IMPORTANT FIX: Wait a tick before subscribing
            setTimeout(() => {
                stompClient.subscribe(`/topic/notifications/${userId}`, (msg) => {
                    if (!msg.body) return;
                    const notification = JSON.parse(msg.body);
                    onNotification(notification);
                });
                console.log(`📡 Subscribed to /topic/notifications/${userId}`);
            }, 50);
        },

        onStompError: (frame) => {
            console.error("❌ STOMP Broker error:", frame.headers["message"]);
        }
    });

    stompClient.activate();
};

export const disconnectWebSocket = () => {
    if (stompClient && stompClient.active) {
        stompClient.deactivate();
    }
};