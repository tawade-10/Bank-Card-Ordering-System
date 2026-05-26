import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectWebSocket = (customerId, onNotification) => {
  const socketUrl = "http://localhost:8080/ws";

  stompClient = new Client({
    webSocketFactory: () => new SockJS(socketUrl),
    reconnectDelay: 3000,

    onConnect: () => {
      console.log("Connected to WebSocket");

      stompClient.subscribe(`/topic/notifications/${customerId}`, (message) => {
        const notif = JSON.parse(message.body);
        onNotification(notif);
      });
    },

    onStompError: (frame) => {
      console.error("WebSocket error:", frame);
    },
  });

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient) stompClient.deactivate();
};