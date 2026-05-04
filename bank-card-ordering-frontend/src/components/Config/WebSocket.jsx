import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export function WebSocket(customerId, onNotification) {
  const socket = new SockJS("http://localhost:8080/ws");
  const stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
  });

  stompClient.onConnect = () => {
    console.log("Connected to WebSocket");

    stompClient.subscribe(`/user/${customerId}/queue/notifications`, (msg) => {
      onNotification(JSON.parse(msg.body));
    });
  };

  stompClient.activate();
  return stompClient;
}