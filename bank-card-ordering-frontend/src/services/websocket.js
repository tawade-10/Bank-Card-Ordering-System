import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

let stompClient = null;

export const connectNotificationSocket = (userId, onMessageReceived) => {
  if (!userId) return;

  const socket = new SockJS("http://localhost:8080/ws");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    console.log("Connected to WebSocket");

    stompClient.subscribe(`/topic/notifications/${userId}`, (message) => {
      onMessageReceived(JSON.parse(message.body));
    });
  });
};

export const disconnectNotificationSocket = () => {
  if (stompClient) {
    stompClient.disconnect();
    console.log("WebSocket Disconnected");
  }
};