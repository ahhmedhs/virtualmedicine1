const express = require("express")
const http = require("http")
const app = express()
const cors = require('cors');
const {Server} = require('socket.io');
app.use(cors());

const server = http.createServer(app)
const io =  new Server(server, {
	cors: {
		origin: ["http://18.141.31.199:3000", "http://18.141.31.199:3001", "http://18.141.31.199:8080", "http://18.141.31.199:8080"],
		methods: [ "GET", "POST" ]
	}
})


io.on("connection", (socket) => {
  // console.log(socket);
	socket.emit("patient", socket.id)

  // socket.on("createRoom", (data)=>{
  //   console.log(data);
  //   socket.join(data);
  //   console.log(`User with id: ${socket.id}, Room Id = ${data}`);
  // });

	socket.on("disconnect", () => {
		socket.broadcast.emit("callEnded")
	})

	socket.on("callUser", (data) => {
    console.log(data);
    // socket.join(data.from);
		io.to(data.userToCall).emit("callUser", { signal: data.signalData, from: data.from, name: data.name })
	})

	socket.on("answerCall", (data) => {
    // socket.join(data.to);
    console.log(data);
		io.to(data.to).emit("callAccepted", data.signal)
	})
})

server.listen(5000, () => console.log("server is running on port 5000"))