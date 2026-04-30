import { useEffect } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default function NotificationListener({ userId, onReceive }) {

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = Stomp.over(socket);

    client.connect({}, () => {
      client.subscribe(`/topic/notifications/${userId}`, (msg) => {
        const n = JSON.parse(msg.body);
        onReceive(n);
      });
    });
  }, []);

  return null;
}