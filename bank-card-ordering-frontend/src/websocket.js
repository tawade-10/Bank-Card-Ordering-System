import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connectWebSocket = (customerId, onNotification) => {
  if (stompClient?.connected) return;

  stompClient = new Client({
    webSocketFactory: () =>
      new SockJS("http://localhost:8080/ws"),

    reconnectDelay: 3000,

    onConnect: () => {
      console.log("WebSocket Connected");

      stompClient.subscribe(
        `/topic/notifications/${customerId}`,
        (message) => {
          const notification = JSON.parse(message.body);
          onNotification(notification);
        }
      );
    }
  });

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
  }
};