// websocket.js
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectWebSocket = (userId, onMessageReceived) => {
  const socket = new SockJS("http://localhost:8080/ws");

  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000, // auto reconnect
    onConnect: () => {
      console.log("WebSocket Connected");

      stompClient.subscribe(`/topic/notifications/${userId}`, (message) => {
        if (message.body) {
          const notification = JSON.parse(message.body);
          onMessageReceived(notification);
        }
      });
    },
  });

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
  }
};