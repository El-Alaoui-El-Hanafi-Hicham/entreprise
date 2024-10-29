// import { Injectable } from '@angular/core';
// import { SocketIoConfig } from 'ngx-socket-io';
// import { Socket, io } from 'socket.io-client';

// @Injectable({
//   providedIn: 'root'
// })
// export class WebSocketService {
//   private jwtKey:string='';

//   constructor(private socket: Sock) {
//     this.jwtKey = localStorage.getItem('jwtToken') || ''; // Or use a service to get the token
//     const config: SocketIoConfig = {
//       url: 'http://localhost:8080/ws',
//       options: {
//           autoConnect: true,
//           transports: ['websocket'], // Force WebSocket
//           extraHeaders: {
//               Authorization: `Bearer ${this.jwtKey}`, // Send JWT token
//           },
//       },
//   };
//     this.socket = io(config.url, config.options);
//  // Listen for connection
//  this.socket.on('connect', () => {
//   console.log('Successfully connected to WebSocket');
// });

// // Handle errors
// this.socket.on('connect_error', (err) => {
//   console.error('Connection Error:', err);
// });
//   }
 
//   // this method is used to start connection/handhshake of socket with server
//   connectSocket(message:any) {
//     this.socket.on('connect', () => {
//       console.log('Successfully connected to WebSocket');
//     });
//   //   // Handle connection events
//   //   console.log("TRYING TO CONNECT")
//   //   this.webSocket.on('connect', () => {
//   //     console.log('Successfully connected to WebSocket with JWT!');
//   //   });
//   }
//   // subscribeToNotification(){
//   //   this.webSocket.fromEvent("/user").subscribe((data: any) => {
//   //     console.log('Message received:', data);
//   //     // Handle the received message here
//   //   });
  
//   // }
 
//   // this method is used to get response from server
//   receiveStatus() {
//   //  return this.webSocket.fromEvent('/get-response');
//   }
 
//   // this method is used to end web socket connection
//   disconnectSocket() {
//   //  this.webSocket.disconnect();
//   }
 
// }
