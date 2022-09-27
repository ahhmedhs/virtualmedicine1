const express = require('express');
const app = express();
const http = require('http');
const cors = require('cors');
const { Server } = require('socket.io')

app.use(cors());

const server = http.createServer(app);

const io = new Server(server, {
  cors: {
    origin: ["http://18.141.31.199:3001", "http://18.141.31.199:3000", "http://18.141.31.199:8080", "http://18.141.31.199:8080"],
    methods: ["GET", "POST"]
  }
})

io.on("connection", (socket) => {
  console.log(socket.id);

  socket.on("chat", (data)=>{
    console.log(data);
    socket.join(data);
    console.log(`User with id: ${socket.id}, Room Id = ${data}`);
  });

  socket.on("send_message", (data) => {
    console.log(data);
    socket.to(data.room).emit("recive_message", data);
  });

  socket.on("disconnect", () => {
    console.log("User Disconnected", socket.id);
  });
})

server.listen(3030, ()=> {
  console.log('Chat Server running in Port 3030')
});